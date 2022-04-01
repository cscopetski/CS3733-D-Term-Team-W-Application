package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.IOException;
import javafx.event.ActionEvent;

public class DefaultPage {
  public void goMap(ActionEvent actionEvent) throws IOException {
    SceneManager.getInstance().setScene(SceneManager.Scenes.MapEditor);
  }
}
