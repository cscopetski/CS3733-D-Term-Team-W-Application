package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import java.util.ArrayList;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MapEditorController {
  @FXML private ImageView mapList;
  @FXML private MenuItem F1;
  @FXML private MenuItem F2;
  @FXML private MenuItem F3;
  @FXML private MenuItem FL1;
  @FXML private MenuItem FL2;
  @FXML private MenuItem Side;
  @FXML private MenuButton dropdown;
  @FXML private AnchorPane page;
  Image img1 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F1.png");
  Image img2 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F2.png");
  Image img3 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F3.png");
  Image img4 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/L1.png");
  Image img5 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/L2.png");
  Image img = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/SideView.jpg");
  ArrayList<Circle> locDots = new ArrayList<>();
  Random rng = new Random();
  Integer size = 0;
  private int currFloor = 0;

  public void addLocation(MouseEvent mouseEvent) {}

  public void refresh(ActionEvent actionEvent) {}

  public void swapFloor1(ActionEvent actionEvent) {
    removeMarkers();
    System.out.println(F1.getText());
    dropdown.setText("Floor 1");
    mapList.setImage(img1);
    generateMarkers();
    generateEquip();
    currFloor = 1;
  }

  public void swapFloor2(ActionEvent actionEvent) {
    removeMarkers();
    System.out.println(F2.getText());
    dropdown.setText("Floor 2");
    mapList.setImage(img2);
    generateMarkers();
    currFloor = 2;
  }

  public void swapFloor3(ActionEvent actionEvent) {
    removeMarkers();
    System.out.println(F3.getText());
    dropdown.setText("Floor 3");
    mapList.setImage(img3);
    generateMarkers();
    currFloor = 3;
  }

  public void swapFloorL1(ActionEvent actionEvent) {
    removeMarkers();
    System.out.println(FL1.getText());
    dropdown.setText("Lower Floor 1");
    mapList.setImage(img4);
    generateMarkers();
    currFloor = 4;
  }

  public void swapFloorL2(ActionEvent actionEvent) {
    removeMarkers();
    System.out.println(FL2.getText());
    dropdown.setText("Lower Floor 2");
    mapList.setImage(img5);
    generateMarkers();
    currFloor = 5;
  }

  public void swapSideView(ActionEvent actionEvent) {
    removeMarkers();
    System.out.println(Side.getText());
    dropdown.setText("Side View");
    mapList.setImage(img);
  }

  private void generateMarkers() {
    size = 5;
    for (int i = 0; i < size; i++) {
      Circle circ = new Circle(5, Color.RED);
      circ.setCenterX((rng.nextDouble() * 559) + 319);
      circ.setCenterY((rng.nextDouble() * 470) + 55);
      locDots.add(circ);
      page.getChildren().add(circ);
    }
  }

  private void generateEquip() {
    size = 4;
    for (int i = 0; i < size; i++) {
      Circle circle = new Circle(5, Color.MEDIUMPURPLE);
      circle.setCenterX((rng.nextDouble() * 559) + 319);
      circle.setCenterY((rng.nextDouble() * 470) + 55);
      locDots.add(circle);
      page.getChildren().add(circle);
    }
    size = 1;
    for (int i = 0; i < size; i++) {
      Circle circle = new Circle(5, Color.GREEN);
      circle.setCenterX((rng.nextDouble() * 559) + 319);
      circle.setCenterY((rng.nextDouble() * 470) + 55);
      locDots.add(circle);
      page.getChildren().add(circle);
    }
    size = 6;
    for (int i = 0; i < size; i++) {
      Circle circle = new Circle(5, Color.YELLOW);
      circle.setCenterX((rng.nextDouble() * 559) + 319);
      circle.setCenterY((rng.nextDouble() * 470) + 55);
      locDots.add(circle);
      page.getChildren().add(circle);
    }
    size = 2;
    for (int i = 0; i < size; i++) {
      Circle circle = new Circle(5, Color.BLUE);
      circle.setCenterX((rng.nextDouble() * 559) + 319);
      circle.setCenterY((rng.nextDouble() * 470) + 55);
      locDots.add(circle);
      page.getChildren().add(circle);
    }
  }

  private void removeMarkers() {
    page.getChildren().removeAll(locDots);
    locDots = new ArrayList<>();
  }
}
