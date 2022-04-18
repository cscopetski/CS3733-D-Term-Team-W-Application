package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class RequestHubController {
  @FXML Label medEquipCredit;
  @FXML Label languageCredit;
  @FXML Label medicineCredit;
  @FXML Label securityCredit;
  @FXML Label labCredit;
  @FXML Label mealCredit;
  @FXML Label computerCredit;
  @FXML Label sanitationServiceCredit;
  @FXML Label flowerCredit;
  @FXML Label giftDeliveryCredit;
  @FXML ToggleButton toggleButton;

  public void switchToMedicineDelivery(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MedicineDelivery);
  }

  public void switchToLab(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Lab);
  }

  public void switchToMedicalEquipmentDelivery(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MedicalEquipment);
  }

  public void switchToMealDelivery(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MealDelivery);
  }

  public void switchToLanguageInterpreter(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.LanguageInterpreter);
  }

  public void switchToSecurity(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Security);
  }

  public void switchToComputerService(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.ComputerService);
  }

  public void switchToSanitationService(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.SanitationService);
  }

  public void switchToFlowerDelivery(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.FlowerRequest);
  }

  public void switchToGiftDelivery(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.GiftDelivery);
  }

  public void creditsToggle(ActionEvent event) throws IOException {
    if (toggleButton.isSelected()) {
      medEquipCredit.setVisible(false);
      languageCredit.setVisible(false);
      medicineCredit.setVisible(false);
      securityCredit.setVisible(false);
      labCredit.setVisible(false);
      mealCredit.setVisible(false);
      computerCredit.setVisible(false);
      sanitationServiceCredit.setVisible(false);
      flowerCredit.setVisible(false);
      giftDeliveryCredit.setVisible(false);
    } else {
      medEquipCredit.setVisible(true);
      languageCredit.setVisible(true);
      medicineCredit.setVisible(true);
      securityCredit.setVisible(true);
      labCredit.setVisible(true);
      mealCredit.setVisible(true);
      computerCredit.setVisible(true);
      sanitationServiceCredit.setVisible(true);
      flowerCredit.setVisible(true);
      giftDeliveryCredit.setVisible(true);
    }
  }
}
