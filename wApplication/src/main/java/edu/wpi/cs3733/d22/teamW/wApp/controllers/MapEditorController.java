package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.LocationController;
import edu.wpi.cs3733.d22.teamW.wDB.LocationDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
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
  @FXML private TableView<Location> LocTab;

  Image img1 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F1.png");
  Image img2 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F2.png");
  Image img3 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F3.png");
  Image img4 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/L1.png");
  Image img5 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/L2.png");
  Image img = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/SideView.jpg");
  ArrayList<Circle> locDots = new ArrayList<>();
  Random rng = new Random();
  Integer size = 0;
  private String currFloor = "0";
  private LocationDaoImpl test;

  {
    try {
      test = new LocationDaoImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private LocationController locationController = new LocationController(test);
  private ArrayList<Location> currFloorLoc = new ArrayList<>();

  public void addLocation(MouseEvent mouseEvent) {}

  public void refresh() {
    currFloorLoc.clear();
    ArrayList<edu.wpi.cs3733.d22.teamW.wDB.Location> locList = locationController.getAllLocations();
    for (int i = 0; i < locList.size(); i++) {
      if (locList.get(i).getFloor().equalsIgnoreCase(currFloor)) {
        currFloorLoc.add(new Location(locList.get(i)));
      }
    }
    LocTab.getItems().clear();
    LocTab.getItems().addAll(currFloorLoc);
    // TODO once equipment is done, implement here
  }

  public void swapFloor1(ActionEvent actionEvent) {
    removeMarkers();
    currFloor = "1";
    refresh();
    System.out.println(F1.getText());
    dropdown.setText("Floor 1");
    mapList.setImage(img1);
    generateMarkers();
    generateEquip();
  }

  public void swapFloor2(ActionEvent actionEvent) {
    removeMarkers();
    currFloor = "2";
    refresh();
    System.out.println(F2.getText());
    dropdown.setText("Floor 2");
    mapList.setImage(img2);
    generateMarkers();
  }

  public void swapFloor3(ActionEvent actionEvent) {
    removeMarkers();
    currFloor = "3";
    refresh();
    System.out.println(F3.getText());
    dropdown.setText("Floor 3");
    mapList.setImage(img3);
    generateMarkers();
  }

  public void swapFloorL1(ActionEvent actionEvent) {
    currFloor = "L1";
    removeMarkers();
    refresh();
    System.out.println(FL1.getText());
    dropdown.setText("Lower Floor 1");
    mapList.setImage(img4);
    generateMarkers();
  }

  public void swapFloorL2(ActionEvent actionEvent) {
    currFloor = "L2";
    removeMarkers();
    refresh();
    System.out.println(FL2.getText());
    dropdown.setText("Lower Floor 2");
    mapList.setImage(img5);
    generateMarkers();
  }

  public void swapSideView(ActionEvent actionEvent) {
    removeMarkers();
    System.out.println(Side.getText());
    dropdown.setText("Side View");
    mapList.setImage(img);
    currFloor = "0";
  }

  private void generateMarkers() {
    size = currFloorLoc.size();
    for (int i = 0; i < size; i++) {
      Circle circ = new Circle(5, Color.RED);
      circ.setCenterX((currFloorLoc.get(i).getXCoord() * 0.155) + 319);
      circ.setCenterY((currFloorLoc.get(i).getYCoord() * 0.170) + 55);
      locDots.add(circ);
      page.getChildren().add(circ);
    }
  }

  private void generateEquip() {
    /*
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
     */
  }

  private void removeMarkers() {
    page.getChildren().removeAll(locDots);
    locDots = new ArrayList<>();
  }
}
