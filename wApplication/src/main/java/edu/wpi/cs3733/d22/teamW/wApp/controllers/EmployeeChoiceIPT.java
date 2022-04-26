package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeChoiceIPT implements Initializable {

    @FXML
    Label RequestID;
    @FXML Label Origin;
    @FXML Label Destination;
    @FXML Label Priority;
    @FXML AutoCompleteInput employeeComboBox;
    Alert confirm = new ConfirmAlert();
    Alert emptyFields = new EmptyAlert();
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

    public void submitButton(ActionEvent actionEvent) throws SQLException {
        if (!emptyFields()) {
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {
                EmployeeChoiceIPTSingleton.getEmployeeChoiceIPTSingleton().setEmployeeID(String.valueOf(getEmployeeID(employeeComboBox.getValue())));
                EmployeeChoiceIPTSingleton.getEmployeeChoiceIPTSingleton().setComfirm(true);
                clearFields();
                fadeOut.playFromStart();
                Stage stage = ((Stage)WindowManager.getInstance().getData("stage"));
                stage.close();
            }
        } else {
            emptyFields.show();
        }
    }

    public void cancelButton(ActionEvent actionEvent) throws SQLException {
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {
                EmployeeChoiceIPTSingleton.getEmployeeChoiceIPTSingleton().setComfirm(false);
                clearFields();
                fadeOut.playFromStart();
                Stage stage = ((Stage)WindowManager.getInstance().getData("stage"));
                stage.close();
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
        EmployeeChoiceIPTSingleton info = EmployeeChoiceIPTSingleton.getEmployeeChoiceIPTSingleton();
        RequestID.setText(info.getRequestID());
        Origin.setText(info.getOrigin());
        Destination.setText(info.getDestination());
        Priority.setText(info.getPriority());
        employeeComboBox.loadValues(getEmployeeNames());
    }

    private boolean emptyFields() {
        return employeeComboBox.getSelectionModel().isEmpty();
    }


    private void clearFields() {
        employeeComboBox.getSelectionModel().clearSelection();
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

}
