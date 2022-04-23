package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MiniProfileController implements Initializable {
  @FXML ImageView profile;
  @FXML Label name;
  @FXML Label id;
  @FXML Label type;
  @FXML Label email;
  @FXML Label phoneNumber;
  @FXML Label address;
  Employee employee;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    employee =
        (Employee)
            SceneManager.getInstance()
                .getInformation(SceneManager.getInstance().getPrimaryStage(), "employee");
    name.setText(employee.getFirstName() + " " + employee.getLastName());
    id.setText(employee.getEmployeeID().toString());
    type.setText(employee.getType().getString());
    email.setText(employee.getEmail());
    phoneNumber.setText(employee.getPhoneNumber());
    address.setText(employee.getAddress());
  }
}
