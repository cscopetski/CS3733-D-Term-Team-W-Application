package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class DefaultPageController implements Initializable {

  @FXML public Pane mainMenuPage;
  @FXML public Pane mapEditorPage;
  @FXML public Pane labServiceRequestPage;
  @FXML public Pane languageInterpreterServiceRequestPage;
  @FXML public Pane mealDeliveryServiceRequestPage;
  @FXML public Pane medicalEquipmentServiceRequestPage;
  @FXML public Pane medicineDeliveryServiceRequestPage;
  @FXML public Pane securityServiceRequestPage;

  @FXML
  public void initialize(URL location, ResourceBundle rb) {
    SceneManager.getInstance().putPane(SceneManager.Scenes.MainMenu, mainMenuPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.MapEditor, mapEditorPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.Lab, labServiceRequestPage);
    SceneManager.getInstance()
        .putPane(SceneManager.Scenes.LanguageInterpreter, languageInterpreterServiceRequestPage);
    SceneManager.getInstance()
        .putPane(SceneManager.Scenes.MealDelivery, mealDeliveryServiceRequestPage);
    SceneManager.getInstance()
        .putPane(SceneManager.Scenes.MedicalEquipment, medicalEquipmentServiceRequestPage);
    SceneManager.getInstance()
        .putPane(SceneManager.Scenes.MedicineDelivery, medicineDeliveryServiceRequestPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.Security, securityServiceRequestPage);
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.MainMenu);

    try {
      // SceneManager.getInstance().setWindow(SceneManager.Scenes.MainMenu);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void switchToMedicineDelivery(ActionEvent event) throws IOException {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.MedicineDelivery);
  }

  public void switchToLab(ActionEvent event) throws IOException {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.Lab);
  }

  public void switchToMedicalEquipmentDelivery(ActionEvent event) throws IOException {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.MedicalEquipment);
  }

  public void switchToMealDelivery(ActionEvent event) throws IOException {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.MealDelivery);
  }

  public void switchToLanguageInterpreter(ActionEvent event) throws IOException {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.LanguageInterpreter);
  }

  public void switchToSecurity(ActionEvent event) throws IOException {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.Security);
  }

  public void switchToMapEditor(ActionEvent event) throws IOException {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.MapEditor);
  }

  public void switchToMainMenu(ActionEvent event) throws IOException {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.MainMenu);
  }

  public void exitProgram() {
    System.exit(0);
  }
}
