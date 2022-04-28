package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class RequestHubController implements Initializable {

  // Labels
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

  // Buttons:
  @FXML Button buttonMedEq;
  @FXML Button buttonMedDel;
  @FXML Button buttonLabSer;
  @FXML Button buttonCompSer;
  @FXML Button buttonFlower;
  @FXML Button buttonLangInt;
  @FXML Button buttonSecSer;
  @FXML Button buttonMeal;
  @FXML Button buttonSanitation;
  @FXML Button buttonGift;
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

  //-----------------------HELP POP UPS---------------------------

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    loadToolTips();
  }

  private void loadToolTips(){
    //computer SR:
    Tooltip compSer = new Tooltip("Click here to\nrequest IT help");
    toolTipDisp(compSer, buttonCompSer);
    //lab SR:
    Tooltip labSer = new Tooltip("Click here for Lab Services\nsuch as Blood Samples,\nX-Rays, MRIs, etc");
    toolTipDisp(labSer, buttonLabSer);
    //flower SR:
    Tooltip flower = new Tooltip("Click here to request\nflowers to a patient");
    toolTipDisp(flower, buttonFlower);
    //gift SR:
    Tooltip gift = new Tooltip("Click here to request\na gift for a patient");
    toolTipDisp(gift, buttonGift);
    //meal SR:
    Tooltip meal = new Tooltip("Click here for meal\ndelivery services");
    toolTipDisp(meal, buttonMeal);
    //language SR:
    Tooltip lang = new Tooltip("Click here for a\nlanguage interpreter\nto come help");
    toolTipDisp(lang, buttonLangInt);
    //Medicine SR:
    Tooltip medDel = new Tooltip("Click here to\nrequest medicine\nsuch as Adderall");
    toolTipDisp(medDel, buttonMedDel);
    //medical equipment SR:
    Tooltip medEqu = new Tooltip("Click here for\nMedical equipment\nsuch as clean beds");
    toolTipDisp(medEqu, buttonMedEq);
    //sanitation SR:
    Tooltip san = new Tooltip("Click here to\nrequest the\ncleaning crew");
    toolTipDisp(san, buttonSanitation);
    //security SR:
    Tooltip sec = new Tooltip("Click here to\nrequest Security");
    toolTipDisp(sec, buttonSecSer);
  }

  private void toolTipDisp(Tooltip whowie, Node whowiePic){
    whowie.setShowDelay(Duration.seconds(1.0));
    whowie.setHideDelay(Duration.seconds(0.4));
    whowie.setStyle("-fx-font-size: 15");
    Tooltip.install(whowiePic, whowie);
  }
}
