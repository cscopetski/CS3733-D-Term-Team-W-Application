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
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GiftDeliveryRequestController extends LoadableController {
  @FXML public VBox patientFieldsBox;
  @FXML TextField recipientLastName;
  @FXML TextField recipientFirstName;
  @FXML ComboBox<String> locationComboBox;
  @FXML ComboBox<String> employeeIDComboBox;
  @FXML EmergencyButton emergencyButton;
  @FXML Label successLabel;
  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();
  private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    if (!emptyFields()) {
      confirm.showAndWait();
      if (confirm.getResult() == ButtonType.OK) {
        pushSanitationServiceRequestToDB();
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
    return SceneManager.Scenes.GiftDelivery;
  }

  @Override
  public void onLoad() throws SQLException {
    fadeOut.setNode(successLabel);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);
    fadeOut.setCycleCount(1);
    fadeOut.setAutoReverse(false);
    locationComboBox.setItems(FXCollections.observableArrayList(getLocations()));
    employeeIDComboBox.setItems(FXCollections.observableArrayList(getEmployeeNames()));
  }

  @Override
  public void onUnload() {
    clearFields();
  }

  private boolean emptyFields() {
    return employeeIDComboBox.getSelectionModel().isEmpty()
        || locationComboBox.getSelectionModel().isEmpty()
        || recipientFirstName.getText().isEmpty()
        || recipientLastName.getText().isEmpty();
  }

  private void pushSanitationServiceRequestToDB() throws SQLException {
    ArrayList<String> srFields = new ArrayList<String>();
    srFields.add(recipientFirstName.getText());
    srFields.add(recipientLastName.getText());
    srFields.add(
        locationToNodeID(locationComboBox.getSelectionModel().getSelectedItem().toString()));
    srFields.add(
        getEmployeeID(employeeIDComboBox.getSelectionModel().getSelectedItem().toString()));
    if (emergencyButton.getValue()) {
      srFields.add("1");
    } else {
      srFields.add("0");
    }
    try {
      RequestFactory.getRequestFactory().getRequest(RequestType.GiftDelivery, srFields, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void clearFields() {
    locationComboBox.getSelectionModel().clearSelection();
    employeeIDComboBox.getSelectionModel().clearSelection();
    recipientFirstName.clear();
    recipientLastName.clear();
    emergencyButton.setValue(false);
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
    try {
      employees = EmployeeManager.getEmployeeManager().getAllEmployees();
    } catch (SQLException e) {
      System.out.println("Failed to unearth employees from database");
      e.printStackTrace();
    }
    for (Employee e : employees) {
      if (e.getEmployeeID() != -1) {
        String empName = String.format("%s, %s", e.getLastName(), e.getFirstName());
        name.add(empName);
      }
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
}
