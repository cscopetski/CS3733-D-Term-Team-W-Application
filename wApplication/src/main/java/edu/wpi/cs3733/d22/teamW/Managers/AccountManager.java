package edu.wpi.cs3733.d22.teamW.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;

public class AccountManager {
  private Employee employee;

  private static class Instance {
    public static final AccountManager instance = new AccountManager();
  }

  private AccountManager() {}

  public static AccountManager getInstance() {
    return Instance.instance;
  }

  public void initialize(Employee e) {
    this.employee = e;
  }
  public void reset() {
    initialize(null);
  }

  public Employee getEmployee() {
    return employee;
  }
}
