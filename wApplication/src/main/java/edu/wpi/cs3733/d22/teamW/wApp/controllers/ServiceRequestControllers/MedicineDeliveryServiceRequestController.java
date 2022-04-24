package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedicineType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.Units;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MedicineDeliveryServiceRequestController extends LoadableController {
  // Buttons:
  @FXML Button submitButton;
  @FXML Button cancelButton;
  int emergency = 0;
  @FXML EmergencyButton emergencyButton;

  // TextFields:
  @FXML TextField quantityField;

  // ComboBoxes:
  @FXML AutoCompleteInput unitCBox;
  @FXML AutoCompleteInput medNameCBox;
  @FXML AutoCompleteInput locationCBox;
  @FXML AutoCompleteInput employee;

  // Alerts:
  Alert emptyFields = new EmptyAlert();
  Alert confirm = new ConfirmAlert();

  // Helper Fcn stuff -> NOT WORKING RIGHT NOW:
  // private Control[] fields = new Control[] {quantityField, itemCodeField, medNameCBox,
  // locationCBox, requesterCBox};
  // private ServiceRequestHelper helper = new ServiceRequestHelper(fields);

  // -----------------------------METHOD CODE STARTS HERE-----------------------------

  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.MedicineDelivery;
  }

  public void onLoad() {
    medNameCBox.loadValues(getListOfMedicine());
    locationCBox.loadValues(getLocations());
    unitCBox.loadValues(getListOfUnits());
    employee.loadValues(getEmployeeNames());
  }

  public void onUnload() {
    clearFields();
  }

  public void submitButton() {
    createRequest();
    clearFields();
  }

  public void clearFields() { // no specific cancelButton function as this method is all it does
    quantityField.clear();
    unitCBox.getSelectionModel().clearSelection();
    medNameCBox.getSelectionModel().clearSelection();
    locationCBox.getSelectionModel().clearSelection();
    employee.getSelectionModel().clearSelection();
  }

  // -------------------------RETRIEVAL FROM DB METHODS------------------------------

  private ArrayList<String> getEmployeeNames() {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Employee> employees = null;
    try {
      employees = EmployeeManager.getEmployeeManager().getAllEmployees();
    } catch (SQLException e) {
      System.out.println("Failed to unearth employees from database");
      e.printStackTrace();
    }
    for (Employee e : employees) {
      if (e.getEmployeeID() != -1 && (e.getType().equals(EmployeeType.Staff))
          || e.getType().equals(EmployeeType.Nurse)
          || e.getType().equals(EmployeeType.Doctor)) {
        names.add(String.format("%s, %s", e.getLastName(), e.getFirstName()));
      }
    }
    return names;
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

  private ArrayList<String> getListOfMedicine() {
    ArrayList<String> medicineList = new ArrayList<>();
    for (MedicineType m : MedicineType.values()) {
      medicineList.add(m.getString());
    }
    return medicineList;
  }

  private ArrayList<String> getListOfUnits() {
    ArrayList<String> unitList = new ArrayList<>();
    for (Units u : Units.values()) {
      unitList.add(u.getUnits());
    }
    return unitList;
  }
  // ---------------------------------------------------------------------------

  private void pushDataToDB() throws Exception {
    ArrayList<String> fields = new ArrayList<>();

    fields.add(medNameCBox.getSelectionModel().getSelectedItem().toString());
    fields.add(quantityField.getText());
    fields.add(unitCBox.getSelectionModel().getSelectedItem().toString());
    fields.add(locationToNodeID(locationCBox.getSelectionModel().getSelectedItem().toString()));
    fields.add(getEmployeeID(employee.getSelectionModel().getSelectedItem().toString()));
    if (emergencyButton.getValue()) {
      emergency = 1;
    } else {
      emergency = 0;
    }
    fields.add(String.valueOf(emergency));
    for (String e : fields) {
      System.out.println(e);
    }

    RequestFactory.getRequestFactory().getRequest(RequestType.MedicineDelivery, fields, false);
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

  public void createRequest() {
    if (fieldsFull()) {
      confirm.showAndWait();

      try {
        pushDataToDB();
      } catch (SQLException e) {
        System.out.println("Unable to push Medicine Delivery request to DB");
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      emptyFields.showAndWait();
    }
  }

  // TRUE if ALL filled
  private boolean fieldsFull() {
    boolean result =
        !(quantityField.getText().isEmpty()
            && unitCBox.getSelectionModel().isEmpty()
            && locationCBox.getSelectionModel().isEmpty()
            && employee.getSelectionModel().isEmpty());

    return result;
  }

  public void switchToRequestList(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.RequestList);
  }
}
