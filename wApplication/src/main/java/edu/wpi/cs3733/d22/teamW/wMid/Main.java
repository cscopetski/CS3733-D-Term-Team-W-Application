package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
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

    DBController.getDBController();

    CSVController csvController =
        new CSVController(
            locationFileName,
            medEquipFileName,
            medEquipRequestFileName,
            labServiceRequestFileName,
            employeesFileName,
            medRequestFileName,
            flowerRequestFileName);

    try {
      csvController.populateTables();
    } catch (Exception e) {
      e.printStackTrace();
    }

    /*
    FlowerRequestManager frm = FlowerRequestManager.getFlowerRequestManager();
    ArrayList<String> fields = new ArrayList<String>();
    fields.add("Rose");
    fields.add("K-W");
    fields.add("Charlie");
    fields.add("FDEPT00101");
    fields.add("4");
    fields.add("0");

    ArrayList<String> fields2 = new ArrayList<String>();
    fields2.add("Succulent");
    fields2.add("S");
    fields2.add("Caleb");
    fields2.add("FDEPT00101");
    fields2.add("5");
    fields2.add("0");

    RequestFactory rq = RequestFactory.getRequestFactory();

    FlowerRequest fr = (FlowerRequest) rq.getRequest(RequestType.FlowerRequest, fields, false);
    FlowerRequest fr2 = (FlowerRequest) rq.getRequest(RequestType.FlowerRequest, fields2, false);
    fr2.setFlower(Flower.Daisy);
    frm.changeFlowerRequest(fr2);
    frm.exportReqCSV(flowerRequestFileName);
     */
    RequestFacade rf = RequestFacade.getRequestFacade();
    ArrayList<Request> reqs = rf.getAllRequests();
    ArrayList<Integer> ids = new ArrayList<>();
    for (Request r : reqs) {
      ids.add(r.getRequestID());
    }
    System.out.println(ids);

    // App.launch(App.class, args);

  }
}
