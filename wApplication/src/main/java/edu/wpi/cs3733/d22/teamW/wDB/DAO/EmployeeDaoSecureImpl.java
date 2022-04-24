package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class EmployeeDaoSecureImpl implements EmployeeDao {

  Statement statement;

  EmployeeDaoSecureImpl(Statement statement) throws SQLException {
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

  String CSVHeaderString =
      "employeeID,firstName,lastName,employeeType,email,phoneNumber,address,username,password,salt";

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
  public Employee getEmployeeType(EmployeeType employeeType) {
    Employee employee = null;
    try {
      ResultSet employeeRequest =
          statement.executeQuery(
              String.format(
                  "SELECT * FROM EMPLOYEES WHERE EMPLOYEETYPE='%s'", employeeType.getString()));
      employeeRequest.next();

      ArrayList<String> employeeFields = new ArrayList<String>();
      for (int i = 0; i < employeeRequest.getMetaData().getColumnCount(); i++) {
        employeeFields.add(employeeRequest.getString(i + 1));
      }
      employee = new Employee(employeeFields);
    } catch (SQLException e) {
      System.out.println("Query from medical equip request table failed.");
    }
    return employee;
  }

  @Override
  public ArrayList<Employee> getAllEmployees() throws SQLException {
    ArrayList<Employee> employeeList = new ArrayList<Employee>();

    try {
      ResultSet employees =
          statement.executeQuery("SELECT * FROM EMPLOYEES WHERE EMPLOYEEID <> -1");

      while (employees.next()) {
        ArrayList<String> employeeData = new ArrayList<String>();

        for (int i = 0; i < employees.getMetaData().getColumnCount(); i++) {
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
  public ArrayList<Employee> getEmployeeListByType(ArrayList<EmployeeType> employeeTypes) throws SQLException {
    ArrayList<Employee> employeeList = new ArrayList<Employee>();

    String query = "SELECT * FROM EMPLOYEES WHERE ";

    for(int a = 0; a < employeeTypes.size(); a++){
      if(a < employeeTypes.size()-1){
        query += String.format("EMPLOYEETYPE = '%s' OR ", employeeTypes.get(a).getString());
      }
      else query += String.format("EMPLOYEETYPE = '%s'", employeeTypes.get(a).getString());
    }
    try {
      ResultSet employees =
              statement.executeQuery(query);

      while (employees.next()) {
        ArrayList<String> employeeData = new ArrayList<String>();

        for (int i = 0; i < employees.getMetaData().getColumnCount(); i++) {
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


  /**
   * If the salt is the string 'NEW', the salt will be randomly generated and the password will get
   * hashed Otherwise the password and salt are just added normally
   *
   * @param emp Employee object to add
   * @throws SQLException
   */
  @Override
  public void addEmployee(Employee emp) throws SQLException {
    if (emp.getSalt().equals("NEW")) {
      emp.setSalt(generateSalt());
      try {
        emp.setPassword(generateHash(emp.getPassword(), emp.getSalt()));
      } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        e.printStackTrace();
      }
    }

    statement.executeUpdate(
        String.format("INSERT INTO EMPLOYEES VALUES (%s)", emp.toValuesString()));

    // System.out.println(String.format("INSERT INTO EMPLOYEES VALUES (%s)", emp.toValuesString()));
  }

  @Override
  public void deleteEmployee(Integer empID) throws SQLException {
    statement.executeUpdate(String.format("DELETE FROM EMPLOYEES WHERE EMPLOYEEID=%d", empID));
  }

  /**
   * Does not change the username or salt
   *
   * @param emp Employee object to change
   * @throws SQLException
   */
  public void changeEmployee(Employee emp) throws SQLException {
    try {
      String newPass = generateHash(emp.getPassword(), getSalt(emp.getEmployeeID()));
      statement.executeUpdate(
          String.format(
              "UPDATE EMPLOYEES SET FIRSTNAME = '%s', LASTNAME = '%s', EMPLOYEETYPE = '%s', EMAIL = '%s', PHONENUMBER = '%s', ADDRESS = '%s', USERNAME = '%s', PASSWORD = '%s' WHERE EMPLOYEEID = %d",
              emp.getFirstName(),
              emp.getLastName(),
              emp.getType().getString(),
              emp.getEmail(),
              emp.getPhoneNumber(),
              emp.getAddress(),
              emp.getUsername(),
              newPass,
              emp.getEmployeeID()));
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void exportEmpCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

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
    try {
      Integer empID = getIDFromUsername(username);
      if (empID == null) return false;
      password = generateHash(password, getSalt(empID));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
    }
    ResultSet rs =
        statement.executeQuery(
            String.format(
                "SELECT COUNT(*) AS COUNT FROM EMPLOYEES WHERE USERNAME = '%s' AND PASSWORD = '%s'",
                username, password));
    rs.next();
    return (rs.getInt("COUNT") == 1);
  }

  private Integer getIDFromUsername(String username) {
    try {
      ResultSet rs =
          statement.executeQuery(
              String.format("SELECT EMPLOYEEID FROM EMPLOYEES WHERE USERNAME = '%s'", username));
      rs.next();
      return rs.getInt("EMPLOYEEID");
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Returns the salt of the employee ID
   *
   * @param employeeID
   * @return
   */
  public String getSalt(Integer employeeID) throws SQLException {
    ResultSet rs =
        statement.executeQuery(
            String.format("SELECT SALT FROM EMPLOYEES WHERE EMPLOYEEID = %d", employeeID));
    rs.next();
    return rs.getString("SALT");
  }

  public String generateHash(String input, String salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] saltBytes = salt.getBytes();
    KeySpec spec = new PBEKeySpec(input.toCharArray(), saltBytes, 65536, 128);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = factory.generateSecret(spec).getEncoded();
    StringBuilder sb = new StringBuilder();
    for (byte b : hash) {
      sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }

  public static String generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    StringBuilder sb = new StringBuilder();
    for (byte b : salt) {
      sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }

  @Override
  public Employee getEmployee(String username) throws SQLException {
    ResultSet rs =
        statement.executeQuery(
            String.format("SELECT * FROM EMPLOYEES WHERE USERNAME = '%s'", username));
    rs.next();
    ArrayList<String> employeeFields = new ArrayList<String>();
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
      employeeFields.add(rs.getString(i + 1));
    }
    return new Employee(employeeFields);
  }

  @Override
  public Employee getEmployee(Integer empID) throws SQLException {
    ResultSet rs =
        statement.executeQuery(
            String.format("SELECT * FROM EMPLOYEES WHERE EMPLOYEEID = %d", empID));
    rs.next();
    ArrayList<String> employeeFields = new ArrayList<String>();
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
      employeeFields.add(rs.getString(i + 1));
    }
    return new Employee(employeeFields);
  }
}
