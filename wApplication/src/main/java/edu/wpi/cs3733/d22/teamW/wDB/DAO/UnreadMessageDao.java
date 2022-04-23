package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.UnreadMessage;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UnreadMessageDao {
  void dropTable();

  void createTable();

  ArrayList<UnreadMessage> getAllUnreadMessages() throws SQLException;

  ArrayList<UnreadMessage> getAllUnreadMessagesForEmployee(Integer empID) throws SQLException;

  ArrayList<UnreadMessage> getAllUnreadMessagesFromChatAndEmployee(Integer chatID, Integer empID)
      throws SQLException;

  void addUnreadMessage(UnreadMessage unreadMessage) throws SQLException;

  void deleteUnreadMessage(Integer msgID, Integer empID) throws SQLException;
}
