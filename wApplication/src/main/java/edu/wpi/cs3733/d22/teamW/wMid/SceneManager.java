package edu.wpi.cs3733.d22.teamW.wMid;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
  public enum Scenes {
    Default
  }

  private static class Instance {
    public static final SceneManager instance = new SceneManager();
  }

  private final Dictionary<Scenes, String> fileNames;
  private Stage primaryStage;

  private SceneManager() {
    fileNames = new Hashtable<>();
    fileNames.put(Scenes.Default, "DefaultPage.fxml");
  }

  public static SceneManager getInstance() {
    return Instance.instance;
  }

  public void setPrimaryStage(Stage primaryStage) {
    getInstance().primaryStage = primaryStage;
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public void setScene(Scenes sceneType) throws IOException {
    Parent root =
        FXMLLoader.load(
            getClass()
                .getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/" + fileNames.get(sceneType)));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public Scene getScene() {
    return primaryStage.getScene();
  }
}
