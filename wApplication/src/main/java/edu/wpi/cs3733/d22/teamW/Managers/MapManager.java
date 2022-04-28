package edu.wpi.cs3733.d22.teamW.Managers;

import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Location;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class MapManager {
    public interface LocationSelectionMade {
        void selectionMade(Location l);
    }

    private static class Instance {
        private static final MapManager instance = new MapManager();
    }

    private String currFloor = "0";
    private final ArrayList<Circle> locDots = new ArrayList<>();
    private final ArrayList<Location> currFloorLoc = new ArrayList<>();
    private final ArrayList<String> currFloorNodeID = new ArrayList<>();

    public static MapManager getInstance() throws NonExistingMedEquip, SQLException {
        return Instance.instance;
    }

    private MapManager() {
        refreshMap();
    }


    public void setCurrFloor(String floor) {
        currFloor = floor;
        refreshMap();
    }

    public void refreshMap() {
        currFloorLoc.clear();
        currFloorNodeID.clear();
        ArrayList<edu.wpi.cs3733.d22.teamW.wDB.entity.Location> locList =
                null;
        try {
            locList = LocationManager.getLocationManager().getAllLocations();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (edu.wpi.cs3733.d22.teamW.wDB.entity.Location location : locList) {
            if (location.getFloor().equalsIgnoreCase(currFloor)) {
                currFloorLoc.add(new Location(location));
                currFloorNodeID.add(location.getNodeID());
            }
        }
    }


    public void createDots(LocationSelectionMade lsm) {
        locDots.clear();
        for (int i = 0; i < currFloorLoc.size(); i++) {
            Circle circ = new Circle(12, Color.RED);
            Image locationIcon =
                    new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/icons/icon_Location.png");
            ImagePattern locPattern = new ImagePattern(locationIcon);
            circ.setFill(locPattern);
            circ.setCenterX((currFloorLoc.get(i).getXCoord()));
            circ.setCenterY((currFloorLoc.get(i).getYCoord()));
            int finalI = i;
            circ.setOnMouseClicked((event -> {
                Location l = currFloorLoc.get(finalI);
                lsm.selectionMade(l);
            }));
            Tooltip T = new Tooltip();
            try {
                T.setText( LocationManager.getLocationManager().getLocation(currFloorLoc.get(i).getNodeID()).getShortName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            T.setShowDelay(Duration.ZERO);
            T.setHideDelay(Duration.ZERO);
            Tooltip.install(circ, T);
            locDots.add(circ);
        }
    }

    public void clearDots() {
        locDots.clear();
    }
    public ArrayList<Circle> getDots(LocationSelectionMade lsm){
        ArrayList<Circle> output = new ArrayList<>();
        createDots(lsm);
        Collections.copy(output, locDots);
        return output;
    }
}
