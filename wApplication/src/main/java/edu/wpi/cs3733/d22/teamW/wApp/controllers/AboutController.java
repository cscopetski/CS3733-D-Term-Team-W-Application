package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.HospitalMap;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;

import java.sql.SQLException;

public class AboutController {
    private HospitalMap map = new HospitalMap();

    public AboutController() throws NonExistingMedEquip, SQLException {
        map.setPrefSize(500, 800);
    }
}
