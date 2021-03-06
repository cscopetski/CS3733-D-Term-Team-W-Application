package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.Managers.BackgroundManager;
import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import java.io.IOException;

import edu.wpi.cs3733.d22.teamW.wDB.entity.InternalPatientTransportationRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.LanguageRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  /*
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
   */

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    WindowManager.getInstance().initialize(primaryStage);
    // primaryStage.setFullScreen(true);

    primaryStage.setMaximized(true);
    primaryStage.setResizable(true);
    primaryStage.toFront();
    primaryStage.setTitle("Mass General Brigham Service Requests");
    primaryStage
        .getIcons()
        .add(
            new Image(
                getClass()
                    .getResourceAsStream("/edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png")));


    WindowManager.getInstance().setScene("DefaultPage.fxml");
  }

  @Override
  public void stop() {

    final String locationNewFileName = "CSVs/TowerLocations_teamW_new.csv";
    final String medEquipNewFileName = "CSVs/MedicalEquipment_teamW_new.csv";
    final String medEquipRequestNewFileName = "CSVs/MedicalEquipmentRequest_teamW_new.csv";
    final String labServiceRequestNewFileName = "CSVs/LabRequests_teamW_new.csv";
    final String employeeNewFileName = "CSVs/Employees_teamW_new.csv";
    final String medRequestNewFileName = "CSVs/MedRequests_teamW_new.csv";
    final String flowerRequestNewFileName = "CSVs/FlowerRequests_teamW_new.csv";
    final String computerServiceRequestNewFileName = "CSVs/ComputerServiceRequest_teamW_new.csv";
    final String sanitationRequestNewFileName = "CSVs/SanitationRequests_teamW_new.csv";
    final String languageNewFileName = "CSVs/Languages_teamW_new.csv";
    final String languageInterpNewFileName = "CSVs/LanguageInterpreter_teamW_new.csv";
    final String giftDeliveryRequestNewFileName = "CSVs/GiftDeliveryRequest_teamW_new.csv";
    final String cleaningRequestNewFileName = "CSVs/CleaningRequest_teamW_new.csv";
    final String mealRequestNewFileName = "CSVs/MealRequest_teamW_new.csv";
    final String securityRequestNewFileName = "CSVs/SecurityRequest_teamW_new.csv";
    final String languageRequestNewFileName = "CSVs/LanguageRequests_teamW_new.csv";
    final String userImageNewFileName = "CSVs/UserImages_teamW_new.csv";
    final String internalPatientTransportationRequestNewFileName = "CSVs/InternalPatientTransportationRequests_teamW_new.csv";
    final String externalTransporationRequestNewFileName = "CSVs/ExternalTransportationRequests_teamW_new.csv";

    LocationManager.getLocationManager().exportLocationsCSV(locationNewFileName);
    MedEquipManager.getMedEquipManager().exportMedicalEquipmentCSV(medEquipNewFileName);
    try {
      MedEquipRequestManager.getMedEquipRequestManager().exportReqCSV(medEquipRequestNewFileName);
    } catch (NonExistingMedEquip e) {
      e.printStackTrace();
    }
    LabServiceRequestManager.getLabServiceRequestManager().exportReqCSV(labServiceRequestNewFileName);
    EmployeeManager.getEmployeeManager().exportEmpCSV(employeeNewFileName);
    MedRequestManager.getMedRequestManager().exportReqCSV(medRequestNewFileName);
    FlowerRequestManager.getFlowerRequestManager().exportReqCSV(flowerRequestNewFileName);
    try {
      ComputerServiceRequestManager.getComputerServiceRequestManager().exportReqCSV(computerServiceRequestNewFileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      SanitationRequestManager.getSanitationRequestManager().exportReqCSV(sanitationRequestNewFileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    LanguageManager.getLanguageManager().exportLocationsCSV(languageNewFileName);
    LanguageInterpreterManager.getLanguageInterpreterManager().exportReqCSV(languageInterpNewFileName);
    try {
      GiftDeliveryRequestManager.getGiftDeliveryRequestManager().exportReqCSV(giftDeliveryRequestNewFileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    CleaningRequestManager.getCleaningRequestManager().exportReqCSV(cleaningRequestNewFileName);
    try {
      MealRequestManager.getMealRequestManager().exportReqCSV(mealRequestNewFileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      SecurityRequestManager.getSecurityRequestManager().exportReqCSV(securityRequestNewFileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    LanguageRequestManager.getLanguageRequestManager().exportReqCSV(languageRequestNewFileName);
    UserImageManager.getUserImageManager().exportUserImageCSV(userImageNewFileName);
    try {
      InternalPatientTransportationRequestManager.getInternalPatientTransportationRequestManager().exportReqCSV(internalPatientTransportationRequestNewFileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      ExternalTransportManager.getRequestManager().exportReqCSV(externalTransporationRequestNewFileName);
    } catch (Exception e) {
      e.printStackTrace();
    }

//    UserImageManager.getUserImageManager().exportUserImageCSV("CSVs/UserImages.csv");
//    CleaningRequestManager.getCleaningRequestManager().exportReqCSV("CSVs/CleaningRequest.csv");
//    try {
//      ComputerServiceRequestManager.getComputerServiceRequestManager()
//          .exportReqCSV("CSVs/ComputerServiceRequest.csv");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    EmployeeManager.getEmployeeManager().exportEmpCSV("CSVs/Employees.csv");
//    FlowerRequestManager.getFlowerRequestManager().exportReqCSV("CSVs/FlowerRequests.csv");
//    try {
//      GiftDeliveryRequestManager.getGiftDeliveryRequestManager()
//          .exportReqCSV("CSVs/GiftDeliveryRequest.csv");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    LabServiceRequestManager.getLabServiceRequestManager().exportReqCSV("CSVs/LabRequests.csv");
//    LanguageInterpreterManager.getLanguageInterpreterManager()
//        .exportReqCSV("CSVs/LanguageInterpreter.csv");
//    LanguageRequestManager.getLanguageRequestManager().exportReqCSV("CSVs/LanguageRequests.csv");
//    LanguageManager.getLanguageManager().exportLocationsCSV("CSVs/Languages.csv");
//    try {
//      MealRequestManager.getMealRequestManager().exportReqCSV("CSVs/MealRequest.csv");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    MedEquipManager.getMedEquipManager().exportMedicalEquipmentCSV("CSVs/MedicalEquipment.csv");
//    try {
//      MedEquipRequestManager.getMedEquipRequestManager()
//          .exportReqCSV("CSVs/MedicalEquipmentRequest.csv");
//    } catch (NonExistingMedEquip ex) {
//      ex.printStackTrace();
//    }
//    MedRequestManager.getMedRequestManager().exportReqCSV("CSVs/MedRequests.csv");
//    try {
//      SanitationRequestManager.getSanitationRequestManager()
//          .exportReqCSV("CSVs/SanitationRequests.csv");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    try {
//      SecurityRequestManager.getSecurityRequestManager().exportReqCSV("CSVs/SecurityRequest.csv");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    LocationManager.getLocationManager().exportLocationsCSV("CSVs/TowerLocations.csv");
//    try {
//      ExternalTransportManager.getRequestManager().exportReqCSV("CSVs/ExternalTransportationRequests.csv");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    try {
//      InternalPatientTransportationRequestManager.getInternalPatientTransportationRequestManager().exportReqCSV("CSVs/InternalPatientTransportationRequests.csv");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
    log.info("Shutting Down");
  }
}
