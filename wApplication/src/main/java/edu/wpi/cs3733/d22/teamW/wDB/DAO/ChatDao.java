package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Chat;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ChatDao {
  void dropTable();

  void createTable();

  ArrayList<Chat> getAllChats() throws SQLException;

  ArrayList<Chat> getAllChatsEmployeeIsIn(Integer empID) throws SQLException;

  ArrayList<Chat> getAllEmployeesInChat(Integer chatID) throws SQLException;

  void addChat(Chat chat) throws SQLException;

  void deleteChat(Integer chatID) throws SQLException;

  void deleteEmployeeFromChat(Integer chatID, Integer empID) throws SQLException;
}
