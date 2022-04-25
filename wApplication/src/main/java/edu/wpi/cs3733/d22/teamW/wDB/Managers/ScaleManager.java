package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ScaleManager {

    // Singleton Pattern:
    private static class Instance {
        public static final ScaleManager instance = new ScaleManager();
    }

    private ScaleManager() {
        constructor();
    }


    public static ScaleManager getInstance() {
        return Instance.instance;
    }

    //------------------------Class Impl.-------------------------
    private final ArrayList<Class> defaultTrueTypes  = new ArrayList<>();
    private void constructor() {
        defaultTrueTypes.add(Button.class);
        defaultTrueTypes.add(ComboBox.class);
        defaultTrueTypes.add(EmergencyButton.class);
        defaultTrueTypes.add(AutoCompleteInput.class);
    }

    //------------------------WIDTH CHANGE------------------------
    public void setTrueX(Pane parent, double o, double n) {
        scaleChildrenX(parent, o == 0 ? 1 : n / o);
    }

    private void scaleChildrenX(Pane parent, double scale) {
        for (Node child : parent.getChildren()) {
            if (scaleHelper(scale, child)) continue;
            child.setScaleX(child.getScaleX() * scale);
        }
    }

    //------------------------LENGTH CHANGE------------------------
    public void setTrueY(Pane parent, double o, double n){
        scaleChildrenY(parent, o == 0 ? 1 : n / o);
    }

    private void scaleChildrenY(Pane parent, double scale) {
        for (Node child : parent.getChildren()) {
            if (scaleHelper(scale, child)) continue;
            child.setScaleY(child.getScaleY() * scale);
        }
    }

    //----------------------HELPER FUNCTIONS-----------------------
    private boolean scaleHelper(double scale, Node child) {
        if (child.getClass().getSuperclass().equals(Pane.class)) {
            scaleChildrenX((Pane) child, scale);
            return true;
        }
        Object scalable = child.getProperties().get("isScalable");
        boolean shouldCheckDefault = true;
        if (scalable != null) {
            if (scalable.equals("false")) {
                return true;
            }
            if (scalable.equals("true")) {
                shouldCheckDefault = false;
            }
        }
        if (shouldCheckDefault && !defaultTrueTypes.contains(child.getClass())) {
            return true;
        }
        return false;
    }
}