package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.MedicalEquipmentSR;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
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

  // other stuff:
  private ArrayList<MedicalEquipmentSR> sr = new ArrayList<>();
  private Control[] fields =
      new Control[] {quantityField, itemCodeField, medNameCBox, locationCBox, requesterCBox};
  private ServiceRequestHelper helper = new ServiceRequestHelper(fields);

  // Getting Employee IDs from DB:
  private ArrayList<Employee> employees = EmployeeManager.getEmployeeManager().getAllEmployees();
  private ArrayList<Integer> ids = new ArrayList<Integer>();

  // ComboBox Lists:
  ObservableList<String> meds = FXCollections.observableArrayList("Advil", "Tylenol");
  ObservableList<String> locations =
      FXCollections.observableArrayList("<Will be implemented from DB>");
  ObservableList<Integer> names = FXCollections.observableArrayList(ids);
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

  private ArrayList<Control> populateFields() {
    ArrayList<Control> fields = new ArrayList<>();

    fields.add(quantityField);
    fields.add(itemCodeField);
    fields.add(medNameCBox);
    fields.add(locationCBox);
    fields.add(timePrefCBox);
    fields.add(requesterCBox);

    return fields;
  }

  public void clearFields() {
    quantityField.clear();
    itemCodeField.clear();
    medNameCBox.getSelectionModel().clearSelection();
    locationCBox.getSelectionModel().clearSelection();
    timePrefCBox.getSelectionModel().clearSelection();
    requesterCBox.getSelectionModel().clearSelection();
  }

  public void onLoad() {
    // populateTable();
    populateEmployeeIDs();
    medNameCBox.setItems(meds);
    locationCBox.setItems(locations);
    timePrefCBox.setItems(times);
    requesterCBox.setItems(names);

    System.out.println(names.isEmpty());
  }

  public void onUnload() {
    clearFields();
  }

  private void populateTable() throws SQLException {
    ArrayList<Request> requests = RequestFactory.getRequestFactory().getAllRequests();
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

  private void populateEmployeeIDs() {
    for (Employee e : employees) {
      ids.add(e.getEmployeeID());
    }
  }

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

  // NO LONGER WORK, NOT SURE WHY NGL
  public void createRequest() throws SQLException {
    // pushDataToDB();
    // populateTable();
  }

  public void submitButton() throws SQLException {
    createRequest();
    clearFields();
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
