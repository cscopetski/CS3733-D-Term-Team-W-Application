package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
  @FXML private FileChooser fileChooser = new FileChooser();
  Image img1 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F1.png");
  Image img2 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F2.png");
  Image img3 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F3.png");
  Image img4 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/L1.png");
  Image img5 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/L2.png");
  Image img = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/SideView.jpg");
  ArrayList<Circle> locDots = new ArrayList<>();
  ArrayList<Circle> eqDots = new ArrayList<>();
  Integer size = 0;
  Integer xOffSet = 396;
  Integer yOffSet = 63;

  private String currFloor = "0";

  private MedEquipManager equipController = MedEquipManager.getMedEquipManager();
  private LocationManager locationManager = LocationManager.getLocationManager();
  private ArrayList<Location> currFloorLoc = new ArrayList<>();
  private ArrayList<String> currFloorNodeID = new ArrayList<>();
  private ArrayList<medEquip> equipList = new ArrayList<>();

  public void addLocation() throws SQLException {
    if (checkFull()) {
      locationManager.addLocation(
          nodeIn.getText(),
          Integer.parseInt(xIn.getText()),
          Integer.parseInt(yIn.getText()),
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

  public void refresh() throws SQLException {
    // test.setLocationsList();
    removeMarkers();
    currFloorLoc.clear();
    currFloorNodeID.clear();
    ArrayList<edu.wpi.cs3733.d22.teamW.wDB.entity.Location> locList =
        locationManager.getAllLocations();
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
    generateEquipMarkers();
  }

  public void swapFloor1(ActionEvent actionEvent) throws SQLException {
    removeMarkers();
    currFloor = "1";
    refresh();
    System.out.println(F1.getText());
    dropdown.setText("Floor 1");
    mapList.setImage(img1);
  }

  public void swapFloor2(ActionEvent actionEvent) throws SQLException {
    removeMarkers();
    currFloor = "2";
    refresh();
    System.out.println(F2.getText());
    dropdown.setText("Floor 2");
    mapList.setImage(img2);
  }

  public void swapFloor3(ActionEvent actionEvent) throws SQLException {
    removeMarkers();
    currFloor = "3";
    refresh();
    System.out.println(F3.getText());
    dropdown.setText("Floor 3");
    mapList.setImage(img3);
  }

  public void swapFloorL1(ActionEvent actionEvent) throws SQLException {
    currFloor = "L1";
    removeMarkers();
    refresh();
    System.out.println(FL1.getText());
    dropdown.setText("Lower Floor 1");
    mapList.setImage(img4);
  }

  public void swapFloorL2(ActionEvent actionEvent) throws SQLException {
    currFloor = "L2";
    removeMarkers();
    refresh();
    System.out.println(FL2.getText());
    dropdown.setText("Lower Floor 2");
    mapList.setImage(img5);
  }

  public void swapSideView(ActionEvent actionEvent) throws SQLException {
    removeMarkers();
    currFloor = "0";
    refresh();
    System.out.println(Side.getText());
    dropdown.setText("Side View");
    mapList.setImage(img);
  }

  private void generateMarkers() {
    size = currFloorLoc.size();
    for (int i = 0; i < size; i++) {
      Circle circ = new Circle(5, Color.RED);
      circ.setCenterX((currFloorLoc.get(i).getXCoord() * 0.67) + xOffSet);
      circ.setCenterY((currFloorLoc.get(i).getYCoord() * 0.67) + yOffSet);
      locDots.add(circ);
      page.getChildren().add(circ);
    }
  }

  private void generateEquipList() {
    equipList.clear();
    ArrayList<MedEquip> eqList = null;
    try {
      eqList = equipController.getAllMedEquip();
    } catch (SQLException e) {
      e.printStackTrace();
    }
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

  private void generateEquipMarkers() {
    for (int i = 0; i < equipList.size(); i++) {
      Circle circle = new Circle(3, Color.BLACK);
      if (equipList.get(i).getType().equalsIgnoreCase("BED")) {
        circle = new Circle(3, Color.BLUE);
      } else if (equipList.get(i).getType().equalsIgnoreCase("XRY")) {
        circle = new Circle(3, Color.GREEN);
      } else if (equipList.get(i).getType().equalsIgnoreCase("INP")) {
        circle = new Circle(3, Color.DARKVIOLET);
      } else if (equipList.get(i).getType().equalsIgnoreCase("REC")) {
        circle = new Circle(3, Color.YELLOW);
      }
      circle.setCenterX((equipList.get(i).getXCoord() * 0.67) + xOffSet - 1);
      circle.setCenterY((equipList.get(i).getYCoord() * 0.67) + yOffSet - 1);
      eqDots.add(circle);
      page.getChildren().add(circle);
    }
  }

  private void removeMarkers() {
    page.getChildren().removeAll(locDots);
    page.getChildren().removeAll(eqDots);
    locDots.clear();
    eqDots.clear();
  }

  public void updateLocation(ActionEvent actionEvent) throws IOException, SQLException {
    if (!nodeIn.getText().isEmpty()) {
      SceneManager.getInstance()
          .putInformation(
              SceneManager.getInstance().getPrimaryStage(), "updateLoc", nodeIn.getText());
      Stage S = SceneManager.getInstance().openWindow("UpdateMapPage.fxml");
      refresh();
      nodeIn.clear();
    }
  }

  public void removeLocation(ActionEvent actionEvent) throws SQLException {
    if (!nodeIn.getText().isEmpty()) {
      locationManager.deleteLocation(nodeIn.getText());
      nodeIn.clear();
      refresh();
    }
  }

  public void resetCSV(ActionEvent actionEvent) throws SQLException, FileNotFoundException {
    locationManager.addLocation("HOLD", -1, -1, "HOLD", null, null, null, null);
    ArrayList<MedEquip> eqList = equipController.getAllMedEquip();
    for (int i = 0; i < eqList.size(); i++) {
      equipController.add(
          eqList.get(i).getMedID(), eqList.get(i).getType(), "HOLD", eqList.get(i).getStatus());
    }
    File inputCSV = fileChooser.showOpenDialog(SceneManager.getInstance().getPrimaryStage());
    final String locationFileName = "TowerLocations.csv";
    final String medEquipFileName = "MedicalEquipment.csv";
    final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
    final String labServiceRequestFileName = "LabRequests.csv";
    final String employeesFileName = "Employees.csv";
    final String medRequestFileName = "MedRequests.csv";
    CSVController csvController =
        new CSVController(
            locationFileName,
            medEquipFileName,
            medEquipRequestFileName,
            labServiceRequestFileName,
            employeesFileName,
            medRequestFileName);
    locationManager.clearLocations();
    csvController.insertIntoLocationsTable(csvController.importCSV(locationFileName));
    // test.setLocationsList();
    refresh();
  }

  public void expCSV(ActionEvent actionEvent) {
    locationManager.exportLocationsCSV("output.csv");
  }
}
