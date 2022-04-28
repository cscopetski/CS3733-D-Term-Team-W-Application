package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class EditEmployee {
  @FXML public TextField firstNametxt;
  public TextField lastNametxt;
  public TextField addresstxt;
  public TextField emailtxt;
  public Button editButton;
  public ComboBox typebox;
  public TextField phonetxt;
  EmployeeManager em = EmployeeManager.getEmployeeManager();
  Employee employee = null;

  public void initialize() {
    ArrayList<String> eTypes = new ArrayList<>();
    int counter = 0;
    employee = (Employee) WindowManager.getInstance().getData("employee");
    /*
    for (int i = 0; i < EmployeeType.values().length; i++) {
      eTypes.add(EmployeeType.values()[i]);
      if (EmployeeType.values()[i] == employee.getType()){
        counter = i;
      }
    }

     */
    for (EmployeeType eT : EmployeeType.values()){
      if (!(eT.equals(employee.getType()) || eT.equals(EmployeeType.NoOne))){
        eTypes.add(eT.getString());
      }
    }
    typebox.setItems(FXCollections.observableArrayList(eTypes));


    firstNametxt.setText(employee.getFirstName());
    lastNametxt.setText(employee.getLastName());
    addresstxt.setText(employee.getAddress());
    emailtxt.setText(employee.getEmail());
    typebox.setValue(employee.getType().getString());
    phonetxt.setText(employee.getPhoneNumber());
  }

  public void edit(ActionEvent actionEvent) throws SQLException {
    if (checkEmpty()) {
      Alert warningAlert =
              new Alert(Alert.AlertType.WARNING, "Please fill in all the fields", ButtonType.OK);
      warningAlert.showAndWait();
    } else {
      System.out.println(EmployeeType.getEmployeeType(typebox.getSelectionModel().getSelectedItem().toString()).getString());
      Employee newEmployee =
              new Employee(
                      employee.getEmployeeID(),
                      firstNametxt.getText(),
                      lastNametxt.getText(),
                      EmployeeType.getEmployeeType(typebox.getSelectionModel().getSelectedItem().toString()).getString(),
                      emailtxt.getText(),
                      phonetxt.getText(),
                      addresstxt.getText(),
                      employee.getUsername(),
                      employee.getPassword());
      em.changeEmployee(newEmployee);
      ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
      WindowManager.getInstance().getPrimaryStage().getScene().getRoot().setEffect(null);
      WindowManager.getInstance().storeData("success", true);
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
    WindowManager.getInstance().getPrimaryStage().getScene().getRoot().setEffect(null);
  }

}
