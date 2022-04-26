package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.AccountManager;
import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.ChatCardHBox;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmployeeImageView;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.ChatManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeMessageManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.UnreadMessageManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Chat;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.EmployeeMessage;
import edu.wpi.cs3733.d22.teamW.wDB.entity.UnreadMessage;

import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MessagingPageController implements Initializable {

    protected Employee currentEmployee;
    protected Integer currentChatID;
    protected boolean displayingMessages = true;

    @FXML
    AutoCompleteInput employeeComboBox;
    @FXML
    Label messageTitleLabel;
    @FXML
    Label viewMembersLabel;
    @FXML
    VBox messagesWindow;
    @FXML
    VBox messageWindow;
    @FXML
    VBox chatCardView;
    @FXML
    HBox messagingControlsHBox;
    @FXML
    TextField messageTextField;
    @FXML
    Button sendButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            onLoad();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onLoad() throws SQLException {
        this.currentEmployee = AccountManager.getInstance().getEmployee();
        employeeComboBox.loadValues(
                (ArrayList<String>)
                        EmployeeManager.getEmployeeManager().getAllEmployees().stream()
                                .map(e -> e.getFirstName() + " " + e.getLastName())
                                .collect(Collectors.toList()));
        resetMessagePage();
    }

    public void resetMessagePage() throws SQLException {
        clearMessages();
        refreshChatCards();
        messageTitleLabel.setText("Select a Chat");
        messageTextField.clear();
        if (!this.employeeComboBox.getSelectionModel().isEmpty()) {
            this.employeeComboBox.getSelectionModel().clearSelection();
        }
        messageWindow.setDisable(true);
        viewMembersLabel.setVisible(false);
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

    private EmployeeImageView generatePlaceHolderImage(boolean small) {
        double imageWidth = 80;
        double imageHeight = 80;
        if (small) {
            imageWidth = 40;
            imageHeight = 40;
        }
        EmployeeImageView placeHolderImage = new EmployeeImageView();
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
        chatCard.setCursor(Cursor.HAND);
        chatCard.setAlignment(Pos.CENTER_LEFT);

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

    public void addEmployeeCardToMessageWindow(Integer empID) {
        Employee selectedEmployee = null;
        try {
            selectedEmployee = EmployeeManager.getEmployeeManager().getEmployee(empID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(selectedEmployee == null) return;
        // Initialize card and other data
        // Card consists of:
        //  VBOX(s)
        //    ImageView(s)
        //  Seperator
        //  Label
        ChatCardHBox employeeCard = new ChatCardHBox();
        employeeCard.setUnread(false);
        employeeCard.setChatID(empID);
        employeeCard.setCursor(Cursor.HAND);
        employeeCard.setAlignment(Pos.CENTER_LEFT);


        // Set image
        boolean smallImages = false;
        ImageView placeHolderImage = generatePlaceHolderImage(smallImages);

        // Add images
        employeeCard.getChildren().add(placeHolderImage);
        // Add seperator
        employeeCard.getChildren().add(new Separator(Orientation.VERTICAL));

        // Add name label
        Label employeeNameLabel = new Label(selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName());
        if(selectedEmployee.getEmployeeID().equals(this.currentEmployee.getEmployeeID())) {
            employeeNameLabel.setText(employeeNameLabel.getText() + " (You)");
        }
        employeeCard.getChildren().add(employeeNameLabel);

        // Add background color and mouse events
        employeeCard.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            displayMiniProfile(event, EmployeeManager.getEmployeeManager().getEmployee(employeeCard.getChatID()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
        employeeCard.setOnMouseEntered(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mouseOverCard(event);
                    }
                });
        employeeCard.setOnMouseExited(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mouseExitCard(event);
                    }
                });

        // Add card to list + seperator
        messagesWindow.getChildren().add(employeeCard);
        messagesWindow.getChildren().add(new Separator());
    }

    //TODO: Reimplement Phil's function
    public void displayMiniProfile(MouseEvent event, Employee emp) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            //if (event.getClickCount() == 2) {
                WindowManager.getInstance()
                        .storeData(
                                "employee", emp);
                WindowManager.getInstance().openWindow(
                        "MiniProfilePage.fxml", emp.getFirstName() + " " + emp.getLastName());
            //}
        }
    }

    private void setCurrentChat(Integer chatID) throws SQLException {
        this.currentChatID = chatID;
        clearMessages();
        if (messageWindow.isDisabled()) messageWindow.setDisable(false);
        messageTitleLabel.setText(getChatTitleFromID(chatID));
        setViewChatMembers(false);
        if(ChatManager.getChatManager().getAllEmployeesInChat(chatID).size() > 1) {
            viewMembersLabel.setVisible(true);
        } else {
            viewMembersLabel.setVisible(false);
        }
        loadMessages(EmployeeMessageManager.getEmployeeMessageManager().getAllMessagesToChat(chatID));
    }

    private void clearMessages() {
        messagesWindow.getChildren().clear();
    }

    private void loadMessages(ArrayList<EmployeeMessage> currentMessages) {
        Collections.sort(currentMessages, Comparator.comparing(EmployeeMessage::getSentTimestamp));
        loadMessagesIntoList(currentMessages);
        markMessagesRead(currentMessages);
    }

    private void loadMessagesIntoList(ArrayList<EmployeeMessage> messages) {
        VBox currentMessageSection = new VBox();
        Integer currentSectionEmpID = null;
        Timestamp firstTimestamp = null;
        for(EmployeeMessage message : messages) {
            if(currentSectionEmpID == null) {
                currentSectionEmpID = message.getEmpIDfrom();
                firstTimestamp = message.getSentTimestamp();
            }

            if(currentSectionEmpID.equals(message.getEmpIDfrom())) {
                currentMessageSection.getChildren().add(getMessageLabel(message));
            } else {
                messagesWindow.getChildren().add(formattedMessageSection(currentMessageSection, currentSectionEmpID, firstTimestamp));

                currentMessageSection = new VBox();
                currentSectionEmpID = message.getEmpIDfrom();
                firstTimestamp = message.getSentTimestamp();
                currentMessageSection.getChildren().add(getMessageLabel(message));
            }
        }
        if(currentSectionEmpID != null) {
            messagesWindow.getChildren().add(formattedMessageSection(currentMessageSection, currentSectionEmpID, firstTimestamp));
        }
    }

    private VBox formattedMessageSection(VBox currentMessageSection, Integer empID, Timestamp firstMsgTime) {
        boolean fromOther = !empID.equals(this.currentEmployee.getEmployeeID());
        Employee sectionEmployee = null;
        try {
            sectionEmployee = EmployeeManager.getEmployeeManager().getEmployee(empID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(sectionEmployee == null) return null;
        VBox formattedMessageSection = new VBox();
        formattedMessageSection.setFillWidth(true);
        formattedMessageSection.setMinHeight(-1.0 / 0.0);
        formattedMessageSection.setPadding(new Insets(0, 0, 10, 0));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String timeString = firstMsgTime.toLocalDateTime().toLocalTime().format(formatter);
        if(fromOther) {
            formattedMessageSection.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            formattedMessageSection.getChildren().add(new Label(String.format("%s %s - %s", sectionEmployee.getFirstName(), sectionEmployee.getLastName(), timeString)));
        } else {
            formattedMessageSection.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            formattedMessageSection.getChildren().add(new Label(String.format("%s", timeString)));
        }
        if(fromOther) {
            HBox containingImageBox = new HBox();
            EmployeeImageView employeeImage = generatePlaceHolderImage(true);
            employeeImage.setEmpID(sectionEmployee.getEmployeeID());
            employeeImage.setCursor(Cursor.HAND);
            employeeImage.setOnMouseClicked(
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                displayMiniProfile(event, EmployeeManager.getEmployeeManager().getEmployee(employeeImage.getEmpID()));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            containingImageBox.getChildren().add(employeeImage);
            containingImageBox.getChildren().add(currentMessageSection);
            formattedMessageSection.getChildren().add(containingImageBox);
        } else {
            formattedMessageSection.getChildren().add(currentMessageSection);
        }
        return formattedMessageSection;
    }

    private Label getMessageLabel(EmployeeMessage message) {
        boolean fromOther = !message.getEmpIDfrom().equals(this.currentEmployee.getEmployeeID());
        Tooltip timestampTooltip = new Tooltip(message.getSentTimestamp().toString());
        timestampTooltip.setShowDelay(Duration.ZERO);
        timestampTooltip.setHideDelay(Duration.ZERO);
        Label messageLabel = new Label(message.getMessageContent());
        messageLabel.setTooltip(timestampTooltip);
        messageLabel.setMinHeight(-1.0 / 0.0);
        messageLabel.setMaxHeight(-1.0 / 0.0);
        messageLabel.setWrapText(true);
        if(fromOther) {
            messageLabel.setStyle(
                    "-fx-background-color: #e5e5ea;"
                            + "-fx-label-padding: 5;"
                            + "-fx-background-radius: 15;");
            messageLabel.setTextFill(Paint.valueOf("#000000"));
        } else {
            messageLabel.setStyle(
                    "-fx-background-color: #248bf5;"
                            + "-fx-label-padding: 5;"
                            + "-fx-background-radius: 15;");
            messageLabel.setTextFill(Paint.valueOf("#ffffff"));
            messageLabel.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        }
        return messageLabel;
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
        WindowManager.getInstance().openWindow("popUpViews/newGroupchatPopupPage.fxml");
        WindowManager.getInstance().getPrimaryStage().getScene().getRoot().setEffect(null);
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

    private void setViewChatMembers(boolean viewMembers) {
        if(viewMembers) {
            displayingMessages = false;
            viewMembersLabel.setText("Hide members in chat");
            messagingControlsHBox.setDisable(true);
        } else {
            displayingMessages = true;
            viewMembersLabel.setText("View members in chat");
            messagingControlsHBox.setDisable(false);
        }
    }

    public void viewMembersLabelClicked(MouseEvent mouseEvent) throws SQLException {
        if(displayingMessages) {
            clearMessages();
            for(Chat chat : ChatManager.getChatManager().getAllEmployeesInChat(currentChatID)) {
                addEmployeeCardToMessageWindow(chat.getEmpID());
            }
            setViewChatMembers(true);
        } else {
            refreshMessages();
            setViewChatMembers(false);
        }
    }

    public void viewMembersLabelMouseEnter(MouseEvent mouseEvent) {
        ((Label) mouseEvent.getSource()).setStyle("-fx-text-fill: #1267bc");
    }

    public void viewMembersLabelMouseExit(MouseEvent mouseEvent) {
        ((Label) mouseEvent.getSource()).setStyle("-fx-text-fill: #248bf5");
    }
}
