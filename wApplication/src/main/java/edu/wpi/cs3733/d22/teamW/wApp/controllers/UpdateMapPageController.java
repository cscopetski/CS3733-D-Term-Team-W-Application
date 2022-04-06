package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.Location;
import edu.wpi.cs3733.d22.teamW.wDB.LocationController;
import edu.wpi.cs3733.d22.teamW.wDB.LocationDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class UpdateMapPageController {
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
                nodeField.getText(),Integer.valueOf(xField.getText()),
                Integer.valueOf(yField.getText()),
                floorField.getText(),
                buildingField.getText(),
                typeField.getText(),lnameField.getText(),snameField.getText());
        onLoad();
    }

    public void resetFields(ActionEvent actionEvent) {
        onLoad();
    }

    public void cancelUpdate(ActionEvent actionEvent) {
        //TODO window manager stuff, swap back to map editor
    }
    public void onLoad(){
        xField.setText("" + Integer.valueOf(loc.getxCoord()));
        yField.setText("" + Integer.valueOf(loc.getyCoord()));
        typeField.setText(loc.getNodeType());
        nodeField.setText(loc.getNodeID());
        buildingField.setText(loc.getBuilding());
        floorField.setText(loc.getFloor());
        lnameField.setText(loc.getLongName());
        snameField.setText(loc.getShortName());
    }
}
