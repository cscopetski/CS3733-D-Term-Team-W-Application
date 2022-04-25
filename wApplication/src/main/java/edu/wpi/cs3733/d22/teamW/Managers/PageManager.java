package edu.wpi.cs3733.d22.teamW.Managers;

import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.ScaleManager;
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
    MainMenu("MainMenuPage.fxml", "Main Menu", true),
    MapEditor("MapEditorPage.fxml", "Map Editor", true),
    RequestList("RequestListPage.fxml", "Request List"),
    RequestHub("RequestHubPage.fxml", "Request Hub", true),
    Login("LoginPage.fxml", "Login"),
    About("AboutPage.fxml", "About", true),
    Profile("ProfilePage.fxml", "Profile"),
    AdminHub("AdminHub.fxml", "Admin"),
    Messaging("MessagingPage.fxml", "Messaging"),
    Gaming("Gaming.fxml", "Gaming"),
    EquipList("EquipmentList.fxml", "Equipment"),
    Dashboard("DashBoard.fxml", "Dashboard"),
    LabSR("ServiceRequestPages/LabServiceRequestPage.fxml", "Lab Service Request"),
    LanguageInterpreterSR("ServiceRequestPages/LanguageInterpreterServiceRequestPage.fxml", "Language Interpreter Service Request"),
    MealDeliverySR("ServiceRequestPages/MealDeliveryServiceRequestPage.fxml", "Meal Delivery Service Request"),
    MedicalEquipmentSR("ServiceRequestPages/MedicalEquipmentServiceRequestPage.fxml", "Medical Equipment Service Request"),
    MedicineDeliverySR("ServiceRequestPages/MedicineDeliveryServiceRequestPage.fxml", "Medicine Delivery Service Request"),
    SecuritySR("ServiceRequestPages/SecurityServiceRequestPage.fxml" ,"Security Service Request"),
    ComputerSR("ServiceRequestPages/ComputerServiceRequestPage.fxml", "Computer Service Request"),
    SanitationSR("ServiceRequestPages/SanitationRequest.fxml", "Sanitation Service Request"),
    FlowerSR("ServiceRequestPages/FlowerRequestPage.fxml", "Flower Delivery Service Request"),
    GiftDeliverySR("ServiceRequestPages/GiftDeliveryRequest.fxml", "Gift Delivery Service Request");

    final String path;
    final String name;
    final Pane persistentControl;

    Pages(String path, String name){
      this(path, name, false);
    }
    Pages(String path, String name, boolean persistent) {
      this.path = path;
      this.name = name;
      if (persistent) {
        persistentControl = loadPage();
      }else {
        persistentControl = null;
      }
    }

    String getPath() {
      return path;
    }

    String getName() {
      return name;
    }

    boolean isPersistent() {
      return this.persistentControl != null;
    }

    Pane loadPage() {
      if (this.persistentControl != null) {
        return this.persistentControl;
      }

      Pane p;
      try {
        p = FXMLLoader.load(
                        getClass().getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/" + getPath()));
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
      return p;
    }
  }

  private AnchorPane parent;
  private Pages current;
  private Pane currentPane;
  private ArrayList<PageChangeFunction> pageChangeListeners = new ArrayList<>();

  public void initialize(AnchorPane pane) {
    parent = pane;
    pane.getChildren().clear();
  }

  public void loadPage(Pages page) {
    callAllPageChangeListeners(current, page);

    Pane prev = null;
    if (!parent.getChildren().isEmpty()) {
      prev = (Pane) parent.getChildren().get(parent.getChildren().size() - 1);
    }
    Pane p = page.loadPage();
    if (p == null) {
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
    currentPane = p;
    p.toFront();

    p.widthProperty().addListener((e, o, n) -> {
      ScaleManager.getInstance().setTrueY(p, o.doubleValue(), n.doubleValue());
      ScaleManager.getInstance().setTrueX(p, o.doubleValue(), n.doubleValue());
    });
  }

  public Pages getCurrent() {
    return current;
  }
  public Pane getCurrentPane() {
    return currentPane;
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
