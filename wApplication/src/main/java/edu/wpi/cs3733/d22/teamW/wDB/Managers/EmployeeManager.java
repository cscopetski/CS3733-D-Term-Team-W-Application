package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.EmployeeDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeManager {
  private EmployeeDao ed;

  private final Integer deletedEmployee = -1;

  private static EmployeeManager employeeManager = new EmployeeManager();

  public static EmployeeManager getEmployeeManager() {
    return employeeManager;
  }

  private EmployeeManager() {}

  public Integer getDeletedEmployee() {
    return this.deletedEmployee;
  }

  public void setEmployeeDao(EmployeeDao ed) {
    this.ed = ed;
  }

  public boolean usernameExists(String s) throws SQLException {
    return (ed.countUsernameOccurences(s) > 0);
  }

  public boolean passwordMatch(String username, String password) throws SQLException {
    return ed.passwordMatch(username, password);
  }

  /** Adds an employee to the database. */
  public void addEmployee(Employee employee) throws SQLException {
    ed.addEmployee(employee);
  }

  public void deleteEmployee(Integer employeeID) throws Exception {

    CleaningRequestManager.getCleaningRequestManager().updateReqWithEmployee(employeeID);
    LabServiceRequestManager.getLabServiceRequestManager().updateReqWithEmployee(employeeID);
    MedEquipRequestManager.getMedEquipRequestManager().updateReqWithEmployee(employeeID);
    MedRequestManager.getMedRequestManager().updateReqWithEmployee(employeeID);
    SanitationRequestManager.getSanitationRequestManager().updateReqWithEmployee(employeeID);
    ed.deleteEmployee(employeeID);
  }

  public void changeEmployee(Employee employee) throws SQLException {
    ed.changeEmployee(employee);
  }

  public ArrayList<Employee> getAllEmployees() throws SQLException {
    return ed.getAllEmployees();
  }

  public Employee getEmployee(String username) throws SQLException {
    return ed.getEmployee(username);
  }

  public Employee getEmployee(Integer empID) throws SQLException {
    return ed.getEmployee(empID);
  }

  public Employee getEmployeeType(EmployeeType employeeType) {
    return ed.getEmployeeType(employeeType);
  }

  public void exportEmpCSV(String filename) {
    ed.exportEmpCSV(filename);
  }
}
