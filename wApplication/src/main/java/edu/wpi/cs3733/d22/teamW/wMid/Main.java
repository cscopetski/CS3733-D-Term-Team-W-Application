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
    final String giftDeliveryRequestFileName = "GiftDeliveryRequest.csv";
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
            languageFileName,
            languageInterpreterFilename,
            giftDeliveryRequestFileName,
            mealRequestFileName);

    try {
      csvController.populateTables();
    } catch (Exception e) {
      e.printStackTrace();
    }

    /*
     String patientLast,
     String patientFirst,
     String mealType,
     String nodeID,
     Integer employeeID,
     Integer emergency,
    */

    //    ArrayList<String> fields = new ArrayList<>();
    //    fields.add(MealType.Burger.getString());
    //    fields.add("Bedison");
    //    fields.add("Edison");
    //    fields.add("FDEPT00101");
    //    fields.add("1");
    //    fields.add("0");
    //
    //    ArrayList<String> fields2 = new ArrayList<>();
    //    fields2.add(MealType.Ramen.getString());
    //    fields2.add("Joe");
    //    fields2.add("Joe");
    //    fields2.add("FDEPT00101");
    //    fields2.add("1");
    //    fields2.add("0");
    //
    //    ArrayList<String> fields3 = new ArrayList<>();
    //    fields3.add(MealType.Burrito.getString());
    //    fields3.add("Jim");
    //    fields3.add("Joe");
    //    fields3.add("FDEPT00101");
    //    fields3.add("1");
    //    fields3.add("0");
    //
    //    MealRequest mr =
    //        (MealRequest)
    //            RequestFactory.getRequestFactory().getRequest(RequestType.MealDelivery, fields,
    // false);
    //    RequestFactory.getRequestFactory().getRequest(RequestType.MealDelivery, fields2, false);
    //    RequestFactory.getRequestFactory().getRequest(RequestType.MealDelivery, fields3, false);

    // MealRequestManager.getMealRequestManager().exportReqCSV("MealRequest.csv");

    App.launch(App.class, args);
  }
}
