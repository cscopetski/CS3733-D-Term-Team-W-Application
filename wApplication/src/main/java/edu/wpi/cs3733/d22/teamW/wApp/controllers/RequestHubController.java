package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import java.io.IOException;
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

  public void switchToMedicineDelivery() {
    PageManager.getInstance().loadPage(PageManager.Pages.MedicineDeliverySR);
  }

  public void switchToLab() {
    PageManager.getInstance().loadPage(PageManager.Pages.LabSR);
  }

  public void switchToMedicalEquipmentDelivery() {
    PageManager.getInstance().loadPage(PageManager.Pages.MedicalEquipmentSR);
  }

  public void switchToMealDelivery() {
    PageManager.getInstance().loadPage(PageManager.Pages.MealDeliverySR);
  }

  public void switchToLanguageInterpreter() {
    PageManager.getInstance().loadPage(PageManager.Pages.LanguageInterpreterSR);
  }

  public void switchToSecurity() {
    PageManager.getInstance().loadPage(PageManager.Pages.SecuritySR);
  }

  public void switchToComputerService() {
    PageManager.getInstance().loadPage(PageManager.Pages.ComputerSR);
  }

  public void switchToSanitationService() {
    PageManager.getInstance().loadPage(PageManager.Pages.SanitationSR);
  }

  public void switchToFlowerDelivery() {
    PageManager.getInstance().loadPage(PageManager.Pages.FlowerSR);
  }

  public void switchToGiftDelivery() {
    PageManager.getInstance().loadPage(PageManager.Pages.GiftDeliverySR);
  }

  public void switchToRequestList() {
    PageManager.getInstance().loadPage(PageManager.Pages.RequestList);
  }

  public void creditsToggle() throws IOException {
    if (toggleButton.isSelected()) {
      toggleButton.setText("Show Credits");
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
      toggleButton.setText("Hide Credits");
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
