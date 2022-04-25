package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Location;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.sql.SQLException;
import java.util.ArrayList;

public class HospitalMap extends VBox {
    Integer size = 0;
    @FXML public ScrollPane scrollPane;
    @FXML public Slider scaleSlider;
    @FXML public Group scrollGroup;
    ArrayList<Circle> locDots = new ArrayList<>();
    private String currFloor = "0";
    private ArrayList<Location> currFloorLoc = new ArrayList<>();
    private ArrayList<String> currFloorNodeID = new ArrayList<>();
    @FXML private ImageView mapList;
    @FXML private MenuItem F1;
    @FXML private MenuItem F2;
    @FXML private MenuItem F3;
    @FXML private MenuItem F4;
    @FXML private MenuItem F5;
    @FXML private MenuItem FL1;
    @FXML private MenuItem FL2;
    @FXML private MenuButton dropdown;
    private LocationManager locationManager = LocationManager.getLocationManager();
    Image img1 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F1.png");
    Image img2 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F2.png");
    Image img3 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F3.png");
    Image imgL1 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/LL1.png");
    Image imgL2 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/LL2.png");
    Image img4 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F4.png");
    Image img5 = new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/F5.png");
    public HospitalMap() throws NonExistingMedEquip, SQLException {
        scaleSlider = new Slider();
        scrollPane = new ScrollPane();
        dropdown = new MenuButton();
        mapList = new ImageView();
        F1 = new MenuItem();
        F2 = new MenuItem();
        F3 = new MenuItem();
        F4 = new MenuItem();
        F5 = new MenuItem();
        FL1 = new MenuItem();
        FL2 = new MenuItem();
        scrollPane.setContent(mapList);
        dropdown.getItems().add(FL1);
        dropdown.getItems().add(FL2);
        dropdown.getItems().add(F1);
        dropdown.getItems().add(F2);
        dropdown.getItems().add(F3);
        dropdown.getItems().add(F4);
        dropdown.getItems().add(F5);
        mapList.setImage(img1);
        FL1.setOnAction((e) -> {
            try {
                swapFloorL1();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        FL2.setOnAction((e) ->{ try { swapFloorL2(); } catch (SQLException ex) { ex.printStackTrace();}});
        F1.setOnAction((e) ->{
            try {
                swapFloor1();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        F2.setOnAction((e) -> {
            try {
                swapFloor2();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        F3.setOnAction((e) -> {
            try {
                swapFloor3();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        F4.setOnAction((e) -> {
            try {
                swapFloor4();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        F5.setOnAction((e) -> {
            try {
                swapFloor5();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    }
    public void swapFloor1() throws SQLException {
        removeMarkers();
        currFloor = "01";
        refresh();
        dropdown.setText("Floor 1");
        mapList.setImage(img1);
    }

    public void swapFloor2() throws SQLException {
        removeMarkers();
        currFloor = "02";
        refresh();
        dropdown.setText("Floor 2");
        mapList.setImage(img2);
    }

    public void swapFloor3() throws SQLException {
        removeMarkers();
        currFloor = "03";
        refresh();
        dropdown.setText("Floor 3");
        mapList.setImage(img3);
    }
    public void swapFloor4() throws SQLException {
        currFloor = "04";
        removeMarkers();
        refresh();
        dropdown.setText("Floor 4");
        mapList.setImage(img4);
    }

    public void swapFloor5() throws SQLException {
        currFloor = "05";
        removeMarkers();
        refresh();
        dropdown.setText("Floor 5");
        mapList.setImage(img5);
    }


    public void swapFloorL1() throws SQLException {
        currFloor = "L1";
        removeMarkers();
        refresh();
        System.out.println(FL1.getText());
        dropdown.setText("Lower Floor 1");
        mapList.setImage(imgL1);
    }

    public void swapFloorL2() throws SQLException {
        currFloor = "L2";
        removeMarkers();
        refresh();
        System.out.println(FL2.getText());
        dropdown.setText("Lower Floor 2");
        mapList.setImage(imgL2);
    }
    private void removeMarkers() {
        scrollGroup.getChildren().removeAll(locDots);
        locDots.clear();
    }
    public void refresh() throws SQLException{
        removeMarkers();
        currFloorLoc.clear();
        currFloorNodeID.clear();
        ArrayList<edu.wpi.cs3733.d22.teamW.wDB.entity.Location> locList =
                locationManager.getAllLocations();
        for (int i = 0; i < locList.size(); i++) {
            if (locList.get(i).getFloor().equalsIgnoreCase(currFloor)) {
                currFloorLoc.add(new edu.wpi.cs3733.d22.teamW.wApp.mapEditor.Location(locList.get(i)));
                currFloorNodeID.add(locList.get(i).getNodeID());
            }
        }
        generateMarkers();
    }
    private void generateMarkers() {
        size = currFloorLoc.size();
        for (int i = 0; i < size; i++) {
            Circle circ = new Circle(12, Color.RED);
            Image locationIcon =
                    new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/Maps/icons/icon_Location.png");
            ImagePattern locPattern = new ImagePattern(locationIcon);
            circ.setFill(locPattern);
            circ.setCenterX((currFloorLoc.get(i).getXCoord()));
            circ.setCenterY((currFloorLoc.get(i).getYCoord()));
            circ.setOnMouseClicked((event -> {
                currFloorLoc.get(locDots.indexOf(event.getSource())).getNodeID();
            }));
            Tooltip T = new Tooltip();
            T.setText(currFloorLoc.get(i).getNodeID());
            T.setShowDelay(Duration.ZERO);
            T.setHideDelay(Duration.ZERO);
            Tooltip.install(circ, T);
            locDots.add(circ);
            scrollGroup.getChildren().add(circ);
        }
    }
}
