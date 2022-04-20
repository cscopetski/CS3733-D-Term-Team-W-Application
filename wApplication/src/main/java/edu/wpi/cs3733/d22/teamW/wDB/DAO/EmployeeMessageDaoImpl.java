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
      //e.printStackTrace();
    }
  }

  @Override
  public void createTable() {
    try {
      statement.execute(
          "CREATE TABLE EMPLOYEEMESSAGES(\n"
              + "                messageID INT,\n"
              + "                empIDfrom INT,\n"
              + "                empIDto INT,\n"
              + "                messageContent varchar(1000),\n"
              + "                sentTimestamp timestamp,\n"
              + "                isRead INT,\n"
              + "                constraint EmpMessage_EmployeeFrom_FK foreign key (empIDfrom) references EMPLOYEES(employeeID),\n"
              + "                constraint EmpMessage_EmployeeTo_FK foreign key (empIDto) references EMPLOYEES(employeeID),\n"
              + "                constraint EmpMessage_PK primary key (messageID),\n"
              + "                constraint EmpMessage_IsRead_check check (isRead = 0 or isRead = 1))");
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
  public ArrayList<EmployeeMessage> getAllUnreadMessages(Integer empIDto) throws SQLException {
    ResultSet messageDatabase =
        statement.executeQuery(
            String.format(
                "SELECT * FROM EMPLOYEEMESSAGES WHERE EMPIDTO=%d AND ISREAD=%d", empIDto, 0));
    return extractMessagesFromResultSet(messageDatabase);
  }

  @Override
  public ArrayList<EmployeeMessage> getMessagesFromTo(Integer empIDfrom, Integer empIDto)
      throws SQLException {
    ResultSet messageDatabase =
        statement.executeQuery(
            String.format(
                "SELECT * FROM EMPLOYEEMESSAGES WHERE EMPIDFROM=%d AND EMPIDTO=%d",
                empIDfrom, empIDto));
    return extractMessagesFromResultSet(messageDatabase);
  }

  @Override
  public Integer countUnreadMessagesAs(Integer empIDto) throws SQLException {
    ResultSet messageDatabase =
        statement.executeQuery(
            String.format(
                "SELECT COUNT(*) FROM EMPLOYEEMESSAGES WHERE EMPIDTO=%d AND ISREAD=0", empIDto));
    messageDatabase.next();
    return messageDatabase.getInt(1);
  }

  @Override
  public Integer countUnreadMessagesAsFrom(Integer empIDto, Integer empIDfrom) throws SQLException {
    ResultSet messageDatabase =
        statement.executeQuery(
            String.format(
                "SELECT COUNT(*) FROM EMPLOYEEMESSAGES WHERE EMPIDTO=%d AND EMPIDFROM=%d AND ISREAD=0",
                empIDto, empIDfrom));
    messageDatabase.next();
    return messageDatabase.getInt(1);
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
            .prepareStatement("INSERT INTO EMPLOYEEMESSAGES VALUES(?,?,?,?,?,?)");
    preparedStatement.setInt(1, em.getMessageID());
    preparedStatement.setInt(2, em.getEmpIDfrom());
    preparedStatement.setInt(3, em.getEmpIDto());
    preparedStatement.setString(4, em.getMessageContent());
    preparedStatement.setTimestamp(5, em.getSentTimestamp());
    preparedStatement.setInt(6, em.getIsRead());
    preparedStatement.executeUpdate();
  }

  @Override
  public void changeEmployeeMessage(EmployeeMessage em) throws SQLException {
    PreparedStatement preparedStatement =
        statement
            .getConnection()
            .prepareStatement(
                "UPDATE EMPLOYEEMESSAGES SET EMPIDFROM=?, EMPIDTO=?, MESSAGECONTENT=?, SENTTIMESTAMP=?, ISREAD=? WHERE MESSAGEID=?");
    preparedStatement.setInt(1, em.getEmpIDfrom());
    preparedStatement.setInt(2, em.getEmpIDto());
    preparedStatement.setString(3, em.getMessageContent());
    preparedStatement.setTimestamp(4, em.getSentTimestamp());
    preparedStatement.setInt(5, em.getIsRead());
    preparedStatement.setInt(6, em.getMessageID());
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
