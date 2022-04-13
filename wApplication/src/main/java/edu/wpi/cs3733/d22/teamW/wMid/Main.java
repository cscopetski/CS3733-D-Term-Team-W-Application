package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws SQLException, FileNotFoundException {
    // DBConnectionMode.INSTANCE.setServerConnection();
    // App.launch(App.class, args);

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

    RequestFactory requestFactory = RequestFactory.getRequestFactory();
    MedEquipManager mem = MedEquipManager.getMedEquipManager();
    ArrayList<String> fields = new ArrayList<>();

    fields.add("XRY");
    fields.add("wSTOR001L1");
    fields.add("2");
    fields.add("" + 0);

    ArrayList<String> fields2 = new ArrayList<>();

    fields2.add("XRY");
    fields2.add("wSTOR001L1");
    fields2.add("2");
    fields2.add("" + 1);

    ArrayList<String> fields3 = new ArrayList<>();

    fields3.add("XRY");
    fields3.add("wSTOR001L1");
    fields3.add("3");
    fields3.add("" + 0);
    /*

        try {
          requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields);
          requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields2);
          requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields3);
        } catch (SQLException e) {
          e.printStackTrace();
        }
    */

    /*Request test = requestFactory.findRequest(5);
      Request test2 = requestFactory.findRequest(21);
      Request test3 = requestFactory.findRequest(22);
      Request test4 = requestFactory.findRequest(23);
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
    LocationManager.getLocationManager().exportLocationsCSV("LOCATIONTEST.csv");
    MedEquipManager.getMedEquipManager().exportMedicalEquipmentCSV("MEDEQUIPTEST.csv");
    merc.exportReqCSV("MEDEQUIPREQUESTTEST.csv");
    LabServiceRequestManager.getLabServiceRequestManager().exportReqCSV("LABTEST.csv");

    EmployeeManager edi = EmployeeManager.getEmployeeManager();
    edi.addEmployee(
        7,
        "Mr.",
        "Secure",
        "Security Officer",
        "security@hospital.com",
        "(123)456789",
        "somewhere",
        "secur",
        "secur",
        "NEW");

    edi.changeEmployee(
        6,
        "new Hasan",
        "new G",
        EmployeeType.Admin,
        "new Email",
        "new Phone",
        "new Address",
        "joe",
        "joe123");

    if (edi.passwordMatch("joe", "joe123")) {
      System.out.println("hzgan, spongeBob MATCH");
    } else {
      System.out.println("NO MATCH");
    }

    edi.addEmployee(
        8, "frontend", "no salt", "no salt", "no salt", "no salt", "no salt", "no", "salt");
    if (edi.passwordMatch("no", "salt")) {
      System.out.println("match");
    } else {
      System.out.println("no match");
    }
    System.out.println(edi.getEmployee("joe").toCSVString());
    /* // Req23 cancelled
    merc.cancel(23);
    // Req7 complete
    merc.complete(7);
    // Req5 cancelled and req 22 start
    merc.cancel(5);
    merc.reQueue(5);
    merc.cancel(22);*/
    merc.complete(2);
    // MedEquipManager.getMedEquipManager().markDirty("BED002", "FEXIT00301");
    System.out.println("\n\n\n\n");
    for (Request e : requestFactory.getAllRequests()) {
      System.out.println(e.toValuesString());
    }
    edi.exportEmpCSV("Employees.csv");
    MedEquipManager.getMedEquipManager().markDirty("BED012", "wSTOR0033");
    MedEquipManager.getMedEquipManager().markDirty("BED013", "wSTOR0033");
    MedEquipManager.getMedEquipManager().markDirty("BED014", "wSTOR0033");
    MedEquipManager.getMedEquipManager().markDirty("BED015", "wSTOR0033");
    MedEquipManager.getMedEquipManager().markDirty("BED016", "wSTOR0033");
    merc.complete(5);
    mem.markDirty("XRY001", "wSTOR0033");
    CleaningRequestManager.getCleaningRequestManager().complete(51, "wSTOR0013");

    ArrayList<String> medRequestFields = new ArrayList<String>();
    medRequestFields.add("medicine");
    medRequestFields.add("FDEPT00101");
    medRequestFields.add("1");
    medRequestFields.add("0");

    MedRequestManager.getMedRequestManager().addRequest(500, medRequestFields);
    MedRequestManager.getMedRequestManager().exportReqCSV("MEDREQUESTTEST.csv");
    // DBConnectionMode.INSTANCE.setServerConnection();
    /*DBController.getDBController().closeConnection();

    try {
      DBController.getDBController().startConnection();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      Alert reconnect =
          new Alert(
              Alert.AlertType.ERROR,
              "Connection to the Db is failed, reconnected?",
              ButtonType.CLOSE,
              ButtonType.OK);
      reconnect.show();
      if (reconnect.getResult() == ButtonType.OK) {
        // reconnect here
        try {
          DBController.getDBController().startConnection();
        } catch (ClassNotFoundException ex) {
          ex.printStackTrace();
        }
      } else if (reconnect.getResult() == ButtonType.CANCEL) reconnect.close();
    }*/
  }
}
