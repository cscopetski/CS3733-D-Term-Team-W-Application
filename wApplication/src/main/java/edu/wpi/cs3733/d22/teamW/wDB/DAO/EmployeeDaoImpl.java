package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployeeDaoImpl implements EmployeeDao {

  Statement statement;

  EmployeeDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() throws SQLException {
    try {
      statement.execute("DROP TABLE EMPLOYEES");
      System.out.println("Dropped Employee Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Employee Table");
      throw (e);
    }
  }

  void createTable() throws SQLException {
    try {
      statement.execute(
          "CREATE TABLE EMPLOYEES(\n"
              + "employeeID INT, \n "
              + "firstName varchar(25), \n "
              + "lastName varchar(25), \n "
              + "employeeType varchar(25), \n "
              + "email varchar(256), \n "
              + "phonenumber varchar(25), \n "
              + "address varchar(256), \n "
              + "username varchar(256), \n "
              + "password varchar(256), \n "
              + "salt varchar(256), \n "
              + "constraint Employees_PK primary key (employeeID),"
              + "constraint username_uq unique(username))");
    } catch (SQLException e) {
      System.out.println("Employee Table failed to be created!");
      throw (e);
    }
    System.out.println("Employee Table created");
  }

  @Override
  public ArrayList<Employee> getAllEmployees() throws SQLException {
    ArrayList<Employee> employeeList = new ArrayList<Employee>();

    try {
      ResultSet employees = statement.executeQuery("SELECT * FROM EMPLOYEES");

      while (employees.next()) {
        ArrayList<String> employeeData = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
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
      String email,
      String phoneNumber,
      String address,
      String username,
      String password,
      String salt)
      throws SQLException {
    Employee newEmployee =
        new Employee(
            employeeID,
            firstname,
            lastname,
            type,
            email,
            phoneNumber,
            address,
            username,
            password,
            salt);
    statement.executeUpdate(
        String.format("INSERT INTO EMPLOYEES VALUES (%s)", newEmployee.toValuesString()));
  }

  @Override
  public void deleteEmployee(Integer empID) throws SQLException {
    statement.executeUpdate(String.format("DELETE FROM EMPLOYEES WHERE EMPLOYEEID=%d", empID));
  }

  @Override
  public void changeEmployee(
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
      throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE EMPLOYEES SET FIRSTNAME = '%s', LASTNAME = '%s', EMPLOYEETYPE = '%s', EMAIL = '%s', PHONENUMBER = '%s', ADDRESS = '%s', USERNAME = '%s', PASSWORD = '%s', SALT = '%s' WHERE EMPLOYEEID = %d",
            firstname,
            lastname,
            type,
            email,
            phoneNumber,
            address,
            username,
            password,
            salt,
            employeeID));
  }

  @Override
  public void exportEmpCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(
          "employeeID,firstname,lastname,employeetype,email,phonenumber,address,username,password,salt");

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
        statement.executeQuery(
            String.format("SELECT COUNT(*) AS COUNT FROM EMPLOYEES WHERE USERNAME = '%s'", s));
    rs.next();
    return rs.getInt("COUNT");
  }

  public Integer countIDOccurences(Integer i) throws SQLException {
    ResultSet rs =
        statement.executeQuery(
            String.format("SELECT COUNT(*) AS COUNT FROM EMPLOYEES WHERE EMPLOYEEID = %d", i));
    rs.next();
    return rs.getInt("COUNT");
  }

  public boolean passwordMatch(String username, String password) throws SQLException {
    ResultSet rs =
        statement.executeQuery(
            String.format(
                "SELECT COUNT(*) AS COUNT FROM EMPLOYEES WHERE USERNAME = '%s', PASSWORD = '%s'",
                username, password));
    rs.next();
    return (rs.getInt("COUNT") == 1);
  }
}
