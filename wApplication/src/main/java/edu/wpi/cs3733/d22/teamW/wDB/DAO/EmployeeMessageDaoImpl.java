package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.EmployeeMessage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployeeMessageDaoImpl implements EmployeeMessageDao {

  Statement statement;

  EmployeeMessageDaoImpl(Statement statement) {
    this.statement = statement;
    dropTable();
  }

  @Override
  public void dropTable() {
    try {
      statement.execute("DROP TABLE EMPLOYEEMESSAGES");
    } catch (SQLException e) {
      System.out.println("Failed to drop EMPLOYEEMESSAGES table");
      // e.printStackTrace();
    }
  }

  //   EmployeeMessage
  //   msgID, empIDfrom, chatIDto, messageContent, sentTimestamp
  //
  //   Chat
  //   chatID, empID
  //
  //   UnreadMessage
  //   msgID, empID
  @Override
  public void createTable() {
    try {
      statement.execute(
          "CREATE TABLE EMPLOYEEMESSAGES(\n"
              + "                messageID INT,\n"
              + "                empIDfrom INT,\n"
              + "                chatIDto INT,\n"
              + "                messageContent varchar(1000),\n"
              + "                sentTimestamp timestamp,\n"
              + "                constraint EmpMessage_EmployeeFrom_FK foreign key (empIDfrom) references EMPLOYEES(employeeID),\n"
              + "                constraint EmpMessage_ChatTo_FK foreign key (chatIDto, empIDfrom) references CHATS(chatID, employeeID),\n"
              + "                constraint EmpMessage_PK primary key (messageID))");
      System.out.println("Created EMPLOYEEMESSAGES table.");
    } catch (SQLException e) {
      System.out.println("Failed to create EMPLOYEEMESSAGES table.");
      e.printStackTrace();
    }
  }

  @Override
  public ArrayList<EmployeeMessage> getAllMessages() throws SQLException {
    ResultSet messageDatabase = statement.executeQuery("SELECT * FROM EMPLOYEEMESSAGES");
    return extractMessagesFromResultSet(messageDatabase);
  }

  @Override
  public ArrayList<EmployeeMessage> getAllMessagesToChat(Integer chatIDto) throws SQLException {
    ResultSet messageDatabase =
        statement.executeQuery(
            String.format("SELECT * FROM EMPLOYEEMESSAGES WHERE CHATIDTO=%d", chatIDto));
    return extractMessagesFromResultSet(messageDatabase);
  }

  @Override
  public ArrayList<EmployeeMessage> getAllMessagesFromEmployee(Integer empIDfrom)
      throws SQLException {
    ResultSet messageDatabase =
        statement.executeQuery(
            String.format("SELECT * FROM EMPLOYEEMESSAGES WHERE EMPIDFROM=%d", empIDfrom));
    return extractMessagesFromResultSet(messageDatabase);
  }

  @Override
  public EmployeeMessage getEmployeeMessage(Integer messageID) throws SQLException {
    ResultSet messageDatabase =
        statement.executeQuery(
            String.format("SELECT * FROM EMPLOYEEMESSAGES WHERE MESSAGEID=%d", messageID));
    ArrayList<EmployeeMessage> employeeMessages = extractMessagesFromResultSet(messageDatabase);
    if (employeeMessages.isEmpty()) throw new SQLException();
    return employeeMessages.get(0);
  }

  private ArrayList<EmployeeMessage> extractMessagesFromResultSet(ResultSet messageDatabase)
      throws SQLException {
    ArrayList<EmployeeMessage> allEmpMsg = new ArrayList<EmployeeMessage>();
    while (messageDatabase.next()) {
      ArrayList<String> empMsgFields = new ArrayList<String>();
      for (int i = 0; i < messageDatabase.getMetaData().getColumnCount(); i++) {
        empMsgFields.add(messageDatabase.getString(i + 1));
      }
      allEmpMsg.add(new EmployeeMessage(empMsgFields));
    }
    return allEmpMsg;
  }

  @Override
  public void addEmployeeMessage(EmployeeMessage em) throws SQLException {
    PreparedStatement preparedStatement =
        statement
            .getConnection()
            .prepareStatement("INSERT INTO EMPLOYEEMESSAGES VALUES(?,?,?,?,?)");
    preparedStatement.setInt(1, em.getMessageID());
    preparedStatement.setInt(2, em.getEmpIDfrom());
    preparedStatement.setInt(3, em.getChatIDto());
    preparedStatement.setString(4, em.getMessageContent());
    preparedStatement.setTimestamp(5, em.getSentTimestamp());
    preparedStatement.executeUpdate();
  }

  @Override
  public void changeEmployeeMessage(EmployeeMessage em) throws SQLException {
    PreparedStatement preparedStatement =
        statement
            .getConnection()
            .prepareStatement(
                "UPDATE EMPLOYEEMESSAGES SET EMPIDFROM=?, CHATIDTO=?, MESSAGECONTENT=?, SENTTIMESTAMP=? WHERE MESSAGEID=?");
    preparedStatement.setInt(1, em.getEmpIDfrom());
    preparedStatement.setInt(2, em.getChatIDto());
    preparedStatement.setString(3, em.getMessageContent());
    preparedStatement.setTimestamp(4, em.getSentTimestamp());
    preparedStatement.setInt(5, em.getMessageID());
    preparedStatement.executeUpdate();
  }

  @Override
  public void deleteEmployeeMessage(Integer messageID) throws SQLException {
    PreparedStatement preparedStatement =
        statement
            .getConnection()
            .prepareStatement("DELETE FROM EMPLOYEEMESSAGES WHERE MESSAGEID=?");
    preparedStatement.setInt(1, messageID);
    preparedStatement.executeUpdate();
  }
}
