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
import edu.wpi.cs3733.d22.teamW.wDB.enums.ThreatLevels;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

public class SecurityServiceRequestController implements Initializable {

  // Fields:
  @FXML AutoCompleteInput locationSelection;
  @FXML AutoCompleteInput threatLevel;
  @FXML AutoCompleteInput employee;
  @FXML EmergencyButton emergencyButton;
  @FXML
  //Pane map;
  HospitalMap map = HospitalMap.getInstance();
  @FXML
  VBox BOX;

  // Alerts:
  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();

  public SecurityServiceRequestController() throws NonExistingMedEquip, SQLException {
  }

  @Override
  public void initialize(URL location, ResourceBundle rb) {
    locationSelection.loadValues(getLocations());
    threatLevel.loadValues(
        (ArrayList<String>)
            Arrays.stream(ThreatLevels.values())
                .map(ThreatLevels::toString)
                .collect(Collectors.toList()));
    employee.loadValues(getEmployeeNames());
    BOX.getChildren().add(map);
    map.attachOnSelectionMade(l -> locationSelection.getSelectionModel().select(l.getLongName()));
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
    ssrFields.add(locationToNodeID(locationSelection.getSelectionModel().getSelectedItem()));
    ssrFields.add(getEmployeeID(employee.getValue())); // replace with employee id
    if (emergencyButton.getValue()) {
      ssrFields.add("1");
    } else {
      ssrFields.add("0");
    }

    ssrFields.add(
        ThreatLevels.valueOf(threatLevel.getSelectionModel().getSelectedItem())
            .getValue()
            .toString());
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
    threatLevel.getSelectionModel().clearSelection();
    employee.getSelectionModel().clearSelection();
    locationSelection.getSelectionModel().clearSelection();
  }

  private boolean checkEmptyFields() {
    return threatLevel.getSelectionModel().isEmpty()
        || locationSelection.getSelectionModel().isEmpty()
        || employee.getSelectionModel().isEmpty();
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
    types.add(EmployeeType.Security);
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

  public void switchToRequestList(ActionEvent event) throws IOException {
    PageManager.getInstance().loadPage(PageManager.Pages.RequestList);
  }
}
