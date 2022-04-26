package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class NoSelectionAlert extends Alert {
  public NoSelectionAlert() {
    super(
        AlertType.ERROR,
        "Nothing is Selected" + "!",
        ButtonType.OK,
        ButtonType.CANCEL);
  }
}
