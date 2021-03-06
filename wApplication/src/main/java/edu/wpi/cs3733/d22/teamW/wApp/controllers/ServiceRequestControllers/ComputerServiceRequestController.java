package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.HospitalMap;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ComputerServiceRequestController implements Initializable {

  //Fields:
  @FXML AutoCompleteInput locationComboBox;
  @FXML AutoCompleteInput employee;
  @FXML EmergencyButton emergencyButton;
  @FXML Label successLabel;
  @FXML
  //Pane map;
  HospitalMap map = HospitalMap.getInstance();
@FXML
  VBox BOX;
  //Alerts:
  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();

  private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

  public ComputerServiceRequestController() throws NonExistingMedEquip, SQLException {
  }

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    if (!emptyFields()) {
      confirm.showAndWait();
      if (confirm.getResult() == ButtonType.OK) {
        try {
          pushComputerServiceRequestToDB();
        } catch (Exception e) {
          System.out.println("Failed to push to database");
        }
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
    onLoad();
    map.attachOnSelectionMade(l -> locationComboBox.getSelectionModel().select(l.getLongName()));
  }

  public void onLoad() {
    BOX.getChildren().add(map);
    fadeOut.setNode(successLabel);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);
    fadeOut.setCycleCount(1);
    fadeOut.setAutoReverse(false);
    locationComboBox.setItems(FXCollections.observableArrayList(getLocations()));
    employee.setItems(FXCollections.observableArrayList(getEmployeeNames()));
  }

  private boolean emptyFields() {
    return employee.getSelectionModel().isEmpty() || locationComboBox.getSelectionModel().isEmpty();
  }

  private void pushComputerServiceRequestToDB() throws SQLException {
    ArrayList<String> csrFields = new ArrayList<String>();
    csrFields.add(
        locationToNodeID(locationComboBox.getSelectionModel().getSelectedItem().toString()));
    csrFields.add(getEmployeeID(employee.getSelectionModel().getSelectedItem().toString()));
    if (emergencyButton.getValue()) {
      csrFields.add("1");
    } else {
      csrFields.add("0");
    }
    try {
      RequestFactory.getRequestFactory()
          .getRequest(RequestType.ComputerServiceRequest, csrFields, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void clearFields() {
    locationComboBox.getSelectionModel().clearSelection();
    employee.getSelectionModel().clearSelection();
    emergencyButton.setValue(false);
  }

  private ArrayList<String> getEmployeeNames() {
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Employee> employees = null;
    ArrayList<EmployeeType> types = new ArrayList<>();
    types.add(EmployeeType.Technician);
    types.add(EmployeeType.Staff);
    try {
      employees = EmployeeManager.getEmployeeManager().getEmployeeListByType(types);
    } catch (SQLException e) {
      System.out.println("Failed to unearth employees from database");
      e.printStackTrace();
    }
    for (Employee e : employees) {
        String empName = String.format("%s, %s", e.getLastName(), e.getFirstName());
        name.add(empName);
    }
    return name;
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

  public void switchToRequestList(ActionEvent event) throws IOException {
    PageManager.getInstance().loadPage(PageManager.Pages.RequestList);
  }
}
