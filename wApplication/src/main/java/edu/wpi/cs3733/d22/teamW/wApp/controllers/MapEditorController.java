package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Floor;
import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Requests;
import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.medEquip;
import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wMid.Account;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MapEditorController extends LoadableController {

  @FXML public ScrollPane scrollPane;
  @FXML public Slider scaleSlider;
  @FXML public Group scrollGroup;
  @FXML public ToggleButton panButton;
  @FXML public ToggleButton modifyButton;
  @FXML private ImageView mapList;
  @FXML private MenuItem F1;
  @FXML private MenuItem F2;
  @FXML private MenuItem F3;
  @FXML private MenuItem FL1;
  @FXML private MenuItem FL2;
  @FXML private MenuItem Side;
  @FXML private MenuButton dropdown;
  @FXML private BorderPane page;
  @FXML private TableView<edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Location> LocTab;
  @FXML private TableView<medEquip> EqTab;
  @FXML private TableView<Floor> FloorTab;
  @FXML private TableView<Floor> EqDashTab;
  @FXML private FileChooser fileChooser = new FileChooser();
  @FXML private CheckBox LocFilter;
  @FXML private CheckBox EquipFilter;
  @FXML private CheckBox ReqFilter;
  @FXML private Label dirtLab;
  @FXML private Alert systemAlert = new Alert(Alert.AlertType.INFORMATION);
  Image img1 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F1.png");
  Image img2 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F2.png");
  Image img3 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F3.png");
  Image imgL1 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/LL1.png");
  Image imgL2 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/LL2.png");
  Image img = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/SideView.png");
  Image img4 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F4.png");
  Image img5 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F5.png");
  ArrayList<Circle> locDots = new ArrayList<>();
  ArrayList<Circle> eqDots = new ArrayList<>();
  ArrayList<Circle> reqDots = new ArrayList<>();
  Integer size = 0;
  private String currFloor = "0";
  private MedEquipManager equipController = MedEquipManager.getMedEquipManager();
  private LocationManager locationManager = LocationManager.getLocationManager();
  private MedEquipRequestManager medEquipRequestManager =
      MedEquipRequestManager.getMedEquipRequestManager();
  private LabServiceRequestManager labServiceRequestManager =
      LabServiceRequestManager.getLabServiceRequestManager();
  private MedRequestManager medRequestManager = MedRequestManager.getMedRequestManager();
  private ArrayList<edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Location> currFloorLoc =
      new ArrayList<>();
  private ArrayList<String> currFloorNodeID = new ArrayList<>();
  private ArrayList<medEquip> equipList = new ArrayList<>();
  private ArrayList<Requests> reqList = new ArrayList<>();
  private ArrayList<Floor> floorList = new ArrayList<>();
  private ArrayList<Floor> locEqList = new ArrayList<>();
  private boolean loaded = false;

  private enum InteractionStates {
    None,
    Pan,
    Modify
  }

  private InteractionStates interactionState = InteractionStates.None;

  @Override
  public void initialize(URL location, ResourceBundle rb) {
    super.initialize(location, rb);
    ToggleGroup tg = new ToggleGroup();
    panButton.setToggleGroup(tg);
    modifyButton.setToggleGroup(tg);
    tg.selectedToggleProperty()
        .addListener(
            (e, o, n) -> {
              if (n == null) {
                interactionState = InteractionStates.None;
              } else if (n.equals(panButton)) {
                interactionState = InteractionStates.Pan;
                scrollPane.setPannable(true);
              } else if (n.equals(modifyButton)) {
                interactionState = InteractionStates.Modify;
              }
              if (o.equals(panButton)) {
                scrollPane.setPannable(false);
              }
            });
  }

  public void refreshDash() throws SQLException {
    floorList.clear();
    equipList.clear();
    EqDashTab.setVisible(true);
    EqTab.setVisible(false);
    LocTab.setVisible(false);
    FloorTab.setVisible(true);
    dirtLab.setVisible(true);
    ArrayList<edu.wpi.cs3733.d22.teamW.wDB.entity.Location> locList =
        locationManager.getAllLocations();
    ArrayList<MedEquip> eqList = equipController.getAllMedEquip();
    Floor F1 = new Floor("01");
    Floor F2 = new Floor("02");
    Floor F3 = new Floor("03");
    Floor F4 = new Floor("04");
    Floor F5 = new Floor("05");
    Floor L1 = new Floor("L1");
    Floor L2 = new Floor("L2");
    for (int i = 0; i < locList.size(); i++) {
      for (int j = 0; j < eqList.size(); j++) {
        /*if (eqList.get(j).getMedID().equalsIgnoreCase("BED005")) ;
        {
          try {
            equipController.markDirty("BED005", "wDIRT0013");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }*/
        if (eqList.get(j).getNodeID().equalsIgnoreCase(locList.get(i).getNodeID())) {
          switch (locList.get(i).getFloor()) {
            case "01":
              switchCase(
                  eqList.get(j).getType().getString(), F1, eqList.get(j).getStatus().getValue());
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "01",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "02":
              switchCase(
                  eqList.get(j).getType().getString(), F2, eqList.get(j).getStatus().getValue());
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "02",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "03":
              switchCase(
                  eqList.get(j).getType().getString(), F3, eqList.get(j).getStatus().getValue());
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "03",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "04":
              switchCase(
                  eqList.get(j).getType().getString(), F4, eqList.get(j).getStatus().getValue());
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "04",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "05":
              switchCase(
                  eqList.get(j).getType().getString(), F5, eqList.get(j).getStatus().getValue());
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "05",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "L1":
              switchCase(
                  eqList.get(j).getType().getString(), L1, eqList.get(j).getStatus().getValue());
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "L1",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "L2":
              switchCase(
                  eqList.get(j).getType().getString(), L2, eqList.get(j).getStatus().getValue());
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "L2",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
          }
        }
      }
    }
    floorList.add(F1);
    floorList.add(F2);
    floorList.add(F3);
    floorList.add(F4);
    floorList.add(F5);
    floorList.add(L1);
    floorList.add(L2);
    EqDashTab.getItems().clear();
    generateLocEqList();
    EqDashTab.getItems().addAll(locEqList);
    FloorTab.getItems().clear();
    FloorTab.getItems().addAll(floorList);
  }

  private void checkFewerthan5cleanPumps() throws SQLException {
    for (int i = 0; i < locEqList.size(); i++) {
      if (locEqList.get(i).getCleanPumpCount() < 5) {
        if (locationManager
            .getLocation(locEqList.get(i).getFloor())
            .getLongName()
            .contains("clean")) {
          systemAlert.setTitle("clean pumps");
          systemAlert.setContentText(
              "There has been less than 5 clean infusion pumps detected on floor: "
                  + locationManager.getLocation(locEqList.get(i).getFloor()).getFloor()
                  + "\nLocated at: "
                  + locationManager.getLocation(locEqList.get(i).getFloor()).getLongName()
                  + ".\nA service request to clean these pumps has been created. Thank you");
          systemAlert.showAndWait();
        }
      }
    }
  }

  private void checkDirtyPumps() throws SQLException {
    for (int i = 0; i < locEqList.size(); i++) {
      if (locEqList.get(i).getDirtyPumpCount() >= 10) {
        if (locationManager
            .getLocation(locEqList.get(i).getFloor())
            .getLongName()
            .contains("dirty")) {
          systemAlert.setTitle("dirty pumps");
          systemAlert.setContentText(
              "There has been "
                  + locEqList.get(i).getDirtyPumpCount()
                  + " dirty infusion pumps detected on floor: "
                  + locationManager.getLocation(locEqList.get(i).getFloor()).getFloor()
                  + " \nLocated at: "
                  + locationManager.getLocation(locEqList.get(i).getFloor()).getLongName()
                  + ".\nA service request to clean these pumps has been created. Thank you");
          systemAlert.showAndWait();
        }
      }
    }
  }

  private void checkDirtyBeds() throws SQLException {
    for (int i = 0; i < locEqList.size(); i++) {
      if (locEqList.get(i).getDirtyBedCount() >= 6) {
        if (locationManager
            .getLocation(locEqList.get(i).getFloor())
            .getNodeType()
            .equalsIgnoreCase("DIRT")) {
          systemAlert.setTitle("Dirty Beds alert");
          systemAlert.setHeaderText("At least 6 dirty beds detected");
          systemAlert.setContentText(
              "There has been "
                  + locEqList.get(i).getDirtyBedCount()
                  + " dirty beds detected on floor: "
                  + locationManager.getLocation(locEqList.get(i).getFloor()).getFloor()
                  + " \n Located at: "
                  + locationManager.getLocation(locEqList.get(i).getFloor()).getLongName()
                  + ". \n A service request to move the beds to the OR park for cleaning \nhas been created. Thank you");
          systemAlert.showAndWait();
        }
      }
    }
  }

  private void switchCase(String eqType, Floor floor, Integer Status) {
    switch (eqType) {
      case "Bed":
        switch (Status) {
          case 0:
            floor.setCleanBedCount(floor.getCleanBedCount() + 1);
            break;
          case 2:
            floor.setDirtyBedCount(floor.getDirtyBedCount() + 1);
            break;
        }
        break;
      case "X-Ray":
        switch (Status) {
          case 0:
            floor.setCleanXrayCount(floor.getCleanXrayCount() + 1);
            break;
          case 2:
            floor.setDirtyXrayCount(floor.getDirtyXrayCount() + 1);
        }
        break;
      case "Infusion Pump":
        switch (Status) {
          case 0:
            floor.setCleanPumpCount(floor.getCleanPumpCount() + 1);
            break;
          case 2:
            floor.setDirtyPumpCount(floor.getDirtyPumpCount() + 1);
        }
        break;
      case "Recliners":
        switch (Status) {
          case 0:
            floor.setCleanReclinCount(floor.getCleanReclinCount() + 1);
            break;
          case 2:
            floor.setDirtyReclinCount(floor.getDirtyReclinCount() + 1);
        }
        break;
    }
  }

  private void generateLocEqList() throws SQLException {
    locEqList.clear();
    ArrayList<edu.wpi.cs3733.d22.teamW.wDB.entity.Location> locList =
        locationManager.getAllLocations();
    ArrayList<MedEquip> eqList = equipController.getAllMedEquip();
    ArrayList<String> arr = new ArrayList<>();
    for (int i = 0; i < locList.size(); i++) {
      for (int j = 0; j < eqList.size(); j++) {
        if (eqList.get(j).getNodeID().equalsIgnoreCase(locList.get(i).getNodeID())) {
          if (arr.indexOf(locList.get(i).getNodeID()) == -1) {
            Floor F = new Floor(locList.get(i).getNodeID());
            locEqList.add(F);
            arr.add(locList.get(i).getNodeID());
          }
          switchCase(
              eqList.get(j).getType().getString(),
              locEqList.get(arr.indexOf(locList.get(i).getNodeID())),
              eqList.get(j).getStatus().getValue());
        }
      }
    }
    locEqList.remove(arr.indexOf("NONE"));
  }

  public void refresh() throws SQLException, NonExistingMedEquip {
    if (currFloor == "0") {
      refreshDash();
      checkFewerthan5cleanPumps();
      checkDirtyPumps();
      checkDirtyBeds();
      return;
    }
    removeMarkers();
    currFloorLoc.clear();
    currFloorNodeID.clear();
    ArrayList<edu.wpi.cs3733.d22.teamW.wDB.entity.Location> locList =
        locationManager.getAllLocations();
    for (int i = 0; i < locList.size(); i++) {
      if (locList.get(i).getFloor().equalsIgnoreCase(currFloor)) {
        currFloorLoc.add(new edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Location(locList.get(i)));
        currFloorNodeID.add(locList.get(i).getNodeID());
      }
    }
    LocTab.getItems().clear();
    LocTab.getItems().addAll(currFloorLoc);
    generateEquipList();
    generateRequestList();
    if (LocFilter.isSelected()) {
      generateMarkers();
    }
    if (EquipFilter.isSelected()) {
      generateEquipMarkers();
    }
    if (ReqFilter.isSelected()) {
      generateRequestDots();
    }
    LocTab.getSelectionModel().clearSelection();
    EqTab.getSelectionModel().clearSelection();
    LocTab.setVisible(true);
    EqTab.setVisible(true);
    EqDashTab.setVisible(false);
    FloorTab.setVisible(false);
    dirtLab.setVisible(false);
  }

  public void swapFloor1(ActionEvent actionEvent) throws SQLException, NonExistingMedEquip {
    removeMarkers();
    currFloor = "01";
    refresh();
    System.out.println(F1.getText());
    dropdown.setText("Floor 1");
    mapList.setImage(img1);
  }

  public void swapFloor2(ActionEvent actionEvent) throws SQLException, NonExistingMedEquip {
    removeMarkers();
    currFloor = "02";
    refresh();
    System.out.println(F2.getText());
    dropdown.setText("Floor 2");
    mapList.setImage(img2);
  }

  public void swapFloor3(ActionEvent actionEvent) throws SQLException, NonExistingMedEquip {
    removeMarkers();
    currFloor = "03";
    refresh();
    System.out.println(F3.getText());
    dropdown.setText("Floor 3");
    mapList.setImage(img3);
  }

  public void swapFloorL1(ActionEvent actionEvent) throws SQLException, NonExistingMedEquip {
    currFloor = "L1";
    removeMarkers();
    refresh();
    System.out.println(FL1.getText());
    dropdown.setText("Lower Floor 1");
    mapList.setImage(imgL1);
  }

  public void swapFloorL2(ActionEvent actionEvent) throws SQLException, NonExistingMedEquip {
    currFloor = "L2";
    removeMarkers();
    refresh();
    System.out.println(FL2.getText());
    dropdown.setText("Lower Floor 2");
    mapList.setImage(imgL2);
  }

  public void swapSideView(ActionEvent actionEvent) throws SQLException, NonExistingMedEquip {
    removeMarkers();
    currFloor = "0";
    System.out.println(Side.getText());
    dropdown.setText("Side View");
    mapList.setImage(img);
    refresh();
  }

  private void generateMarkers() {
    size = currFloorLoc.size();
    for (int i = 0; i < size; i++) {
      AtomicReference<Double> anchorX = new AtomicReference<>((double) 0);
      AtomicReference<Double> anchorY = new AtomicReference<>((double) 0);
      Circle circ = new Circle(12, Color.RED);
      Image locationIcon =
          new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/icons/icon_Location.png");
      ImagePattern locPattern = new ImagePattern(locationIcon);
      circ.setFill(locPattern);
      circ.setCenterX((currFloorLoc.get(i).getXCoord()));
      circ.setCenterY((currFloorLoc.get(i).getYCoord()));

      circ.setOnMouseClicked(
          (event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
              try {
                if (Account.getInstance().getEmployee().getType().getAccessLevel() == 5) {
                  testUpdate(currFloorLoc.get(locDots.indexOf(event.getSource())).getNodeID());
                } else {
                  locView(currFloorLoc.get(locDots.indexOf(event.getSource())).getNodeID());
                }
              } catch (SQLException | IOException | NonExistingMedEquip e) {
                e.printStackTrace();
              }
            }
          }));
      if (Account.getInstance().getEmployee().getType().getAccessLevel() == 5) {
        circ.setOnMousePressed(
            event -> {
              anchorX.set(event.getSceneX());
              anchorY.set(event.getSceneY());
            });
        circ.setOnMouseDragged(
            event -> {
              circ.setTranslateX(event.getSceneX() - anchorX.get());
              circ.setTranslateY(event.getSceneY() - anchorY.get());
            });
        circ.setOnMouseReleased(
            event -> {
              circ.setCenterX(circ.getCenterX() + circ.getTranslateX());
              circ.setCenterY(circ.getCenterY() + circ.getTranslateY());
              circ.setTranslateX(0);
              circ.setTranslateY(0);
              anchorX.set(0.0);
              anchorY.set(0.0);
              Location val = null;
              try {
                val =
                    locationManager.getLocation(
                        currFloorLoc.get(locDots.indexOf(event.getSource())).getNodeID());
              } catch (SQLException e) {
                e.printStackTrace();
              }
              currFloorLoc
                  .get(locDots.indexOf(event.getSource()))
                  .setXCoord((int) circ.getCenterX());
              currFloorLoc
                  .get(locDots.indexOf(event.getSource()))
                  .setYCoord((int) circ.getCenterY());
              try {
                locationManager.changeLocation(
                    new Location(
                        val.getNodeID(),
                        (int) (circ.getCenterX()),
                        (int) (circ.getCenterY()),
                        val.getFloor(),
                        val.getBuilding(),
                        val.getNodeType(),
                        val.getLongName(),
                        val.getShortName()));
              } catch (SQLException e) {
                e.printStackTrace();
              }
              scrollGroup.getChildren().removeAll(eqDots);
              scrollGroup.getChildren().removeAll(reqDots);
              eqDots.clear();
              reqDots.clear();
              if (EquipFilter.isSelected()) {
                generateEquipMarkers();
              }
              if (ReqFilter.isSelected()) {
                generateRequestDots();
              }
            });
      }
      locDots.add(circ);
      scrollGroup.getChildren().add(circ);
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
                  eqList.get(i).getType().getString(),
                  currFloorLoc.get(j).getXCoord(),
                  currFloorLoc.get(j).getYCoord(),
                  currFloorLoc.get(j)));
          break;
        }
      }
    }
    EqTab.getItems().clear();
    EqTab.getItems().addAll(equipList);
  }

  private void generateEquipMarkers() {
    for (int i = 0; i < equipList.size(); i++) {
      Circle circle = new Circle(10, Color.TRANSPARENT);
      AtomicReference<Double> anchorX = new AtomicReference<>((double) 0);
      AtomicReference<Double> anchorY = new AtomicReference<>((double) 0);
      if (equipList.get(i).getType().equalsIgnoreCase("BED")) {
        Image bedIcon = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/icons/icon_Bed.png");
        ImagePattern bedPattern = new ImagePattern(bedIcon);
        circle = new Circle(10, Color.BLUE);
        circle.setFill(bedPattern);
      } else if (equipList.get(i).getType().equalsIgnoreCase("XRY")) {
        Image xRayIcon = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/icons/icon_XRay.png");
        ImagePattern xRayPattern = new ImagePattern(xRayIcon);
        circle = new Circle(10, Color.GREEN);
        circle.setFill(xRayPattern);
      } else if (equipList.get(i).getType().equalsIgnoreCase("INP")) {
        Image INPIcon = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/icons/icon_Inp.png");
        ImagePattern INPPattern = new ImagePattern(INPIcon);
        circle = new Circle(10, Color.VIOLET);
        circle.setFill(INPPattern);
      } else if (equipList.get(i).getType().equalsIgnoreCase("REC")) {
        Image reclinerIcon =
            new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/icons/icon_Recliner.png");
        ImagePattern ReclinerPattern = new ImagePattern(reclinerIcon);
        circle = new Circle(10, Color.YELLOW);
        circle.setFill(ReclinerPattern);
      }
      circle.setCenterX((equipList.get(i).getXCoord()));
      circle.setCenterY((equipList.get(i).getYCoord()) - 8);
      if (Account.getInstance().getEmployee().getType().getAccessLevel() == 5) {
        circle.setOnMousePressed(
            event -> {
              anchorX.set(event.getSceneX());
              anchorY.set(event.getSceneY());
            });
        Circle finalCircle1 = circle;
        circle.setOnMouseDragged(
            event -> {
              finalCircle1.setTranslateX(event.getSceneX() - anchorX.get());
              finalCircle1.setTranslateY(event.getSceneY() - anchorY.get());
            });
        Circle finalCircle = circle;
        circle.setOnMouseReleased(
            event -> {
              finalCircle.setCenterY(finalCircle.getCenterY() + finalCircle.getTranslateY());
              finalCircle.setCenterX(finalCircle.getCenterX() + finalCircle.getTranslateX());
              edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Location loc =
                  snapToClosestLoc(finalCircle.getCenterX(), finalCircle.getCenterY());
              finalCircle.setCenterX(loc.getXCoord());
              finalCircle.setCenterY(loc.getYCoord() - 8);
              medEquip eq = equipList.get(eqDots.indexOf(finalCircle));
              eq.setXCoord(loc.getXCoord());
              eq.setYCoord(loc.getYCoord() - 8);
              eq.setHomeLoc(loc);
              try {
                equipController.change(
                    new MedEquip(
                        eq.getMedID(),
                        eq.getType(),
                        loc.getNodeID(),
                        equipController.getMedEquip(eq.getMedID()).getStatus().getValue()));
              } catch (SQLException e) {
                e.printStackTrace();
              } catch (NonExistingMedEquip e) {
                e.printStackTrace();
              } catch (StatusError e) {
                e.printStackTrace();
              }
              finalCircle.setTranslateX(0);
              finalCircle.setTranslateY(0);
              anchorX.set(0.0);
              anchorY.set(0.0);
            });
      }
      eqDots.add(circle);
      scrollGroup.getChildren().add(circle);
    }
  }

  private edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Location snapToClosestLoc(double x, double y) {
    int bestFit = 0;
    double bestDist = 10000;
    for (int i = 0; i < currFloorLoc.size(); i++) {
      double euclid =
          Math.sqrt(
              Math.pow((currFloorLoc.get((i)).getXCoord() - x), 2)
                  + (Math.pow((currFloorLoc.get((i)).getYCoord() - y), 2)));
      if (euclid < bestDist) {
        bestFit = Integer.valueOf(i);
        bestDist = Double.valueOf(euclid);
      }
    }
    return currFloorLoc.get(bestFit);
  }

  private void removeMarkers() {
    scrollGroup.getChildren().removeAll(locDots);
    scrollGroup.getChildren().removeAll(eqDots);
    scrollGroup.getChildren().removeAll(reqDots);
    locDots.clear();
    eqDots.clear();
    reqDots.clear();
  }

  public void locView(String nodeID) throws SQLException, IOException, NonExistingMedEquip {
    SceneManager.getInstance()
        .putInformation(SceneManager.getInstance().getPrimaryStage(), "updateLoc", nodeID);
    Stage S = SceneManager.getInstance().openWindow("locationInfoPage.fxml");
    refresh();
  }

  public void testUpdate(String nodeID) throws SQLException, IOException, NonExistingMedEquip {
    SceneManager.getInstance()
        .putInformation(SceneManager.getInstance().getPrimaryStage(), "updateLoc", nodeID);
    Stage S = SceneManager.getInstance().openWindow("UpdateMapPage.fxml");
    refresh();
  }

  public void resetCSV(ActionEvent actionEvent) throws Exception {
    if (loaded) {

    } else {
      locationManager.addLocation(new Location("HOLD", -1, -1, "HOLD", null, null, null, null));
      loaded = true;
    }
    File inputCSV = fileChooser.showOpenDialog(SceneManager.getInstance().getPrimaryStage());
    moveVals();

    CSVController csvController = new CSVController();

    locationManager.clearLocations();
    csvController.insertIntoLocationsTable(csvController.importCSVfromFile(inputCSV));
    refresh();
  }

  public void expCSV(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Export Location");
    File file = fileChooser.showSaveDialog(SceneManager.getInstance().getPrimaryStage());
    locationManager.exportLocationsToChosen(file);
  }

  public void addLocation2(javafx.scene.input.MouseEvent mouseEvent)
      throws IOException, SQLException, NonExistingMedEquip {
    if (interactionState == InteractionStates.Modify) {
      Point p = new Point();
      p.x = (int) mouseEvent.getX();
      p.y = (int) mouseEvent.getY();
      SceneManager.getInstance()
          .putInformation(SceneManager.getInstance().getPrimaryStage(), "addLoc", p);
      SceneManager.getInstance()
          .putInformation(SceneManager.getInstance().getPrimaryStage(), "floor", currFloor);
      Stage S = SceneManager.getInstance().openWindow("popUpViews/newLocationPage.fxml");
      refresh();
    }
  }

  public void swapFloor4(ActionEvent actionEvent) throws SQLException, NonExistingMedEquip {
    currFloor = "04";
    removeMarkers();
    refresh();
    dropdown.setText("Floor 4");
    mapList.setImage(img4);
  }

  public void swapFloor5(ActionEvent actionEvent) throws SQLException, NonExistingMedEquip {
    currFloor = "05";
    removeMarkers();
    refresh();
    dropdown.setText("Floor 5");
    mapList.setImage(img5);
  }

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.MapEditor;
  }

  @Override
  public void onLoad() {
    scaleSlider.setMin(0.9);
    scaleSlider.setMax(3.0);
    scaleSlider.setValue(1.0);

    scrollGroup.scaleXProperty().bind(scaleSlider.valueProperty());
    scrollGroup.scaleYProperty().bind(scaleSlider.valueProperty());
    mapList.autosize();
    scrollGroup.autosize();

    panButton.setSelected(true);
    scrollPane.setPannable(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    try {
      refresh();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (NonExistingMedEquip e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onUnload() {}

  private void moveVals() throws SQLException, NonExistingMedEquip {
    ArrayList<MedEquip> eqList = equipController.getAllMedEquip();
    for (MedEquip medEquip : eqList) {
      medEquip.setNodeID("HOLD");
      equipController.change(medEquip);
    }
    ArrayList<Request> lsr = labServiceRequestManager.getAllRequests();
    for (int i = 0; i < lsr.size(); i++) {
      labServiceRequestManager.changeLoc((LabServiceRequest) lsr.get(i), "HOLD");
    }
    ArrayList<Request> medr = MedRequestManager.getMedRequestManager().getAllRequests();
    for (int i = 0; i < medr.size(); i++) {
      medRequestManager.changeRequest((MedRequest) medr.get(i));
    }
    ArrayList<Request> eqrl = medEquipRequestManager.getAllRequests();
    for (int i = 0; i < eqrl.size(); i++) {
      medEquipRequestManager.changeReq((MedEquipRequest) eqrl.get(i), "HOLD");
    }
  }

  private void generateRequestList() throws SQLException, NonExistingMedEquip {
    reqList.clear();
    ArrayList<Request> rList = new ArrayList<>();
    rList.addAll(medRequestManager.getAllRequests());
    rList.addAll(medEquipRequestManager.getAllRequests());
    rList.addAll(labServiceRequestManager.getAllRequests());
    for (int i = 0; i < rList.size(); i++) {
      for (int j = 0; j < currFloorNodeID.size(); j++) {
        if (rList.get(i).getNodeID().equalsIgnoreCase(currFloorLoc.get(j).getNodeID())) {
          reqList.add(
              new Requests(
                  rList.get(i).getStatus().getString(),
                  rList.get(i).getEmployeeID(),
                  rList.get(i).getEmergency(),
                  rList.get(i).getRequestID(),
                  currFloorLoc.get(j).getXCoord(),
                  currFloorLoc.get(j).getYCoord()));
        }
      }
    }
  }

  private void generateRequestDots() {
    size = reqList.size();
    for (int i = 0; i < size; i++) {
      Circle circ = new Circle(3, Color.BLACK);
      circ.setCenterX((reqList.get(i).getXcoord()));
      circ.setCenterY((reqList.get(i).getYcoord()) - 8);
      reqDots.add(circ);
      scrollGroup.getChildren().add(circ);
    }
  }
}
