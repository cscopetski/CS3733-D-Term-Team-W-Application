package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeMessageManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.EmployeeMessage;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class MessagingPageController extends LoadableController {

  protected Employee currentEmployee;
  protected Employee selectedEmployee;

  @FXML ComboBox employeeComboBox;
  @FXML Label messageTitleLabel;
  @FXML VBox messagesWindow;
  @FXML VBox messageWindow;
  @FXML TextArea messageTextField;
  @FXML Button sendButton;
  @FXML Button sendButtonMe;

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Messaging;
  }

  @Override
  public void onLoad() throws SQLException {
    employeeComboBox.setItems(FXCollections.observableArrayList(getEmployeeIDs()));
  }

  @Override
  public void onUnload() {}

  private void clearMessages() {
    messagesWindow.getChildren().clear();
  }

  private void loadMessages(ArrayList<EmployeeMessage> currentMessages) {
    clearMessages();
    Collections.sort(
        currentMessages,
        new Comparator<EmployeeMessage>() {
          @Override
          public int compare(EmployeeMessage o1, EmployeeMessage o2) {
            return o1.getSentTimestamp().compareTo(o2.getSentTimestamp());
          }
        });
    for (EmployeeMessage message : currentMessages) {
      if (message.getEmpIDto().equals(currentEmployee.getEmployeeID())) {
        addMessageToList(message.getMessageContent(), true);
      } else {
        addMessageToList(message.getMessageContent(), false);
      }
    }
  }

  public void addMessageToList(String text, boolean fromOther) {
    if (fromOther) { // Display on left
      Label otherMessageLabel = new Label(text);
      otherMessageLabel.setStyle(
          "-fx-background-color: #e5e5ea;"
              + "-fx-label-padding: 5;"
              + "-fx-background-radius: 50;");
      otherMessageLabel.setTextFill(Paint.valueOf("#000000"));
      otherMessageLabel.setMinHeight(-1.0 / 0.0);
      otherMessageLabel.setMaxHeight(-1.0 / 0.0);
      otherMessageLabel.setWrapText(true);

      VBox otherMessageVBox = new VBox(otherMessageLabel);
      otherMessageVBox.setFillWidth(true);
      otherMessageVBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      otherMessageVBox.setMinHeight(-1.0 / 0.0);
      otherMessageVBox.setPadding(new Insets(0, 0, 10, 0));
      messagesWindow.getChildren().add(otherMessageVBox);
    } else { // Display on right
      Label myMessageLabel = new Label(text);
      myMessageLabel.setStyle(
          "-fx-background-color: #248bf5;"
              + "-fx-label-padding: 5;"
              + "-fx-background-radius: 50;");
      myMessageLabel.setTextFill(Paint.valueOf("#ffffff"));
      myMessageLabel.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      myMessageLabel.setMinHeight(-1.0 / 0.0);
      myMessageLabel.setMaxHeight(-1.0 / 0.0);
      myMessageLabel.setWrapText(true);

      VBox myMessageVBox = new VBox(myMessageLabel);

      myMessageVBox.setFillWidth(true);
      myMessageVBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      myMessageVBox.setMinHeight(-1.0 / 0.0);
      myMessageVBox.setPadding(new Insets(0, 0, 10, 0));
      messagesWindow.getChildren().add(myMessageVBox);
    }
  }

  public void onSendButtonClick() {
    EmployeeMessage sentMessage =
        new EmployeeMessage(
            EmployeeMessageManager.getEmployeeMessageManager().getNextMsgID(),
            this.currentEmployee.getEmployeeID(),
            this.selectedEmployee.getEmployeeID(),
            messageTextField.getText(),
            new Timestamp(System.currentTimeMillis()),
            0);
    try {
      EmployeeMessageManager.getEmployeeMessageManager().addEmployeeMessage(sentMessage);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      refreshMessages();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void onSendMeButtonClick() {
    EmployeeMessage sentMessage =
        new EmployeeMessage(
            EmployeeMessageManager.getEmployeeMessageManager().getNextMsgID(),
            this.selectedEmployee.getEmployeeID(),
            this.currentEmployee.getEmployeeID(),
            messageTextField.getText(),
            new Timestamp(System.currentTimeMillis()),
            0);
    try {
      EmployeeMessageManager.getEmployeeMessageManager().addEmployeeMessage(sentMessage);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      refreshMessages();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void setEmployee(Employee employee) {
    this.currentEmployee = employee;
  }

  public void refreshMessages() throws SQLException {
    ArrayList<EmployeeMessage> messagesFromThemToMe =
        EmployeeMessageManager.getEmployeeMessageManager()
            .getMessagesFromTo(
                this.selectedEmployee.getEmployeeID(), this.currentEmployee.getEmployeeID());
    ArrayList<EmployeeMessage> messagesFromMeToThem =
        EmployeeMessageManager.getEmployeeMessageManager()
            .getMessagesFromTo(
                this.currentEmployee.getEmployeeID(), this.selectedEmployee.getEmployeeID());
    messagesFromThemToMe.addAll(messagesFromMeToThem);
    loadMessages(messagesFromThemToMe);
  }

  public void employeeSelected(ActionEvent actionEvent) throws SQLException {
    this.selectedEmployee =
        EmployeeManager.getEmployeeManager()
            .getEmployee(
                Integer.parseInt(
                    employeeComboBox.getSelectionModel().getSelectedItem().toString()));
    messageWindow.setDisable(false);
    messageTitleLabel.setText(
        this.selectedEmployee.getFirstName() + " " + this.selectedEmployee.getLastName());
    refreshMessages();
  }

  private ArrayList<Integer> getEmployeeIDs() {
    ArrayList<Integer> ids = new ArrayList<>();
    ArrayList<Employee> employees = null;
    try {
      employees = EmployeeManager.getEmployeeManager().getAllEmployees();
    } catch (SQLException e) {
      System.out.println("Failed to unearth employees from database");
      e.printStackTrace();
    }
    for (Employee e : employees) {
      if (e.getEmployeeID() != -1
          && !e.getEmployeeID().equals(this.currentEmployee.getEmployeeID()))
        ids.add(e.getEmployeeID());
    }
    return ids;
  }

  public void messageTextKeyPress(KeyEvent keyEvent) {
    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
      onSendButtonClick();
      messageTextField.clear();
    }
  }
}
