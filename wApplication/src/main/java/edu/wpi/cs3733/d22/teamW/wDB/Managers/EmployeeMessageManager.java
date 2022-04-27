package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.EmployeeMessageDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.EmployeeMessage;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeMessageManager {
  private EmployeeMessageDao emd;

  private static EmployeeMessageManager employeeMessageManager = new EmployeeMessageManager();

  public static EmployeeMessageManager getEmployeeMessageManager() {
    return employeeMessageManager;
  }

  private EmployeeMessageManager() {}

  public void setEmployeeMessageDao(EmployeeMessageDao emd) {
    this.emd = emd;
  }

  public ArrayList<EmployeeMessage> getAllMessages() throws SQLException {
    return this.emd.getAllMessages();
  }

  public ArrayList<EmployeeMessage> getAllMessagesToChat(Integer chatIDto) throws SQLException {
    return this.emd.getAllMessagesToChat(chatIDto);
  }

  public ArrayList<EmployeeMessage> getAllMessagesFromEmployee(Integer empIDfrom)
      throws SQLException {
    return this.emd.getAllMessagesFromEmployee(empIDfrom);
  }

  public EmployeeMessage getEmployeeMessage(Integer messageID) throws SQLException {
    return this.emd.getEmployeeMessage(messageID);
  }

  public EmployeeMessage getMostRecentMessageInChat(Integer chatID) throws SQLException {
    return this.emd.getMostRecentMessageInChat(chatID);
  }

  public void addEmployeeMessage(EmployeeMessage em) throws SQLException {
    this.emd.addEmployeeMessage(em);
  }

  public void changeEmployeeMessage(EmployeeMessage em) throws SQLException {
    this.emd.changeEmployeeMessage(em);
  }

  public void deleteEmployeeMessage(Integer messageID) throws SQLException {
    this.emd.deleteEmployeeMessage(messageID);
  }

  public Integer getNextMsgID() throws SQLException {
    return this.emd.getMaxID() + 1;
  }
}
