package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfirmAlert extends Alert {
  public ConfirmAlert() {
    super(
        Alert.AlertType.CONFIRMATION,
        "Would you like to confirm this request" + "?",
        ButtonType.OK,
        ButtonType.CANCEL);
  }
}
