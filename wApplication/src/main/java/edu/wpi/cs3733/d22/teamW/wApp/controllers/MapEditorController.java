package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
  @FXML private TableView<medEquip> EqTab;
  @FXML private TextField xIn;
  @FXML private TextField yIn;
  @FXML private TextField nodeIn;
  Image img1 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F1.png");
  Image img2 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F2.png");
  Image img3 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F3.png");
  Image img4 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/L1.png");
  Image img5 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/L2.png");
  Image img = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/SideView.jpg");
  ArrayList<Circle> locDots = new ArrayList<>();
  Random rng = new Random();
  Integer size = 0;
  private MedEquipDaoImpl medEquipDao;

  {
    try {
      medEquipDao = new MedEquipDaoImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private MedEquipRequestDaoImpl medEquipRequestDao = new MedEquipRequestDaoImpl();
  private String currFloor = "0";
  private LocationDaoImpl test;

  {
    try {
      test = new LocationDaoImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private MedEquipController equipController =
      new MedEquipController(medEquipDao, medEquipRequestDao);
  private LocationController locationController = new LocationController(test);
  private ArrayList<Location> currFloorLoc = new ArrayList<>();
  private ArrayList<String> currFloorNodeID = new ArrayList<>();
  private ArrayList<medEquip> equipList = new ArrayList<>();

  public void addLocation() throws SQLException {
    if (checkFull()) {
      locationController.addLocation(
          nodeIn.getText(),
          Integer.valueOf(xIn.getText()),
          Integer.valueOf(yIn.getText()),
          currFloor,
          null,
          null,
          null,
          null);
      xIn.clear();
      nodeIn.clear();
      yIn.clear();
      refresh();
    }
  }

  private boolean checkFull() {
    System.out.println("test");
    if (xIn.getText().isEmpty() && yIn.getText().isEmpty() && nodeIn.getText().isEmpty()) {
      System.out.println("test1");
      return false;
    }
    System.out.println("test2");
    return true;
  }

  public void refresh() {
    removeMarkers();
    currFloorLoc.clear();
    currFloorNodeID.clear();
    ArrayList<edu.wpi.cs3733.d22.teamW.wDB.Location> locList = locationController.getAllLocations();
    for (int i = 0; i < locList.size(); i++) {
      if (locList.get(i).getFloor().equalsIgnoreCase(currFloor)) {
        currFloorLoc.add(new Location(locList.get(i)));
        currFloorNodeID.add(locList.get(i).getNodeID());
      }
    }
    LocTab.getItems().clear();
    LocTab.getItems().addAll(currFloorLoc);
    generateEquipList();
    generateMarkers();
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
      circ.setCenterX((currFloorLoc.get(i).getXCoord()) + 319);
      circ.setCenterY((currFloorLoc.get(i).getYCoord()) + 55);
      locDots.add(circ);
      page.getChildren().add(circ);
    }
  }

  private void generateEquipList() {
    equipList.clear();
    ArrayList<MedEquip> eqList = equipController.getAll();
    for (int i = 0; i < eqList.size(); i++) {
      for (int j = 0; j < currFloorNodeID.size(); j++) {
        if (eqList.get(i).getNodeID().equalsIgnoreCase(currFloorNodeID.get(j))) {
          equipList.add(
              new medEquip(
                  eqList.get(i).getMedID(),
                  eqList.get(i).getType(),
                  currFloorLoc.get(j).getXCoord(),
                  currFloorLoc.get(j).getYCoord()));
          break;
        }
      }
    }
    EqTab.getItems().clear();
    EqTab.getItems().addAll(equipList);
  }

  private void removeMarkers() {
    page.getChildren().removeAll(locDots);
    locDots = new ArrayList<>();
  }

  public void updateLocation(ActionEvent actionEvent) {}

  public void removeLocation(ActionEvent actionEvent) throws SQLException {
    if (!nodeIn.getText().isEmpty()) {
      locationController.deleteLocation(nodeIn.getText());
      nodeIn.clear();
      refresh();
    }
  }

  public void resetCSV(ActionEvent actionEvent) {}

  public void expCSV(ActionEvent actionEvent) {
    test.exportLocationCSV("output.csv");
  }
}
