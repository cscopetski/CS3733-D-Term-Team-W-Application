package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class DashBoardController {

  public Rectangle floor5Click;
  public Rectangle floor4Click;
  public Rectangle floor3Click;
  public Rectangle floor2Click;
  public Rectangle floor1Click;
  public Rectangle LL1Click;
  public Rectangle LL2Click;

  public void clickFloor5(MouseEvent mouseEvent) {
    floor5Click.setStyle("-fx-fill: #189da6");
  }

  public void clickFloor4(MouseEvent mouseEvent) {}

  public void clickFloor3(MouseEvent mouseEvent) {}

  public void clickFloor2(MouseEvent mouseEvent) {}

  public void clickFloor1(MouseEvent mouseEvent) {}

  public void clickLL1(MouseEvent mouseEvent) {}

  public void clickLL2(MouseEvent mouseEvent) {}
}
