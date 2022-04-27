package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EquipAlertType;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.util.ArrayList;


@Getter
@Setter
public class AlertInfoWrapper {

    ArrayList<MedEquip> listOfEquipments;
    String location;
    EquipAlertType alertType;

    public AlertInfoWrapper(ArrayList<MedEquip> listOfEquipments, String location, EquipAlertType alertType) throws SQLException {
        this.listOfEquipments = listOfEquipments;
        this.location = LocationManager.getLocationManager().getLocation(location).getLongName();
        this.alertType = alertType;
    }
    public ArrayList<MedEquip> getListEquip(){
        return listOfEquipments;
    }
    public EquipAlertType equipAlert(){
        return alertType;
    }

}
