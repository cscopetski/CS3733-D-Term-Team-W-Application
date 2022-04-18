package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;

public class Account {
  private Employee employee;

  private static class Instance {
    public static final Account instance = new Account();
  }

  private Account() {}

  public static Account getInstance() {
    return Instance.instance;
  }

  public void setEmployee(Employee e) {
    this.employee = e;
  }

  public Employee getEmployee() {
    return employee;
  }
}
