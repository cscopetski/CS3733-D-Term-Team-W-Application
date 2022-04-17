package edu.wpi.cs3733.d22.teamW.wApp.controllers.PacMan;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class PacManController {

  public void start(ActionEvent actionEvent) {
    new Game(new Stage());
  }
}
