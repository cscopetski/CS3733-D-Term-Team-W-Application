package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDao {

  ArrayList<Employee> getAllEmployees() throws SQLException;

  Employee getEmployee(String username) throws SQLException;

  Employee getEmployee(Integer empID) throws SQLException;

  public Employee getEmployeeType(EmployeeType employeeType);

  void addEmployee(Employee employee) throws SQLException;

  void deleteEmployee(Integer empID) throws SQLException;

  void changeEmployee(Employee employee) throws SQLException;

  void exportEmpCSV(String fileName);

  // public String getSalt(Integer id);

  public Integer countUsernameOccurences(String s) throws SQLException;

  public Integer countIDOccurences(Integer i) throws SQLException;

  public boolean passwordMatch(String username, String password) throws SQLException;
}
