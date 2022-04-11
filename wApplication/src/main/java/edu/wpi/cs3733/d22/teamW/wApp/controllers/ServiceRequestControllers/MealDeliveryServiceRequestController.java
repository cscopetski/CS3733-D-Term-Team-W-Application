package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MealDeliveryServiceRequestController {
  @FXML Button menuButton1;
  @FXML Button menuButton2;
  @FXML Button menuButton3;
  @FXML Label menuLabel1;
  @FXML Label menuLabel2;
  @FXML Label menuLabel3;
  @FXML TextField roomNum;
  @FXML TextField bedNum;
  @FXML ComboBox<String> timeSelection;
  Alert confirm =
      new Alert(
          Alert.AlertType.CONFIRMATION,
          "Would you like to confirm this request " + " ?",
          ButtonType.OK,
          ButtonType.CANCEL);
  Alert emptyFields =
      new Alert(
          Alert.AlertType.ERROR,
          "There are required fields empty " + " !",
          ButtonType.OK,
          ButtonType.CANCEL);

  private int currentSelection = 0;

  public void menuSelection1() {
    currentSelection = 1;
    menuButton1.setStyle("-fx-background-color: #013895;");
    menuButton2.setStyle("-fx-background-color: transparent;");
    menuButton3.setStyle("-fx-background-color: transparent;");
    menuLabel1.setStyle("-fx-text-fill: white");
    menuLabel2.setStyle("-fx-text-fill: black");
    menuLabel3.setStyle("-fx-text-fill: black");
  }

  public void menuSelection2() {
    currentSelection = 2;
    menuButton1.setStyle("-fx-background-color: transparent");
    menuButton2.setStyle("-fx-background-color: #013895;");
    menuButton3.setStyle("-fx-background-color: transparent;");
    menuLabel1.setStyle("-fx-text-fill: black");
    menuLabel2.setStyle("-fx-text-fill: white");
    menuLabel3.setStyle("-fx-text-fill: black");
  }

  public void menuSelection3() {
    currentSelection = 3;
    menuButton1.setStyle("-fx-background-color: transparent");
    menuButton2.setStyle("-fx-background-color: transparent;");
    menuButton3.setStyle("-fx-background-color: #013895;");
    menuLabel1.setStyle("-fx-text-fill: black");
    menuLabel2.setStyle("-fx-text-fill: black");
    menuLabel3.setStyle("-fx-text-fill: white");
  }

  public void submitButton(ActionEvent actionEvent) {
    if (currentSelection != 0
        && !roomNum.getText().isEmpty()
        && !bedNum.getText().isEmpty()
        && !timeSelection.getValue().isEmpty()) {
      confirm.show();
      if (confirm.getResult() == ButtonType.OK) {}
    } else {
      emptyFields.show();
    }
  }

  public void onEnter(ActionEvent actionEvent) {
    submitButton(actionEvent);
  }
}
