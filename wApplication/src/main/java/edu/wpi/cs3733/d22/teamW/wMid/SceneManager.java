package edu.wpi.cs3733.d22.teamW.wMid;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneManager {
  public enum Scenes {
    Lab,
    LanguageInterpreter,
    MealDelivery,
    MedicalEquipment,
    MedicineDelivery,
    Security,
    Default,
    MainMenu,
    MapEditor
  }

  private static class Instance {
    public static final SceneManager instance = new SceneManager();
  }

  private Stage primaryStage;
  private final Dictionary<Scenes, Pane> panes = new Hashtable<>();
  private Scenes current;

  private SceneManager() {}

  public static SceneManager getInstance() {
    return Instance.instance;
  }

  public void setPrimaryStage(Stage primaryStage) {
    getInstance().primaryStage = primaryStage;
  }

  public void putPane(Scenes scene, Pane pane) {
    panes.put(scene, pane);
    pane.setDisable(true);
    pane.setVisible(false);
  }

  public void setPaneVisible(Scenes scene) {
    if (current != null) {
      panes.get(current).setVisible(false);
      panes.get(current).setDisable(true);
    }
    current = scene;
    panes.get(current).setVisible(true);
    panes.get(current).setDisable(false);
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public void setWindow(String fileName) throws IOException {
    Parent root =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/" + fileName));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public Scene getScene() {
    return primaryStage.getScene();
  }
}
