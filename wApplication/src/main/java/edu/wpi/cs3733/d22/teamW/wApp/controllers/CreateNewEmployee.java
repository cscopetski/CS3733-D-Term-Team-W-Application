package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateNewEmployee {
  @FXML public TextField firstNametxt;
  public TextField lastNametxt;
  public TextField addresstxt;
  public TextField emailtxt;
  public Button addButton;
  public AutoCompleteInput typebox;

  public void add(ActionEvent actionEvent) {}

  private boolean checkEmpty() {
    if (firstNametxt.getText().isEmpty()
        || lastNametxt.getText().isEmpty()
        || addresstxt.getText().isEmpty()
        || emailtxt.getText().isEmpty()
        || typebox.getSelectionModel().isEmpty()) {
      return true;
    }
    return false;
  }

  public void cancelAdd(ActionEvent actionEvent) {
    ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
  }
}
