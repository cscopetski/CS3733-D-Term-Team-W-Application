package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import java.awt.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class MedicineDeliveryServiceRequestController {

  // FXML connectors:
  // @FXML TextField location;
  // @FXML TextField employee;
  // @FXML TextField medicine;

  // Combo Boxes:
  @FXML ComboBox medNameCBox;
  @FXML ComboBox freeCBox;
  @FXML ComboBox locationCBox;
  @FXML ComboBox timePrefCBox;

  // PULL FROM DB OF MEDS??
  ObservableList<String> meds = FXCollections.observableArrayList("Advil", "Sleep");

  public void clearFields() {
    //
  }

  public void temp() {
    medNameCBox.setItems(meds);
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
