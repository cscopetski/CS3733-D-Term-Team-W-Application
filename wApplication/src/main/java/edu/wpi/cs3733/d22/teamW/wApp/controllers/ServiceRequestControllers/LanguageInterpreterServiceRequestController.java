package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.HospitalMap;
import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LanguageInterpreterManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.enums.Languages;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LanguageInterpreterServiceRequestController implements Initializable {

  //Fields:
  @FXML AutoCompleteInput locationSelection;
  @FXML AutoCompleteInput employeeSelection;
  @FXML AutoCompleteInput languageSelection;
  @FXML EmergencyButton emergencyButton;
  @FXML Label successLabel;
  //Pane map;
  HospitalMap map = HospitalMap.getInstance();
  @FXML
  VBox BOX;

  //Alerts:
  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();
  private LanguageInterpreterManager languageInterpreterManager =
      LanguageInterpreterManager.getLanguageInterpreterManager();

  private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

  public LanguageInterpreterServiceRequestController() throws NonExistingMedEquip, SQLException {
  }

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    if (!emptyFields()) {
      confirm.showAndWait();
      if (confirm.getResult() == ButtonType.OK) {
        pushLanguageRequestToDB();
        clearFields();
        successLabel.setVisible(true);
        fadeOut.playFromStart();
      }
    } else {
      emptyFields.show();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      onLoad();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    map.attachOnSelectionMade(l -> locationSelection.getSelectionModel().select(l.getLongName()));
  }

  public void onLoad() throws SQLException {
    BOX.getChildren().add(map);
    fadeOut.setNode(successLabel);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);
    fadeOut.setCycleCount(1);
    fadeOut.setAutoReverse(false);
    employeeSelection.setDisable(true);
    locationSelection.loadValues(getLocations());
    languageSelection.loadValues(getLanguageTypeList());
    languageSelection.setOnAction(
        (event) -> {
          if (!languageSelection.getSelectionModel().isEmpty()) {
            String language = languageSelection.getSelectionModel().getSelectedItem().toString();
            employeeSelection.setDisable(false);
            try {
              employeeSelection.setItems(
                  FXCollections.observableArrayList(getEmployeeNames(language)));
            } catch (SQLException e) {
              e.printStackTrace();
              System.out.println("Lmao front end ez");
            }
          }
        });
  }

  private boolean emptyFields() {
    return employeeSelection.getSelectionModel().isEmpty()
        || locationSelection.getSelectionModel().isEmpty()
        || languageSelection.getSelectionModel().isEmpty();
  }

  private void pushLanguageRequestToDB() throws SQLException {
    ArrayList<String> srFields = new ArrayList<String>();
    srFields.add(languageSelection.getSelectionModel().getSelectedItem().toString());
    srFields.add(
        locationToNodeID(locationSelection.getSelectionModel().getSelectedItem().toString()));
    srFields.add(getEmployeeID(employeeSelection.getSelectionModel().getSelectedItem().toString()));
    if (emergencyButton.getValue()) {
      srFields.add("1");
    } else {
      srFields.add("0");
    }
    try {
      RequestFactory.getRequestFactory().getRequest(RequestType.LanguageRequest, srFields, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void clearFields() {
    if (!locationSelection.getSelectionModel().isEmpty()) {
      locationSelection.getSelectionModel().clearSelection();
    }
    if (!employeeSelection.getSelectionModel().isEmpty()) {
      employeeSelection.getSelectionModel().clearSelection();
    }
    if (!languageSelection.getSelectionModel().isEmpty()) {
      languageSelection.getSelectionModel().clearSelection();
    }

    emergencyButton.setValue(false);
    employeeSelection.setDisable(true);
  }

  private String getEmployeeID(String name) throws SQLException {
    name = name.trim();
    Integer employeeID = null;
    String employeeLastName;
    String employeeFirstName;
    Integer commaIndex = name.indexOf(',');
    employeeLastName = name.substring(0, commaIndex);
    employeeFirstName = name.substring(commaIndex + 2);
    employeeID = EmployeeManager.getEmployeeManager().getEmployeeFromName(employeeLastName,employeeFirstName).getEmployeeID();

    return String.format("%d", employeeID);
  }

  private String locationToNodeID(String target) {
    String nodeID = "FAIL";

    ArrayList<Location> locationsRaw = null;
    try {
      locationsRaw = LocationManager.getLocationManager().getAllLocations();
    } catch (SQLException e) {
      System.out.println("Failed to unearth locations from database");
      e.printStackTrace();
    }
    for (Location l : locationsRaw) {
      if (l.getLongName().equals(target)) {
        nodeID = l.getNodeID();
      }
    }
    return nodeID;
  }

  private ArrayList<String> getEmployeeNames(String language) throws SQLException {
    ArrayList<Integer> ids = languageInterpreterManager.getEligibleEmployees(language);
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();

    for (Integer id : ids) {
      try {
        employees.add(EmployeeManager.getEmployeeManager().getEmployee(id));
      } catch (SQLException e) {
        System.out.println("Failed to unearth employees from database");
        e.printStackTrace();
      }
    }

    for (Employee e : employees) {
      if (e.getEmployeeID() != -1) {
        String empName = String.format("%s, %s", e.getLastName(), e.getFirstName());
        names.add(empName);
      }
    }
    return names;
  }

  private ArrayList<String> getLocations() {
    ArrayList<String> locations = new ArrayList<>();
    ArrayList<Location> locationsRaw = null;
    ArrayList<Integer> removeIndexes = new ArrayList<>();
    try {
      locationsRaw = LocationManager.getLocationManager().getAllLocations();
    } catch (SQLException e) {
      System.out.println("Failed to unearth locations from database");
      e.printStackTrace();
    }

    for (Location l : locationsRaw) {
      if (l.getNodeType().equals("NONE")) {
      } else locations.add(l.getLongName());
    }
    return locations;
  }

  private ArrayList<String> getLanguageTypeList() {
    ArrayList<String> languages = new ArrayList<>();
    for (Languages l : Languages.values()) {
      languages.add(l.getString());
    }
    return languages;
  }

  public void switchToRequestList() throws IOException {
    PageManager.getInstance().loadPage(PageManager.Pages.RequestList);
  }
}
