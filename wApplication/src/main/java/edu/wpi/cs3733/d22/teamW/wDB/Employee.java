package edu.wpi.cs3733.d22.teamW.wDB;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee implements Entity {

  private Integer employeeID;
  private String firstName;
  private String lastName;
  private String type;
  private String username;
  private String password;

  public Employee(
      Integer employeeID,
      String fName,
      String lName,
      String type,
      String username,
      String password) {
    this.employeeID = employeeID;
    this.firstName = fName;
    this.lastName = lName;
    this.type = type;
    this.username = username;
    this.password = password;
  }

  public Employee(ArrayList<String> employeeData) {
    this.employeeID = Integer.parseInt(employeeData.get(0));
    this.firstName = employeeData.get(1);
    this.lastName = employeeData.get(2);
    this.type = employeeData.get(3);
    this.username = employeeData.get(4);
    this.password = employeeData.get(5);
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%s,%s,%s", employeeID, firstName, lastName, type, username, password);
  }

  @Override
  public String toValuesString() {
    return String.format(
        "%d, '%s', '%s', '%s', '%s', '%s'",
        employeeID, firstName, lastName, type, username, password);
  }
}
