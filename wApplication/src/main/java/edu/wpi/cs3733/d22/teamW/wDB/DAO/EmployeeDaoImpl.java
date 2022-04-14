package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
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

  void dropTable() {
    try {
      statement.execute("DROP TABLE EMPLOYEES");
      System.out.println("Dropped Employee Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Employee Table");
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
  public Employee getEmployee(String username) throws SQLException {
    ResultSet rs =
        statement.executeQuery(
            String.format("SELECT * FROM EMPLOYEES WHERE USERNAME = '%s'", username));
    rs.next();
    Integer empID = rs.getInt("EMPLOYEEID");
    String firstName = rs.getString("FIRSTNAME");
    String lastName = rs.getString("LASTNAME");
    String employeeType = rs.getString("EMPLOYEETYPE");
    String email = rs.getString("EMAIL");
    String phoneNum = rs.getString("PHONENUMBER");
    String address = rs.getString("ADDRESS");
    String user = rs.getString("USERNAME");
    String pass = rs.getString("PASSWORD");
    String salt = rs.getString("SALT");
    return new Employee(
        empID, firstName, lastName, employeeType, email, phoneNum, address, user, pass, salt);
  }

  @Override
  public Employee getEmployee(Integer empID) throws SQLException {
    ResultSet rs =
        statement.executeQuery(
            String.format("SELECT * FROM EMPLOYEES WHERE EMPLOYEEID = %d", empID));
    rs.next();
    Integer id = rs.getInt("EMPLOYEEID");
    String firstName = rs.getString("FIRSTNAME");
    String lastName = rs.getString("LASTNAME");
    String employeeType = rs.getString("EMPLOYEETYPE");
    String email = rs.getString("EMAIL");
    String phoneNum = rs.getString("PHONENUMBER");
    String address = rs.getString("ADDRESS");
    String user = rs.getString("USERNAME");
    String pass = rs.getString("PASSWORD");
    String salt = rs.getString("SALT");
    return new Employee(
        id, firstName, lastName, employeeType, email, phoneNum, address, user, pass, salt);
  }

  @Override
  public void addEmployee(
      Integer employeeID,
      String firstname,
      String lastname,
      EmployeeType employeeType,
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
            employeeType.toString(),
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
  public Employee getEmployeeType(EmployeeType employeeType) {
    Employee employee = null;
    try {
      ResultSet employeeRequest =
          statement.executeQuery(
              String.format(
                  "SELECT * FROM EMPLOYEES WHERE EMPLOYEETYPE='%s'", employeeType.toString()));
      employeeRequest.next();
      Integer employeeID = employeeRequest.getInt("EMPLOYEEID");
      String firstName = employeeRequest.getString("FIRSTNAME");
      String lastName = employeeRequest.getString("LASTNAME");
      String employeeTypeString = employeeRequest.getString("EMPLOYEETYPE");
      String email = employeeRequest.getString("EMAIL");
      String phoneNumber = employeeRequest.getString("PHONENUMBER");
      String address = employeeRequest.getString("ADDRESS");
      String username = employeeRequest.getString("USERNAME");
      String password = employeeRequest.getString("PASSWORD");
      String salt = employeeRequest.getString("SALT");

      ArrayList<String> employeeData = new ArrayList<>();
      employeeData.add(String.format("%d", employeeID));
      employeeData.add(firstName);
      employeeData.add(lastName);
      employeeData.add(employeeTypeString);
      employeeData.add(email);
      employeeData.add(phoneNumber);
      employeeData.add(address);
      employeeData.add(username);
      employeeData.add(password);
      employeeData.add(salt);

      employee = new Employee(employeeData);
    } catch (SQLException e) {
      System.out.println("Query from medical equip request table failed.");
    }
    return employee;
  }

  @Override
  public void changeEmployee(
      Integer employeeID,
      String firstname,
      String lastname,
      EmployeeType type,
      String email,
      String phoneNumber,
      String address,
      String username,
      String password)
      throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE EMPLOYEES SET FIRSTNAME = '%s', LASTNAME = '%s', EMPLOYEETYPE = '%s', EMAIL = '%s', PHONENUMBER = '%s', ADDRESS = '%s', USERNAME = '%s', PASSWORD = '%s' WHERE EMPLOYEEID = %d",
            firstname,
            lastname,
            type.getString(),
            email,
            phoneNumber,
            address,
            username,
            password,
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
                "SELECT COUNT(*) AS COUNT FROM EMPLOYEES WHERE USERNAME = '%s' AND PASSWORD = '%s'",
                username, password));
    rs.next();
    return (rs.getInt("COUNT") == 1);
  }
}
