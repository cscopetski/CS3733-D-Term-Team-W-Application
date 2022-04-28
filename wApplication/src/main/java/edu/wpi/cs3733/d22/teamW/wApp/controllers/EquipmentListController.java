package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.AccountManager;
import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.medEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

public class EquipmentListController implements Initializable {
    @FXML
    private TableView<medEquip> eqTab;
    @FXML
    private AutoCompleteInput location;
@FXML
private Button addEquipButton;
@FXML
private Button deleteEquipButton;
    private medEquip selected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            onLoad();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onLoad() throws SQLException {

        ArrayList<medEquip> eqList = new ArrayList<>();
        ArrayList<MedEquip> equips;
        if(AccountManager.getInstance().getEmployee().getType().equals(EmployeeType.Admin)){
            addEquipButton.setVisible(true);
            deleteEquipButton.setVisible(true);
            addEquipButton.setDisable(false);
            deleteEquipButton.setDisable(false);
        } else{
            addEquipButton.setVisible(false);
            deleteEquipButton.setVisible(false);
            addEquipButton.setDisable(true);
            deleteEquipButton.setDisable(true);
        }
        try {
            equips = MedEquipManager.getMedEquipManager().getAllMedEquip();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < equips.size(); i++) {
            if (!(equips.get(i).getMedID().equals("NONE") || equips.get(i).getMedID().equals("DELETED"))) {
                Location location = LocationManager.getLocationManager().getLocation(equips.get(i).getNodeID());
                medEquip equip = new medEquip(
                        equips.get(i).getMedID(), equips.get(i).getStatus(), location.getLongName());
                equip.setFloorNum(location.getFloor());
                eqList.add(equip);
            }
        }
        location.loadValues(getLocations());
        eqTab.getItems().clear();
        eqTab.getItems().addAll(eqList);
    }

    public void setSelected() {
        selected = eqTab.getSelectionModel().getSelectedItem();
    }

    public void markClean() throws Exception {
        String nodeID = null;
        if (selected != null) {
            if (!location.getSelectionModel().isEmpty()) {
                nodeID = locationToNodeID(location.getSelectionModel().getSelectedItem().toString());

            } else {
                nodeID = LocationManager.getLocationManager().getLocation(selected.getFloor(), selected.getFloorNum()).getNodeID();
            }
            MedEquipManager.getMedEquipManager().markClean(selected.getMedID(), nodeID);
            onLoad();
        } else {
            Alert alert =
                    new Alert(
                            Alert.AlertType.WARNING,
                            "Select an Equipment",
                            ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void markInUse() throws Exception {
        String nodeID = null;
        if (selected != null) {
            if (!location.getSelectionModel().isEmpty()) {
                nodeID = locationToNodeID(location.getSelectionModel().getSelectedItem().toString());

            } else {
                nodeID = LocationManager.getLocationManager().getLocation(selected.getFloor(), selected.getFloorNum()).getNodeID();
            }
            MedEquipManager.getMedEquipManager().markInUse(selected.getMedID(), nodeID);
            onLoad();
        } else {
            Alert alert =
                    new Alert(
                            Alert.AlertType.WARNING,
                            "Select an Equipment",
                            ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void markDirty() throws Exception {
        String nodeID = null;
        if (selected != null) {
            if (!location.getSelectionModel().isEmpty()) {
                nodeID = locationToNodeID(location.getSelectionModel().getSelectedItem().toString());
            } else {
                nodeID = LocationManager.getLocationManager().getLocation(selected.getFloor(), selected.getFloorNum()).getNodeID();
            }
            MedEquipManager.getMedEquipManager().markDirty(selected.getMedID(), nodeID);
            onLoad();
        } else {
            Alert alert =
                    new Alert(
                            Alert.AlertType.WARNING,
                            "Select an Equipment",
                            ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void moveTo() throws Exception {
        if (selected != null) {
            if (!location.getSelectionModel().isEmpty()) {
                MedEquipManager.getMedEquipManager().moveTo(selected.getMedID(), locationToNodeID(location.getSelectionModel().getSelectedItem().toString()));
                onLoad();
            } else {
                Alert alert =
                        new Alert(Alert.AlertType.WARNING, "Selection Location to move equipment!", ButtonType.OK);
                alert.showAndWait();
            }
        } else {
            Alert alert =
                    new Alert(
                            Alert.AlertType.WARNING,
                            "Select an Equipment",
                            ButtonType.OK);
            alert.showAndWait();
        }
    }

    private String locationToNodeID(String target) {
        String nodeID = "FAIL";

        ArrayList<Location> locationsRaw = null;
        try {
            locationsRaw = LocationManager.getLocationManager().getAllLocations();
        } catch (SQLException e) {
            System.out.println("Failed to unearth locations from database");
            e.printStackTrace();
        }
        for (Location l : locationsRaw) {
            if (l.getLongName().equals(target)) {
                nodeID = l.getNodeID();
            }
        }
        return nodeID;
    }

    private ArrayList<String> getLocations() {
        ArrayList<String> locations = new ArrayList<>();
        ArrayList<Location> locationsRaw = null;
        ArrayList<Integer> removeIndexes = new ArrayList<>();
        try {
            locationsRaw = LocationManager.getLocationManager().getAllLocations();
        } catch (SQLException e) {
            System.out.println("Failed to unearth locations from database");
            e.printStackTrace();
        }
        for (Location l : locationsRaw) {
            if (l.getNodeType().equals("NONE")) {
            } else locations.add(l.getLongName());
        }
        return locations;
    }

    public void addEquip() throws SQLException {
        WindowManager.getInstance().openWindow("popUpViews/AddEquipmentDetail.fxml");
        onLoad();
    }

    public void deleteEquip(){
        if (selected != null) {
        try {
            MedEquipManager.getMedEquipManager().delete(selected.getMedID());
            onLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
        } else {
            Alert alert =
                    new Alert(
                            Alert.AlertType.WARNING,
                            "Select an Equipment",
                            ButtonType.OK);
            alert.showAndWait();
        }
    }

}
