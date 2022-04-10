package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.EmployeeDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeManager {
  private EmployeeDao ed;

  private static EmployeeManager employeeManager = new EmployeeManager();

  public static EmployeeManager getEmployeeManager() {
    return employeeManager;
  }

  private EmployeeManager() {}

  public void setEmployeeDao(EmployeeDao ed) {
    this.ed = ed;
  }

  public boolean usernameExists(String s) throws SQLException {
    return (ed.countUsernameOccurences(s) > 0);
  }

  public boolean passwordMatch(String username, String password) throws SQLException {
    return ed.passwordMatch(username, password);
  }

  public void addEmployee(
      Integer employeeID,
      String firstname,
      String lastname,
      String type,
      String username,
      String password)
      throws SQLException {
    ed.addEmployee(employeeID, firstname, lastname, type, username, password);
  }

  public void deleteEmployee(Integer employeeID) throws SQLException {
    ed.deleteEmployee(employeeID);
  }

  public void changeEmployee(
      Integer employeeID,
      String firstname,
      String lastname,
      String type,
      String username,
      String password)
      throws SQLException {
    ed.changeEmployee(employeeID, firstname, lastname, type, username, password);
  }

  public ArrayList<Employee> getAllEmployees() throws SQLException {
    return ed.getAllEmployees();
  }

  public void exportEmpCSV(String filename) {
    ed.exportEmpCSV(filename);
  }
}
