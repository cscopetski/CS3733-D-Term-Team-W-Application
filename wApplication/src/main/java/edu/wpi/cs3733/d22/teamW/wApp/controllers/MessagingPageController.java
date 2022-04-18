package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.MessageCardHBox;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeMessageManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.EmployeeMessage;
import edu.wpi.cs3733.d22.teamW.wMid.Account;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class MessagingPageController extends LoadableController {

  protected Employee currentEmployee;
  protected Employee selectedEmployee;

  @FXML ComboBox employeeComboBox;
  @FXML Label messageTitleLabel;
  @FXML VBox messagesWindow;
  @FXML VBox messageWindow;
  @FXML VBox employeeCardView;
  @FXML TextArea messageTextField;
  @FXML Button sendButton;
  @FXML Button sendButtonMe;

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Messaging;
  }

  @Override
  public void onLoad() throws SQLException {
    this.currentEmployee = Account.getInstance().getEmployee();
    employeeComboBox.setItems(FXCollections.observableArrayList(getEmployeeIDs()));
    resetMessagePage();
  }

  public void refreshEmployeeCard() throws SQLException {
    clearEmployeeCards();
    ArrayList<EmployeeMessage> allMessages =
        EmployeeMessageManager.getEmployeeMessageManager().getAllMessages();
    Collections.sort(
        allMessages,
        new Comparator<EmployeeMessage>() {
          @Override
          public int compare(EmployeeMessage o1, EmployeeMessage o2) {
            return -1 * o1.getSentTimestamp().compareTo(o2.getSentTimestamp());
          }
        });
    TreeSet<Integer> uniqueID = new TreeSet<>();
    for (EmployeeMessage message : allMessages) {
      if (message.getEmpIDto().equals(this.currentEmployee.getEmployeeID())) {
        if (uniqueID.add(message.getEmpIDfrom())) {
          if (message.getIsRead() == 0) {
            addEmployeeCard(message.getEmpIDfrom(), true);
          } else {
            addEmployeeCard(message.getEmpIDfrom(), false);
          }
        }
      }
      if (message.getEmpIDfrom().equals(this.currentEmployee.getEmployeeID())) {
        if (uniqueID.add(message.getEmpIDto())) {
          addEmployeeCard(message.getEmpIDto(), false);
        }
      }
    }
  }

  public void clearEmployeeCards() {
    employeeCardView.getChildren().clear();
  }

  public void addEmployeeCard(Integer empID, boolean hasUnread) throws SQLException {
    Employee emp = EmployeeManager.getEmployeeManager().getEmployee(empID);
    ImageView placeHolderImage = new ImageView();
    placeHolderImage.setImage(
        new Image(
            MessagingPageController.class
                .getClassLoader()
                .getResource("edu/wpi/cs3733/d22/teamW/wApp/assets/Icons/profilePicture.png")
                .toString()));
    placeHolderImage.setFitWidth(80);
    placeHolderImage.setFitHeight(80);
    Label employeeNameLabel =
        new Label(String.format("%s %s", emp.getFirstName(), emp.getLastName()));
    if (hasUnread) {
      employeeNameLabel =
          new Label(
              String.format(
                  "(%d) %s %s",
                  EmployeeMessageManager.getEmployeeMessageManager()
                      .countUnreadMessagesAsFrom(this.currentEmployee.getEmployeeID(), empID),
                  emp.getFirstName(),
                  emp.getLastName()));
    }
    MessageCardHBox newHBOX =
        new MessageCardHBox(
            placeHolderImage, new Separator(Orientation.VERTICAL), employeeNameLabel);
    newHBOX.setEmpID(emp.getEmployeeID());
    newHBOX.setHasUrgent(hasUnread);
    newHBOX.setPrefHeight(100);
    newHBOX.setMinHeight(-1.0 / 0.0);
    newHBOX.setMinWidth(-1.0 / 0.0);
    newHBOX.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            try {
              clickEmployeeCard(event);
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        });
    newHBOX.setOnMouseEntered(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            mouseOverCard(event);
          }
        });
    newHBOX.setOnMouseExited(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            mouseExitCard(event);
          }
        });
    if (hasUnread) {
      newHBOX.setStyle("-fx-background-color: #ffcccc;");
    }
    newHBOX.setAlignment(Pos.CENTER_LEFT);
    VBox cardVBox = new VBox(newHBOX, new Separator());
    employeeCardView.getChildren().add(cardVBox);
  }

  public void clickEmployeeCard(MouseEvent event) throws SQLException {
    Integer selectedID = ((MessageCardHBox) event.getSource()).getEmpID();
    this.selectedEmployee = EmployeeManager.getEmployeeManager().getEmployee(selectedID);
    if (!this.employeeComboBox.getSelectionModel().isEmpty()) {
      this.employeeComboBox.getSelectionModel().clearSelection();
    }
    updateMessageWindow();
    refreshEmployeeCard();
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
      if (message.getEmpIDto().equals(this.currentEmployee.getEmployeeID())) {
        addMessageToList(message, true);
      } else {
        addMessageToList(message, false);
      }
    }
  }

  public void addMessageToList(EmployeeMessage message, boolean fromOther) {
    Tooltip timestampTooltip = new Tooltip(message.getSentTimestamp().toString());
    timestampTooltip.setShowDelay(Duration.ZERO);
    timestampTooltip.setHideDelay(Duration.ZERO);

    if (fromOther) { // Display on left
      Label otherMessageLabel = new Label(message.getMessageContent());
      otherMessageLabel.setStyle(
          "-fx-background-color: #e5e5ea;"
              + "-fx-label-padding: 5;"
              + "-fx-background-radius: 50;");
      otherMessageLabel.setTextFill(Paint.valueOf("#000000"));
      otherMessageLabel.setMinHeight(-1.0 / 0.0);
      otherMessageLabel.setMaxHeight(-1.0 / 0.0);
      otherMessageLabel.setWrapText(true);
      otherMessageLabel.setTooltip(timestampTooltip);

      VBox otherMessageVBox = new VBox(otherMessageLabel);
      otherMessageVBox.setFillWidth(true);
      otherMessageVBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      otherMessageVBox.setMinHeight(-1.0 / 0.0);
      otherMessageVBox.setPadding(new Insets(0, 0, 10, 0));
      messagesWindow.getChildren().add(otherMessageVBox);
    } else { // Display on right
      Label myMessageLabel = new Label(message.getMessageContent());
      myMessageLabel.setStyle(
          "-fx-background-color: #248bf5;"
              + "-fx-label-padding: 5;"
              + "-fx-background-radius: 50;");
      myMessageLabel.setTextFill(Paint.valueOf("#ffffff"));
      myMessageLabel.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      myMessageLabel.setMinHeight(-1.0 / 0.0);
      myMessageLabel.setMaxHeight(-1.0 / 0.0);
      myMessageLabel.setWrapText(true);
      myMessageLabel.setTooltip(timestampTooltip);

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

  public void resetMessagePage() throws SQLException {
    clearMessages();
    refreshEmployeeCard();
    messageTitleLabel.setText("Select an Employee");
    messageTextField.clear();
    if (!this.employeeComboBox.getSelectionModel().isEmpty()) {
      this.employeeComboBox.getSelectionModel().clearSelection();
    }
    messageWindow.setDisable(true);
  }

  public void refreshMessages() throws SQLException {
    refreshEmployeeCard();
    ArrayList<EmployeeMessage> messagesFromThemToMe =
        EmployeeMessageManager.getEmployeeMessageManager()
            .getMessagesFromTo(
                this.selectedEmployee.getEmployeeID(), this.currentEmployee.getEmployeeID());
    ArrayList<EmployeeMessage> messagesFromMeToThem =
        EmployeeMessageManager.getEmployeeMessageManager()
            .getMessagesFromTo(
                this.currentEmployee.getEmployeeID(), this.selectedEmployee.getEmployeeID());
    markMessagesRead(messagesFromThemToMe);
    messagesFromThemToMe.addAll(messagesFromMeToThem);
    loadMessages(messagesFromThemToMe);
  }

  public void markMessagesRead(ArrayList<EmployeeMessage> messages) throws SQLException {
    for (EmployeeMessage message : messages) {
      message.setIsRead(1);
      EmployeeMessageManager.getEmployeeMessageManager().changeEmployeeMessage(message);
    }
  }

  public void employeeSelected(ActionEvent actionEvent) throws SQLException {
    if (employeeComboBox.getSelectionModel().isEmpty()) return;
    this.selectedEmployee =
        EmployeeManager.getEmployeeManager()
            .getEmployee(
                Integer.parseInt(
                    employeeComboBox.getSelectionModel().getSelectedItem().toString()));
    updateMessageWindow();
    refreshEmployeeCard();
  }

  public void updateMessageWindow() throws SQLException {
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

  public void mouseOverCard(MouseEvent mouseEvent) {
    if (((MessageCardHBox) mouseEvent.getSource()).isHasUrgent()) {
      ((MessageCardHBox) mouseEvent.getSource()).setStyle("-fx-background-color: #ffb2b2;");
    } else {
      ((MessageCardHBox) mouseEvent.getSource()).setStyle("-fx-background-color: AliceBlue;");
    }
  }

  public void mouseExitCard(MouseEvent mouseEvent) {
    if (((MessageCardHBox) mouseEvent.getSource()).isHasUrgent()) {
      ((MessageCardHBox) mouseEvent.getSource()).setStyle("-fx-background-color: #ffcccc;");
    } else {
      ((MessageCardHBox) mouseEvent.getSource()).setStyle("");
    }
  }
}
