package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LabServiceRequestController {
  Alert confirm = new ConfirmAlert();

  @FXML Button emergencyB;
  @FXML Label undoLabel;

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    confirm.showAndWait();
  }
}
