package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
  @FXML public Pane requestListPage;
  @FXML public Pane requestHubPage;
  @FXML public Pane loginPage;
  @FXML public Pane helpPage;
  @FXML public Pane aboutPage;
  @FXML public Pane profilePage;
  @FXML public Pane gamingPage;
  @FXML public HBox menuBar;
  @FXML public Pane buttonPane;

  protected Employee employee;

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
    SceneManager.getInstance().putPane(SceneManager.Scenes.RequestList, requestListPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.RequestHub, requestHubPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.Help, helpPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.About, aboutPage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.Profile, profilePage);
    SceneManager.getInstance().putPane(SceneManager.Scenes.Gaming, gamingPage);
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.Login);
  }

  public void setEmployee(Employee em) {
    this.employee = em;
  }

  public Employee getEmployee() {
    return this.employee;
  }

  public void switchToMedicineDelivery(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MedicineDelivery);
  }

  public void switchToLab(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Lab);
  }

  public void switchToMedicalEquipmentDelivery(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MedicalEquipment);
  }

  public void switchToMealDelivery(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MealDelivery);
  }

  public void switchToLanguageInterpreter(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.LanguageInterpreter);
  }

  public void switchToSecurity(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Security);
  }

  public void switchToMapEditor(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MapEditor);
  }

  public void switchToRequestList(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.RequestList);
  }

  public void switchToRequestHub(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.RequestHub);
  }

  public void switchToMainMenu() {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MainMenu);
  }

  public void logOut(ActionEvent actionEvent) {
    menuBar.setVisible(false);
    buttonPane.setDisable(true);
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Login);
  }

  public void exitProgram() {
    SceneManager.getInstance().exitApplication();
  }

  public void switchToProfile(ActionEvent actionEvent) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Profile);
  }

  public void switchToAbout(ActionEvent actionEvent) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.About);
  }

  public void switchToHelp(ActionEvent actionEvent) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Help);
  }

  public void switchToGaming(ActionEvent actionEvent) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Gaming);
  }
}
