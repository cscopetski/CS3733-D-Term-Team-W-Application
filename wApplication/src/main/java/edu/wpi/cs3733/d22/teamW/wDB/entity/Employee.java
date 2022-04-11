package edu.wpi.cs3733.d22.teamW.wDB.entity;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee extends Entity {

  private Integer employeeID;
  private String firstName;
  private String lastName;
  private String type;
  private String email;
  private String phoneNumber;
  private String address;
  private String username;
  private String password;
  private String salt;

  public Employee(
      Integer employeeID,
      String fName,
      String lName,
      String type,
      String email,
      String phoneNumber,
      String address,
      String username,
      String password,
      String salt) {
    this.employeeID = employeeID;
    this.firstName = fName;
    this.lastName = lName;
    this.type = type;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.username = username;
    this.password = password;
    this.salt = salt;
  }

  public Employee(ArrayList<String> employeeData) {
    this.employeeID = Integer.parseInt(employeeData.get(0));
    this.firstName = employeeData.get(1);
    this.lastName = employeeData.get(2);
    this.type = employeeData.get(3);
    this.email = employeeData.get(4);
    this.phoneNumber = employeeData.get(5);
    this.address = employeeData.get(6);
    this.username = employeeData.get(7);
    this.password = employeeData.get(8);
    this.salt = employeeData.get(9);
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%s,%s,%s,%s,%s,%s,%s",
        employeeID,
        firstName,
        lastName,
        type,
        email,
        phoneNumber,
        address,
        username,
        password,
        salt);
  }

  @Override
  public String toValuesString() {
    return String.format(
        "%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'",
        employeeID,
        firstName,
        lastName,
        type,
        email,
        phoneNumber,
        address,
        username,
        password,
        salt);
  }
}
