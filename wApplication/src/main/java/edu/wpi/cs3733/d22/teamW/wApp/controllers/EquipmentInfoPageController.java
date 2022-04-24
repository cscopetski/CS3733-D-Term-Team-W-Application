package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EquipmentInfoPageController implements Initializable {
    MedEquipManager medEquipManager = MedEquipManager.getMedEquipManager();
    MedEquip medEquip;
    @FXML private Label eqID;
    @FXML private Label eqLoc;
    @FXML private Label eqStatus;
    @FXML private Label eqType;
    public void markClean(ActionEvent actionEvent) throws Exception {
        medEquipManager.markClean(medEquip.getMedID(), medEquip.getNodeID());
        exit(actionEvent);
    }

    public void markInUse(ActionEvent actionEvent) throws Exception {
        medEquipManager.markInUse(medEquip.getMedID(),medEquip.getNodeID());
        exit(actionEvent);
    }

    public void markDirty(ActionEvent actionEvent) throws Exception {
        medEquipManager.markDirty(medEquip.getMedID(),medEquip.getNodeID());
        exit(actionEvent);
    }

    public void exit(ActionEvent actionEvent) {
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            onLoad();
        } catch (SQLException | NonExistingMedEquip e) {
            e.printStackTrace();
        }
    }
    public void onLoad() throws SQLException, NonExistingMedEquip {
        String equipName =
                (String)
                        WindowManager.getInstance()
                                .getData( "equipment");
        medEquip = medEquipManager.getMedEquip(equipName);
        eqID.setText(medEquip.getMedID());
        eqStatus.setText(medEquip.getStatus().getString());
        eqLoc.setText(medEquip.getNodeID());
        eqType.setText(medEquip.getType().getString());
    }
}
