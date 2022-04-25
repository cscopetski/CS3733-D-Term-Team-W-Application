package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import static edu.wpi.cs3733.d22.teamW.wDB.enums.Automation.Automation;

import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmployeeTable;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class AdminHub {
  @FXML EmployeeTable employeeTable;
  @FXML ToggleButton automation;
  EmployeeManager em = EmployeeManager.getEmployeeManager();

  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();

  public void initialize() {
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
    WindowManager.getInstance().openWindow("createNewEmployee.fxml");
    setItems(); // Refresh the table
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

    setItems();
  }

  public void deleteEmployee(ActionEvent actionEvent) throws Exception {
    if (employeeTable.getSelection() != null) {
      confirm.showAndWait();
      if (confirm.getResult() == ButtonType.OK) {
        em.deleteEmployee(employeeTable.getSelection().getEmployeeID());
        setItems();
      }
    } else {
      emptyFields.show();
    }
  }
}
