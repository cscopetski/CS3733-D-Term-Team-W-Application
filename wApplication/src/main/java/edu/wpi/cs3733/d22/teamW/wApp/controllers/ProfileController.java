package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wMid.Account;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
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

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Profile;
  }

  @Override
  public void onLoad() {
    Employee employee = Account.getInstance().getEmployee();
    name.setText(employee.getFirstName() + " " + employee.getLastName());
    id.setText(employee.getEmployeeID().toString());
    type.setText(employee.getType().getString());
    email.setText(employee.getEmail());
    phoneNumber.setText(employee.getPhoneNumber());
    address.setText(employee.getAddress());

    rt.setColumnWidth("Request Type", 130);
    rt.setColumnWidth("Employee Name", 150);
    rt.setColumnWidth("Status", 60);
  }

  @Override
  public void onUnload() {}
}
