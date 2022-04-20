package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import static edu.wpi.cs3733.d22.teamW.wDB.enums.Automation.Automation;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmployeeTable;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class AdminHub {
  @FXML EmployeeTable employeeTable;
  @FXML ToggleButton automation;
  EmployeeManager em = EmployeeManager.getEmployeeManager();

  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.AdminHub;
  }

  public void initialize() {
    setItems();
  }

  private void setItems() {
    try {
      employeeTable.setItems(em.getAllEmployees());
    } catch (SQLException ex) {
      ex.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void openAddEmployee(ActionEvent actionEvent) throws IOException {
    Stage S = SceneManager.getInstance().openWindow("createNewEmployee.fxml");
    SceneManager.getInstance().eraseBlur();
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
}
