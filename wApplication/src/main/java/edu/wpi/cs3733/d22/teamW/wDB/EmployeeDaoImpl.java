package edu.wpi.cs3733.d22.teamW.wDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDaoImpl implements EmployeeDao {

  DBController dbController = DBController.getDBController();

  public EmployeeDaoImpl(DBController dbc) {
    this.dbController = dbc;
  }

  @Override
  public ArrayList<Employee> getAllEmployees() throws SQLException {
    ArrayList<Employee> employeeList = new ArrayList<Employee>();

    try {
      ResultSet employees = dbController.executeQuery("SELECT * FROM EMPLOYEES");

      while (employees.next()) {
        ArrayList<String> employeeData = new ArrayList<String>();

        for (int i = 0; i < 6; i++) {
          employeeData.add(employees.getString(i + 1));
        }

        employeeList.add(new Employee(employeeData));
      }

    } catch (SQLException e) {
      System.out.println("Query from locations table failed");
      throw (e);
    }
    return employeeList;
  }

  @Override
  public void addEmployee(
      Integer employeeID,
      String firstname,
      String lastname,
      String type,
      String username,
      String password)
      throws SQLException {
    Employee newEmployee = new Employee(employeeID, firstname, lastname, type, username, password);
    DBController.getDBController()
        .executeUpdate(
            String.format("INSERT INTO EMPLOYEES VALUES (%s)", newEmployee.toValuesString()));
  }

  @Override
  public void deleteEmployee(Integer empID) throws SQLException {
    DBController.getDBController()
        .executeUpdate(String.format("DELETE FROM EMPLOYEES WHERE EMPLOYEEID=%d", empID));
  }

  @Override
  public void changeEmployee(
      Integer employeeID,
      String firstname,
      String lastname,
      String type,
      String username,
      String password)
      throws SQLException {
    DBController.getDBController()
        .executeUpdate(
            String.format(
                "UPDATE EMPLOYEES SET FIRSTNAME = '%s', LASTNAME = '%s', EMPLOYEETYPE = '%s', USERNAME = '%s', PASSWORD = '%s' WHERE EMPLOYEEID = %d",
                firstname, lastname, type, username, password, employeeID));
  }

  @Override
  public void exportEmpCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print("employeeID,firstname,lastname,employeetype,username,password");

      // print all locations
      for (Employee e : getAllEmployees()) {
        pw.println();
        pw.print(e.toCSVString());
      }

    } catch (FileNotFoundException | SQLException s) {
      System.out.println(String.format("Error Exporting to File %s", fileName));
      s.printStackTrace();
    }
  }

  public Integer countUsernameOccurences(String s) throws SQLException {
    ResultSet rs =
        dbController.executeQuery(
            String.format("SELECT COUNT(*) AS COUNT FROM EMPLOYEES WHERE USERNAME = '%s'", s));
    rs.next();
    return rs.getInt("COUNT");
  }

  public Integer countIDOccurences(Integer i) throws SQLException {
    ResultSet rs =
        dbController.executeQuery(
            String.format("SELECT COUNT(*) AS COUNT FROM EMPLOYEES WHERE EMPLOYEEID = %d", i));
    rs.next();
    return rs.getInt("COUNT");
  }

  public boolean passwordMatch(String username, String password) throws SQLException {
    ResultSet rs =
        dbController.executeQuery(
            String.format(
                "SELECT COUNT(*) AS COUNT FROM EMPLOYEES WHERE USERNAME = '%s', PASSWORD = '%s'",
                username, password));
    rs.next();
    return (rs.getInt("COUNT") == 1);
  }
}
