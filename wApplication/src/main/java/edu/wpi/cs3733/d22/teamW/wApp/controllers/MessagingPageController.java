package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.ChatCardHBox;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.ChatManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeMessageManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.UnreadMessageManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Chat;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.EmployeeMessage;
import edu.wpi.cs3733.d22.teamW.wDB.entity.UnreadMessage;
import edu.wpi.cs3733.d22.teamW.wMid.Account;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class MessagingPageController extends LoadableController {

  protected Employee currentEmployee;
  protected Integer currentChatID;

  @FXML AutoCompleteInput employeeComboBox;
  @FXML Label messageTitleLabel;
  @FXML VBox messagesWindow;
  @FXML VBox messageWindow;
  @FXML VBox chatCardView;
  @FXML TextField messageTextField;
  @FXML Button sendButton;

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Messaging;
  }

  @Override
  public void onLoad() throws SQLException {
    this.currentEmployee = Account.getInstance().getEmployee();
    employeeComboBox.loadValues(
        (ArrayList<String>)
            EmployeeManager.getEmployeeManager().getAllEmployees().stream()
                .map(e -> e.getFirstName() + " " + e.getLastName())
                .collect(Collectors.toList()));
    resetMessagePage();
  }

  @Override
  public void onUnload() {}

  public void resetMessagePage() throws SQLException {
    clearMessages();
    refreshChatCards();
    messageTitleLabel.setText("Select a Chat");
    messageTextField.clear();
    if (!this.employeeComboBox.getSelectionModel().isEmpty()) {
      this.employeeComboBox.getSelectionModel().clearSelection();
    }
    messageWindow.setDisable(true);
  }

  public void refreshChatCards() throws SQLException {
    clearChatCards();
    ArrayList<Chat> currentChats =
        ChatManager.getChatManager().getAllChatsEmployeeIsIn(this.currentEmployee.getEmployeeID());
    // get most recent message in each chat
    // sort chats by their recent message
    //        for(int i=0; i<currentChats.size(); i++) {
    //            Chat chat = currentChats.get(i);
    //
    // if(EmployeeMessageManager.getEmployeeMessageManager().getMostRecentMessageInChat(chat.getChatID()) == null) {
    //                currentChats.remove(i);
    //                i--;
    //            }
    //        }
    Collections.sort(
        currentChats,
        new Comparator<Chat>() {
          @Override
          public int compare(Chat o1, Chat o2) {
            EmployeeMessage o1Recent = null;
            EmployeeMessage o2Recent = null;
            try {
              o1Recent =
                  EmployeeMessageManager.getEmployeeMessageManager()
                      .getMostRecentMessageInChat(o1.getChatID());
              o2Recent =
                  EmployeeMessageManager.getEmployeeMessageManager()
                      .getMostRecentMessageInChat(o2.getChatID());
            } catch (SQLException e) {
            }
            if (o1Recent == null) return 1;
            if (o2Recent == null) return -1;
            return -o1Recent.getSentTimestamp().compareTo(o2Recent.getSentTimestamp());
          }
        });

    for (Chat chat : currentChats) {
      int unreadInChat =
          UnreadMessageManager.getUnreadMessageManager()
              .getAllUnreadMessagesFromChatAndEmployee(
                  chat.getChatID(), this.currentEmployee.getEmployeeID())
              .size();
      // If the card is a DM between two people and has no messages, do not display it
      if (!(ChatManager.getChatManager().getAllEmployeesInChat(chat.getChatID()).size() == 2
          && EmployeeMessageManager.getEmployeeMessageManager()
                  .getAllMessagesToChat(chat.getChatID())
                  .size()
              == 0)) {
        addChatCard(chat.getChatID(), unreadInChat);
      }
    }
  }

  public void clearChatCards() {
    chatCardView.getChildren().clear();
  }

  private ImageView generatePlaceHolderImage(boolean small) {
    double imageWidth = 80;
    double imageHeight = 80;
    if (small) {
      imageWidth = 40;
      imageHeight = 40;
    }
    ImageView placeHolderImage = new ImageView();
    placeHolderImage.setImage(
        new Image(
            MessagingPageController.class
                .getClassLoader()
                .getResource("edu/wpi/cs3733/d22/teamW/wApp/assets/Icons/profilePicture.png")
                .toString()));
    placeHolderImage.setFitWidth(imageWidth);
    placeHolderImage.setFitHeight(imageHeight);
    return placeHolderImage;
  }

  public void addChatCard(Integer chatID, int numUnread) throws SQLException {
    boolean hasUnread = numUnread > 0;
    // Initialize card and other data
    // Card consists of:
    //  VBOX(s)
    //    ImageView(s)
    //  Seperator
    //  Label
    ChatCardHBox chatCard = new ChatCardHBox();
    chatCard.setPrefWidth(332);
    chatCard.setPrefHeight(80);
    chatCard.setUnread(hasUnread);
    chatCard.setChatID(chatID);

    ArrayList<Employee> otherEmployeesInChat = getOtherEmployeesInChat(chatID);

    // Set image
    boolean smallImages = false;
    if (otherEmployeesInChat.size() > 1) {
      smallImages = true;
    }
    ImageView placeHolderImage = generatePlaceHolderImage(smallImages);
    ImageView placeHolderImage2 = generatePlaceHolderImage(smallImages);
    ImageView placeHolderImage3 = generatePlaceHolderImage(smallImages);
    ImageView placeHolderImage4 = generatePlaceHolderImage(smallImages);

    // Add images
    VBox imageHolderVBOX_1 = new VBox();
    VBox imageHolderVBOX_2 = new VBox();
    switch (otherEmployeesInChat.size()) {
      case 1:
        imageHolderVBOX_1.getChildren().add(placeHolderImage);
        chatCard.getChildren().add(imageHolderVBOX_1);
        break;
      case 2:
        imageHolderVBOX_1.setPrefWidth(80);
        imageHolderVBOX_1.getChildren().add(placeHolderImage);
        imageHolderVBOX_1.getChildren().add(placeHolderImage2);
        chatCard.getChildren().add(imageHolderVBOX_1);
        break;
      case 3:
        imageHolderVBOX_1.getChildren().add(placeHolderImage);
        imageHolderVBOX_1.getChildren().add(placeHolderImage2);
        imageHolderVBOX_2.getChildren().add(placeHolderImage3);
        chatCard.getChildren().add(imageHolderVBOX_1);
        chatCard.getChildren().add(imageHolderVBOX_2);
        break;
      case 4:
        imageHolderVBOX_1.getChildren().add(placeHolderImage);
        imageHolderVBOX_1.getChildren().add(placeHolderImage2);
        imageHolderVBOX_2.getChildren().add(placeHolderImage3);
        imageHolderVBOX_2.getChildren().add(placeHolderImage4);
        chatCard.getChildren().add(imageHolderVBOX_1);
        chatCard.getChildren().add(imageHolderVBOX_2);
        break;
      default:
        imageHolderVBOX_1.getChildren().add(placeHolderImage);
        imageHolderVBOX_1.getChildren().add(placeHolderImage2);
        imageHolderVBOX_2.getChildren().add(placeHolderImage3);
        Label additionalLabel = new Label(String.format("+%d", otherEmployeesInChat.size() - 3));
        additionalLabel.setFont(new Font(19));
        imageHolderVBOX_2.getChildren().add(additionalLabel);
        chatCard.getChildren().add(imageHolderVBOX_1);
        chatCard.getChildren().add(imageHolderVBOX_2);
        break;
    }
    // Add seperator
    chatCard.getChildren().add(new Separator(Orientation.VERTICAL));

    // Add title label
    Label chatNameLabel = new Label(getChatTitleFromID(chatID));
    if (hasUnread) {
      chatNameLabel.setText(String.format("(%d) %s", numUnread, chatNameLabel.getText()));
    }
    chatCard.getChildren().add(chatNameLabel);

    // Add background color and mouse events
    if (hasUnread) {
      chatCard.setStyle("-fx-background-color: #ffcccc;");
    }
    chatCard.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            try {
              clickChatCard(event);
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        });
    chatCard.setOnMouseEntered(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            mouseOverCard(event);
          }
        });
    chatCard.setOnMouseExited(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            mouseExitCard(event);
          }
        });

    // Add card to list + seperator
    chatCardView.getChildren().add(chatCard);
    chatCardView.getChildren().add(new Separator());
  }

  private void setCurrentChat(Integer chatID) throws SQLException {
    this.currentChatID = chatID;
    clearMessages();
    if (messageWindow.isDisabled()) messageWindow.setDisable(false);
    messageTitleLabel.setText(getChatTitleFromID(chatID));
    loadMessages(EmployeeMessageManager.getEmployeeMessageManager().getAllMessagesToChat(chatID));
  }

  private void clearMessages() {
    messagesWindow.getChildren().clear();
  }

  private void loadMessages(ArrayList<EmployeeMessage> currentMessages) {
    Collections.sort(currentMessages, Comparator.comparing(EmployeeMessage::getSentTimestamp));
    for (EmployeeMessage message : currentMessages) {
      addMessageToList(message);
    }
    markMessagesRead(currentMessages);
  }

  public void addMessageToList(EmployeeMessage message) {
    boolean fromOther = !message.getEmpIDfrom().equals(this.currentEmployee.getEmployeeID());
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

  private ArrayList<Employee> getOtherEmployeesInChat(Integer chatID) throws SQLException {
    return EmployeeManager.getEmployeeManager()
        .getOtherEmployeesInChat(chatID, this.currentEmployee.getEmployeeID());
  }

  private String getChatTitleFromID(Integer chatID) throws SQLException {
    ArrayList<Employee> otherEmployeesInChat = getOtherEmployeesInChat(chatID);
    return getChatTitleFromEmployees(otherEmployeesInChat);
  }

  private String getChatTitleFromEmployees(ArrayList<Employee> otherEmployeesInChat)
      throws SQLException {
    String chatTitle = "";
    if (otherEmployeesInChat.size() == 1) {
      chatTitle =
          (otherEmployeesInChat.get(0).getFirstName()
              + " "
              + otherEmployeesInChat.get(0).getLastName());
    } else {
      for (int i = 0; i < otherEmployeesInChat.size(); i++) {
        Employee employee = otherEmployeesInChat.get(i);
        if (i == 0) {
          chatTitle = (employee.getFirstName());
        } else {
          chatTitle = (chatTitle + ", " + employee.getFirstName());
        }
      }
    }
    return chatTitle;
  }

  public void onSendButtonClick() {
    if (messageTextField.getText().isEmpty()) {
      return;
    }
    Integer newMessageID = EmployeeMessageManager.getEmployeeMessageManager().getNextMsgID();
    EmployeeMessage sentMessage =
        new EmployeeMessage(
            newMessageID,
            this.currentEmployee.getEmployeeID(),
            this.currentChatID,
            messageTextField.getText(),
            new Timestamp(System.currentTimeMillis()));
    try {
      EmployeeMessageManager.getEmployeeMessageManager().addEmployeeMessage(sentMessage);
      for (Chat chat : ChatManager.getChatManager().getAllEmployeesInChat(this.currentChatID)) {
        if (!chat.getEmpID().equals(this.currentEmployee.getEmployeeID())) {
          UnreadMessageManager.getUnreadMessageManager()
              .addUnreadMessage(new UnreadMessage(newMessageID, chat.getEmpID()));
        }
      }
      refreshMessages();
      refreshChatCards();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    messageTextField.clear();
  }

  public void refreshMessages() throws SQLException {
    clearMessages();
    loadMessages(
        EmployeeMessageManager.getEmployeeMessageManager()
            .getAllMessagesToChat(this.currentChatID));
  }

  public void markMessagesRead(ArrayList<EmployeeMessage> messages) {
    for (EmployeeMessage message : messages) {
      try {
        UnreadMessageManager.getUnreadMessageManager()
            .deleteUnreadMessage(message.getMessageID(), this.currentEmployee.getEmployeeID());
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void employeeSelected() throws SQLException {
    // CREATE A DM BETWEEN TWO EMPLOYEES IF IT DOES NOT EXIST
    // Find the chosen employee
    Employee chosenEmployee = null;
    if (employeeComboBox.getSelectionModel().isEmpty()) return;
    for (Employee e : EmployeeManager.getEmployeeManager().getAllEmployees()) {
      if (e.getFirstName().equals(employeeComboBox.getValue().split(" ")[0])
          && e.getLastName().equals(employeeComboBox.getValue().split(" ")[1])) {
        chosenEmployee = e;
        if (chosenEmployee.getEmployeeID().equals(this.currentEmployee.getEmployeeID())) return;
        break;
      }
    }
    if (chosenEmployee == null) return;
    // Check if chat between only these two exists
    for (Chat chat :
        ChatManager.getChatManager()
            .getAllChatsEmployeeIsIn(this.currentEmployee.getEmployeeID())) {
      ArrayList<Chat> currentChatCheck =
          ChatManager.getChatManager().getAllEmployeesInChat(chat.getChatID());
      if (currentChatCheck.size() == 2) {
        if (currentChatCheck.get(0).getEmpID().equals(chosenEmployee.getEmployeeID())) {
          setCurrentChat(currentChatCheck.get(0).getChatID());
          return;
        }
        if (currentChatCheck.get(1).getEmpID().equals(chosenEmployee.getEmployeeID())) {
          setCurrentChat(currentChatCheck.get(1).getChatID());
          return;
        }
      }
    }
    // Make the new DM
    Integer newChatID = ChatManager.getChatManager().getNextChatID();
    ArrayList<Chat> newChat = new ArrayList<>();
    newChat.add(new Chat(newChatID, this.currentEmployee.getEmployeeID()));
    newChat.add(new Chat(newChatID, chosenEmployee.getEmployeeID()));
    ChatManager.getChatManager().addChat(newChat);

    refreshChatCards();
    setCurrentChat(newChatID);
  }

  public void messageTextKeyPress(KeyEvent keyEvent) {
    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
      onSendButtonClick();
    }
  }

  public void clickChatCard(MouseEvent event) throws SQLException {
    setCurrentChat(((ChatCardHBox) event.getSource()).getChatID());
    refreshChatCards();
    if (!this.employeeComboBox.getSelectionModel().isEmpty()) {
      this.employeeComboBox.getSelectionModel().clearSelection();
    }
  }

  public void mouseOverCard(MouseEvent mouseEvent) {
    if (((ChatCardHBox) mouseEvent.getSource()).isUnread()) {
      ((ChatCardHBox) mouseEvent.getSource()).setStyle("-fx-background-color: #ffb2b2;");
    } else {
      ((ChatCardHBox) mouseEvent.getSource()).setStyle("-fx-background-color: #d9e0ff;");
    }
  }

  public void mouseExitCard(MouseEvent mouseEvent) {
    if (((ChatCardHBox) mouseEvent.getSource()).isUnread()) {
      ((ChatCardHBox) mouseEvent.getSource()).setStyle("-fx-background-color: #ffcccc;");
    } else {
      ((ChatCardHBox) mouseEvent.getSource()).setStyle("");
    }
  }

  public void createGroupchatClicked(ActionEvent actionEvent) throws IOException, SQLException {
    Stage S = SceneManager.getInstance().openWindow("popUpViews/newGroupchatPopupPage.fxml");
    S.getScene()
        .getWindow()
        .setOnCloseRequest(
            new EventHandler<WindowEvent>() {
              @Override
              public void handle(WindowEvent event) {
                System.out.println("CLOSED WINDOW WITH X BUTTON");
                newGroupchatPopupPageController.clearSelectedEmployeeIDs();
              }
            });
    TreeSet<Integer> groupChatEmployees = newGroupchatPopupPageController.getSelectedEmployeeIDs();
    if (groupChatEmployees.size() >= 2) {
      groupChatEmployees.add(this.currentEmployee.getEmployeeID());
      ArrayList<Chat> newGroupChat = new ArrayList<>();
      Integer newGroupChatID = ChatManager.getChatManager().getNextChatID();
      for (Integer empID : groupChatEmployees) {
        newGroupChat.add(new Chat(newGroupChatID, empID));
      }
      ChatManager.getChatManager().addChat(newGroupChat);
      setCurrentChat(newGroupChatID);
      refreshChatCards();
    }
  }
}
