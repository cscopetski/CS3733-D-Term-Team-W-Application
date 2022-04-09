package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

public class MapSideViewController {
  @FXML private AnchorPane towerMap;
  @FXML private Slider slider;

  public void initialize() {
    towerMap.scaleXProperty().bind(slider.valueProperty());
  }
}
