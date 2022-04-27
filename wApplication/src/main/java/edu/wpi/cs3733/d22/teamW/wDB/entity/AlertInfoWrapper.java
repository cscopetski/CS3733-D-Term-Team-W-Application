package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.enums.EquipAlertType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class AlertInfoWrapper {

    ArrayList<String> listOfEquipments;
    String location;
    EquipAlertType exception;

    public AlertInfoWrapper(ArrayList<String> listOfEquipments, String location, EquipAlertType exception){
        this.listOfEquipments = listOfEquipments;
        this.location = location;
        this.exception = exception;
    }

}
