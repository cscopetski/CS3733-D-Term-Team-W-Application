package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.IOException;
import javafx.event.ActionEvent;

public class RequestHubController {

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
}
