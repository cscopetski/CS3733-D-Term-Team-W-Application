package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import javax.security.sasl.SaslServer;
import javax.swing.text.html.ImageView;

public class MapEditorController {
  @FXML MenuItem F1;
  @FXML MenuItem F2;
  @FXML MenuItem F3;
  @FXML MenuItem FL1;
  @FXML MenuItem FL2;
  @FXML ImageView map;

  public void addLocation(MouseEvent mouseEvent) {}

  public void refresh(ActionEvent actionEvent) {}

  public void swapFloor1(ActionEvent actionEvent) {
    System.out.println(F1.getText());

  }

  public void swapFloor2(ActionEvent actionEvent) {
    System.out.println(F2.getText());
  }

  public void swapFloor3(ActionEvent actionEvent) {
    System.out.println(F3.getText());
  }

  public void swapFloorL1(ActionEvent actionEvent) {
    System.out.println(FL1.getText());
  }

  public void swapFloorL2(ActionEvent actionEvent) {
    System.out.println(FL2.getText());
  }
}
