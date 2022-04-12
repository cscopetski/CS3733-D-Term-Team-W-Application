package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class EmptyAlert extends Alert {
  public EmptyAlert() {
    super(
        Alert.AlertType.ERROR,
        "There are required fields empty" + "!",
        ButtonType.OK,
        ButtonType.CANCEL);
  }
}
