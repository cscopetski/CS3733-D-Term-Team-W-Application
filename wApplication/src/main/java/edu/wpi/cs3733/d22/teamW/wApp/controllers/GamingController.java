package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.PacMan.Game;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.HighScoreTable;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.HighScoreManager;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class GamingController implements Initializable {
  @FXML HighScoreTable hs;

  public void playPacMan(ActionEvent actionEvent) {
    new Game(new Stage());
  }

  public void playSnake(ActionEvent actionEvent) throws IOException {
    Stage S =
        SceneManager.getInstance()
            .openGameWindow(
                "Snake.fxml",
                "Wiggling Wong",
                "/edu/wpi/cs3733/d22/teamW/wApp/assets/Icons/wong.png");
  }

  @Override
  public void initialize(URL location, ResourceBundle rb) {
    hs.setColumnWidth("EmployeeID", 50);
    hs.setColumnWidth("Name", 200);
    hs.setColumnWidth("scoreWiggling", 125);
    hs.setColumnWidth("scoreThreat", 125);
    hs.setStyle("-fx-alignment: CENTER;");

    System.out.println("Trying to load table values now, have already loaded columns");
    try {
      hs.setItems(HighScoreManager.getHighScoreManager().getAllHighScores());
    } catch (Exception e) {
      e.printStackTrace();
    }
    hs.setEditable(false);
  }
}
