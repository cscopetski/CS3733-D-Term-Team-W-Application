package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws SQLException, FileNotFoundException {

    // App.launch(App.class, args);

    final String locationFileName = "TowerLocations.csv";
    final String medEquipFileName = "MedicalEquipment.csv";
    final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
    final String labServiceRequestFileName = "LabRequests.csv";

    DBController.getDBController();

    CSVController csvController =
        new CSVController(
            locationFileName, medEquipFileName, medEquipRequestFileName, labServiceRequestFileName);

    try {
      csvController.populateEntityTables();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    LocationDaoImpl locationDao = new LocationDaoImpl();
    LocationController locationController = new LocationController(locationDao);

    MedEquipDaoImpl medi = new MedEquipDaoImpl();
    MedEquipRequestDaoImpl merdi = new MedEquipRequestDaoImpl();
    MedEquipController medEquipController = new MedEquipController(medi, merdi);
    MedEquipRequestController merc = new MedEquipRequestController(merdi, medi);

    LabServiceRequestDaoImpl labServiceRequestDao = new LabServiceRequestDaoImpl();
    LabServiceRequestController lsrc = new LabServiceRequestController(labServiceRequestDao);

    RequestFactory requestFactory = RequestFactory.getRequestFactory(merc, lsrc);

    csvController.populateRequestTables(requestFactory);

    // itemType, location(nodeID), employeeName, (String) isEmergency (0=no, 1=yes)

    ArrayList<String> fields = new ArrayList<>();

    fields.add("XRY");
    fields.add("wSTOR001L1");
    fields.add("JOE NAME");
    fields.add("" + 0);

    ArrayList<String> fields2 = new ArrayList<>();

    fields2.add("XRY");
    fields2.add("wSTOR001L1");
    fields2.add("JOE2 NAME");
    fields2.add("" + 1);

    ArrayList<String> fields3 = new ArrayList<>();

    fields3.add("XRY");
    fields3.add("wSTOR001L1");
    fields3.add("JOE2 NAME");
    fields3.add("" + 0);

    try {
      requestFactory.getRequest("MEDEQUIPREQUEST", fields);
      requestFactory.getRequest("MEDEQUIPREQUEST", fields2);
      requestFactory.getRequest("MEDEQUIPREQUEST", fields3);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    /*
        Request test = requestFactory.findRequest(5);
        Request test2 = requestFactory.findRequest(11);
        Request test3 = requestFactory.findRequest(12);
        Request test4 = requestFactory.findRequest(13);
        // completes test
        merc.completeRequest(test);
        // Tries to cancel test but fails since it is completed
        merc.cancelRequest(test);
        // test 2 should be enqueue then cancelled starting test 3
        merc.cancelRequest(test2);
        merc.completeRequest(test2);
        merc.cancelRequest(test3);
        merc.completeRequest(test3);
    */
    locationController.changeLocation(
        locationController.getAllLocations().get(0).getNodeID(),
        Integer.parseInt("100"),
        Integer.parseInt("100"),
        "01",
        "Tower",
        "DEPT",
        "TESTING",
        "TEST");
    App.launch(App.class, args);
  }
}
