package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class DefaultPage {

  @FXML private Pane content;

  public void switchToMedicineDelivery(ActionEvent event) throws IOException {
    SceneManager.getInstance().setWindow(SceneManager.Scenes.MedicineDelivery, content);
  }

  public void switchToLab(ActionEvent event) throws IOException {
    SceneManager.getInstance().setWindow(SceneManager.Scenes.Lab, content);
  }

  public void switchToMedicalEquipmentDelivery(ActionEvent event) throws IOException {
    SceneManager.getInstance().setWindow(SceneManager.Scenes.MedicalEquipment, content);
  }

  public void switchToMealDelivery(ActionEvent event) throws IOException {
    SceneManager.getInstance().setWindow(SceneManager.Scenes.MealDelivery, content);
  }

  public void switchToLanguageInterpreter(ActionEvent event) throws IOException {
    SceneManager.getInstance().setWindow(SceneManager.Scenes.LanguageInterpreter, content);
  }

  public void switchToSecurity(ActionEvent event) throws IOException {
    SceneManager.getInstance().setWindow(SceneManager.Scenes.Security, content);
  }

  public void switchToMapEditor(ActionEvent event) throws IOException {
    SceneManager.getInstance().setWindow(SceneManager.Scenes.MapEditor, content);
  }
}
