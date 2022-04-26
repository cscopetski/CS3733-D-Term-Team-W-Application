package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmployeeTable;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

import java.io.IOException;

import static edu.wpi.cs3733.d22.teamW.wDB.enums.Automation.Automation;

public class AdminHub {
    @FXML
    EmployeeTable employeeTable;
    @FXML
    ToggleButton automation;
    @FXML
    Label successLabel;
    EmployeeManager em = EmployeeManager.getEmployeeManager();

    Alert confirm = new ConfirmAlert();
    Alert noSelectionAlert = new NoSelectionAlert();

    public void initialize() {
        WindowManager.getInstance().storeData("success", false);
        successLabel.setVisible(false);
        setItems();
        if (Automation.getAuto()) {
            automation.setText("Deactivate Automation");
            automation.setSelected(true);
            Automation.on();
        } else {
            automation.setText("Activate Automation");
            automation.setSelected(false);
            Automation.off();
        }
    }

    private void setItems() {
        try {
            employeeTable.setItems(em.getAllEmployees());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void openAddEmployee(ActionEvent actionEvent) throws IOException {
        successLabel.setVisible(false);
        WindowManager.getInstance().openWindow("createNewEmployee.fxml");
        setItems(); // Refresh the table
        successLabel.setVisible((Boolean) WindowManager.getInstance().getData("success"));
    }

    public void automationToggle(ActionEvent actionEvent) {
        if (automation.isSelected()) {
            automation.setText("Deactivate Automation");
            Automation.on();
        } else {
            automation.setText("Activate Automation");
            Automation.off();
        }
    }

    public void openChangeEmployee(ActionEvent actionEvent) {
        successLabel.setVisible(false);
        if (employeeTable.getSelection() != null) {
            WindowManager.getInstance()
                    .storeData("employee", employeeTable.getSelection());
            WindowManager.getInstance().openWindow("EditEmployee.fxml");
            setItems();
            successLabel.setVisible((Boolean) WindowManager.getInstance().getData("success"));
        } else {
            noSelectionAlert.show();
        }
    }

    public void deleteEmployee(ActionEvent actionEvent) throws Exception {
        successLabel.setVisible(false);
        if (employeeTable.getSelection() != null) {
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {
                em.deleteEmployee(employeeTable.getSelection().getEmployeeID());
                setItems();
                successLabel.setVisible(true);
            }
        } else {
            noSelectionAlert.show();
        }
    }
}
