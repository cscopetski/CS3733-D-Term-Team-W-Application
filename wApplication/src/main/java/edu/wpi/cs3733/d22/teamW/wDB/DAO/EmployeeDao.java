package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Employee;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDao {

  ArrayList<Employee> getAllEmployees() throws SQLException;

  void addEmployee(
      Integer employeeID,
      String firstname,
      String lastname,
      String type,
      String username,
      String password)
      throws SQLException;

  void deleteEmployee(Integer empID) throws SQLException;

  void changeEmployee(
      Integer employeeID,
      String firstname,
      String lastname,
      String type,
      String username,
      String password)
      throws SQLException;

  void exportEmpCSV(String fileName);

  public Integer countUsernameOccurences(String s) throws SQLException;

  public Integer countIDOccurences(Integer i) throws SQLException;

  public boolean passwordMatch(String username, String password) throws SQLException;
}
