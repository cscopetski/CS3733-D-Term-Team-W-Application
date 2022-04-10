package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDao {

  ArrayList<Employee> getAllEmployees() throws SQLException;

  void addEmployee(
      Integer employeeID,
      String firstname,
      String lastname,
      String type,
      String email,
      String phoneNumber,
      String address,
      String username,
      String password,
      String salt)
      throws SQLException;

  void deleteEmployee(Integer empID) throws SQLException;

  void changeEmployee(
      Integer employeeID,
      String firstname,
      String lastname,
      String type,
      String email,
      String phoneNumber,
      String address,
      String username,
      String password,
      String salt)
      throws SQLException;

  void exportEmpCSV(String fileName);

  // public String getSalt(Integer id);

  public Integer countUsernameOccurences(String s) throws SQLException;

  public Integer countIDOccurences(Integer i) throws SQLException;

  public boolean passwordMatch(String username, String password) throws SQLException;
}