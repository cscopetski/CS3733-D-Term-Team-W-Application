package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MedicineDeliveryServiceRequestController extends LoadableController {

  // TextFields:
  @FXML TextField quantityField;
  @FXML TextField itemCodeField;

  // Buttons:
  @FXML Button addButton;
  @FXML Button submitButton;
  @FXML Button cancelButton;

  // ComboBoxes:
  @FXML ComboBox medNameCBox;
  @FXML ComboBox locationCBox;
  @FXML ComboBox timePrefCBox;
  @FXML ComboBox requesterCBox; // FREE BOX, NOT SURE WHAT TO DO

  // ComboBox Lists:
  ObservableList<String> meds = FXCollections.observableArrayList("create list in DB");
  ObservableList<String> locations = FXCollections.observableArrayList("get from DB");
  ObservableList<String> times =
      FXCollections.observableArrayList(
          "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
          "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00",
          "18:30");

  // Tables:
  @FXML TableView orderTable;

  // Table Lists:
  ObservableList<String> orders = FXCollections.observableArrayList();

  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.MedicineDelivery;
  }

  // clear all UI inputs:
  public void clearFields() {
    quantityField.clear();
    itemCodeField.clear();
    medNameCBox.setValue(null);
    locationCBox.setValue(null);
    timePrefCBox.setValue(null);
    requesterCBox.setValue(null);
  }

  public void onLoad() {
    medNameCBox.setItems(meds);
    locationCBox.setItems(locations);
    timePrefCBox.setItems(times);
    orderTable.setItems(orders);
  }

  public void onUnload() {}

  public void createRequest() {
    orders.add(0, "hi");
  }

  public void submitButton() {
    createRequest();
    clearFields();
  }

  // getters and setters for combo box's list
  // -unsure if needed
  public ObservableList<String> getMeds() {
    return meds;
  }

  public void setMeds(ObservableList<String> meds) {
    this.meds = meds;
  }
}
