package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.MultiToggle;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class CustomControlTesting implements Initializable {
  @FXML public RequestTable table;
  @FXML public MultiToggle mt;
  public ToggleButton b1;
  public ToggleButton b2;
  public MultiToggle mt2;
  public VBox toggles;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ToggleGroup tg = new ToggleGroup();
    b1.setToggleGroup(tg);
    b2.setToggleGroup(tg);
  }
}
