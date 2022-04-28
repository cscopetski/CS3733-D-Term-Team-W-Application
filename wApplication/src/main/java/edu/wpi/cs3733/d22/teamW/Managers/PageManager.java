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
        MainMenu("MainMenuPage.fxml", "Main Menu"),
        MapEditor("MapEditorPage.fxml", "Map Editor"),
        RequestList("RequestListPage.fxml", "Request List"),
        RequestHub("RequestHubPage.fxml", "Request Hub"),
        Login("LoginPage.fxml", "Login"),
        About("AboutPage.fxml", "About"),
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

        private Pane content;

        Pages(String path, String name) {
            this.path = path;
            this.name = name;
            ;
            this.content = null;
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

        public Pane loadPage() {
            if (content != null) {
                return content;
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
            content = p;
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
    private Pages current;
    private final ArrayList<Pages> historyF = new ArrayList<>();
    private final ArrayList<Pages> historyB = new ArrayList<>();
    //private int offset = 0;
    private final ArrayList<PageChangeFunction> pageChangeListeners = new ArrayList<>();
    private final ArrayList<SimpleFunction> historyChangeListeners = new ArrayList<>();

    public void initialize(AnchorPane pane) {
        parent = pane;
        pane.getChildren().clear();
    }

    public void attachOnLoad(final Pages page, SimpleFunction sf) {
        attachPageChangeListener((o, n) -> {
            if (n != null && n.equals(page)) {
                sf.function();
            }
        });
    }

    public void attachOnUnload(final Pages page, SimpleFunction sf) {
        attachPageChangeListener((o, n) -> {
            if (o != null && o.equals(page)) {
                sf.function();
            }
        });
    }

    public void attachPageChangeListener(PageChangeFunction pageChangeListener) {
        pageChangeListeners.add(pageChangeListener);
    }

    private Pages pageIndexOf(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index < historyB.size()) {
            return historyB.get(index);
        }
        index -= historyB.size();
        if (index == 0) {
            return current;
        }
        index--;
        if (index < historyF.size()) {
            return historyF.get(index);
        }
        throw new IndexOutOfBoundsException();
    }

    private void callAllPageChangeListeners(int o, int n) {
        callAllPageChangeListeners(pageIndexOf(o), pageIndexOf(n));
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
        if (historyB.contains(page)) {
            setActiveIndex(historyB.indexOf(page));
            return;
        }
        if (current == page) {
            return;
        }
        if (historyF.contains(page)) {
            setActiveIndex(historyB.size() + 1 + historyF.indexOf(page));
            return;
        }

        //add at end
        Pages prev = current;
        if (!parent.getChildren().isEmpty()) {
            Node c = parent.getChildren().get(parent.getChildren().size() - 1);
            int index = historyB.size();
            TransitionManager.createTransition(
                    TransitionManager.Transitions.FadeOut, (Pane) c, () -> {
                        parent.getChildren().remove(c);
                        historyB.add(index, current);
                    }).play();
        }
        historyB.addAll(historyF);
        historyF.clear();

        Node p = page.loadPage();
        TransitionManager.createTransition(
                TransitionManager.Transitions.FadeIn, (Pane) p, () -> {
                }).play();
        parent.getChildren().add(p);
        current = page;

        callAllPageChangeListeners(prev, page);
        callAllHistoryPageChangeListeners();
    }
    public int getActiveIndex() {
        return historyB.size();
    }
    public void setActiveIndex(int index) {
        int o = getActiveIndex();
        int n = index;
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index < historyB.size()) {
            //move pages in historyB after index to historyF
            var historyToMove = historyB.subList(index, historyB.size());
            historyB.removeAll(historyToMove);
            historyF.addAll(0, historyToMove);

            Node c = parent.getChildren().get(parent.getChildren().size() - 1);
            TransitionManager.createTransition(
                    TransitionManager.Transitions.FadeOut, (Pane) c, () -> {
                        parent.getChildren().remove(c);
                        historyF.add(0, current);
                    }).play();

            //move current page to historyF properly
            Pages page = historyB.remove(index);
            Node p = page.loadPage();
            TransitionManager.createTransition(
                    TransitionManager.Transitions.FadeIn, (Pane) p, () -> {
                    }).play();
            parent.getChildren().add(p);

            callAllPageChangeListeners(o, n);
            //callAllHistoryPageChangeListeners();
            return;
        }
        index -= historyB.size();
        if (index == 0) {
            return;//current index is active
        }
        index--;
        if (index < historyF.size()) {
            //show page at index
            //move all other pages to historyB
            //move current page to historyB properly


            //callAllPageChangeListeners(o, n);
            //callAllHistoryPageChangeListeners();
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean canGoBack() {
        return !historyB.isEmpty();
    }
    public void goBack() {
        setActiveIndex(historyB.size() - 1);
    }
    public boolean canGoForward() {
        return !historyF.isEmpty();
    }
    public void goForward() {
        setActiveIndex(historyB.size() + 2);
    }

    public ArrayList<String> getHistory() {
        ArrayList<String> values = historyB.stream().map(Pages::toString).collect(Collectors.toCollection(ArrayList::new));
        values.add(current.toString());
        values.addAll(historyF.stream().map(Pages::toString).collect(Collectors.toList()));
        return values;
    }

    public void clearAllHistory() {
        historyB.clear();
        historyF.clear();

        System.out.println("Cleared Page History");
        callAllHistoryPageChangeListeners();
    }

    /*
    public void loadPage(Pages page) {
        Pane p = page.loadPage();
        if (p == null) {
            return;
        }

        if (page == getCurrentPage()) {
            return;
        }

        historyF.remove(page);
        historyB.remove(page);

        //clearForwardHistory();

        parent.getChildren().add(p);

        switchPage();
        //switchBetween(getPreviousPane(), getCurrentPane());

        //checkMaxPages();

        callAllHistoryPageChangeListeners();
    }

    private void checkMaxPages() {
        if (parent.getChildren().size() > 5) {
            parent.getChildren().remove(0);
        }
    }

    public Pane getCurrentPane() {
        return (Pane) parent.getChildren().get(parent.getChildren().size() - 1);
    }

    public Pages getCurrentPage() {
        return current;
    }

    public int getActiveIndex() {
        return historyB.size();
    }

    int numShifted = 0;
    public void setActiveIndex(int index) {
        if (index < 0 || index >= historyB.size() + 1 + historyF.size()) {
            throw new IndexOutOfBoundsException();
        }
        if (index == getActiveIndex()) {
            return;
        }

        if (index < historyB.size()) {
            //shift back and forward history around

            //numShifted = historyB.size() - index - 1;

            loadPage(historyB.remove(index));
            return;
        }
        index -= historyB.size();
        if (index == 0) {
            return;
        }
        index--;
        if (index < historyF.size()) {
            loadPage(historyF.remove(index));
        }
        //switchBetween(getActiveIndex(), index);
        //offset += getActiveIndex() - index;
    }

    public void goBack() {
        setActiveIndex(getActiveIndex() - 1);
    }

    public void goForward() {
        setActiveIndex(getActiveIndex() + 1);
    }

    public boolean canGoBack() {
        return historyB.size() > 0;
    }

    public boolean canGoForward() {
        return historyF.size() > 0;
    }

    public ArrayList<String> getHistory() {
        ArrayList<String> values = new ArrayList<>();
        values.addAll(historyB.stream().map(p -> p.toString()).collect(Collectors.toList()));
        values.add(current.toString());
        values.addAll(historyF.stream().map(p -> p.toString()).collect(Collectors.toList()));
        return values;
        //return (ArrayList<String>) parent.getChildren().stream().map(n -> Pages.getPageType(n).getName()).collect(Collectors.toList());
    }

    public void clearAllHistory() {
        historyB.clear();
        historyF.clear();

        //clearBackHistory();
        //clearForwardHistory();

        System.out.println("Cleared Page History");
        callAllHistoryPageChangeListeners();
    }


    private void showPage(Pages p) {
        TransitionManager.createTransition(TransitionManager.Transitions.FadeIn, p.loadPage()).play();
    }

    private void switchPage() {
        Node o = parent.getChildren().get(0);
        Node n = parent.getChildren().size() > 1 ? parent.getChildren().get(1) : null;

        callAllPageChangeListeners(Pages.getPageType(o), Pages.getPageType(n));

        if (o != null) {
            final Node prevF = o;
            Pages p = Pages.getPageType(prevF);
            TransitionManager.createTransition(
                    TransitionManager.Transitions.FadeOut, (Pane) o, () -> {
                        //hide(prevF);
                        parent.getChildren().remove(prevF);
                        historyB.add(p);
                        System.out.println(p + ": hidden complete");
                    }).play();
            System.out.println(p + ": hidden started");
        }
        TransitionManager.createTransition(TransitionManager.Transitions.FadeIn, (Pane) n).play();

        //show(n);
        current = Pages.getPageType(n);
        System.out.println(Pages.getPageType(o) + " -> " + Pages.getPageType(n));
    }

    private void switchBetween(int o, int n) {
        switchBetween((Pane) parent.getChildren().get(o), (Pane) parent.getChildren().get(n));
    }
    private void switchBetween(Pane o, Pane n) {
        callAllPageChangeListeners(Pages.getPageType(o), Pages.getPageType(n));

        if (o != null) {
            final Node prevF = o;
            Pages p = Pages.getPageType(prevF);
            TransitionManager.createTransition(
                    TransitionManager.Transitions.FadeOut, o, () -> {
                        hide(prevF);
                        parent.getChildren().remove(prevF);
                        historyB.add(p);
                        System.out.println(p + ": hidden complete");
                    }).play();
            System.out.println(p + ": hidden started");
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
        historyB.clear();
        if (canGoBack()) {
            parent.getChildren().remove(0, getActiveIndex());
        }
    }

    private void clearForwardHistory() {
        historyF.clear();
        if (canGoForward()) {
            parent.getChildren().remove(getActiveIndex() + 1, parent.getChildren().size());
        }
        offset = 0;
    }
    */
}
