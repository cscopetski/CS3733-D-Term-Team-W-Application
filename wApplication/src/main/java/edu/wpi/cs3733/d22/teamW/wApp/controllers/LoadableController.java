package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public abstract class LoadableController implements Initializable {
  protected abstract SceneManager.Scenes GetSceneType();

  @FXML
  public void initialize(URL location, ResourceBundle rb) {
    SceneManager.getInstance().putController(GetSceneType(), this);
  }

  public abstract void onLoad() throws SQLException;

  public abstract void onUnload();

  public void clearAll(ArrayList<Control> allFields) {
    for (int i = 0; i < allFields.size(); i++) {
      if (allFields.get(i).getClass().equals(TextField.class)) {}
    }
  }
}
