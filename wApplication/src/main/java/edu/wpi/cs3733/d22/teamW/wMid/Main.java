package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws SQLException, FileNotFoundException {
<<<<<<< HEAD
=======
    // DBConnectionMode.INSTANCE.setServerConnection();
    // App.launch(App.class, args);
>>>>>>> Backend-Merge

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

    try {
      requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields);
      requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields2);
      requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields3);
    } catch (SQLException e) {
      e.printStackTrace();
    }

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
    merc.exportMedEquipRequestCSV("MEDEQUIPREQUESTTEST.csv");
    LabServiceRequestManager.getLabServiceRequestManager()
        .exportLabServiceRequestCSV("LABTEST.csv");

    EmployeeManager edi = EmployeeManager.getEmployeeManager();
    edi.addEmployee(
        7,
        "Mr.",
        "Secure",
        "Security",
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
        "new Type",
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
<<<<<<< HEAD

    /*
    edi.addEmployee(
        1,
        "N/A",
        "Staff",
        "Staff Member",
        "staff@hospital.com",
        "(123)4567890",
        "Mass General",
        "staff",
        "staff",
        "salt");
    edi.addEmployee(
        2,
        "N/A",
        "Administrator",
        "admin",
        "admin@hospital.com",
        "(123)4567890",
        "Office",
        "admin",
        "admin",
        "salt");
    edi.addEmployee(
        3,
        "Caleb",
        "Scopetski",
        "Doctor",
        "N/A",
        "(123)4567890",
        "NeVer LeFt MaSs",
        "Scoop",
        "backEndGang",
        "salt");
    edi.addEmployee(
        4,
        "Edison",
        "Zhang",
        "Janitor",
        "N/A",
        "(123)4567890",
        "Jamaica",
        "ezhang",
        "broMyASSSSS",
        "salt");
    edi.addEmployee(
        5,
        "Charlie",
        "K-W",
        "Nurse",
        "N/A",
        "(123)4567890",
        "The North",
        "charkw",
        "Ih8Testing",
        "salt");
    edi.addEmployee(
        6,
        "Hasan",
        "G",
        "Lab Technician",
        "N/A",
        "(123)4567890",
        "Bikini Bottom",
        "hzgan",
        "spongeBob",
        "salt");

     */

    edi.exportEmpCSV("Employees.csv");

    App.launch(App.class, args);
=======

    edi.addEmployee(
        8, "frontend", "no salt", "no salt", "no salt", "no salt", "no salt", "no", "salt");
    if (edi.passwordMatch("no", "salt")) {
      System.out.println("match");
    } else {
      System.out.println("no match");
    }
    System.out.println(edi.getEmployee("joe").toCSVString());
    /*
       edi.addEmployee(
           1,
           "N/A",
           "Staff",
           "Staff Member",
           "staff@hospital.com",
           "(123)4567890",
           "Mass General",
           "staff",
           "staff",
           "salt");
       edi.addEmployee(
           2,
           "N/A",
           "Administrator",
           "admin",
           "admin@hospital.com",
           "(123)4567890",
           "Office",
           "admin",
           "admin",
           "salt");
       edi.addEmployee(
           3,
           "Caleb",
           "Scopetski",
           "Doctor",
           "N/A",
           "(123)4567890",
           "NeVer LeFt MaSs",
           "Scoop",
           "backEndGang",
           "salt");
       edi.addEmployee(
           4,
           "Edison",
           "Zhang",
           "Janitor",
           "N/A",
           "(123)4567890",
           "Jamaica",
           "ezhang",
           "broMyASSSSS",
           "salt");
       edi.addEmployee(
           5,
           "Charlie",
           "K-W",
           "Nurse",
           "N/A",
           "(123)4567890",
           "The North",
           "charkw",
           "Ih8Testing",
           "salt");
       edi.addEmployee(
           6,
           "Hasan",
           "G",
           "Lab Technician",
           "N/A",
           "(123)4567890",
           "Bikini Bottom",
           "hzgan",
           "spongeBob",
           "salt");

       edi.exportEmpCSV("Employees.csv");

    */
    edi.exportEmpCSV("Employees.csv");
>>>>>>> Backend-Merge
  }
}
