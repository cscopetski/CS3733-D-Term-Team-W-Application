package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeMessageManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.UnreadMessage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UnreadMessageDaoImpl implements UnreadMessageDao {
  Statement statement;

  public UnreadMessageDaoImpl(Statement statement) {
    this.statement = statement;
    dropTable();
  }

  @Override
  public void dropTable() {
    try {
      statement.execute("DROP TABLE UNREADMESSAGES");
      System.out.println("Dropped create table UNREADMESSAGES");
    } catch (SQLException e) {
      System.out.println("Failed to drop table UNREADMESSAGES");
    }
  }

  @Override
  public void createTable() {
    try {
      statement.execute(
          "CREATE TABLE UNREADMESSAGES("
              + "messageID INT,"
              + "employeeID INT,"
              + "constraint UnreadMessage_MsgID_FK foreign key (messageID) references EMPLOYEEMESSAGES(messageID),"
              + "constraint UnreadMessage_EmpID_FK foreign key (employeeID) references EMPLOYEES(employeeID),"
              + "constraint UnreadMessage_msgIDempID_PK primary key (messageID, employeeID)"
              + ")");
      System.out.println("Created table UNREADMESSAGES");
    } catch (SQLException e) {
      System.out.println("Failed to create table UNREADMESSAGES");
      // e.printStackTrace();
    }
  }

  ArrayList<UnreadMessage> extractUnreadMessages(ResultSet unreadMessageDatabase)
      throws SQLException {
    ArrayList<UnreadMessage> result = new ArrayList<>();
    while (unreadMessageDatabase.next()) {
      ArrayList<Integer> fields = new ArrayList<>();
      for (int i = 0; i < unreadMessageDatabase.getMetaData().getColumnCount(); i++) {
        fields.add(unreadMessageDatabase.getInt(i + 1));
      }
      result.add(new UnreadMessage(fields));
    }
    return result;
  }

  @Override
  public ArrayList<UnreadMessage> getAllUnreadMessages() throws SQLException {
    ResultSet rs = statement.executeQuery("SELECT * FROM UNREADMESSAGES");
    return extractUnreadMessages(rs);
  }

  @Override
  public ArrayList<UnreadMessage> getAllUnreadMessagesForEmployee(Integer empID)
      throws SQLException {
    ResultSet rs =
        statement.executeQuery(
            String.format("SELECT * FROM UNREADMESSAGES WHERE EMPLOYEEID=%d", empID));
    return extractUnreadMessages(rs);
  }

  @Override
  public ArrayList<UnreadMessage> getAllUnreadMessagesFromChatAndEmployee(
      Integer chatID, Integer empID) throws SQLException {
    ArrayList<UnreadMessage> result = new ArrayList<>();
    for (UnreadMessage unreadMessage : getAllUnreadMessagesForEmployee(empID)) {
      if (EmployeeMessageManager.getEmployeeMessageManager()
          .getEmployeeMessage(unreadMessage.getMsgID())
          .getChatIDto()
          .equals(chatID)) {
        result.add(unreadMessage);
      }
    }
    return result;
  }

  @Override
  public void addUnreadMessage(UnreadMessage unreadMessage) throws SQLException {
    statement.execute(
        String.format("INSERT INTO UNREADMESSAGES VALUES(%s)", unreadMessage.toValuesString()));
  }

  @Override
  public void deleteUnreadMessage(Integer msgID, Integer empID) throws SQLException {
    statement.execute(
        String.format(
            "DELETE FROM UNREADMESSAGES WHERE MESSAGEID=%d AND EMPLOYEEID=%d", msgID, empID));
  }
}
