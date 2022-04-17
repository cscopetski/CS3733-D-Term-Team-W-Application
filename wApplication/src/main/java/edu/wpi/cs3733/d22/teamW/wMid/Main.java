package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.SecurityRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.SecurityRequest;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
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
    final String languageFileName = "Languages.csv";
    final String languageInterpreterFilename = "LanguageInterpreter.csv";
    final String giftDeliveryRequestFileName = "GiftDeliveryRequest.csv";
    final String cleaningRequestFileName = "CleaningRequest.csv";
    final String mealRequestFileName = "MealRequest.csv";

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
            cleaningRequestFileName,
            languageFileName,
            languageInterpreterFilename,
            giftDeliveryRequestFileName,
            mealRequestFileName);

    try {
      csvController.populateTables();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ArrayList<String> fields = new ArrayList<>();

    fields.add("FDEPT00101");
    fields.add("1");
    fields.add("0");
    fields.add("0");

    ArrayList<String> fields2 = new ArrayList<>();
    fields2.add("FDEPT00101");
    fields2.add("1");
    fields2.add("0");
    fields2.add("5");

    ArrayList<String> fields3 = new ArrayList<>();
    fields3.add("FDEPT00101");
    fields3.add("1");
    fields3.add("0");
    fields3.add("10");

    SecurityRequest mr =
        (SecurityRequest)
            RequestFactory.getRequestFactory()
                .getRequest(RequestType.SecurityService, fields, false);
    RequestFactory.getRequestFactory().getRequest(RequestType.SecurityService, fields2, false);
    RequestFactory.getRequestFactory().getRequest(RequestType.SecurityService, fields3, false);

    SecurityRequestManager.getSecurityRequestManager().exportReqCSV("SecurityRequest.csv");

    App.launch(App.class, args);
  }
}
