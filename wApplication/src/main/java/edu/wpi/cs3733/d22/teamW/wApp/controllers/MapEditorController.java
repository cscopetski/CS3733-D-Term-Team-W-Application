package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Requests;
import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.medEquip;
import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MapEditorController extends LoadableController {
  @FXML public ScrollPane scrollPane;
  @FXML public Slider scaleSlider;
  @FXML public Group scrollGroup;
  @FXML private ImageView mapList;
  @FXML private MenuItem F1;
  @FXML private MenuItem F2;
  @FXML private MenuItem F3;
  @FXML private MenuItem FL1;
  @FXML private MenuItem FL2;
  @FXML private MenuItem Side;
  @FXML private MenuButton dropdown;
  @FXML private AnchorPane page;
  @FXML private TableView<edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Location> LocTab;
  @FXML private TableView<medEquip> EqTab;
  @FXML private FileChooser fileChooser = new FileChooser();
  Image img1 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F1.png");
  Image img2 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F2.png");
  Image img3 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F3.png");
  Image imgL1 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/L1.png");
  Image imgL2 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/L2.png");
  Image img = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/SideView.jpg");
  Image img4 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F4.png");
  Image img5 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F5.png");
  ArrayList<Circle> locDots = new ArrayList<>();
  ArrayList<Circle> eqDots = new ArrayList<>();
  ArrayList<Circle> reqDots = new ArrayList<>();
  Integer size = 0;
  Integer xOffSet = 396;
  Integer yOffSet = 63;
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
  private boolean loaded = false;

  public void refresh() throws SQLException {
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
    generateMarkers();
    generateEquipMarkers();
    generateRequestDots();
    LocTab.getSelectionModel().clearSelection();
    EqTab.getSelectionModel().clearSelection();
  }

  public void swapFloor1(ActionEvent actionEvent) throws SQLException {
    removeMarkers();
    currFloor = "01";
    refresh();
    System.out.println(F1.getText());
    dropdown.setText("Floor 1");
    mapList.setImage(img1);
  }

  public void swapFloor2(ActionEvent actionEvent) throws SQLException {
    removeMarkers();
    currFloor = "02";
    refresh();
    System.out.println(F2.getText());
    dropdown.setText("Floor 2");
    mapList.setImage(img2);
  }

  public void swapFloor3(ActionEvent actionEvent) throws SQLException {
    removeMarkers();
    currFloor = "03";
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
    mapList.setImage(imgL1);
  }

  public void swapFloorL2(ActionEvent actionEvent) throws SQLException {
    currFloor = "L2";
    removeMarkers();
    refresh();
    System.out.println(FL2.getText());
    dropdown.setText("Lower Floor 2");
    mapList.setImage(imgL2);
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
      Circle circ = new Circle(12, Color.RED);
      Image locationIcon =
          new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/icons/icon_Location.png");
      ImagePattern bedPattern = new ImagePattern(locationIcon);
      circ.setFill(bedPattern);
      circ.setCenterX((currFloorLoc.get(i).getXCoord() * 2.0 / 3.0));
      circ.setCenterY((currFloorLoc.get(i).getYCoord() * 2.0 / 3.0));

      circ.setOnMouseClicked(
          (event -> {
            try {
              testUpdate(currFloorLoc.get(locDots.indexOf(event.getSource())).getNodeID());
            } catch (SQLException | IOException e) {
              e.printStackTrace();
            }
          }));
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
      Circle circle = new Circle(10, Color.TRANSPARENT);
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
      circle.setCenterX((equipList.get(i).getXCoord() * 2.0 / 3.0) - 1);
      circle.setCenterY((equipList.get(i).getYCoord() * 2.0 / 3.0) - 1);
      eqDots.add(circle);
      scrollGroup.getChildren().add(circle);
    }
  }

  private void removeMarkers() {
    scrollGroup.getChildren().removeAll(locDots);
    scrollGroup.getChildren().removeAll(eqDots);
    scrollGroup.getChildren().removeAll(reqDots);
    locDots.clear();
    eqDots.clear();
    reqDots.clear();
  }

  public void testUpdate(String nodeID) throws SQLException, IOException {
    SceneManager.getInstance()
        .putInformation(SceneManager.getInstance().getPrimaryStage(), "updateLoc", nodeID);
    Stage S = SceneManager.getInstance().openWindow("UpdateMapPage.fxml");
    refresh();
  }

  public void resetCSV(ActionEvent actionEvent) throws SQLException, FileNotFoundException {
    if (loaded) {

    } else {
      locationManager.addLocation("HOLD", -1, -1, "HOLD", null, null, null, null);
      loaded = true;
    }
    File inputCSV = fileChooser.showOpenDialog(SceneManager.getInstance().getPrimaryStage());
    moveVals();
    final String locationFileName = inputCSV.getName();
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
    csvController.insertIntoLocationsTable(csvController.importCSVfromFile(inputCSV));
    refresh();
  }

  public void expCSV(ActionEvent actionEvent) {
    locationManager.exportLocationsCSV("output.csv");
  }

  public void addLocation2(javafx.scene.input.MouseEvent mouseEvent)
      throws IOException, SQLException {
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

  public void swapFloor4(ActionEvent actionEvent) throws SQLException {
    currFloor = "04";
    removeMarkers();
    refresh();
    dropdown.setText("Floor 4");
    mapList.setImage(img4);
  }

  public void swapFloor5(ActionEvent actionEvent) throws SQLException {
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

    scrollPane.setPannable(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
  }

  @Override
  public void onUnload() {}

  private void moveVals() throws SQLException {
    ArrayList<MedEquip> eqList = equipController.getAllMedEquip();
    for (int i = 0; i < eqList.size(); i++) {
      equipController.change(
          eqList.get(i).getMedID(), eqList.get(i).getType(), "HOLD", eqList.get(i).getStatus());
    }
    ArrayList<Request> lsr = labServiceRequestManager.getAllRequests();
    for (int i = 0; i < lsr.size(); i++) {
      labServiceRequestManager.changeLoc((LabServiceRequest) lsr.get(i), "HOLD");
    }
    ArrayList<Request> medr = MedRequestManager.getMedRequestManager().getAllRequests();
    for (int i = 0; i < medr.size(); i++) {
      medRequestManager.changeMedRequest(
          medr.get(i).getRequestID(),
          ((MedRequest) medr.get(i)).getMedicine(),
          "HOLD",
          medr.get(i).getEmployeeID(),
          medr.get(i).getEmergency(),
          medr.get(i).getStatus(),
          medr.get(i).getCreatedTimestamp(),
          medr.get(i).getUpdatedTimestamp());
    }
    ArrayList<Request> eqrl = medEquipRequestManager.getAllRequests();
    for (int i = 0; i < eqrl.size(); i++) {
      medEquipRequestManager.changeReq((MedEquipRequest) eqrl.get(i), "HOLD");
    }
  }

  private void generateRequestList() throws SQLException {
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
      circ.setCenterX((reqList.get(i).getXcoord() * 2.0 / 3.0) - 1);
      circ.setCenterY((reqList.get(i).getYcoord() * 2.0 / 3.0) - 1);
      reqDots.add(circ);
      scrollGroup.getChildren().add(circ);
    }
  }
}
