package edu.wpi.cs3733.d22.teamW.wApp.controllers.Employee;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;

public class EMPLOYEE {
  protected final Employee employee;

  public EMPLOYEE(Employee e) {
    super();
    this.employee = e;
  }

  public Integer getID() {
    return employee.getEmployeeID();
  }

  public String getFirstName() {
    return employee.getFirstName();
  }

  public String getLastName() {
    return employee.getLastName();
  }

  public String getAddress() {
    return employee.getAddress();
  }

  public String getEmail() {
    return employee.getEmail();
  }

  public String getType() {
    return employee.getType().toString();
  }
}
