package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.layout.Pane;

public class LoginController extends LoadableController {

  @FXML Pane background;
  @FXML Button loginButton;
  @FXML TextField username;
  @FXML TextField password;
  ArrayList<String> passwordHidden;
  ArrayList<String> passwordShown;
  @FXML Label existCase;
  @FXML Label matchCase;

  EmployeeManager eM = EmployeeManager.getEmployeeManager();

  Alert emptyFields =
      new Alert(
          Alert.AlertType.ERROR,
          "There are required fields empty " + " !",
          ButtonType.OK,
          ButtonType.CANCEL);
  @FXML ComboBox<String> equipmentSelection;

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Login;
  }

  @Override
  public void onLoad() {}

  @Override
  public void onUnload() {}

  public void login() throws SQLException {
    if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
      existCase.setVisible(false);
      matchCase.setVisible(false);
      if (eM.passwordMatch(username.getText(), password.getText())) {
        ((DefaultPageController)
                SceneManager.getInstance().getController(SceneManager.Scenes.Default))
            .setEmployee(eM.getEmployee(username.getText()));

        ((DefaultPageController)
                SceneManager.getInstance().getController(SceneManager.Scenes.Default))
            .menuBar.setVisible(true);
        ((DefaultPageController)
                SceneManager.getInstance().getController(SceneManager.Scenes.Default))
            .buttonPane.setDisable(false);
        SceneManager.getInstance()
            .transitionTo(SceneManager.Scenes.MainMenu, SceneManager.Transitions.FadeOut);
        username.clear();
        password.clear();
      } else if (!eM.usernameExists(username.getText())) {
        existCase.setVisible(true);
      } else {
        matchCase.setVisible(true);
      }
    } else {
      emptyFields.show();
    }
  }

  public void onEnter(ActionEvent actionEvent) throws SQLException {
    login();
  }
}
