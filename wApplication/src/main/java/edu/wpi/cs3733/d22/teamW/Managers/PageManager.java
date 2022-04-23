package edu.wpi.cs3733.d22.teamW.Managers;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.Transition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PageManager {
  public interface PageChangeFunction {
    void changed(PageManager.Pages oldPage, PageManager.Pages newPage);
  }

  // Singleton Pattern:
  private static class Instance {
    public static final PageManager instance = new PageManager();
  }

  private PageManager() {}

  public static PageManager getInstance() {
    return Instance.instance;
  }

  // PageManager Class:
  public enum Pages {
    Default("DefaultPage.fxml"),
    MainMenu("MainMenuPage.fxml"),
    MapEditor("MapEditorPage.fxml"),
    RequestList("RequestListPage.fxml"),
    RequestHub("RequestHubPage.fxml"),
    Login("LoginPage.fxml"),
    About("AboutPage.fxml"),
    Profile("ProfilePage.fxml"),
    AdminHub("AdminHub.fxml"),
    Messaging("MessagingPage.fxml"),
    Gaming("Gaming.fxml"),
    EquipList("EquipmentList.fxml"),
    Dashboard("DashBoard.fxml"),
    LabSR("ServiceRequestPages/LabServiceRequestPage.fxml"),
    LanguageInterpreterSR("ServiceRequestPages/LanguageInterpreterServiceRequestPage.fxml"),
    MealDeliverySR("ServiceRequestPages/MealDeliveryServiceRequestPage.fxml"),
    MedicalEquipmentSR("ServiceRequestPages/MedicalEquipmentServiceRequestPage.fxml"),
    MedicineDeliverySR("ServiceRequestPages/MedicineDeliveryServiceRequestPage.fxml"),
    SecuritySR("ServiceRequestPages/SecurityServiceRequestPage.fxml"),
    ComputerSR("ServiceRequestPages/ComputerServiceRequestPage.fxml"),
    SanitationSR("ServiceRequestPages/SanitationRequest.fxml"),
    FlowerSR("ServiceRequestPages/ServiceRequestPage.fxml"),
    GiftDeliverySR("ServiceRequestPages/GiftDeliveryRequest.fxml");

    final String path;

    Pages(String path) {
      this.path = path;
    }

    String getPath() {
      return path;
    }
  }

  private AnchorPane parent;
  private Pages current;
  private ArrayList<PageChangeFunction> pageChangeListeners = new ArrayList<>();

  public void initialize(AnchorPane pane) {
    parent = pane;
    pane.getChildren().clear();
  }
  //
  //  public void loadPage(Pages page) {
  //    loadPage(page, TransitionManager.Transitions.Fade);
  //    //    callAllPageChangeListeners(current, page);
  //    //
  //    //    parent.getChildren().clear(); // remove this to enable a history
  //    //
  //    //    Pane p;
  //    //    try {
  //    //      p =
  //    //          FXMLLoader.load(
  //    //              getClass().getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/" +
  //    // page.getPath()));
  //    //    } catch (IOException e) {
  //    //      e.printStackTrace();
  //    //      return;
  //    //    }
  //    //    AnchorPane.setTopAnchor(p, 0.0);
  //    //    AnchorPane.setBottomAnchor(p, 0.0);
  //    //    AnchorPane.setLeftAnchor(p, 0.0);
  //    //    AnchorPane.setRightAnchor(p, 0.0);
  //    //
  //    //    if (!parent.getChildren().isEmpty()) {
  //    //      hide(parent.getChildren().get(parent.getChildren().size() - 1));
  //    //    }
  //    //    parent.getChildren().add(p);
  //    //    p.requestFocus();
  //    //    current = page;
  //  }

  public void loadPage(Pages page) {
    callAllPageChangeListeners(current, page);

    Pane prev = null;
    if (!parent.getChildren().isEmpty()) {
      prev = (Pane) parent.getChildren().get(parent.getChildren().size() - 1);
    }
    Pane p;
    try {
      p =
          FXMLLoader.load(
              getClass().getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/" + page.getPath()));
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    AnchorPane.setTopAnchor(p, 0.0);
    AnchorPane.setBottomAnchor(p, 0.0);
    AnchorPane.setLeftAnchor(p, 0.0);
    AnchorPane.setRightAnchor(p, 0.0);

    if (prev != null) {
      final Pane prevF = prev;
      Transition tOut =
          TransitionManager.createTransition(
              TransitionManager.Transitions.FadeOut, prev, () -> hide(prevF));
      prev.setDisable(true);
      tOut.play();
    }
    Transition tIn = TransitionManager.createTransition(TransitionManager.Transitions.FadeIn, p);
    tIn.play();

    parent.getChildren().add(p);
    p.requestFocus();
    current = page;
    p.toFront();
  }

  public Pages getCurrent() {
    return current;
  }

  private void show(Node p) {
    p.setVisible(false);
    p.setDisable(true);
    p.requestFocus();
  }

  private void hide(Node p) {
    p.setVisible(true);
    p.setDisable(false);
    parent.getChildren().remove(p); // remove this to enable history
  }

  public void attachPageChangeListener(PageChangeFunction pageChangeListener) {
    pageChangeListeners.add(pageChangeListener);
  }

  public void removePageChangeListener(int index) {
    pageChangeListeners.remove(index);
  }

  public void removePageChangeListener(PageChangeFunction pageChangeListener) {
    pageChangeListeners.remove(pageChangeListener);
  }

  private void callAllPageChangeListeners(Pages o, Pages n) {
    pageChangeListeners.forEach(pcl -> pcl.changed(o, n));
  }
}

/*
public void transitionTo(Scenes scene) {
    if (current == scene) {
      setPaneVisible(scene);
    } else {
      transitionTo(scene, Transitions.Fade);
    }
  }

  public void transitionTo(Scenes scene, Transitions transition) {
    if (current == scene) {
      setPaneVisible(scene);
    } else {
      transitionTo(scene, transition, 250);
    }
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
 */
