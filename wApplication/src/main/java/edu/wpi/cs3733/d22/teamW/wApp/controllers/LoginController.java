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

  @FXML public Button switchServer;
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
  final String flowerRequestFileName = "FlowerRequests.csv";
  final String computerServiceRequestFileName = "ComputerServiceRequest.csv";
  final String sanitationRequestFileName = "SanitationRequests.csv";
  final String giftDeliveryRequestFileName = "GiftDeliveryRequest.csv";

  @Override
  public void initialize(URL location, ResourceBundle rb) {
    super.initialize(location, rb);
    switchServer.setText("Embedded");
  }

  Alert emptyFields =
      new Alert(
          Alert.AlertType.ERROR,
          "There are required fields empty" + " !",
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

  @FXML
  private void updateSwitchingServer() throws SQLException {
    switchServer.setText(switchServer.getText().equals("Client") ? "Embedded" : "Client");
    background.requestFocus();

    CSVController csvController =
        new CSVController(
            locationFileName,
            medEquipFileName,
            medEquipRequestFileName,
            labServiceRequestFileName,
            employeesFileName,
            medRequestFileName,
            flowerRequestFileName,
            computerServiceRequestFileName,
            sanitationRequestFileName,
            giftDeliveryRequestFileName);

    System.out.println("SWAP CONNECTION ");

    if (switchServer.getText().equals("Embedded")) {
      DBConnectionMode.INSTANCE.setEmbeddedConnection();
    } else if (switchServer.getText().equals("Client")) {
      DBConnectionMode.INSTANCE.setServerConnection();
    }

    DBController.getDBController().closeConnection();
    try {
      DBController.getDBController().startConnection();
    } catch (SQLException | ClassNotFoundException e) {
      if (!DBConnectionMode.INSTANCE.getConnectionType()) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Connection Failed", ButtonType.OK);
        alert.setContentText(
            "Connecting to the Client Server failed. Please contact your IT department to fix this issue.");
        alert.show();
        updateSwitchingServer();
        return;
      }
      e.printStackTrace();
    }

    try {
      csvController.populateTables();
    } catch (FileNotFoundException | SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
