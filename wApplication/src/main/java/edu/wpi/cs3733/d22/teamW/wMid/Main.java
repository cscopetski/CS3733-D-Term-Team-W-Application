package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LanguageRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipType;
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
    final String securityRequestFileName = "SecurityRequest.csv";
    final String languageRequestFileName = "LanguageRequests.csv";

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
            mealRequestFileName,
            securityRequestFileName,
            languageRequestFileName);

    try {
      csvController.populateTables();
    } catch (Exception e) {
      e.printStackTrace();
    }
    ArrayList<MedEquip> medEquipArrayList =
        MedEquipManager.getMedEquipManager().getAllMedEquip(MedEquipType.Recliners, null);
    for (MedEquip m : medEquipArrayList) {
      System.out.println(m.toCSVString());
    }
    MedEquipManager.getMedEquipManager().markDirty("BED001", "FDEPT00101");
    MedEquipManager.getMedEquipManager().markDirty("BED002", "FDEPT00101");
    MedEquipManager.getMedEquipManager().markDirty("BED003", "FDEPT00101");
    MedEquipManager.getMedEquipManager().markDirty("BED004", "FDEPT00101");
    MedEquipManager.getMedEquipManager().markDirty("BED005", "FDEPT00101");
    MedEquipManager.getMedEquipManager().markClean("BED005", "FDEPT00101");

    LanguageRequestManager lrm = LanguageRequestManager.getLanguageRequestManager();
    RequestFactory rf = RequestFactory.getRequestFactory();

    ArrayList<String> fields = new ArrayList<>();
    fields.add("70");
    fields.add("Spanish");
    fields.add("FDEPT00101");
    fields.add("5");
    fields.add("0");
    fields.add("0");

    rf.getRequest(RequestType.LanguageRequest, fields, false);

    App.launch(App.class, args);
  }
}
