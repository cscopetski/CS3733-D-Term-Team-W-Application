package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.enums.EquipAlertType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class AlertInfoWrapper {

    ArrayList<MedEquip> listOfEquipments;
    String location;
    EquipAlertType alertType;

    public AlertInfoWrapper(ArrayList<MedEquip> listOfEquipments, String location, EquipAlertType alertType){
        this.listOfEquipments = listOfEquipments;
        this.location = location;
        this.alertType = alertType;
    }

}
