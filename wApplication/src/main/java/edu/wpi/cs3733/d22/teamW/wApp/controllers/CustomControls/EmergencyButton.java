package edu.wpi.cs3733.d22.teamW.wApp.controllers.CustomControls;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class EmergencyButton extends Button {

  private boolean value = false;

  public EmergencyButton() {
    super();

    setText("Non-Emergency");
    getStylesheets().clear();
    getStylesheets()
        .add(
            "edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/EmergencyButton/emergencyButtonFalse.css");
    setOnAction(this::onAction);
  }

  public boolean getValue() {
    return value;
  }

  public void setValue(boolean value) {
    if (value != this.value) {
      this.value = value;
      getStylesheets().clear();
      if (value) {
        setText("Emergency");
        getStylesheets()
            .add(
                "edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/EmergencyButton/emergencyButtonTrue.css");
      } else {
        setText("Non-Emergency");
        getStylesheets()
            .add(
                "edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/EmergencyButton/emergencyButtonFalse.css");
      }
    }
  }

  protected void onAction(ActionEvent e) {
    setValue(!value);
  }
}
