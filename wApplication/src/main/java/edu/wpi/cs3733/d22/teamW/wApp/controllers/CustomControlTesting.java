package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.MultiToggle;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CustomControlTesting implements Initializable {
  @FXML public RequestTable table;
  @FXML public MultiToggle mt;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    mt.setOptions(new String[] {"Option 1", "Option 2", "Option 3"});
    mt.setSpacing(10);
  }
}
