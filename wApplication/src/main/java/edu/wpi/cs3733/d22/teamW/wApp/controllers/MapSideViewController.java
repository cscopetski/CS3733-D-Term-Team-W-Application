package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class MapSideViewController {
  @FXML public ScrollPane scrollPane;
  @FXML public ImageView towerMap;
  @FXML private Slider slider;

  public void initialize() {
    slider.setValue(2.0);
    slider.setMin(1.0);
    slider.setMax(3.0);

    towerMap.scaleXProperty().bind(slider.valueProperty());
    towerMap.scaleYProperty().bind(slider.valueProperty());
    towerMap.autosize();
    scrollPane.setPannable(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    // scrollPane.getContent().scaleXProperty().bind(slider.valueProperty());
    // scrollPane.getContent().scaleYProperty().bind(slider.valueProperty());
  }
}
