package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.ButtonEmpID;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wMid.Account;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class newGroupchatPopupPageController implements Initializable {

  @FXML AutoCompleteInput employeeComboBox;
  @FXML VBox selectedEmployeesVBOX;
  @FXML Label notificationLabel;

  private static TreeSet<Integer> selectedEmployeeIDs = new TreeSet<>();
  private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    selectedEmployeeIDs.clear();
    selectedEmployeesVBOX.getChildren().clear();
    notificationLabel.setVisible(false);
    fadeOut.setNode(notificationLabel);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);
    fadeOut.setCycleCount(1);
    fadeOut.setAutoReverse(false);
    try {
      employeeComboBox.loadValues(
          (ArrayList<String>)
              EmployeeManager.getEmployeeManager().getAllEmployees().stream()
                  .map(e -> e.getFirstName() + " " + e.getLastName())
                  .collect(Collectors.toList()));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void employeeSelected() throws SQLException {
    Employee chosenEmployee = null;
    if (employeeComboBox.getSelectionModel().isEmpty()) return;
    for (Employee e : EmployeeManager.getEmployeeManager().getAllEmployees()) {
      if (e.getFirstName().equals(employeeComboBox.getValue().split(" ")[0])
          && e.getLastName().equals(employeeComboBox.getValue().split(" ")[1])) {
        chosenEmployee = e;
        break;
      }
    }
    if (chosenEmployee == null
        || chosenEmployee
            .getEmployeeID()
            .equals(Account.getInstance().getEmployee().getEmployeeID())) return;

    addNewSelectionCard(chosenEmployee);
  }

  private void addNewSelectionCard(Employee chosenEmployee) {
    if (selectedEmployeeIDs.add(chosenEmployee.getEmployeeID())) {
      refreshSelectionCards();
    }
  }

  private void addSelectionCardToList(Employee chosenEmployee) {
    // Create the card
    HBox selectedCard = new HBox();
    selectedCard.setAlignment(Pos.CENTER_LEFT);
    selectedCard.setPrefWidth(275);

    // Create label section of card
    HBox labelContainer = new HBox();
    Label employeeNameLabel =
        new Label(chosenEmployee.getFirstName() + " " + chosenEmployee.getLastName());
    employeeNameLabel.setFont(new Font(16));
    employeeNameLabel.setWrapText(true);
    employeeNameLabel.setAlignment(Pos.CENTER_LEFT);
    labelContainer.getChildren().add(employeeNameLabel);

    // Create button section of card
    HBox removeButtonContainer = new HBox();
    removeButtonContainer.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    removeButtonContainer.setAlignment(Pos.CENTER_RIGHT);
    ButtonEmpID removeButton = new ButtonEmpID("Remove");
    removeButton.setEmpID(chosenEmployee.getEmployeeID());
    removeButton.setMinHeight(-1.0 / 0.0); // set min to prefered
    removeButton.setMinWidth(-1.0 / 0.0);
    removeButton.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            removeSelectionCard(((ButtonEmpID) event.getSource()).getEmpID());
          }
        });
    removeButtonContainer.getChildren().add(removeButton);

    selectedCard.getChildren().add(labelContainer);
    selectedCard.getChildren().add(removeButtonContainer);

    selectedEmployeesVBOX.getChildren().add(selectedCard);
    selectedEmployeesVBOX.getChildren().add(new Separator(Orientation.HORIZONTAL));
  }

  public void removeSelectionCard(Integer empID) {
    selectedEmployeeIDs.remove(empID);
    refreshSelectionCards();
  }

  private void refreshSelectionCards() {
    selectedEmployeesVBOX.getChildren().clear();
    for (Integer selectedEmpID : selectedEmployeeIDs) {
      try {
        addSelectionCardToList(EmployeeManager.getEmployeeManager().getEmployee(selectedEmpID));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void clearSelectedEmployees() {
    selectedEmployeeIDs.clear();
    selectedEmployeesVBOX.getChildren().clear();
    notificationLabel.setVisible(true);
    notificationLabel.setTextFill(Paint.valueOf("#00ff00"));
    notificationLabel.setText("Successfully cleared selected employees.");
    fadeOut.playFromStart();
  }

  public static TreeSet<Integer> getSelectedEmployeeIDs() {
    return selectedEmployeeIDs;
  }

  public static void clearSelectedEmployeeIDs() {
    selectedEmployeeIDs.clear();
  }

  public void submitButtonClicked(ActionEvent actionEvent) {
    if (selectedEmployeeIDs.size() < 2) {
      // Need at least 2 employees to form a group chat
      notificationLabel.setVisible(true);
      notificationLabel.setTextFill(Paint.valueOf("#ff3232"));
      notificationLabel.setText("At least 2 other employees are required to create a group chat.");
      fadeOut.playFromStart();
    } else {
      ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }
  }

  public void cancelButtonClicked(ActionEvent actionEvent) {
    selectedEmployeeIDs.clear();
    ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
  }
}
