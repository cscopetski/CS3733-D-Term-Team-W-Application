package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.ChatDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Chat;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatManager {
  // TODO: Fix this (wont work on server cause its local)
  private static Integer count = 1;

  private ChatDao cd;

  private ChatManager() {}

  private static ChatManager chatManager = new ChatManager();

  public static ChatManager getChatManager() {
    return chatManager;
  }

  public void setChatDao(ChatDao cd) {
    this.cd = cd;
  }

  public ArrayList<Chat> getAllChats() throws SQLException {
    return this.cd.getAllChats();
  }

  public ArrayList<Chat> getAllChatsEmployeeIsIn(Integer empID) throws SQLException {
    return this.cd.getAllChatsEmployeeIsIn(empID);
  }

  public ArrayList<Chat> getAllEmployeesInChat(Integer chatID) throws SQLException {
    return this.cd.getAllEmployeesInChat(chatID);
  }

  private void addChat(Chat chat) throws SQLException {
    this.cd.addChat(chat);
  }

  public void addChat(ArrayList<Chat> chats) throws SQLException {
    for (Chat chat : chats) {
      addChat(chat);
    }
    count++;
  }

  public void deleteChat(Integer chatID) throws SQLException {
    this.cd.deleteChat(chatID);
  }

  public void deleteEmployeeFromChat(Integer chatID, Integer empID) throws SQLException {
    this.cd.deleteEmployeeFromChat(chatID, empID);
  }

  public Integer getNextChatID() {
    return count;
  }
}
