package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController extends LoadableController {

  @FXML Button buttonSR;

  public void switchToRequestHub(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.RequestHub);
  }

  public void switchToMapDisplay(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MapEditor);
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
