package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.AccountManager;
import edu.wpi.cs3733.d22.teamW.Managers.BackgroundManager;
import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeMessageManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class MainMenuController implements Initializable {

  @FXML Button buttonSR;
  @FXML Circle newMessagesCircle;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    newMessagesCircle.setVisible(false);
    try {
      if (EmployeeMessageManager.getEmployeeMessageManager()
              .countUnreadMessagesAs(AccountManager.getInstance().getEmployee().getEmployeeID())
          > 0) {
        newMessagesCircle.setVisible(true);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    BackgroundManager.getInstance().setContent(BackgroundManager.DefaultBackgrounds.HospitalImage);
    PageManager.getInstance()
        .attachPageChangeListener(
            (o, n) -> {
              if (o == PageManager.Pages.MainMenu) {
                BackgroundManager.getInstance()
                    .setContent(BackgroundManager.DefaultBackgrounds.White);
              }
            });
  }

  public void switchToRequestHub() {
    PageManager.getInstance().loadPage(PageManager.Pages.RequestHub);
  }

  public void switchToMapDisplay() {
    PageManager.getInstance().loadPage(PageManager.Pages.MapEditor);
  }

  public void switchToMessagesPage() {
    PageManager.getInstance().loadPage(PageManager.Pages.Messaging);
  }
}
