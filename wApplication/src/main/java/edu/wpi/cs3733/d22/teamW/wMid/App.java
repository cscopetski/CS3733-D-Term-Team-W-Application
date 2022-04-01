package edu.wpi.cs3733.d22.teamW.wMid;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    SceneManager.getInstance().setPrimaryStage(primaryStage);
    primaryStage.setTitle("Mass General Brigham Service Requests");
    primaryStage
        .getIcons()
        .add(
            new Image(
                getClass()
                    .getResourceAsStream("/edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png")));
    SceneManager.getInstance().setScene(SceneManager.Scenes.Default);
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
