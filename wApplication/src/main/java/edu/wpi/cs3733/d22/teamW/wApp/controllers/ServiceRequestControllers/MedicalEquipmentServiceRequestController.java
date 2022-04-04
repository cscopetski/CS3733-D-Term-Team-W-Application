package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

public class MedicalEquipmentServiceRequestController {
  Alert confirm =
      new Alert(
          Alert.AlertType.CONFIRMATION,
          "Would you like to confirm this request " + " ?",
          ButtonType.OK,
          ButtonType.CANCEL);
  Alert emptyFields =
      new Alert(
          Alert.AlertType.ERROR,
          "There are required fields empty " + " !",
          ButtonType.OK,
          ButtonType.CANCEL);
  @FXML ComboBox<String> equipmentSelection;

  public void submitButton(ActionEvent actionEvent) {
    if (!equipmentSelection.getValue().isEmpty()) {
      confirm.show();
      if (confirm.getResult() == ButtonType.OK) {}
    } else {
      emptyFields.show();
    }
  }
}
