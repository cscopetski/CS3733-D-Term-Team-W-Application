package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.Timestamp;
import java.util.ArrayList;

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

    ArrayList<String> fields = new ArrayList<>();
    fields.add("27");
    fields.add("BED001");
    fields.add("FDEPT00101");
    fields.add("1");
    fields.add("0");
    fields.add("2");
    fields.add((new Timestamp(System.currentTimeMillis())).toString());
    fields.add((new Timestamp(System.currentTimeMillis())).toString());

    ArrayList<String> fields2 = new ArrayList<>();
    fields2.add("28");
    fields2.add("REC008");
    fields2.add("FDEPT00101");
    fields2.add("3");
    fields2.add("0");
    fields2.add("3");
    fields2.add((new Timestamp(System.currentTimeMillis())).toString());
    fields2.add((new Timestamp(System.currentTimeMillis())).toString());

    RequestFactory.getRequestFactory().getRequest(RequestType.CleaningRequest, fields, true);
    RequestFactory.getRequestFactory().getRequest(RequestType.CleaningRequest, fields2, true);

    CleaningRequestManager.getCleaningRequestManager().exportReqCSV("CleaningRequest.csv");
    // App.launch(App.class, args);

  }
}
