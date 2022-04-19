package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmployeeTable;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import javafx.fxml.FXML;

public class AdminHub {
  @FXML EmployeeTable employeeTable;
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
}
