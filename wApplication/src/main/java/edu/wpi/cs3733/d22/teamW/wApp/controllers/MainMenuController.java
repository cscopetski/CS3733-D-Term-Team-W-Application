package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import javafx.event.ActionEvent;

public class MainMenuController extends LoadableController {
  public void switchToServiceRequest(ActionEvent event) {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.MedicalEquipment);
  }

  public void switchToMapDisplay(ActionEvent event) {
    SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.MapEditor);
  }

  @Override
  public SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.MainMenu;
  }

  @Override
  public void onLoad() {
    System.out.println("load");
  }

  @Override
  public void onUnload() {
    System.out.println("unload");
  }
}
