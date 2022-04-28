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
import edu.wpi.cs3733.d22.teamW.wDB.enums.MealType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MealDeliveryServiceRequestController implements Initializable {

  private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

  @FXML AutoCompleteInput locationComboBox;
  @FXML AutoCompleteInput employeeNameComboBox;
  @FXML EmergencyButton emergencyButton;
  @FXML Button menuButton1;
  @FXML Button menuButton2;
  @FXML Button menuButton3;
  @FXML Label menuLabel1;
  @FXML Label menuLabel2;
  @FXML Label menuLabel3;
  @FXML Label successLabel;
  @FXML TextField patientFirst;
  @FXML TextField patientLast;
  @FXML
  HospitalMap map = HospitalMap.getInstance();
  @FXML
  VBox BOX;

  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();

  private int currentSelection = 0;

  public MealDeliveryServiceRequestController() throws NonExistingMedEquip, SQLException {
  }

  public void menuSelection0() {
    currentSelection = 0;
    menuButton1.setStyle("-fx-background-color: transparent;");
    menuButton2.setStyle("-fx-background-color: transparent;");
    menuButton3.setStyle("-fx-background-color: transparent;");
    menuLabel1.setStyle("-fx-text-fill: black");
    menuLabel2.setStyle("-fx-text-fill: black");
    menuLabel3.setStyle("-fx-text-fill: black");
  }

  public void menuSelection1() {
    currentSelection = 1;
    menuButton1.setStyle("-fx-background-color: #013895;");
    menuButton2.setStyle("-fx-background-color: transparent;");
    menuButton3.setStyle("-fx-background-color: transparent;");
    menuLabel1.setStyle("-fx-text-fill: white");
    menuLabel2.setStyle("-fx-text-fill: black");
    menuLabel3.setStyle("-fx-text-fill: black");
  }

  public void menuSelection2() {
    currentSelection = 2;
    menuButton1.setStyle("-fx-background-color: transparent");
    menuButton2.setStyle("-fx-background-color: #013895;");
    menuButton3.setStyle("-fx-background-color: transparent;");
    menuLabel1.setStyle("-fx-text-fill: black");
    menuLabel2.setStyle("-fx-text-fill: white");
    menuLabel3.setStyle("-fx-text-fill: black");
  }

  public void menuSelection3() {
    currentSelection = 3;
    menuButton1.setStyle("-fx-background-color: transparent");
    menuButton2.setStyle("-fx-background-color: transparent;");
    menuButton3.setStyle("-fx-background-color: #013895;");
    menuLabel1.setStyle("-fx-text-fill: black");
    menuLabel2.setStyle("-fx-text-fill: black");
    menuLabel3.setStyle("-fx-text-fill: white");
  }

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    if (!emptyFields()) {
      confirm.showAndWait();
      if (confirm.getResult() == ButtonType.OK) {
        pushMealDeliveryToDB();
        clearFields();
        successLabel.setVisible(true);
        fadeOut.playFromStart();
      }
    } else {
      emptyFields.show();
    }
  }

  public void onEnter(ActionEvent actionEvent) throws SQLException {
    submitButton(actionEvent);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      onLoad();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    map.attachOnSelectionMade(l -> locationComboBox.getSelectionModel().select(l.getLongName()));
  }

  public void onLoad() throws SQLException {
    locationComboBox.loadValues(getLocations());
    employeeNameComboBox.loadValues(getEmployeeNames());
    BOX.getChildren().add(map);
  }

  private boolean emptyFields() {
    return employeeNameComboBox.getSelectionModel().isEmpty()
        || locationComboBox.getSelectionModel().isEmpty();
  }

  private void pushMealDeliveryToDB() throws SQLException {
    ArrayList<String> mdFields = new ArrayList<String>();
    mdFields.add(getMealTypeList().get(currentSelection - 1));
    mdFields.add(patientLast.getText());
    mdFields.add(patientFirst.getText());
    mdFields.add(
        locationToNodeID(locationComboBox.getSelectionModel().getSelectedItem().toString()));
    mdFields.add(
        getEmployeeID(employeeNameComboBox.getSelectionModel().getSelectedItem().toString()));
    if (emergencyButton.getValue()) {
      mdFields.add("1");
    } else {
      mdFields.add("0");
    }
    try {
      RequestFactory.getRequestFactory().getRequest(RequestType.MealDelivery, mdFields, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void clearFields() {
    locationComboBox.getSelectionModel().clearSelection();
    employeeNameComboBox.getSelectionModel().clearSelection();
    emergencyButton.setValue(false);
    menuSelection0();
    patientFirst.clear();
    patientLast.clear();
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

  private ArrayList<String> getEmployeeNames() {
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Employee> employees = null;
    ArrayList<EmployeeType> types = new ArrayList<>();
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

  private ArrayList<String> getMealTypeList() {
    ArrayList<String> meal = new ArrayList<>();
    for (MealType s : MealType.values()) {
      meal.add(s.getString());
    }
    return meal;
  }

  public void switchToRequestList() throws IOException {
    PageManager.getInstance().loadPage(PageManager.Pages.RequestList);
  }
}
