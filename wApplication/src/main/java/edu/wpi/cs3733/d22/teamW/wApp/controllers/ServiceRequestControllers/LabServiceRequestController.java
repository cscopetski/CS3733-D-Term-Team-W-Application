package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.HospitalMap;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.LabServiceRequestType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class LabServiceRequestController implements Initializable {

  @FXML public TextField patientFirstName;
  @FXML public TextField patientLastName;
  // Combo Boxes:
  @FXML AutoCompleteInput employeeIDCBox;
  @FXML AutoCompleteInput locationCBox;
  @FXML CheckBox bloodBox;
  @FXML CheckBox urineBox;
  @FXML CheckBox mriBox;
  @FXML CheckBox xRayBox;
  @FXML CheckBox catBox;
  @FXML HospitalMap map;

  int emergency = 0;
  @FXML EmergencyButton emergencyButton;

  // Alerts:
  Alert emptyFields = new EmptyAlert();

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    createRequest();
    clearFields();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    onLoad();
    map.attachOnSelectionMade(l -> locationCBox.getSelectionModel().select(l.getLongName()));
  }

  public void onLoad() {
    locationCBox.loadValues(getLocations());
    employeeIDCBox.loadValues(getEmployeeNames());
  }

  // ----------------------- HELPERS -------------------

  // TRUE if ALL filled --> OR at least one Check Box
  private boolean fieldsFull() {
    boolean result =
        (!(employeeIDCBox.getSelectionModel().isEmpty())
            && !(locationCBox.getSelectionModel().isEmpty())
            && (catBox.isSelected()
                || mriBox.isSelected()
                || xRayBox.isSelected()
                || bloodBox.isSelected()
                || urineBox.isSelected()));

    return result;
  }

  private void createRequest() {
    if (fieldsFull()) {
      try {
        pushDataToDB();
      } catch (SQLException e) {
        System.out.println("Unable to push Medicine Delivery request to DB");
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      emptyFields.show();
    }
  }

  private void pushDataToDB() throws Exception {
    ArrayList<String> fields = new ArrayList<String>();
    fields.add(patientLastName.getText());
    fields.add(patientFirstName.getText());
    fields.add(""); // placeholder for remaining code: check if statements below
    fields.add(locationToNodeID(locationCBox.getSelectionModel().getSelectedItem().toString()));
    fields.add(getEmployeeID(employeeIDCBox.getSelectionModel().getSelectedItem().toString()));
    if (emergencyButton.getValue()) {
      emergency = 1;
    } else {
      emergency = 0;
    }
    fields.add(String.valueOf(emergency));
    // Check Boxes:
    if (bloodBox.isSelected()) {

      fields.set(2, LabServiceRequestType.BloodSamples.getString());

      for (String e : fields) {
        System.out.println(e);
      }
      RequestFactory.getRequestFactory().getRequest(RequestType.LabServiceRequest, fields, false);
    }
    if (urineBox.isSelected()) {
      fields.set(2, LabServiceRequestType.UrineSamples.getString());
      for (String e : fields) {
        System.out.println(e);
      }
      RequestFactory.getRequestFactory().getRequest(RequestType.LabServiceRequest, fields, false);
    }
    if (mriBox.isSelected()) {
      fields.set(2, LabServiceRequestType.MRIs.getString());
      for (String e : fields) {
        System.out.println(e);
      }
      RequestFactory.getRequestFactory().getRequest(RequestType.LabServiceRequest, fields, false);
    }
    if (xRayBox.isSelected()) {
      fields.set(2, LabServiceRequestType.XRays.getString());
      for (String e : fields) {
        System.out.println(e);
      }
      RequestFactory.getRequestFactory().getRequest(RequestType.LabServiceRequest, fields, false);
    }
    if (catBox.isSelected()) {
      fields.set(2, LabServiceRequestType.CATScans.getString());
      for (String e : fields) {
        System.out.println(e);
      }
      RequestFactory.getRequestFactory().getRequest(RequestType.LabServiceRequest, fields, false);
    }
  }

  private void clearFields() {
    employeeIDCBox.getSelectionModel().clearSelection();
    locationCBox.getSelectionModel().clearSelection();
    bloodBox.setSelected(false);
    urineBox.setSelected(false);
    mriBox.setSelected(false);
    xRayBox.setSelected(false);
    catBox.setSelected(false);
    emergencyButton.setValue(false);
    patientFirstName.clear();
    patientLastName.clear();
  }

  public String locationToNodeID(String target) {
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
      if (!l.getNodeID().equals("NONE")) {
        locations.add(l.getLongName());
      }
    }
    return locations;
  }

  private ArrayList<String> getEmployeeNames() {
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Employee> employees = null;
    ArrayList<EmployeeType> types = new ArrayList<>();
    types.add(EmployeeType.Nurse);
    types.add(EmployeeType.Doctor);
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
