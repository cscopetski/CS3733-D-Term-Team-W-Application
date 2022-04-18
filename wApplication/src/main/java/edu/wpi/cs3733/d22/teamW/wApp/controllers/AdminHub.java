package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import javafx.fxml.FXML;

public class AdminHub {
  @FXML RequestTable employeeTable;
  EmployeeManager em = EmployeeManager.getEmployeeManager();

  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.AdminHub;
  }

  public void initialize() {
    employeeTable
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              try {
                em.getAllEmployees();
              } catch (SQLException e) {
                e.printStackTrace();
              }
            });
  }
}
