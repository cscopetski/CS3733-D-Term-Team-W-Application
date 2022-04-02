package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.CSVController;
import edu.wpi.cs3733.d22.teamW.wDB.DBController;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) {
    App.launch(App.class, args);

    final String locationFileName = "edu/wpi/teamW/CSVs/TowerLocations.csv";
    final String medEquipFileName = "edu/wpi/teamW/CSVs/MedicalEquipment.csv";
    final String medEquipRequestFileName = "edu/wpi/teamW/CSVs/MedicalEquipmentRequest.csv";

    DBController.getDBController();

    CSVController csvController =
        new CSVController(locationFileName, medEquipFileName, medEquipRequestFileName);

    try {
      csvController.populateTables();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
