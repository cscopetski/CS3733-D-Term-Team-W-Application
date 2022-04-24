package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.Flower;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class InternalTransportPopUpController implements Initializable {

    @FXML AutoCompleteInput OriginComboBox;
    @FXML AutoCompleteInput DestinationComboBox;
    @FXML AutoCompleteInput employeeComboBox;
    @FXML EmergencyButton emergencyButton;
    Alert confirm = new ConfirmAlert();
    Alert emptyFields = new EmptyAlert();
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

    public void submitButton(ActionEvent actionEvent) throws SQLException {
        if (!emptyFields()) {
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {
                //pushFlowerRequestToDB();
                WindowManager.getInstance().storeData("isEmergency", emergencyButton.getValue());
                WindowManager.getInstance().storeData("origin", locationToNodeID(OriginComboBox.getSelectionModel().getSelectedItem().toString()));
                WindowManager.getInstance().storeData("destination", locationToNodeID(OriginComboBox.getSelectionModel().getSelectedItem().toString()));
                WindowManager.getInstance().storeData("employee", getEmployeeID(employeeComboBox.getSelectionModel().getSelectedItem().toString()));
                clearFields();
                fadeOut.playFromStart();
                Stage stage = ((Stage)WindowManager.getInstance().getData("stage"));
                stage.close();
            }
        } else {
            emptyFields.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            onLoad();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onLoad() throws SQLException {
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        OriginComboBox.loadValues(getLocations());
        DestinationComboBox.loadValues(getLocations());
        employeeComboBox.loadValues(getEmployeeNames());
    }

    private boolean emptyFields() {
        return employeeComboBox.getSelectionModel().isEmpty()
                || OriginComboBox.getSelectionModel().isEmpty()
                || DestinationComboBox.getSelectionModel().isEmpty();
    }


    private void clearFields() {
        OriginComboBox.getSelectionModel().clearSelection();
        employeeComboBox.getSelectionModel().clearSelection();
        DestinationComboBox.getSelectionModel().clearSelection();
        emergencyButton.setValue(false);
    }

    private String getEmployeeID(String name) throws SQLException {
        name = name.trim();
        Integer employeeID = null;
        String employeeLastName;
        String employeeFirstName;
        Integer commaIndex = name.indexOf(',');
        employeeLastName = name.substring(0, commaIndex);
        employeeFirstName = name.substring(commaIndex + 2);

        for (Employee e : EmployeeManager.getEmployeeManager().getAllEmployees()) {
            if (e.getLastName().equals(employeeLastName) && e.getFirstName().equals(employeeFirstName)) {
                employeeID = e.getEmployeeID();
            }
        }
        return String.format("%d", employeeID);
    }

    private String locationToNodeID(String target) {
        String nodeID = "FAIL";

        ArrayList<Location> locationsRaw = null;
        try {
            locationsRaw = LocationManager.getLocationManager().getAllLocations();
        } catch (SQLException e) {
            System.out.println("Failed to unearth locations from database");
            e.printStackTrace();
        }
        for (Location l : locationsRaw) {
            if (l.getLongName().equals(target)) {
                nodeID = l.getNodeID();
            }
        }
        return nodeID;
    }

    private ArrayList<String> getEmployeeNames() {
        ArrayList<String> name = new ArrayList<>();
        ArrayList<Employee> employees = null;
        try {
            employees = EmployeeManager.getEmployeeManager().getAllEmployees();
        } catch (SQLException e) {
            System.out.println("Failed to unearth employees from database");
            e.printStackTrace();
        }
        for (Employee e : employees) {
            if (e.getEmployeeID() != -1
                    && (e.getType().equals(EmployeeType.Staff) || e.getType().equals(EmployeeType.Nurse))) {
                String empName = String.format("%s, %s", e.getLastName(), e.getFirstName());
                name.add(empName);
            }
        }
        return name;
    }

    private ArrayList<String> getLocations() {
        ArrayList<String> locations = new ArrayList<>();
        ArrayList<Location> locationsRaw = null;
        ArrayList<Integer> removeIndexes = new ArrayList<>();
        try {
            locationsRaw = LocationManager.getLocationManager().getAllLocations();
        } catch (SQLException e) {
            System.out.println("Failed to unearth locations from database");
            e.printStackTrace();
        }

        for (Location l : locationsRaw) {
            if (l.getNodeType().equals("NONE")) {
            } else locations.add(l.getLongName());
        }
        return locations;
    }
    
}
