package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.UnreadMessageDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.UnreadMessage;
import java.sql.SQLException;
import java.util.ArrayList;

public class UnreadMessageManager {
  private UnreadMessageDao umd;

  private static UnreadMessageManager unreadMessageManager = new UnreadMessageManager();

  public static UnreadMessageManager getUnreadMessageManager() {
    return unreadMessageManager;
  }

  public void setUnreadMessageDao(UnreadMessageDao unreaDMessageDao) {
    this.umd = unreaDMessageDao;
  }

  private UnreadMessageManager() {}

  public ArrayList<UnreadMessage> getAllUnreadMessages() throws SQLException {
    return this.umd.getAllUnreadMessages();
  }

  public ArrayList<UnreadMessage> getAllUnreadMessagesForEmployee(Integer empID)
      throws SQLException {
    return this.umd.getAllUnreadMessagesForEmployee(empID);
  }

  public ArrayList<UnreadMessage> getAllUnreadMessagesFromChatAndEmployee(
      Integer chatID, Integer empID) throws SQLException {
    return this.umd.getAllUnreadMessagesFromChatAndEmployee(chatID, empID);
  }

  public void addUnreadMessage(UnreadMessage unreadMessage) throws SQLException {
    this.umd.addUnreadMessage(unreadMessage);
  }

  public void deleteUnreadMessage(Integer msgID, Integer empID) throws SQLException {
    this.umd.deleteUnreadMessage(msgID, empID);
  }
}
