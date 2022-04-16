package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedicineType;
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
    } catch (Exception e) {
      e.printStackTrace();
    }

    ArrayList<Employee> employees = EmployeeManager.getEmployeeManager().getAllEmployees();

    for (Employee e : employees) {
      System.out.println(e.toCSVString());
    }

    for (MedicineType m : MedicineType.values()) {
      System.out.println(m.toString());
      System.out.println(m.getString());
    }

    CleaningRequestManager crm = CleaningRequestManager.getCleaningRequestManager();
    ArrayList<Request> test = crm.getEmployeeRequests(1);
    System.out.println("Cleaning Requests: " + test);

    LabServiceRequestManager lsrm = LabServiceRequestManager.getLabServiceRequestManager();
    ArrayList<Request> testLab = lsrm.getEmployeeRequests(1);
    ArrayList<Integer> reqs = new ArrayList<Integer>();
    for (Request r : testLab) {
      reqs.add(r.getRequestID());
    }
    System.out.println("Lab Service Requests: " + reqs);

    MedEquipRequestManager merm = MedEquipRequestManager.getMedEquipRequestManager();
    ArrayList<Request> requs = merm.getEmployeeRequests(1);
    ArrayList<Integer> mernums = new ArrayList<Integer>();
    for (Request r : requs) {
      mernums.add(r.getRequestID());
    }
    System.out.println("Med Equip Requests: " + mernums);

    MedRequestManager mrm = MedRequestManager.getMedRequestManager();
    requs = mrm.getEmployeeRequests(1);
    mernums = new ArrayList<Integer>();
    for (Request r : requs) {
      mernums.add(r.getRequestID());
    }
    System.out.println("Med Requests: " + mernums);

    //    MedEquipRequestManager merc = MedEquipRequestManager.getMedEquipRequestManager();
    //
    //    MedEquipManager mem = MedEquipManager.getMedEquipManager();
    //
    //    MedEquipManager.getMedEquipManager().markDirty("BED012", "wSTOR0033");
    //    MedEquipManager.getMedEquipManager().markDirty("BED013", "wSTOR0033");
    //    MedEquipManager.getMedEquipManager().markDirty("BED014", "wSTOR0033");
    //    MedEquipManager.getMedEquipManager().markDirty("BED015", "wSTOR0033");
    //    MedEquipManager.getMedEquipManager().markDirty("BED016", "wSTOR0033");
    //    // merc.complete(5);
    //    mem.markDirty("XRY001", "wSTOR0033");
    //    try {
    //      CleaningRequestManager.getCleaningRequestManager().complete(26, "wSTOR0013");
    //    } catch (Exception e) {
    //      e.printStackTrace();
    //    }
    // App.launch(App.class, args);
  }
}
