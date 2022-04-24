package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class newGroupchatPopupPageController implements Initializable {

  @FXML AutoCompleteInput employeeComboBox;
  @FXML VBox selectedEmployeesVBOX;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      employeeComboBox.loadValues(
          (ArrayList<String>)
              EmployeeManager.getEmployeeManager().getAllEmployees().stream()
                  .map(e -> e.getFirstName() + " " + e.getLastName())
                  .collect(Collectors.toList()));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void employeeSelected() throws SQLException {
    // CREATE A DM BETWEEN TWO EMPLOYEES IF IT DOES NOT EXIST
    // Find the chosen employee
    Employee chosenEmployee = null;
    if (employeeComboBox.getSelectionModel().isEmpty()) return;
    for (Employee e : EmployeeManager.getEmployeeManager().getAllEmployees()) {
      if (e.getFirstName().equals(employeeComboBox.getValue().split(" ")[0])
          && e.getLastName().equals(employeeComboBox.getValue().split(" ")[1])) {
        chosenEmployee = e;
        break;
      }
    }
    selectedEmployeesVBOX
        .getChildren()
        .add(new Label(chosenEmployee.getFirstName() + " " + chosenEmployee.getLastName()));
  }

  public void clearSelectedEmployees() {
    selectedEmployeesVBOX.getChildren().clear();
  }
}
