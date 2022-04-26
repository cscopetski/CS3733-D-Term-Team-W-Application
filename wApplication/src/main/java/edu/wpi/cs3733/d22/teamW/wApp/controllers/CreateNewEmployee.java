package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateNewEmployee {
  @FXML public TextField firstNametxt;
  public TextField lastNametxt;
  public TextField addresstxt;
  public TextField emailtxt;
  public Button addButton;
  public ComboBox typebox;
  public TextField phonetxt;
  EmployeeManager em = EmployeeManager.getEmployeeManager();

  public void initialize() {
    ArrayList<EmployeeType> eTypes = new ArrayList<>();
    for (EmployeeType e : EmployeeType.values()) {
      eTypes.add(e);
    }
    typebox.setItems(FXCollections.observableArrayList(eTypes));
  }

  public void add(ActionEvent actionEvent) throws Exception {
    Random rand = new Random();
    int newID = rand.nextInt(100);
    if (checkEmpty()) {
      Alert warningAlert =
          new Alert(Alert.AlertType.WARNING, "Please fill in all the fields", ButtonType.OK);
      warningAlert.showAndWait();
    } else {
      Employee employee =
          new Employee(
              newID,
              firstNametxt.getText(),
              lastNametxt.getText(),
              typebox.getSelectionModel().getSelectedItem().toString(),
              emailtxt.getText(),
              phonetxt.getText(),
              addresstxt.getText(),
              genUsername(),
              firstNametxt.getText());
      em.addEmployee(employee);
    }
  }

  private boolean checkEmpty() {
    if (firstNametxt.getText().isEmpty()
        || lastNametxt.getText().isEmpty()
        || addresstxt.getText().isEmpty()
        || emailtxt.getText().isEmpty()
        || typebox.getSelectionModel().isEmpty()) {
      return true;
    }
    return false;
  }

  public void cancelAdd(ActionEvent actionEvent) {
    ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
  }

  private String genUsername() {
    Random rand = new Random();
    return firstNametxt.getText() + rand.nextInt(100);
  }
}
