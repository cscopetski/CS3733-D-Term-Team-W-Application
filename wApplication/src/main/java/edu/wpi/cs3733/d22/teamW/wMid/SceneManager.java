package edu.wpi.cs3733.d22.teamW.wMid;

import static edu.wpi.cs3733.d22.teamW.wMid.SceneManager.Transitions.TranslateY;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class SceneManager {
  private class Page {
    public Pane pane;
    public Object controller;

    public Page(Pane pane, Object controller) {
      this.pane = pane;
      this.controller = controller;
    }

    public boolean tryOnLoad() {
      if (controller != null && controller.getClass().equals(LoadableController.class)) {
        ((LoadableController) controller).onLoad();
        return true;
      }
      return false;
    }

    public boolean tryOnUnload() {
      if (controller != null && controller.getClass().equals(LoadableController.class)) {
        ((LoadableController) controller).onUnload();
        return true;
      }
      return false;
    }
  }

  public enum Scenes {
    Lab,
    LanguageInterpreter,
    MealDelivery,
    MedicalEquipment,
    MedicineDelivery,
    Security,
    Default,
    MainMenu,
    MapEditor,
    RequestList,
    RequestHub,
    Login
  }

  private static class Instance {
    public static final SceneManager instance = new SceneManager();
  }

  private Stage primaryStage;
  private final Hashtable<Scenes, Page> pages = new Hashtable<>();
  private Scenes current;

  private final Hashtable<Stage, Dictionary<String, Object>> information = new Hashtable<>();

  public enum Transitions {
    TranslateY,
    FadeOut,
    Fade
  }

  private SceneManager() {}

  public static SceneManager getInstance() {
    return Instance.instance;
  }

  public void exitApplication() {
    if (pages.get(current).controller != null) {
      pages.get(current).tryOnUnload();
    }
    System.exit(0);
  }

  public void setPrimaryStage(Stage primaryStage) {
    getInstance().primaryStage = primaryStage;

    information.put(primaryStage, new Hashtable<>());
  }

  public void putPane(Scenes scene, Pane pane) {
    pane.setDisable(true);
    pane.setVisible(false);
    if (pages.get(scene) != null) {
      pages.get(scene).pane = pane;
    } else {
      pages.put(scene, new Page(pane, null));
    }
  }

  public void putController(Scenes scene, Object controller) {
    if (pages.get(scene) != null) {
      pages.get(scene).controller = controller;
    } else {
      pages.put(scene, new Page(null, controller));
    }
  }

  /**
   * DEPRECATED
   *
   * @param scene
   */
  public void setPaneVisible(Scenes scene) {
    if (current != null) {;
      pages.get(current).pane.setVisible(false);
      pages.get(current).pane.setDisable(true);
      pages.get(current).tryOnUnload();
    }

    current = scene;
    pages.get(current).pane.setVisible(true);
    pages.get(current).pane.setDisable(false);
    pages.get(current).tryOnLoad();
  }

  public void transitionTo(Scenes scene) {
    transitionTo(scene, Transitions.Fade);
  }

  public void transitionTo(Scenes scene, Transitions transition) {
    transitionTo(scene, transition, 250);
  }

  public void transitionTo(Scenes scene, Transitions transition, double duration) {
    Transition tOld = null;
    Transition tNew = null;
    switch (transition) {
      case TranslateY:
        tOld = new TranslateTransition(Duration.millis(duration), pages.get(current).pane);
        ((TranslateTransition) tOld).setByY(pages.get(current).pane.getHeight());
        break;
      case FadeOut:
        tOld = new FadeTransition(Duration.millis(duration), pages.get(current).pane);
        ((FadeTransition) tOld).setFromValue(1);
        ((FadeTransition) tOld).setToValue(0);
        break;
      case Fade:
        tOld = new FadeTransition(Duration.millis(duration), pages.get(current).pane);
        ((FadeTransition) tOld).setFromValue(1);
        ((FadeTransition) tOld).setToValue(0);
        tNew = new FadeTransition(Duration.millis(duration), pages.get(scene).pane);
        ((FadeTransition) tNew).setFromValue(0);
        ((FadeTransition) tNew).setToValue(1);
    }
    // t.setOnFinished(e -> pages.get(current).pane.setVisible(false));
    pages.get(current).pane.setDisable(true);
    pages.get(current).tryOnUnload();

    current = scene;

    pages.get(current).pane.setVisible(true);
    pages.get(current).pane.setDisable(false);
    pages.get(current).tryOnLoad();

    if (tOld != null) {
      tOld.play();
    }
    if (tNew != null) {
      tNew.play();
    }
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public void setScene(String fileName) throws IOException {
    Parent root =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/" + fileName));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public Scene getScene() {
    return primaryStage.getScene();
  }

  public void putInformation(Stage stage, String infoName, Object info) {
    information.get(stage).put(infoName, info);
  }

  public Object getInformation(Stage stage, String infoName) {
    return information.get(stage).get(infoName);
  }

  public Stage openWindow(String fileName) throws IOException {
    return openWindow(fileName, "");
  }

  public Stage openWindow(String fileName, String title) throws IOException {
    Stage stage = new Stage();
    stage.initOwner(primaryStage);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle(title);
    stage
        .getIcons()
        .add(
            new Image(
                getClass()
                    .getResourceAsStream("/edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png")));
    Parent root =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/" + fileName));
    stage.setScene(new Scene(root));
    stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);

    information.put(stage, new Hashtable<>());
    getScene().getRoot().setEffect(new GaussianBlur(5));
    stage.setOnCloseRequest(e -> getScene().getRoot().setEffect(null));

    stage.showAndWait();

    return stage;
  }

  public Object getController(Scenes scene) {
    return pages.get(scene).controller;
  }

  private void closeWindowEvent(WindowEvent e) {
    information.remove(e.getSource());
  }
}
