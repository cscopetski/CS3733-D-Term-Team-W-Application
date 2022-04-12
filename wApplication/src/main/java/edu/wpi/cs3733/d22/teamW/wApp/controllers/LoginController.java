package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.CSVController;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.layout.Pane;

public class LoginController extends LoadableController {

  public ComboBox switchServer;
  @FXML Pane background;
  @FXML Button loginButton;
  @FXML TextField username;
  @FXML TextField password;
  ArrayList<String> passwordHidden;
  ArrayList<String> passwordShown;
  @FXML Label existCase;
  @FXML Label matchCase;

  EmployeeManager eM = EmployeeManager.getEmployeeManager();

  ObservableList<String> servers = FXCollections.observableArrayList("Embedded", "Client");

  final String locationFileName = "TowerLocations.csv";
  final String medEquipFileName = "MedicalEquipment.csv";
  final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
  final String labServiceRequestFileName = "LabRequests.csv";
  final String employeesFileName = "Employees.csv";
  final String medRequestFileName = "MedRequests.csv";

  @Override
  public void initialize(URL location, ResourceBundle rb) {
    super.initialize(location, rb);
    switchServer.getItems().addAll(servers);
    switchServer.setValue("Embedded"); // No null server at first
    try {
      updateSwitchingServer();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  Alert emptyFields =
      new Alert(
          Alert.AlertType.ERROR,
          "There are required fields empty " + " !",
          ButtonType.OK,
          ButtonType.CANCEL);
  @FXML ComboBox<String> equipmentSelection;

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Login;
  }

  @Override
  public void onLoad() {}

  @Override
  public void onUnload() {}

  public void login() throws SQLException {
    if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
      existCase.setVisible(false);
      matchCase.setVisible(false);
      if (eM.passwordMatch(username.getText(), password.getText())) {
        ((DefaultPageController)
                SceneManager.getInstance().getController(SceneManager.Scenes.Default))
            .setEmployee(eM.getEmployee(username.getText()));
        ((DefaultPageController)
                SceneManager.getInstance().getController(SceneManager.Scenes.Default))
            .menuBar.setVisible(true);
        ((DefaultPageController)
                SceneManager.getInstance().getController(SceneManager.Scenes.Default))
            .buttonPane.setDisable(false);
        SceneManager.getInstance().transitionTo(SceneManager.Scenes.MainMenu);
        username.clear();
        password.clear();
      } else if (!eM.usernameExists(username.getText())) {
        existCase.setVisible(true);
      } else {
        matchCase.setVisible(true);
      }
    } else {
      emptyFields.show();
    }
  }

  public void onEnter(ActionEvent actionEvent) throws SQLException {
    login();
  }

  private void updateSwitchingServer() throws SQLException {

    CSVController csvController =
        new CSVController(
            locationFileName,
            medEquipFileName,
            medEquipRequestFileName,
            labServiceRequestFileName,
            employeesFileName,
            medRequestFileName);

    System.out.println("CLIENT CONNECTION ");

    if (switchServer.getValue().equals("Embedded")) {
      DBConnectionMode.INSTANCE.setEmbeddedConnection();
    } else if (switchServer.getValue().equals("Client")) {
      DBConnectionMode.INSTANCE.setServerConnection();
    }

    DBController.getDBController().closeConnection();
    try {
      DBController.getDBController().startConnection();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }

    try {
      csvController.populateTables();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
