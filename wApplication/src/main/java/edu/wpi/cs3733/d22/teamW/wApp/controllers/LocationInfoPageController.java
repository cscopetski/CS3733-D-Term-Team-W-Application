package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Requests;
import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.medEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;

public class LocationInfoPageController implements Initializable {
  public Button cancelButton;
  public Button resetButton;
  public Button updateButton;
  private String nodeID = "";
  @FXML private Label nodeField;
  @FXML private Label xField;
  @FXML private Label yField;
  @FXML private Label floorField;
  @FXML private Label typeField;
  @FXML private Label lnameField;
  @FXML private Label snameField;
  @FXML private Label buildingField;
  @FXML private TableView<medEquip> EqTab;
  @FXML private TableView<Requests> ReqTab;
  @FXML private TableView<medEquip> EqDashTab;
  private ArrayList<medEquip> equipList = new ArrayList<>();
  private ArrayList<Requests> reqList = new ArrayList<>();
  @FXML private Alert confirmChoice = new Alert(Alert.AlertType.CONFIRMATION);
  Location loc;
  private MedEquipManager equipController = MedEquipManager.getMedEquipManager();
  private LocationManager locationManager = LocationManager.getLocationManager();
  private MedEquipRequestManager medEquipRequestManager =
      MedEquipRequestManager.getMedEquipRequestManager();
  private LabServiceRequestManager labServiceRequestManager =
      LabServiceRequestManager.getLabServiceRequestManager();
  private MedRequestManager medRequestManager = MedRequestManager.getMedRequestManager();

  public void cancelUpdate(ActionEvent actionEvent) {
    Stage stage = (Stage) WindowManager.getInstance().getData("Stage");
    stage.close();
    WindowManager.getInstance().getPrimaryStage().getScene().getRoot().setEffect(new GaussianBlur(0));
  }

  private void generateRequestList() throws SQLException, NonExistingMedEquip {
    reqList.clear();
    ArrayList<Request> rList = new ArrayList<>();
    rList.addAll(medRequestManager.getAllRequests());
    rList.addAll(medEquipRequestManager.getAllRequests());
    rList.addAll(labServiceRequestManager.getAllRequests());
    for (int i = 0; i < rList.size(); i++) {
      if (rList.get(i).getNodeID().equalsIgnoreCase(loc.getNodeID())) {
        reqList.add(
            new Requests(
                rList.get(i).getStatus().getString(),
                rList.get(i).getEmployeeID(),
                rList.get(i).getEmergency(),
                rList.get(i).getRequestID()));
      }
    }
    ReqTab.getItems().clear();
    ReqTab.getItems().addAll(reqList);
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
      if (eqList.get(i).getNodeID().equalsIgnoreCase(loc.getNodeID())) {
        equipList.add(
            new medEquip(
                eqList.get(i).getMedID(),
                eqList.get(i).getType().getString(),
                eqList.get(i).getStatus()));
      }
    }
    EqTab.getItems().clear();
    EqTab.getItems().addAll(equipList);
  }

  public void onLoad() throws SQLException, NonExistingMedEquip {
    String locName =
        (String)
            WindowManager.getInstance()
                .getData( "updateLoc");
    for (int i = 0; i < locationManager.getAllLocations().size(); i++) {
      if (locationManager.getAllLocations().get(i).getNodeID().equalsIgnoreCase(locName)) {
        loc = locationManager.getAllLocations().get(i);
        break;
      }
    }
    xField.setText("" + Integer.valueOf(loc.getxCoord()));
    yField.setText("" + Integer.valueOf(loc.getyCoord()));
    typeField.setText(loc.getNodeType());
    nodeField.setText(loc.getNodeID());
    buildingField.setText(loc.getBuilding());
    floorField.setText(loc.getFloor());
    lnameField.setText(loc.getLongName());
    snameField.setText(loc.getShortName());
    generateEquipList();
    generateRequestList();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      onLoad();
    } catch (SQLException | NonExistingMedEquip e) {
      e.printStackTrace();
    }
  }
}
