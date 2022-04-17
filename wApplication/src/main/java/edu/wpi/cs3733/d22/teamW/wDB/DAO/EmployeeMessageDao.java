package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.EmployeeMessage;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeMessageDao {

  void dropTable();

  void createTable();

  ArrayList<EmployeeMessage> getAllMessages() throws SQLException;

  ArrayList<EmployeeMessage> getAllUnreadMessages(Integer empIDto) throws SQLException;

  ArrayList<EmployeeMessage> getMessagesFromTo(Integer empIDfrom, Integer empIDto)
      throws SQLException;

  EmployeeMessage getEmployeeMessage(Integer messageID) throws SQLException;

  void addEmployeeMessage(EmployeeMessage em) throws SQLException;

  void changeEmployeeMessage(EmployeeMessage em) throws SQLException;

  void deleteEmployeeMessage(Integer messageID) throws SQLException;
}
