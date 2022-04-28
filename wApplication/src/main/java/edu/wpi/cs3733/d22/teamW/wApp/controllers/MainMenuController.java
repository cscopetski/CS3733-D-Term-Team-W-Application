package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.UnreadMessageManager;
import edu.wpi.cs3733.d22.teamW.Managers.AccountManager;
import edu.wpi.cs3733.d22.teamW.Managers.BackgroundManager;
import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import org.apache.derby.iapi.store.raw.Page;

public class MainMenuController implements Initializable {

  @FXML Button buttonSR;
  @FXML Circle newMessagesCircle;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    PageManager.getInstance().attachOnLoad(PageManager.Pages.MainMenu, this::onLoad);
    PageManager.getInstance().attachOnUnload(PageManager.Pages.MainMenu, this::onUnload);
  }

  private void onLoad() {
    BackgroundManager.getInstance().setContent(BackgroundManager.DefaultBackgrounds.HospitalImage.getContent());

    newMessagesCircle.setVisible(false);
    try {
      if(AccountManager.getInstance().getEmployee() != null) {
        if (UnreadMessageManager.getUnreadMessageManager().getAllUnreadMessagesForEmployee(AccountManager.getInstance().getEmployee().getEmployeeID()).size() > 0) {
          newMessagesCircle.setVisible(true);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void onUnload() {
    BackgroundManager.getInstance().setContent(BackgroundManager.DefaultBackgrounds.Shapes.getContent());
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

  public void switchToDashboard(ActionEvent actionEvent) {
    PageManager.getInstance().loadPage(PageManager.Pages.Dashboard);

  }
}
