package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class LabServiceRequestController {
  @FXML Button emergencyB;
  @FXML Label undoLabel;
  boolean emergencyLevel = false;

  public void undoEmergency(MouseEvent mouseEvent) {
    // Undo the emergency value
    // change button color again
    emergencyB.setStyle(
        "-fx-background-color: white; -fx-text-fill: #013895; -fx-border-color: #013895; -fx-border-radius: 26; -fx-border-width: 1");
  }

  public void initialize() {}

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
