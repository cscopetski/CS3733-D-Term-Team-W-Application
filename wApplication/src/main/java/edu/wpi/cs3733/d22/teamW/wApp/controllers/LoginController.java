package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.util.ArrayList;
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

  public void login() {

    if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
      username.getText();
      password.getText();
      if (true) {
        ((DefaultPageController)
                SceneManager.getInstance().getController(SceneManager.Scenes.Default))
            .menuBar.setVisible(true);
        SceneManager.getInstance().setPaneVisible(SceneManager.Scenes.MainMenu);
      }
    } else {
      emptyFields.show();
    }
  }

  public void hidePassword() {
    if (!password.getText().isEmpty()) {
      for (int i = 0; i < password.getText().length(); i++) {
        String currLetter = Character.toString(password.getText().charAt(i));
        if (!currLetter.equals("*")) {
          passwordHidden.add(currLetter);
        }
        passwordShown.add("*");
        password.setText(passwordShown.toString());
      }
    }
    password.setText("*");
  }
}
