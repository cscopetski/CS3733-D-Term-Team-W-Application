package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws SQLException {

    // App.launch(App.class, args);

    final String locationFileName = "TowerLocations.csv";
    final String medEquipFileName = "MedicalEquipment.csv";
    final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";

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

    ArrayList<String> fields = new ArrayList<>();

    fields.add("BED");
    fields.add("wSTOR001L1");
    fields.add("JOE NAME");
    fields.add("" + 0);
    fields.add("0");

    RequestFactory requestFactory =
        new RequestFactory(
            new MedEquipRequestController(new MedEquipRequestDaoImpl(), new MedEquipDaoImpl()));
    try {
      requestFactory.getRequest("MEDEQUIPREQUEST", fields);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
