package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws SQLException, FileNotFoundException {

    DBConnectionMode.INSTANCE.setEmbeddedConnection();

    final String locationFileName = "TowerLocations.csv";
    final String medEquipFileName = "MedicalEquipment.csv";
    final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
    final String labServiceRequestFileName = "LabRequests.csv";
    final String employeesFileName = "Employees.csv";
    final String medRequestFileName = "MedRequests.csv";

    DBController.getDBController();

    CSVController csvController =
        new CSVController(
            locationFileName,
            medEquipFileName,
            medEquipRequestFileName,
            labServiceRequestFileName,
            employeesFileName,
            medRequestFileName);

    try {
      csvController.populateTables();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    MedEquipRequestManager merc = MedEquipRequestManager.getMedEquipRequestManager();

    MedEquipManager mem = MedEquipManager.getMedEquipManager();

    MedEquipManager.getMedEquipManager().markDirty("BED012", "wSTOR0033");
    MedEquipManager.getMedEquipManager().markDirty("BED013", "wSTOR0033");
    MedEquipManager.getMedEquipManager().markDirty("BED014", "wSTOR0033");
    MedEquipManager.getMedEquipManager().markDirty("BED015", "wSTOR0033");
    MedEquipManager.getMedEquipManager().markDirty("BED016", "wSTOR0033");
    merc.complete(5);
    mem.markDirty("XRY001", "wSTOR0033");
    CleaningRequestManager.getCleaningRequestManager().complete(26, "wSTOR0013");

    App.launch(App.class, args);
  }
}
