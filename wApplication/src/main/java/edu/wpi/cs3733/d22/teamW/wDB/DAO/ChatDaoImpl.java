package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Chat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChatDaoImpl implements ChatDao {

  Statement statement;

  public ChatDaoImpl(Statement statement) {
    this.statement = statement;
    dropTable();
  }

  @Override
  public void dropTable() {
    try {
      statement.execute("DROP TABLE CHATS");
    } catch (SQLException e) {
      System.out.println("Failed to drop CHATS table");
    }
  }

  @Override
  public void createTable() {
    try {
      statement.execute(
          "CREATE TABLE CHATS("
              + "chatID INT,"
              + "employeeID INT,"
              + "constraint Chat_EmpId_FK foreign key (employeeID) references EMPLOYEES(employeeID),"
              + "constraint Chat_ChatIDEmpID_PK primary key (chatID, employeeID)"
              + ")");
      System.out.println("Created CHATS table.");
    } catch (SQLException e) {
      System.out.println("Failed to create table CHATS");
    }
  }

  private ArrayList<Chat> extractChatsFromResultSet(ResultSet chatDatabase) throws SQLException {
    ArrayList<Chat> result = new ArrayList<Chat>();
    while (chatDatabase.next()) {
      ArrayList<Integer> fields = new ArrayList<>();
      for (int i = 0; i < chatDatabase.getMetaData().getColumnCount(); i++) {
        fields.add(chatDatabase.getInt(i + 1));
      }
      result.add(new Chat(fields));
    }
    return result;
  }

  @Override
  public ArrayList<Chat> getAllChats() throws SQLException {
    ResultSet chatDatabase = statement.executeQuery("SELECT * FROM CHATS");
    return extractChatsFromResultSet(chatDatabase);
  }

  @Override
  public ArrayList<Chat> getAllChatsEmployeeIsIn(Integer empID) throws SQLException {
    ResultSet chatDatabase =
        statement.executeQuery(String.format("SELECT * FROM CHATS WHERE EMPLOYEEID=%d", empID));
    return extractChatsFromResultSet(chatDatabase);
  }

  @Override
  public ArrayList<Chat> getAllEmployeesInChat(Integer chatID) throws SQLException {
    ResultSet chatDatabase =
        statement.executeQuery(String.format("SELECT * FROM CHATS WHERE CHATID=%d", chatID));
    return extractChatsFromResultSet(chatDatabase);
  }

  @Override
  public void addChat(Chat chat) throws SQLException {
    statement.executeUpdate(String.format("INSERT INTO CHATS VALUES(%s)", chat.toValuesString()));
  }

  @Override
  public void deleteChat(Integer chatID) throws SQLException {
    statement.executeUpdate(String.format("DELETE FROM CHATS WHERE CHATID=%d", chatID));
  }

  @Override
  public void deleteEmployeeFromChat(Integer chatID, Integer empID) throws SQLException {
    statement.executeUpdate(
        String.format("DELETE FROM CHATS WHERE CHATID=%d AND EMPLOYEEID=%d", chatID, empID));
  }

  @Override
  public Integer getMaxID() throws SQLException {
    ResultSet maxID = statement.executeQuery("SELECT MAX(CHATID) FROM CHATS");
    maxID.next();
    return maxID.getInt(1);
  }
}
