package edu.wpi.cs3733.d22.teamW.Managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.animation.Transition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PageManager {
    //Pages Enum:
    public interface PageChangeFunction {
        void changed(PageManager.Pages oldPage, PageManager.Pages newPage);
    }

    public interface SimpleFunction {
        void function();
    }

    public enum Pages {
        None("nopath", "none"),
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
        SecuritySR("ServiceRequestPages/SecurityServiceRequestPage.fxml", "Security Service Request"),
        ComputerSR("ServiceRequestPages/ComputerServiceRequestPage.fxml", "Computer Service Request"),
        SanitationSR("ServiceRequestPages/SanitationRequest.fxml", "Sanitation Service Request"),
        FlowerSR("ServiceRequestPages/FlowerRequestPage.fxml", "Flower Delivery Service Request"),
        GiftDeliverySR("ServiceRequestPages/GiftDeliveryRequest.fxml", "Gift Delivery Service Request"),
        APILandingPage("APILandingPage.fxml", "API Service Hub");

        private final String path;
        private final String name;
        private final boolean isPersistent;

        private Pane persistentControl;

        Pages(String path, String name) {
            this(path, name, false);
        }

        Pages(String path, String name, boolean isPersistent) {
            this.path = path;
            this.name = name;
            this.isPersistent = isPersistent;
            this.persistentControl = null;
        }

        public String getPath() {
            return path;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getName();
        }

        public boolean isPersistent() {
            return isPersistent;
        }

        public Pane loadPage() {
            if (isPersistent && persistentControl != null) {
                return persistentControl;
            }

            Pane p;
            try {
                p = FXMLLoader.load(
                        getClass().getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/" + getPath()));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            AnchorPane.setTopAnchor(p, 0.0);
            AnchorPane.setBottomAnchor(p, 0.0);
            AnchorPane.setLeftAnchor(p, 0.0);
            AnchorPane.setRightAnchor(p, 0.0);

            p.getProperties().put("PageType", this);
            System.out.println(getName() + " Loaded");
            if (isPersistent) {
                persistentControl = p;
            }
            return p;
        }

        public static Pages getPageType(Node n) {
            try {
                return (Pages) n.getProperties().get("PageType");
            } catch (Exception e) {
                return Pages.None;
            }
        }
    }


    // Singleton Pattern:
    private static class Instance {
        public static final PageManager instance = new PageManager();
    }

    private PageManager() {
    }

    public static PageManager getInstance() {
        return Instance.instance;
    }

    // PageManager Class:
    private AnchorPane parent;
    private int offset = 0;
    private final ArrayList<PageChangeFunction> pageChangeListeners = new ArrayList<>();
    private final ArrayList<SimpleFunction> historyChangeListeners = new ArrayList<>();

    public void initialize(AnchorPane pane) {
        parent = pane;
        pane.getChildren().clear();
    }

    public void attachOnLoad(final Pages page, SimpleFunction sf) {
        attachPageChangeListener((o, n) -> {
            if (n.equals(page)) {
                sf.function();
            }
        });
    }
    public void attachOnUnload(final Pages page, SimpleFunction sf) {
        attachPageChangeListener((o, n) -> {
            if (o.equals(page)) {
                sf.function();
            }
        });
    }
    public void attachPageChangeListener(PageChangeFunction pageChangeListener) {
        pageChangeListeners.add(pageChangeListener);
    }
    private void callAllPageChangeListeners(Pages o, Pages n) {
        pageChangeListeners.forEach(pcl -> pcl.changed(o, n));
    }
    public void attachHistoryChangeListener(SimpleFunction sf) {
        historyChangeListeners.add(sf);
    }
    private void callAllHistoryPageChangeListeners() {
        historyChangeListeners.forEach(SimpleFunction::function);
    }

    public void loadPage(Pages page) {
        Pane p = page.loadPage();
        if (p == null) {
            return;
        }

        if (page.isPersistent()) {
            if (page == getCurrentPage()) {
                return;
            }
            AtomicInteger indexOf = new AtomicInteger(-1);
            parent.getChildren().removeIf(t -> {
                if (Pages.getPageType(t) == page) {
                    indexOf.set(parent.getChildren().indexOf(t));
                    return true;
                }
                return false;
            });
            if (indexOf.get() != -1) {
                if (getActiveIndex() < indexOf.get()) {
                    offset--;
                }
            }
        }

        clearForwardHistory();

        parent.getChildren().add(p);

        switchBetween(getPreviousPane(), getCurrentPane());

        checkMaxPages();

        callAllHistoryPageChangeListeners();
    }

    private void checkMaxPages() {
        if (parent.getChildren().size() > 5) {
            parent.getChildren().remove(0);
        }
    }

    public Pane getCurrentPane() {
        return getOffsetPane(0);
    }
    public Pages getCurrentPage() {
        return Pages.getPageType(getCurrentPane());
    }

    public int getActiveIndex() {
        return parent.getChildren().size() - 1 - offset;
    }
    public void setActiveIndex(int index) {
        if (index < 0 || index >= parent.getChildren().size()) {
            throw new IndexOutOfBoundsException();
        }
        if (index == getActiveIndex()) {
            return;
        }

        switchBetween(getActiveIndex(), index);
        offset += getActiveIndex() - index;
    }

    public void goBack() {
        setActiveIndex(getActiveIndex() - 1);
    }
    public void goForward() {
        setActiveIndex(getActiveIndex() + 1);
    }
    public boolean canGoBack() {
        return getActiveIndex() > 0;
    }
    public boolean canGoForward() {
        return offset > 0;
    }

    public ArrayList<String> getHistory() {
        return (ArrayList<String>) parent.getChildren().stream().map(n -> Pages.getPageType(n).getName()).collect(Collectors.toList());
    }
    public void clearAllHistory() {
        clearBackHistory();
        clearForwardHistory();

        System.out.println("Cleared Page History");
        callAllHistoryPageChangeListeners();
    }

    private void switchBetween(int o, int n) {
        switchBetween((Pane) parent.getChildren().get(o), (Pane) parent.getChildren().get(n));
    }
    private void switchBetween(Pane o, Pane n) {
        callAllPageChangeListeners(Pages.getPageType(o), Pages.getPageType(n));

        if (o != null) {
            final Node prevF = o;
            TransitionManager.createTransition(
                    TransitionManager.Transitions.FadeOut, o, () -> {
                        hide(prevF);
                        System.out.println(Pages.getPageType(prevF) + ": hidden complete");
                    }).play();
            System.out.println(Pages.getPageType(prevF) + ": hidden started");
            o.setDisable(true);
        }
        TransitionManager.createTransition(TransitionManager.Transitions.FadeIn, n).play();

        show(n);
        System.out.println(Pages.getPageType(o) + " -> " + Pages.getPageType(n));
    }

    private Pane getOffsetPane(int offset) {
        if (parent.getChildren().isEmpty() || getActiveIndex() - offset < 0) {
            return null;
        }
        return (Pane) parent.getChildren().get(getActiveIndex() - offset);
    }
    private Pane getPreviousPane() {
        return getOffsetPane(1);
    }

    private void hide(Node n) {
        n.setVisible(false);
        n.setDisable(true);
        if (Pages.getPageType(n).equals(Pages.Login)) {
            clearForwardHistory();
        }
    }
    private void show(Node n) {
        n.setVisible(true);
        n.setDisable(false);
        n.requestFocus();
    }

    private void clearBackHistory() {
        if (canGoBack()) {
            parent.getChildren().remove(0, getActiveIndex());
        }
    }
    private void clearForwardHistory() {
        if (canGoForward()) {
            parent.getChildren().remove(getActiveIndex() + 1, parent.getChildren().size());
        }
        offset = 0;
    }
}
