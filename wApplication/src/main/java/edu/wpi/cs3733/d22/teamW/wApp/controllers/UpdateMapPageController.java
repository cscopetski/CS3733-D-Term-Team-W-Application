package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpdateMapPageController implements Initializable {
  public Button cancelButton;
  public Button resetButton;
  public Button updateButton;
  private String nodeID = "";
  @FXML private TextField nodeField;
  @FXML private TextField xField;
  @FXML private TextField yField;
  @FXML private TextField floorField;
  @FXML private TextField typeField;
  @FXML private TextField lnameField;
  @FXML private TextField snameField;
  @FXML private TextField buildingField;
  @FXML private TableView<medEquip> EqTab;
  private ArrayList<medEquip> equipList = new ArrayList<>();
  @FXML private Alert confirmChoice = new Alert(Alert.AlertType.CONFIRMATION);
  Location loc;
  private MedEquipManager equipController = MedEquipManager.getMedEquipManager();
  private LocationManager locationManager = LocationManager.getLocationManager();

  public void updateLoc(ActionEvent actionEvent) throws SQLException {

    Optional<ButtonType> result = confirmChoice.showAndWait();
    if (result.get() == ButtonType.OK) {
      locationManager.changeLocation(
          nodeField.getText(),
          Integer.parseInt(xField.getText()),
          Integer.parseInt(yField.getText()),
          floorField.getText(),
          buildingField.getText(),
          typeField.getText(),
          lnameField.getText(),
          snameField.getText());
      onLoad();
    }

    locationManager.changeLocation(
        nodeField.getText(),
        Integer.parseInt(xField.getText()),
        Integer.parseInt(yField.getText()),
        floorField.getText(),
        buildingField.getText(),
        typeField.getText(),
        lnameField.getText(),
        snameField.getText());
    onLoad();
  }

  public void resetFields(ActionEvent actionEvent) throws SQLException {
    onLoad();
  }

  public void cancelUpdate(ActionEvent actionEvent) {
    ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
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
                eqList.get(i).getMedID(), eqList.get(i).getType(), eqList.get(i).getStatus()));
      }
    }
    EqTab.getItems().clear();
    EqTab.getItems().addAll(equipList);
  }

  public void onLoad() throws SQLException {
    String locName =
        (String)
            SceneManager.getInstance()
                .getInformation(SceneManager.getInstance().getPrimaryStage(), "updateLoc");
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
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      onLoad();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void removeLoc(ActionEvent actionEvent) throws SQLException {
    Optional<ButtonType> result = confirmChoice.showAndWait();
    if (result.get() == ButtonType.OK) {
      locationManager.deleteLocation(loc.getNodeID());
      cancelUpdate(actionEvent);
    }
  }
}
