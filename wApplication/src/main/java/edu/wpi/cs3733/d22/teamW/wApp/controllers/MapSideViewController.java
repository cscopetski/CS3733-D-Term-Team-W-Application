package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class MapSideViewController {
  public ScrollPane scrollPane;
  public ImageView towerMap;
  @FXML private Slider slider;

  public void initialize() {
    slider.setValue(10);
    towerMap.scaleXProperty().bind(slider.valueProperty());
    towerMap.scaleYProperty().bind(slider.valueProperty());
    scrollPane.setPannable(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    // scrollPane.setVvalue(towerMap.getX());
    // scrollPane.setHvalue(towerMap.getY());
  }
}
