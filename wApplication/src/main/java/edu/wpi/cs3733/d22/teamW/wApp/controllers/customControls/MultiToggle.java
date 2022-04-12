package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class MultiToggle extends AnchorPane {
  private final HBox container;
  private ToggleButton selected;

  public MultiToggle() {
    container = new HBox();
    this.getChildren().add(container);
    setBorderSpacing(0);
    container.setStyle("-fx-background-color: green");
    container.setAlignment(Pos.CENTER);
  }

  public void setOptions(ToggleButton[] options) {
    for (int i = 0; i < options.length; i++) {
      HBox.setHgrow(options[i], Priority.ALWAYS);
      int finalI = i;
      options[i]
              .selectedProperty()
              .addListener(
                      (observable, oldValue, newValue) -> {
                        if (newValue) {
                          if (selected == null) {
                            selected = options[finalI];
                          } else {
                            selected.setSelected(false);
                          }
                          selected = options[finalI];
                        } else {
                          if (!selected.isSelected()) {
                            selected = null;
                          }
                        }
                      });
    }
    container.getChildren().clear();
    container.getChildren().addAll(options);
  }
  public void setOptions(String[] options) {
    ToggleButton[] buttons = new ToggleButton[options.length];
    for (int i = 0; i < buttons.length; i++) {
      buttons[i] = new ToggleButton(options[i]);
      HBox.setHgrow(buttons[i], Priority.ALWAYS);
      int finalI = i;
      buttons[i]
          .selectedProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                if (newValue) {
                  if (selected == null) {
                    selected = buttons[finalI];
                  } else {
                    selected.setSelected(false);
                  }
                  selected = buttons[finalI];
                } else {
                  if (!selected.isSelected()) {
                    selected = null;
                  }
                }
              });
    }
    container.getChildren().clear();
    container.getChildren().addAll(buttons);
  }

  public void setSpacing(double spacing) {
    setInnerSpacing(spacing);
    setBorderSpacing(spacing);
  }

  public void setInnerSpacing(double spacing) {
    container.setSpacing(spacing);
  }

  public void setBorderSpacing(double spacing) {
    setTopAnchor(container, spacing);
    setBottomAnchor(container, spacing);
    setLeftAnchor(container, spacing);
    setRightAnchor(container, spacing);
  }
}
