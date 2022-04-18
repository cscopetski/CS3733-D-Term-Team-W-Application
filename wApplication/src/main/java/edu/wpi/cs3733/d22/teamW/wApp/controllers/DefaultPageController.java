package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.Account;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
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
  @FXML public Pane computerServiceRequestPage;
  @FXML public Pane sanitationRequestPage;
  @FXML public Pane flowerRequestPage;
  @FXML public Pane giftDeliveryPage;
  @FXML public Pane requestListPage;
  @FXML public Pane requestHubPage;
  @FXML public Pane loginPage;
  @FXML public Pane helpPage;
  @FXML public Pane aboutPage;
  @FXML public Pane profilePage;
  @FXML public Pane snakePage;
  @FXML public HBox menuBar;
  @FXML public Pane buttonPane;

  public void initialize(URL location, ResourceBundle rb) {

    SceneManager.getInstance().putController(SceneManager.Scenes.Default, this);

    SceneManager.getInstance().putPane(SceneManager.Scenes.Login, loginPage);
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
    SceneManager.getInstance()
        .putPane(SceneManager.Scenes.ComputerService, computerServiceRequestPage);
    SceneManager.getInstance()
        .putPane(SceneManager.Scenes.SanitationService, sanitationRequestPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.FlowerRequest, flowerRequestPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.GiftDelivery, giftDeliveryPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.RequestList, requestListPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.RequestHub, requestHubPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.Help, helpPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.About, aboutPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.Profile, profilePage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.Snake, snakePage);
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.Login);
  }

  public void switchToMedicineDelivery() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MedicineDelivery);
  }

  public void switchToLab() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Lab);
  }

  public void switchToMedicalEquipmentDelivery() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MedicalEquipment);
  }

  public void switchToMealDelivery() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MealDelivery);
  }

  public void switchToLanguageInterpreter() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.LanguageInterpreter);
  }

  public void switchToSecurity() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Security);
  }

  public void switchToComputerService() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.ComputerService);
  }

  public void switchToFlowerService() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.FlowerRequest);
  }

  public void switchToGiftService() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.GiftDelivery);
  }

  public void switchToSanitationService() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.SanitationService);
  }

  public void switchToMapEditor() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MapEditor);
  }

  public void switchToRequestList() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.RequestList);
  }

  public void switchToRequestHub() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.RequestHub);
  }

  public void switchToMainMenu() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MainMenu);
  }

  public void logOut() {
    menuBar.setVisible(false);
    buttonPane.setDisable(true);
    Account.getInstance().setEmployee(null);
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Login);
  }

  public void exitProgram() {
    SceneManager.getInstance().exitApplication();
  }

  public void switchToProfile() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Profile);
  }

  public void switchToAbout() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.About);
  }

  public void switchToHelp() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Help);
  }

  public void switchToSnake() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Snake);
  }
}
