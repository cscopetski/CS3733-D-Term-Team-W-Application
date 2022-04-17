package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeMessageManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.EmployeeMessage;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class MessagingPageController extends LoadableController {

  protected Employee currentEmployee;

  @FXML ComboBox employeeComboBox;
  @FXML Label messageTitleLabel;
  @FXML VBox messagesWindow;
  @FXML TextArea messageTextField;
  @FXML Button sendButton;
  @FXML Button sendButtonMe;

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Messaging;
  }

  @Override
  public void onLoad() throws SQLException {
    ArrayList<EmployeeMessage> messagesFromThemToMe =
        EmployeeMessageManager.getEmployeeMessageManager()
            .getMessagesFromTo(1, this.currentEmployee.getEmployeeID());
    ArrayList<EmployeeMessage> messagesFromMeToThem =
        EmployeeMessageManager.getEmployeeMessageManager()
            .getMessagesFromTo(this.currentEmployee.getEmployeeID(), 1);
    messagesFromThemToMe.addAll(messagesFromMeToThem);
    loadMessages(messagesFromThemToMe);
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
      VBox otherMessageVBox = new VBox(otherMessageLabel);
      otherMessageVBox.setPadding(new Insets(5, 0, 5, 0));
      otherMessageVBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      messagesWindow.getChildren().add(otherMessageVBox);
    } else { // Display on right
      Label myMessageLabel = new Label(text);
      myMessageLabel.setStyle(
          "-fx-background-color: #248bf5;"
              + "-fx-label-padding: 5;"
              + "-fx-background-radius: 50;");
      myMessageLabel.setTextFill(Paint.valueOf("#ffffff"));
      VBox myMessageVBox = new VBox(myMessageLabel);
      myMessageVBox.setPadding(new Insets(5, 0, 5, 0));
      myMessageVBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
      messagesWindow.getChildren().add(myMessageVBox);
    }
  }

  public void onSendButtonClick(ActionEvent actionEvent) {
    EmployeeMessage sentMessage =
        new EmployeeMessage(
            EmployeeMessageManager.getEmployeeMessageManager().getNextMsgID(),
            this.currentEmployee.getEmployeeID(),
            1,
            messageTextField.getText(),
            new Timestamp(System.currentTimeMillis()),
            0);
    try {
      EmployeeMessageManager.getEmployeeMessageManager().addEmployeeMessage(sentMessage);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      onLoad();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void onSendMeButtonClick(ActionEvent actionEvent) {
    EmployeeMessage sentMessage =
        new EmployeeMessage(
            EmployeeMessageManager.getEmployeeMessageManager().getNextMsgID(),
            1,
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
      onLoad();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void setEmployee(Employee employee) {
    this.currentEmployee = employee;
  }
}
