package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.CustomControls.EmergencyButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class CustomControlTesting implements Initializable {
  @FXML public AnchorPane ap;
  @FXML public EmergencyButton eb;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ap.getChildren().add(new EmergencyButton());
  }
  // @FXML public EmergencyButton eb;

}
