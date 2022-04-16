package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.MedicalEquipmentSR;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MedicineDeliveryServiceRequestController extends LoadableController {
  // Buttons:
  @FXML Button submitButton;
  @FXML Button cancelButton;
  int emergency = 0;
  @FXML EmergencyButton emergencyB;

  // TextFields:
  @FXML TextField quantityField;
  @FXML TextField itemCodeField;

  // ComboBoxes:
  @FXML ComboBox medNameCBox;
  @FXML ComboBox locationCBox;
  @FXML ComboBox timePrefCBox;
  @FXML ComboBox employeeIDCBox;

  // Tables:
  @FXML private TableView<MedicalEquipmentSR> table;
  private ArrayList<MedicalEquipmentSR> sr = new ArrayList<>();

  // Alerts:
  Alert emptyFields = new EmptyAlert();

  // Helper Fcn stuff -> NOT WORKING RIGHT NOW:
  // private Control[] fields = new Control[] {quantityField, itemCodeField, medNameCBox,
  // locationCBox, requesterCBox};
  // private ServiceRequestHelper helper = new ServiceRequestHelper(fields);

  // ComboBox Lists:
  ObservableList<String> meds = FXCollections.observableArrayList("Advil", "Tylenol");
  ObservableList<String> times =
      FXCollections.observableArrayList(
          "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
          "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00",
          "18:30");

  // -----------------------------METHOD CODE STARTS HERE-----------------------------

  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.MedicineDelivery;
  }

  public void onLoad() {
    // populateTable();
    medNameCBox.setItems(meds);
    locationCBox.setItems(FXCollections.observableArrayList(getLocations()));
    timePrefCBox.setItems(times);
    employeeIDCBox.setItems(FXCollections.observableArrayList(getEmployeeIDs()));
  }

  public void onUnload() {
    clearFields();
  }

  public void submitButton() throws SQLException {
    createRequest();
    clearFields();
  }

  public void clearFields() { // no specific cancelButton function as this method is all it does
    quantityField.clear();
    itemCodeField.clear();
    medNameCBox.getSelectionModel().clearSelection();
    locationCBox.getSelectionModel().clearSelection();
    timePrefCBox.getSelectionModel().clearSelection();
    employeeIDCBox.getSelectionModel().clearSelection();
  }

  // -------------------------RETRIEVAL FROM DB METHODS------------------------------

  private ArrayList<Integer> getEmployeeIDs() {
    ArrayList<Integer> ids = new ArrayList<>();
    ArrayList<Employee> employees = null;
    try {
      employees = EmployeeManager.getEmployeeManager().getAllEmployees();
    } catch (SQLException e) {
      System.out.println("Failed to unearth employees from database");
      e.printStackTrace();
    }
    for (Employee e : employees) {
      ids.add(e.getEmployeeID());
    }
    return ids;
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

  // ---------------------------------------------------------------------------

  private void pushDataToDB() throws Exception {
    ArrayList<String> fields = new ArrayList<>();

    fields.add(medNameCBox.getSelectionModel().getSelectedItem().toString());
    fields.add(locationToNodeID(locationCBox.getSelectionModel().getSelectedItem().toString()));
    fields.add(employeeIDCBox.getSelectionModel().getSelectedItem().toString());
    if (emergencyB.getValue()) {
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

  private void populateTable() {
    ArrayList<Request> requests = null;
    try {
      requests = RequestFacade.getRequestFacade().getAllRequests();
    } catch (SQLException e) {
      System.out.println("Failed to unearth request form database");
      e.printStackTrace();
    } catch (NonExistingMedEquip e) {
      e.printStackTrace();
    }
    for (int i = 0; i < requests.size(); i++) {
      Request r = requests.get(i);
      if (MedEquipRequest.class.equals(r.getClass())) {
        MedEquipRequest mer = (MedEquipRequest) r;
        sr.add(new MedicalEquipmentSR(mer));
      }
    }

    table.getItems().clear();
    table.getItems().addAll(sr);
  }

  public void createRequest() {
    if (fieldsFull()) {
      populateTable();

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

  // TRUE if ALL filled
  private boolean fieldsFull() {
    boolean result =
        !(quantityField.getText().isEmpty()
            && itemCodeField.getText().isEmpty()
            && locationCBox.getSelectionModel().isEmpty()
            && timePrefCBox.getSelectionModel().isEmpty()
            && employeeIDCBox.getSelectionModel().isEmpty());

    return result;
  }
}
