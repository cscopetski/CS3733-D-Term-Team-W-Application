package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;

public class newLocationPageController implements Initializable {
  public Button cancelButton;
  public Button resetButton;
  public Button addButton;
  private String nodeID = "";
  @FXML private TextField nodeField;
  @FXML private TextField xField;
  @FXML private TextField yField;
  @FXML private TextField floorField;
  @FXML private TextField typeField;
  @FXML private TextField lnameField;
  @FXML private TextField snameField;
  @FXML private TextField buildingField;
  private Point p;
  @FXML private Alert confirmChoice = new Alert(Alert.AlertType.CONFIRMATION);

  private LocationManager locationManager = LocationManager.getLocationManager();

  public void resetFields(ActionEvent actionEvent) throws SQLException {
    onLoad();
  }

  public void addLoc(ActionEvent actionEvent) throws SQLException {
    Optional<ButtonType> result = confirmChoice.showAndWait();
    if (checkFull()) {
      if (result.get() == ButtonType.OK) {
        locationManager.addLocation(
            new Location(
                nodeField.getText(),
                Integer.parseInt(xField.getText()),
                Integer.parseInt(yField.getText()),
                floorField.getText(),
                buildingField.getText(),
                typeField.getText(),
                lnameField.getText(),
                snameField.getText()));
      }
    }
  }

  private boolean checkFull() {
    if (nodeField.getText().isEmpty()
        || xField.getText().isEmpty()
        || floorField.getText().isEmpty()
        || buildingField.getText().isEmpty()
        || typeField.getText().isEmpty()
        || lnameField.getText().isEmpty()
        || snameField.getText().isEmpty()) {
      return false;
    }
    return true;
  }

  public void cancelUpdate(ActionEvent actionEvent) {
    Stage stage = (Stage) WindowManager.getInstance().getData("Stage");
    stage.close();
    WindowManager.getInstance().getPrimaryStage().getScene().getRoot().setEffect(new GaussianBlur(0));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    onLoad();
  }

  private void onLoad() {
    p = (Point) WindowManager.getInstance().getData("addLoc");
    xField.setText(p.x + "");
    yField.setText(p.y + "");
    floorField.setText((String) WindowManager.getInstance().getData("floor"));
  }
}
