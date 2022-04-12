package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.MedicalEquipmentSR;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquipRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class MedicineDeliveryServiceRequestController extends LoadableController {
  // Buttons:
  @FXML Button addButton;
  @FXML Button submitButton;
  @FXML Button cancelButton;
  boolean emergencyLevel = false;
  @FXML Button emergencyB;

  // TextFields:
  @FXML TextField quantityField;
  @FXML TextField itemCodeField;

  // ComboBoxes:
  @FXML ComboBox medNameCBox;
  @FXML ComboBox locationCBox;
  @FXML ComboBox timePrefCBox;
  @FXML ComboBox requesterCBox;

  // Tables:
  @FXML private TableView<MedicalEquipmentSR> table;
  private ArrayList<MedicalEquipmentSR> sr = new ArrayList<>();

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

  public MedicineDeliveryServiceRequestController() throws SQLException {}

  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.MedicineDelivery;
  }

  public void onLoad() {
    // populateTable();
    medNameCBox.setItems(meds);
    locationCBox.setItems(FXCollections.observableArrayList(getLocations()));
    timePrefCBox.setItems(times);
    requesterCBox.setItems(FXCollections.observableArrayList(getEmployeeIDs()));
  }

  public void onUnload() {
    clearFields();
  }

  public void submitButton() throws SQLException {
    createRequest();
    clearFields();
  }

  // NO LONGER WORK, NOT SURE WHY NGL
  public void createRequest() {
    // pushDataToDB();
    if (fieldsFull()) {
      populateTable();
    }
  }

  public void clearFields() { // no specific cancelButton function as this method is all it does
    quantityField.clear();
    itemCodeField.clear();
    medNameCBox.getSelectionModel().clearSelection();
    locationCBox.getSelectionModel().clearSelection();
    timePrefCBox.getSelectionModel().clearSelection();
    requesterCBox.getSelectionModel().clearSelection();
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

  //  !!!!NOT SURE IF IN CORRECT ORDER FOR DB!!!!
  private void pushDataToDB() throws SQLException {
    ArrayList<String> fields = new ArrayList<>();
    fields.add(quantityField.getText());
    fields.add(itemCodeField.getText());
    fields.add(medNameCBox.getSelectionModel().getSelectedItem().toString());
    fields.add(locationCBox.getSelectionModel().getSelectedItem().toString());
    fields.add(timePrefCBox.getSelectionModel().getSelectedItem().toString());
    fields.add(requesterCBox.getSelectionModel().getSelectedItem().toString());
    RequestFactory.getRequestFactory().getRequest(RequestType.MedicineDelivery, fields);
  }

  private void populateTable() {
    ArrayList<Request> requests = null;
    try {
      requests = RequestFactory.getRequestFactory().getAllRequests();
    } catch (SQLException e) {
      System.out.println("Failed to unearth request form database");
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

  //TRUE: if
  private boolean fieldsFull() {
    boolean result = !(quantityField.getText().isEmpty()
            && itemCodeField.getText().isEmpty()
            && locationCBox.getSelectionModel().isEmpty()
            && timePrefCBox.getSelectionModel().isEmpty()
            && requesterCBox.getSelectionModel().isEmpty());

    return result;
  }

  public void emergencyClicked(MouseEvent mouseEvent) {
    if (emergencyLevel) {
      emergencyLevel = false;
      emergencyB.getStylesheets().clear();

      emergencyB
          .getStylesheets()
          .add(
              "edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/EmergencyButton/emergencyButtonFalse.css");
    } else {
      emergencyLevel = true;
      emergencyB.getStylesheets().clear();
      emergencyB
          .getStylesheets()
          .add(
              "edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/EmergencyButton/emergencyButtonTrue.css");
    }
  }
}
