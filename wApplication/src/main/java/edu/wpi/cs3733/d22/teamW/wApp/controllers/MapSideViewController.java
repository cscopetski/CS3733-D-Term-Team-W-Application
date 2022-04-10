package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MapSideViewController {
  public ScrollPane scrollPane;
  @FXML public ImageView towerMap;
  public Pane towerPane;
  @FXML private Slider slider;

  public void initialize() {
    slider.setValue(1);
    slider.setMin(1);
    slider.setMax(5);
    //scaling the pane to make it fit with the slider value
    towerPane.scaleXProperty().bind(slider.valueProperty());
    towerPane.scaleYProperty().bind(slider.valueProperty());
    scrollPane.setHvalue(towerMap.getScaleX());
    scrollPane.setVvalue(towerMap.getScaleY());
    scrollPane.setPannable(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

  }
}
