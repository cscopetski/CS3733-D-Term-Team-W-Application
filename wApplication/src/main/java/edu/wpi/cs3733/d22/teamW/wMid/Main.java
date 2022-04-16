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
    final String languageFileName = "Languages.csv";

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
            sanitationRequestsFileName,
            languageFileName);

    try {
      csvController.populateTables();
    } catch (Exception e) {
      e.printStackTrace();
    }

    /*
    LanguageManager lm = LanguageManager.getLocationManager();
    lm.addLanguage("French");
    lm.addLanguage("English");
    lm.addLanguage("Spanish");
    lm.addLanguage("Mandarin");
    lm.addLanguage("German");
    lm.addLanguage("Swedish");
    lm.addLanguage("Hindi");
    lm.addLanguage("Portuguese");
    lm.addLanguage("Arabic");
    lm.addLanguage("Russian");
    lm.addLanguage("Cantonese");
    lm.addLanguage("Bengali");
    lm.addLanguage("Indonesian");

    lm.exportLocationsCSV(languageFileName);
     */

    // App.launch(App.class, args);

  }
}
