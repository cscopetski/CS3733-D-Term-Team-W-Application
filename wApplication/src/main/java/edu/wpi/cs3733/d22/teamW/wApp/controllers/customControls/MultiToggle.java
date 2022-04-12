package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import javafx.scene.control.Toggle;

public class MultiToggle {
  private Toggle[] items;
  private int selected = -1;

  public MultiToggle(Toggle... options) {
    items = options;
    for (int i = 0; i < options.length; i++) {
      int finalI = i;
      options[i]
          .selectedProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                if (selected != -1) {
                  items[selected].setSelected(false);
                  selected = finalI;
                }
                if (!newValue) {
                  selected = -1;
                }
              });
    }
  }
}
