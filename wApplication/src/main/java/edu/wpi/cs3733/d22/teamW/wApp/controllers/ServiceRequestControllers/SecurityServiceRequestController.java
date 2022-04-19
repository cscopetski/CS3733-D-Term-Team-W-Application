package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;

public class SecurityServiceRequestController extends LoadableController {

  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();

  // boolean emergencyLevel = false;
  int emergency = 0;

  @FXML EmergencyButton emergencyB;
  @FXML ChoiceBox threatChoice;
  @FXML ChoiceBox employeeBox;
  @FXML AutoCompleteInput locationBox;

  // ArrayList<String> threatLevels = new ArrayList<>(Arrays.asList("Wong", "Red", "Yellow",
  // "Blue"));

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Security;
  }

  @Override
  public void onLoad() throws SQLException {
    threatChoice.setItems(FXCollections.observableArrayList(threatLevels.values()));
    employeeBox.setItems(FXCollections.observableArrayList(getEmployeeNames()));
    locationBox.loadValues(getLocations());
  }

  @Override
  public void onUnload() {
    clearFields();
  }

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    if (checkEmptyFields()) emptyFields.show();
    else {
      confirm.showAndWait();
      if (confirm.getResult() == ButtonType.OK) {
        pushSecurityRequestToDB();
        clearFields();
      }
    }
  }

  private void pushSecurityRequestToDB() throws SQLException {
    ArrayList<String> ssrFields = new ArrayList<String>();
    ssrFields.add(locationToNodeID(locationBox.getSelectionModel().getSelectedItem().toString()));
    ssrFields.add(getEmployeeID(employeeBox.getSelectionModel().getSelectedItem().toString()));
    if (emergencyB.getValue()) {
      ssrFields.add("1");
    } else {
      ssrFields.add("0");
    }
    ssrFields.add(
        Integer.toString(
            threatLevels
                .valueOf(threatChoice.getSelectionModel().getSelectedItem().toString())
                .ordinal())); // This is gross
    try {
      RequestFactory.getRequestFactory().getRequest(RequestType.SecurityService, ssrFields, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void cancelButton(ActionEvent event) {
    clearFields();
  }

  private void clearFields() {
    threatChoice.getSelectionModel().clearSelection();
    employeeBox.getSelectionModel().clearSelection();
    locationBox.getSelectionModel().clearSelection();
  }

  private boolean checkEmptyFields() {
    return threatChoice.getSelectionModel().isEmpty()
        || locationBox.getSelectionModel().isEmpty()
        || employeeBox.getSelectionModel().isEmpty();
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
      if (e.getEmployeeID() != -1 && e.getType().equals(EmployeeType.Sanitation)) {
        String empName = String.format("%s, %s", e.getLastName(), e.getFirstName());
        name.add(empName);
      }
    }
    return name;
  }

  private enum threatLevels {
    Green,
    Blue,
    Yellow,
    Red,
    Wong
  }
}
