package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;

public class Main {

  public static void main(String[] args) throws Exception {

    DBConnectionMode.INSTANCE.setEmbeddedConnection();

    final String locationFileName = "TowerLocations.csv";
    final String medEquipFileName = "MedicalEquipment.csv";
    final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
    final String labServiceRequestFileName = "LabRequests.csv";
    final String employeesFileName = "Employees.csv";
    final String medRequestFileName = "MedRequests.csv";
    final String flowerRequestFileName = "FlowerRequests.csv";
    final String computerServiceRequestFileName = "ComputerServiceRequest.csv";
    final String sanitationRequestsFileName = "SanitationRequests.csv";

    DBController.getDBController();

    CSVController csvController =
        new CSVController(
            locationFileName,
            medEquipFileName,
            medEquipRequestFileName,
            labServiceRequestFileName,
            employeesFileName,
            medRequestFileName,
            flowerRequestFileName,
            computerServiceRequestFileName,
            sanitationRequestsFileName);

    try {
      csvController.populateTables();
    } catch (Exception e) {
      e.printStackTrace();
    }

    App.launch(App.class, args);
  }
}
