package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class MealDeliveryServiceRequestController {
  @FXML VBox menuVBox1;
  @FXML VBox menuVBox2;
  @FXML VBox menuVBox3;
  @FXML CheckBox option1;
  @FXML CheckBox option2;
  @FXML CheckBox option3;

  public void menuSelection1(ActionEvent actionEvent) {
    option2.setSelected(false);
    option3.setSelected(false);
  }

  public void menuSelection2(ActionEvent actionEvent) {
    option1.setSelected(false);
    option3.setSelected(false);
  }

  public void menuSelection3(ActionEvent actionEvent) {
    option1.setSelected(false);
    option2.setSelected(false);
  }

  public void submitButton(ActionEvent actionEvent) {}
}
