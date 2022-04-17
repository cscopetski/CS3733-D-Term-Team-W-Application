package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Floor;
import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Requests;
import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.medEquip;
import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
  @FXML private TableView<medEquip> EqDashTab;
  @FXML private FileChooser fileChooser = new FileChooser();
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
  private ArrayList<Floor> floorList = new ArrayList<>();
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
        if (eqList.get(j).getNodeID().equalsIgnoreCase(locList.get(i).getNodeID())) {
          switch (locList.get(i).getFloor()) {
            case "01":
              switchCase(eqList.get(j).getType().getString(), F1);
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "01",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "02":
              switchCase(eqList.get(j).getType().getString(), F2);
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "02",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "03":
              switchCase(eqList.get(j).getType().getString(), F3);
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "03",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "04":
              switchCase(eqList.get(j).getType().getString(), F4);
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "04",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "05":
              switchCase(eqList.get(j).getType().getString(), F5);
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "05",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "L1":
              switchCase(eqList.get(j).getType().getString(), L1);
              equipList.add(
                  new medEquip(
                      eqList.get(j).getMedID(),
                      "L1",
                      locList.get(i).getNodeType(),
                      eqList.get(j).getStatus().getString()));
              break;
            case "L2":
              switchCase(eqList.get(j).getType().getString(), L2);
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
    EqDashTab.getItems().addAll(equipList);
    FloorTab.getItems().clear();
    FloorTab.getItems().addAll(floorList);
  }

  private void switchCase(String eqType, Floor floor) {
    switch (eqType) {
      case "BED":
        floor.setBedCount(floor.getBedCount() + 1);
        break;
      case "XRY":
        floor.setXrayCount(floor.getXrayCount() + 1);
        break;
      case "INP":
        floor.setPumpCount(floor.getPumpCount() + 1);
        break;
      case "REC":
        floor.setReclinCount(floor.getReclinCount() + 1);
        break;
    }
  }

  public void refresh() throws SQLException, NonExistingMedEquip {
    if (currFloor == "0") {
      refreshDash();
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
    generateMarkers();
    generateEquipMarkers();
    generateRequestDots();
    LocTab.getSelectionModel().clearSelection();
    EqTab.getSelectionModel().clearSelection();
    LocTab.setVisible(true);
    EqTab.setVisible(true);
    EqDashTab.setVisible(false);
    FloorTab.setVisible(false);
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

  public void swapSideView(ActionEvent actionEvent) throws SQLException {
    removeMarkers();
    currFloor = "0";
    // refresh();
    refreshDash();
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
      circ.setCenterX((currFloorLoc.get(i).getXCoord()) - 75);
      circ.setCenterY((currFloorLoc.get(i).getYCoord()));
      circ.setOnMouseClicked(
          (event -> {
            try {
              testUpdate(currFloorLoc.get(locDots.indexOf(event.getSource())).getNodeID());
            } catch (SQLException | IOException | NonExistingMedEquip e) {
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
                  eqList.get(i).getType().getString(),
                  eqList.get(i).getStatus()));
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
      circle.setCenterX((equipList.get(i).getXCoord()));
      circle.setCenterY((equipList.get(i).getYCoord()) - 8);
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
    final String locationFileName = inputCSV.getName();
    final String medEquipFileName = "MedicalEquipment.csv";
    final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
    final String labServiceRequestFileName = "LabRequests.csv";
    final String employeesFileName = "Employees.csv";
    final String medRequestFileName = "MedRequests.csv";
    final String computerServiceRequestFileName = "ComputerServiceRequest.csv";
    final String flowerRequestFileName = "FlowerRequests.csv";
    final String languagesFileName = "Languages.csv";
    final String languageInterpFileName = "LanguageInterpreters.csv";
    final String sanitationRequestFileName = "SanitationRequests.csv";
    final String giftDeliveryRequestFileName = "GiftDeliveryRequest.csv";
    final String mealRequestFileName = "MealRequest.csv";

    CSVController csvController =
        new CSVController(
            locationFileName,
            medEquipFileName,
            medEquipRequestFileName,
            labServiceRequestFileName,
            employeesFileName,
            medRequestFileName,
            flowerRequestFileName,
            computerServiceRequestFileName,
            sanitationRequestFileName,
            languagesFileName,
            languageInterpFileName,
            giftDeliveryRequestFileName,
            mealRequestFileName);

    locationManager.clearLocations();
    csvController.insertIntoLocationsTable(csvController.importCSVfromFile(inputCSV));
    refresh();
  }

  public void expCSV(ActionEvent actionEvent) {
    locationManager.exportLocationsCSV("output.csv");
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
      refreshDash();
    } catch (SQLException e) {
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
