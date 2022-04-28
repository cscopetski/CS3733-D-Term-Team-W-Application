package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.Managers.BackgroundManager;
import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import java.io.IOException;

import edu.wpi.cs3733.d22.teamW.wDB.entity.InternalPatientTransportationRequest;
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
    UserImageManager.getUserImageManager().exportUserImageCSV("CSVs/UserImages.csv");
    CleaningRequestManager.getCleaningRequestManager().exportReqCSV("CSVs/CleaningRequest.csv");
    try {
      ComputerServiceRequestManager.getComputerServiceRequestManager()
          .exportReqCSV("CSVs/ComputerServiceRequest.csv");
    } catch (Exception e) {
      e.printStackTrace();
    }
    EmployeeManager.getEmployeeManager().exportEmpCSV("CSVs/Employees.csv");
    FlowerRequestManager.getFlowerRequestManager().exportReqCSV("CSVs/FlowerRequests.csv");
    try {
      GiftDeliveryRequestManager.getGiftDeliveryRequestManager()
          .exportReqCSV("CSVs/GiftDeliveryRequest.csv");
    } catch (Exception e) {
      e.printStackTrace();
    }
    LabServiceRequestManager.getLabServiceRequestManager().exportReqCSV("CSVs/LabRequests.csv");
    LanguageInterpreterManager.getLanguageInterpreterManager()
        .exportReqCSV("CSVs/LanguageInterpreter.csv");
    LanguageRequestManager.getLanguageRequestManager().exportReqCSV("CSVs/LanguageRequests.csv");
    LanguageManager.getLanguageManager().exportLocationsCSV("CSVs/Languages.csv");
    try {
      MealRequestManager.getMealRequestManager().exportReqCSV("CSVs/MealRequest.csv");
    } catch (Exception e) {
      e.printStackTrace();
    }
    MedEquipManager.getMedEquipManager().exportMedicalEquipmentCSV("CSVs/MedicalEquipment.csv");
    try {
      MedEquipRequestManager.getMedEquipRequestManager()
          .exportReqCSV("CSVs/MedicalEquipmentRequest.csv");
    } catch (NonExistingMedEquip ex) {
      ex.printStackTrace();
    }
    MedRequestManager.getMedRequestManager().exportReqCSV("CSVs/MedRequests.csv");
    try {
      SanitationRequestManager.getSanitationRequestManager()
          .exportReqCSV("CSVs/SanitationRequests.csv");
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      SecurityRequestManager.getSecurityRequestManager().exportReqCSV("CSVs/SecurityRequest.csv");
    } catch (Exception e) {
      e.printStackTrace();
    }
    LocationManager.getLocationManager().exportLocationsCSV("CSVs/TowerLocations.csv");
    try {
      ExternalTransportManager.getRequestManager().exportReqCSV("CSVs/ExternalTransportationRequests.csv");
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      InternalPatientTransportationRequestManager.getInternalPatientTransportationRequestManager().exportReqCSV("CSVs/InternalPatientTransportationRequests.csv");
    } catch (Exception e) {
      e.printStackTrace();
    }
    log.info("Shutting Down");
  }
}
