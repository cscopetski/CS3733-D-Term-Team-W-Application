package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ComputerServiceRequestController extends LoadableController {

  @FXML ComboBox locationComboBox;
  @FXML ComboBox employee;
  @FXML EmergencyButton emergencyButton;
  @FXML Label successLabel;
  Alert confirm = new ConfirmAlert();

  Alert emptyFields = new EmptyAlert();
  private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    if (!emptyFields()) {
      confirm.showAndWait();
      if (confirm.getResult() == ButtonType.OK) {
        pushComputerServiceRequestToDB();
        clearFields();
        successLabel.setVisible(true);
        fadeOut.playFromStart();
      }
    } else {
      emptyFields.show();
    }
  }

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.ComputerService;
  }

  @Override
  public void onLoad() throws SQLException {
    fadeOut.setNode(successLabel);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);
    fadeOut.setCycleCount(1);
    fadeOut.setAutoReverse(false);
    locationComboBox.setItems(FXCollections.observableArrayList(getLocations()));
    employee.setItems(FXCollections.observableArrayList(getEmployeeNames()));
  }

  @Override
  public void onUnload() {
    clearFields();
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
    try {
      employees = EmployeeManager.getEmployeeManager().getAllEmployees();
    } catch (SQLException e) {
      System.out.println("Failed to unearth employees from database");
      e.printStackTrace();
    }
    for (Employee e : employees) {
      if (e.getEmployeeID() != -1 && e.getType().equals(EmployeeType.Technician)) {
        String empName = String.format("%s, %s", e.getLastName(), e.getFirstName());
        name.add(empName);
      }
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
    try {
      locationsRaw = LocationManager.getLocationManager().getAllLocations();
    } catch (SQLException e) {
      System.out.println("Failed to unearth locations from database");
      e.printStackTrace();
    }
    for (Location l : locationsRaw) {
      locations.add(l.getLongName());
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

    for (Employee e : EmployeeManager.getEmployeeManager().getAllEmployees()) {
      if (e.getLastName().equals(employeeLastName) && e.getFirstName().equals(employeeFirstName)) {
        employeeID = e.getEmployeeID();
      }
    }

    return String.format("%d", employeeID);
  }
}
