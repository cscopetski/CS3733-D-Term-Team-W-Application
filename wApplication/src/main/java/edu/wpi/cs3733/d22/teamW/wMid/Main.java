package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
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
    final String languageInterpreterFilename = "LanguageInterpreter.csv";

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
            languageFileName,
            languageInterpreterFilename);

    try {
      csvController.populateTables();
    } catch (Exception e) {
      e.printStackTrace();
    }

    /*
    LanguageInterpreterManager lim = LanguageInterpreterManager.getLanguageInterpreterManager();
    ArrayList<String> fields = new ArrayList<>();
    fields.add("1");
    fields.add("Spanish");

    ArrayList<String> fields2 = new ArrayList<>();
    fields2.add("4");
    fields2.add("Cantonese");

    ArrayList<String> field = new ArrayList<>();
    field.add("4");
    field.add("Spanish");

    LanguageInterpreter test = lim.addLanguageInterpreter(fields);
    LanguageInterpreter test2 = lim.addLanguageInterpreter(fields2);
    LanguageInterpreter test3 = lim.addLanguageInterpreter(field);

    lim.exportReqCSV("LanguageInterpreter.csv");

     */

    // App.launch(App.class, args);

  }
}
