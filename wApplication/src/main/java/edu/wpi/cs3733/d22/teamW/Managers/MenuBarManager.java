package edu.wpi.cs3733.d22.teamW.Managers;

import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;

public class MenuBarManager {

  // Singleton Pattern:
  private static class Instance {
    public static final MenuBarManager instance = new MenuBarManager();
  }

  private MenuBarManager() {}

  public static MenuBarManager getInstance() {
    return MenuBarManager.Instance.instance;
  }

  // MenuBarManager Class:
  private HBox menuBar;

  public void initialize(HBox menuBar) {
    this.menuBar = menuBar;
  }

  public void DisableMenuBar() {
    menuBar.setDisable(true);
    menuBar.setEffect(new GaussianBlur(7.5));
  }

  public void EnableMenuBar() {
    menuBar.setDisable(false);
    menuBar.setEffect(null);
  }

  public void setMenuBarVisible(boolean bool){
    menuBar.setVisible(bool);
  }
}
