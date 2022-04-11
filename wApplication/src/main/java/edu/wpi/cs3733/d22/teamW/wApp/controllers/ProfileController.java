package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ProfileController extends LoadableController {
  public RequestTable rt;
  @FXML ImageView profile;
  @FXML Label name;
  @FXML Label id;
  @FXML Label type;
  @FXML Label email;
  @FXML Label phoneNumber;
  @FXML Label address;
  Employee employee;

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Profile;
  }

  @Override
  public void onLoad() {
    employee =
        ((DefaultPageController)
                SceneManager.getInstance().getController(SceneManager.Scenes.Default))
            .getEmployee();
    name.setText(employee.getFirstName() + " " + employee.getLastName());
    id.setText(employee.getEmployeeID().toString());
    type.setText(employee.getType());
    email.setText(employee.getEmail());
    phoneNumber.setText(employee.getPhoneNumber());
    address.setText(employee.getAddress());
    System.out.println(employee.getFirstName());
    System.out.println("name");
  }

  @Override
  public void onUnload() {}

  public void load(ActionEvent actionEvent) {
    employee =
        ((DefaultPageController)
                SceneManager.getInstance().getController(SceneManager.Scenes.Default))
            .getEmployee();
    name.setText(employee.getFirstName() + " " + employee.getLastName());
    id.setText(employee.getEmployeeID().toString());
    type.setText(employee.getType());
    email.setText(employee.getEmail());
    phoneNumber.setText(employee.getPhoneNumber());
    address.setText(employee.getAddress());
    System.out.println(employee.getFirstName());
    System.out.println("name");
  }
}
