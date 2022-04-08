package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.Location;
import edu.wpi.cs3733.d22.teamW.wDB.LocationController;
import edu.wpi.cs3733.d22.teamW.wDB.LocationDaoImpl;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
  private LocationDaoImpl test;

  {
    try {
      test = new LocationDaoImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  Location loc;

  private LocationController locationController = new LocationController(test);

  public void updateLoc(ActionEvent actionEvent) throws SQLException {
    locationController.changeLocation(
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

  public void onLoad() throws SQLException {
    String locName =
        (String)
            SceneManager.getInstance()
                .getInformation(SceneManager.getInstance().getPrimaryStage(), "updateLoc");
    for (int i = 0; i < locationController.getAllLocations().size(); i++) {
      if (locationController.getAllLocations().get(i).getNodeID().equalsIgnoreCase(locName)) {
        loc = locationController.getAllLocations().get(i);
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
    test.setLocationsList();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      onLoad();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}