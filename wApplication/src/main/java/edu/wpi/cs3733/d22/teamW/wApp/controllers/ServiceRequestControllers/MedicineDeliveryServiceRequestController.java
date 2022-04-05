package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class MedicineDeliveryServiceRequestController {

  // Textfields:
  @FXML TextField quantityField;
  @FXML TextField itemCodeField;

  // Buttons:
  @FXML Button addButton;
  @FXML Button submitButton;

  // Combo Boxes:
  @FXML ComboBox medNameCBox;
  @FXML ComboBox locationCBox;
  @FXML ComboBox timePrefCBox;
  @FXML ComboBox requesterCBox; // FREE BOX, NOT SURE WHAT TO DO

  // PULL FROM DB //
  ObservableList<String> meds = FXCollections.observableArrayList("Advil", "Sleep");
  ObservableList<String> locations = FXCollections.observableArrayList("Advil", "Sleep");
  ObservableList<String> times =
      FXCollections.observableArrayList("8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30");

  public void clearFields() {
    //
  }

  public void tempStartup() {
    medNameCBox.setItems(meds);
    locationCBox.setItems(locations);
    timePrefCBox.setItems(times);
  }

  public void createRequest() {
    // NEEDS LINK TO DB
  }

  public void submitButton() {
    clearFields();
    createRequest();
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
