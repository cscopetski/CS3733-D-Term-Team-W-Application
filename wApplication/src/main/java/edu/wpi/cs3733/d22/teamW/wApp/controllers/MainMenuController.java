package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeMessageManager;
import edu.wpi.cs3733.d22.teamW.wMid.Account;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class MainMenuController extends LoadableController {

  @FXML Button buttonSR;
  @FXML Circle newMessagesCircle;

  public void switchToRequestHub(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.RequestHub);
  }

  public void switchToMapDisplay(ActionEvent event) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.MapEditor);
  }

  public void switchToMessagesPage(ActionEvent actionEvent) {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.Messaging);
  }

  @Override
  public SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.MainMenu;
  }

  @Override
  public void onLoad() {
    System.out.println("load");
    newMessagesCircle.setVisible(false);
    try {
      if (EmployeeMessageManager.getEmployeeMessageManager()
              .countUnreadMessagesAs(Account.getInstance().getEmployee().getEmployeeID())
          > 0) {
        newMessagesCircle.setVisible(true);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onUnload() {
    System.out.println("unload");
  }
}
