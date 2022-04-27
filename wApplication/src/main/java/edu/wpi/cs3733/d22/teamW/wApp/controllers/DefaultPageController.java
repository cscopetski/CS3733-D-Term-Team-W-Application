package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.AccountManager;
import edu.wpi.cs3733.d22.teamW.Managers.BackgroundManager;
import edu.wpi.cs3733.d22.teamW.Managers.MenuBarManager;
import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DefaultPageController implements Initializable {
  @FXML public HBox menuBar;
  @FXML public Pane buttonPane;
  @FXML public AnchorPane content;
  @FXML public AnchorPane background;
  @FXML public AnchorPane pages;

  public void initialize(URL location, ResourceBundle rb) {
    BackgroundManager.getInstance().initialize(background);
    PageManager.getInstance().initialize(pages);
    MenuBarManager.getInstance().initialize(menuBar);

    PageManager.getInstance().loadPage(PageManager.Pages.Login);
  }
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

  public void switchToFlowerService() {
    PageManager.getInstance().loadPage(PageManager.Pages.FlowerSR);
  }

  public void switchToGiftService() {
    PageManager.getInstance().loadPage(PageManager.Pages.GiftDeliverySR);
  }

  public void switchToSanitationService() {
    PageManager.getInstance().loadPage(PageManager.Pages.SanitationSR);
  }

  public void switchToMapEditor() {
    PageManager.getInstance().loadPage(PageManager.Pages.MapEditor);
  }

  public void switchToRequestList() {
    PageManager.getInstance().loadPage(PageManager.Pages.RequestList);
  }

  public void switchToRequestHub() {
    PageManager.getInstance().loadPage(PageManager.Pages.RequestHub);
  }

  public void switchToMainMenu() {
    PageManager.getInstance().loadPage(PageManager.Pages.MainMenu);
  }

  public void switchToAPILandingPage(){ PageManager.getInstance().loadPage(PageManager.Pages.APILandingPage);
  }

  public void switchToAdminHub() {
    if (AccountManager.getInstance().getEmployee().getType().getAccessLevel() == 5) {
      PageManager.getInstance().loadPage(PageManager.Pages.AdminHub);
    } else {
      Alert warningAlert =
          new Alert(Alert.AlertType.WARNING, "Sorry you cannot access to this site", ButtonType.OK);
      warningAlert.showAndWait();
    }
  }

  public void logOut() {
    buttonPane.setDisable(true);
    AccountManager.getInstance().reset();
    PageManager.getInstance().loadPage(PageManager.Pages.Login);
  }

  public void exitProgram() {
    WindowManager.getInstance().getPrimaryStage().close();
  }

  public void switchToProfile() {
    PageManager.getInstance().loadPage(PageManager.Pages.Profile);
  }

  public void switchToAbout() {
    PageManager.getInstance().loadPage(PageManager.Pages.About);
  }

  public void switchToGaming() {
    PageManager.getInstance().loadPage(PageManager.Pages.Gaming);
  }

  public void switchToMessaging() {
    PageManager.getInstance().loadPage(PageManager.Pages.Messaging);
  }

  public void switchToEquipList() {
    PageManager.getInstance().loadPage(PageManager.Pages.EquipList);
  }

  public void switchToDashBoard() {
    PageManager.getInstance().loadPage(PageManager.Pages.Dashboard);
  }
}
