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

    DBController.getDBController();

    CSVController csvController =
        new CSVController(locationFileName, medEquipFileName, medEquipRequestFileName);

    try {
      csvController.populateEntityTables();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }


    MedEquipDaoImpl medi = new MedEquipDaoImpl();
    MedEquipRequestDaoImpl merdi = new MedEquipRequestDaoImpl();
    MedEquipRequestController merc = new MedEquipRequestController(merdi, medi);

    RequestFactory requestFactory = new RequestFactory(merc);


    csvController.populateRequestTables(requestFactory);


    ArrayList<String> fields = new ArrayList<>();

    fields.add("BED");
    fields.add("wSTOR001L1");
    fields.add("JOE NAME");
    fields.add("" + 0);


    ArrayList<String> fields2 = new ArrayList<>();

    fields2.add("XRY");
    fields2.add("wSTOR001L1");
    fields2.add("JOE2 NAME");
    fields2.add("" + 0);

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

    Request test = requestFactory.findRequest(12);
    merc.completeRequest(test);
  }
}
