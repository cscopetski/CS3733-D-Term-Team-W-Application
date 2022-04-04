package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public abstract class LoadableController implements Initializable {
  protected abstract SceneManager.Scenes GetSceneType();

  @FXML
  public void initialize(URL location, ResourceBundle rb) {
    SceneManager.getInstance().putController(GetSceneType(), this);
  }

  public abstract void onLoad();

  public abstract void onUnload();
}
