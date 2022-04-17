package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    SceneManager.getInstance().setPrimaryStage(primaryStage);
    // primaryStage.setFullScreen(true);

    primaryStage.setMaximized(true);
    primaryStage.setResizable(false);
    primaryStage.toFront();
    primaryStage.setTitle("Mass General Brigham Service Requests");
    primaryStage
        .getIcons()
        .add(
            new Image(
                getClass()
                    .getResourceAsStream("/edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png")));
    primaryStage.setOnCloseRequest(
        e -> {
          EmployeeManager.getEmployeeManager()
              .exportEmpCSV("edu/wpi/cs3733/d22/teamW/wDB/CSVs/Employees.csv");
          LabServiceRequestManager.getLabServiceRequestManager()
              .exportReqCSV("edu/wpi/cs3733/d22/teamW/wDB/CSVs/LabRequests.csv");
          LocationManager.getLocationManager()
              .exportLocationsCSV("edu/wpi/cs3733/d22/teamW/wDB/CSVs/TowerLocations.csv");
          MedEquipManager.getMedEquipManager()
              .exportMedicalEquipmentCSV("edu/wpi/cs3733/d22/teamW/wDB/CSVs/MedicalEquipment.csv");
          try {
            MedEquipRequestManager.getMedEquipRequestManager()
                .exportReqCSV("edu/wpi/cs3733/d22/teamW/wDB/CSVs/MedicalEquipmentRequest.csv");
          } catch (NonExistingMedEquip ex) {
            ex.printStackTrace();
          }
          MedRequestManager.getMedRequestManager()
              .exportReqCSV("edu/wpi/cs3733/d22/teamW/wDB/CSVs/MedRequests.csv");
        });
    SceneManager.getInstance().setScene("DefaultPage.fxml");
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
