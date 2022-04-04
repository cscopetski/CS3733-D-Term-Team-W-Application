package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javax.swing.*;

public class LabServiceRequestController {
  @FXML Button emergencyB;
  @FXML Label undoLabel;
  @FXML ChoiceBox genderChoiceBox;
  boolean emergencyLevel = false;

  public void undoEmergency(MouseEvent mouseEvent) {
    // Undo the emergency value
    // change button color again
    emergencyB.setStyle(
        "-fx-background-color: white; -fx-text-fill: #013895; -fx-border-color: #013895; -fx-border-radius: 26; -fx-border-width: 1");
  }

  // Gender choice box
  private String[] gender = {"Female", "Male", "Other"};

  public void initialize() {
    genderChoiceBox.getItems().addAll(gender);
    genderChoiceBox.setOnAction(this::getGender);
  }

  private void getGender(Event event) {
    String myGender = (String) genderChoiceBox.getValue();
  }

  public void emergencyClicked(MouseEvent mouseEvent) {
    if (emergencyLevel) {
      emergencyLevel = false;
      emergencyB.getStylesheets().clear();

      emergencyB
          .getStylesheets()
          .add("edu/wpi/cs3733/d22/teamW/wApp/CSS/LabServiceRequestPage/emergencyButtonFalse.css");
    } else {
      emergencyLevel = true;
      emergencyB.getStylesheets().clear();
      emergencyB
          .getStylesheets()
          .add("edu/wpi/cs3733/d22/teamW/wApp/CSS/LabServiceRequestPage/emergencyButtonTrue.css");
    }
  }
}
