package edu.wpi.cs3733.d22.teamW.Managers;

// import static edu.wpi.cs3733.d22.teamW.Managers.WindowManager.Transitions.TranslateY;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WindowManager {
  // Singleton Pattern:
  private static class Instance {
    public static final WindowManager instance = new WindowManager();
  }

  private WindowManager() {}

  public static WindowManager getInstance() {
    return Instance.instance;
  }

  // WindowManager class:
  private Stage primaryStage;
  private HashMap<String, Object> data = new HashMap<>();

  public void initialize(Stage primaryStage) {
    getInstance().primaryStage = primaryStage;
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public void setScene(String fileName) {
    Parent root = null;
    try {
      root =
          FXMLLoader.load(
              getClass().getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/" + fileName));
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    Scene scene = new Scene(root);

    primaryStage.setScene(scene);
    primaryStage.show();

    //final Parent rootF = root;
    //scene.widthProperty().addListener((e, o, n) -> rootF.setScaleX(rootF.getScaleX() * (o.doubleValue() == 0 ? 1 : n.doubleValue() / o.doubleValue())));
    //scene.heightProperty().addListener((e, o, n) -> rootF.setScaleY(rootF.getScaleY() * (o.doubleValue() == 0 ? 1 : n.doubleValue() / o.doubleValue())));
  }

  public void storeData(String name, Object data) {
    this.data.put(name, data);
  }

  public Object getData(String name) {
    return this.data.get(name);
  }

  public void openWindow(String fxmlPath) {
    openWindow(fxmlPath, "");
  }

  public void openWindow(String fxmlPath, String title) {
    openWindow(fxmlPath, title, "/edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png");
  }

  public void openWindow(String fxmlPath, String title, String iconPath) {
    Parent root;
    try {
      root =
          FXMLLoader.load(
              getClass().getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/" + fxmlPath));
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle(title);
    stage.getIcons().add(new Image(getClass().getResourceAsStream(iconPath)));
    stage.setOnCloseRequest(e -> primaryStage.getScene().getRoot().setEffect(null));
    storeData("Stage",stage);
    primaryStage.getScene().getRoot().setEffect(new GaussianBlur(7.5));
    WindowManager.getInstance().storeData("stage",stage);
    stage.showAndWait();
  }
}

