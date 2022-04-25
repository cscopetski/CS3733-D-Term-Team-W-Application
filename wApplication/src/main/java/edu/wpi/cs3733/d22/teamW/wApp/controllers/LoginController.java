package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.AccountManager;
import edu.wpi.cs3733.d22.teamW.Managers.BackgroundManager;
import edu.wpi.cs3733.d22.teamW.Managers.MenuBarManager;
import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import edu.wpi.cs3733.d22.teamW.wDB.CSVController;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class LoginController implements Initializable {

  @FXML public Button switchServer;
  @FXML Button loginButton;
  @FXML TextField username;
  @FXML TextField password;
  @FXML Label existCase;
  @FXML Label matchCase;
  @FXML Label illegalCharacter;

  @Override
  public void initialize(URL location, ResourceBundle rb) {
    BackgroundManager.getInstance().setContent(BackgroundManager.DefaultBackgrounds.HospitalImage);
    BackgroundManager.getInstance().blur();
    MenuBarManager.getInstance().DisableMenuBar();
    switchServer.setText("Embedded");
  }

  Alert emptyFields =
      new Alert(
          Alert.AlertType.ERROR,
          "There are required fields empty" + " !",
          ButtonType.OK,
          ButtonType.CANCEL);

  public void login() throws SQLException {
    if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
      if (TextEntryChecker.check(username.getText())
              && TextEntryChecker.check(password.getText())) {
        illegalCharacter.setVisible(false);
        existCase.setVisible(false);
        matchCase.setVisible(false);
        Employee employee = EmployeeManager.getEmployeeManager().login(username.getText(), password.getText());
        if (employee != null) {


          AccountManager.getInstance()
                  .initialize(employee);
          MenuBarManager.getInstance().EnableMenuBar();
          BackgroundManager.getInstance().unBlur();
          PageManager.getInstance().loadPage(PageManager.Pages.MainMenu);
          username.clear();
          password.clear();
        }



        else if (!EmployeeManager.getEmployeeManager().usernameExists(username.getText())) {
          existCase.setVisible(true);
        } else {
          matchCase.setVisible(true);
        }
      } else {
        matchCase.setVisible(false);
        illegalCharacter.setVisible(true);
      }
    } else {
      emptyFields.show();
    }
  }

  /*public void login() throws SQLException {
    if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
      if (TextEntryChecker.check(username.getText())
          && TextEntryChecker.check(password.getText())) {
        illegalCharacter.setVisible(false);
        existCase.setVisible(false);
        matchCase.setVisible(false);
        if (EmployeeManager.getEmployeeManager()
            .passwordMatch(username.getText(), password.getText())) {
          AccountManager.getInstance()
              .initialize(EmployeeManager.getEmployeeManager().getEmployee(username.getText()));
          MenuBarManager.getInstance().EnableMenuBar();
          BackgroundManager.getInstance().unBlur();
          PageManager.getInstance().loadPage(PageManager.Pages.MainMenu);
          username.clear();
          password.clear();
        } else if (!EmployeeManager.getEmployeeManager().usernameExists(username.getText())) {
          existCase.setVisible(true);
        } else {
          matchCase.setVisible(true);
        }
      } else {
        matchCase.setVisible(false);
        illegalCharacter.setVisible(true);
      }
    } else {
      emptyFields.show();
    }
  }*/

  public void onEnter(ActionEvent actionEvent) throws SQLException {
    login();
  }

  @FXML
  private void updateSwitchingServer() throws SQLException {
    switchServer.setText(switchServer.getText().equals("Client") ? "Embedded" : "Client");
    BackgroundManager.getInstance().getContent().requestFocus();

    CSVController csvController = new CSVController();

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
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
