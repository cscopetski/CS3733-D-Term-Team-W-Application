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
        this.location = location;
        this.alertType = alertType;
    }
    public ArrayList<MedEquip> getListEquip(){
        return listOfEquipments;
    }
    public EquipAlertType equipAlert(){
        return alertType;
    }
    public String getLongName(){
        try {
            return LocationManager.getLocationManager().getLocation(location).getLongName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getFloorNum(){
        try {
            return LocationManager.getLocationManager().getLocation(location).getFloor();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
