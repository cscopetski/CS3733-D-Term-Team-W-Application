package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import javafx.event.ActionEvent;

public class MainMenuController {
  public void switchToServiceRequest(ActionEvent event) {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.MedicalEquipment);
  }

  public void switchToMapDisplay(ActionEvent event) {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.MapEditor);
  }
}
