package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class SecurityServiceRequestController {

  Alert confirm = new ConfirmAlert();

  public TextField roomNumber;
  // boolean emergencyLevel = false;
  int emergency = 0;

  @FXML EmergencyButton emergencyB;

  //  public void emergencyClicked(MouseEvent mouseEvent) {
  //    if (emergencyLevel) {
  //      emergencyLevel = false;
  //      emergencyButton.getStylesheets().clear();
  //
  //      emergencyButton
  //          .getStylesheets()
  //          .add(
  //
  // "edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/EmergencyButton/emergencyButtonFalse.css");
  //    } else {
  //      emergencyLevel = true;
  //      emergencyButton.getStylesheets().clear();
  //      emergencyButton
  //          .getStylesheets()
  //          .add(
  //
  // "edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/EmergencyButton/emergencyButtonTrue.css");
  //    }
  //  }

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    confirm.showAndWait();
  }

  public void cancelButton(ActionEvent event) {
    roomNumber.clear();
  }
}
