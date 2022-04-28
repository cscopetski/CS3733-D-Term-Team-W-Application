package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.AccountManager;
import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipType;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddEquipmentDetailController implements Initializable {

    @FXML AutoCompleteInput equipTypeChoice;
    @FXML AutoCompleteInput location;
    @FXML TextField quantity;
    Alert confirm = new ConfirmAlert();
    Alert emptyFields = new EmptyAlert();
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

    public void submitButton(ActionEvent actionEvent) throws SQLException, NonExistingMedEquip, StatusError {
        if (!emptyFields()) {
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {
                ArrayList<String> equipIDList = new ArrayList<>();
                for(int a = 0; a< Integer.parseInt(quantity.getText()) ; a++) {
                    MedEquip output = MedEquipManager.getMedEquipManager().add(MedEquipType.getMedEquipFromString(equipTypeChoice.getSelectionModel().getSelectedItem()), locationToNodeID(location.getSelectionModel().getSelectedItem()));
                    equipIDList.add(output.getMedID());
                }
                WindowManager.getInstance().storeData("ListOfProducedEquip", equipIDList);
                WindowManager.getInstance().storeData("LocationOfAddedEquip", location.getSelectionModel().getSelectedItem());
                WindowManager.getInstance().storeData("EquipType", equipTypeChoice.getSelectionModel().getSelectedItem());
                clearFields();
                fadeOut.playFromStart();
                WindowManager.getInstance().openWindow("popUpViews/CreatedEquipmentInfo.fxml");
                Stage stage = ((Stage)WindowManager.getInstance().getData("stage"));
                WindowManager.getInstance().getPrimaryStage().getScene().getRoot().setEffect(null);
                stage.close();
            }
        } else {
            emptyFields.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            onLoad();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onLoad() throws SQLException {
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        if(AccountManager.getInstance().getEmployee().getType().equals(EmployeeType.Admin)) {
            equipTypeChoice.loadValues(getEquipmentList());
            location.loadValues(getLocations());
        }
    }

    private boolean emptyFields() {
        return location.getSelectionModel().isEmpty();
    }


    private void clearFields() {
        location.getSelectionModel().clearSelection();
    }


    private ArrayList<String> getEquipmentList() {
        ArrayList<String> medEquipTypeList = new ArrayList<>();
        for(MedEquipType type : MedEquipType.values()){
            if(!type.equals(MedEquipType.NONE)) {
                medEquipTypeList.add(type.getString());
            }
        }
        return medEquipTypeList;
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
}
