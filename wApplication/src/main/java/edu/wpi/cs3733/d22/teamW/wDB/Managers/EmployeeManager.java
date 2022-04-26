package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.EmployeeDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.LanguageInterpreter;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class EmployeeManager {
  private EmployeeDao ed;

  private final Integer deletedEmployee = -1;

  private static EmployeeManager employeeManager = new EmployeeManager();

  public static EmployeeManager getEmployeeManager() {
    return employeeManager;
  }

  private EmployeeManager() {}

  private TreeSet<Integer> empIDList= new TreeSet<>();

  public void resetEmpIDSet() {
    this.empIDList = new TreeSet<>();
  }

  // fields is every field except for request id and itemID

  public Set<Integer> getEmpIDList() {
    return empIDList;
  }

  public Integer getNewEmpID(){
    return empIDList.last();
  }

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
  public void addEmployee(Employee employee) throws Exception {
    if(empIDList.add(employee.getEmployeeID())){
      ed.addEmployee(employee);
    }else{
      throw new Exception("Employee ID already exists in database");
    }

  }

  public void deleteEmployee(Integer employeeID) throws Exception {

    empIDList.remove(employeeID);

    CleaningRequestManager.getCleaningRequestManager().updateReqWithEmployee(employeeID);
    LabServiceRequestManager.getLabServiceRequestManager().updateReqWithEmployee(employeeID);
    MedEquipRequestManager.getMedEquipRequestManager().updateReqWithEmployee(employeeID);
    MedRequestManager.getMedRequestManager().updateReqWithEmployee(employeeID);
    SanitationRequestManager.getSanitationRequestManager().updateReqWithEmployee(employeeID);
    ExternalTransportManager.getRequestManager().updateReqWithEmployee(employeeID);
    InternalPatientTransportationRequestManager.getInternalPatientTransportationRequestManager().updateReqWithEmployee(employeeID);
    ComputerServiceRequestManager.getComputerServiceRequestManager().updateReqWithEmployee(employeeID);
    GiftDeliveryRequestManager.getGiftDeliveryRequestManager().updateReqWithEmployee(employeeID);
    FlowerRequestManager.getFlowerRequestManager().updateReqWithEmployee(employeeID);
    SecurityRequestManager.getSecurityRequestManager().updateReqWithEmployee(employeeID);
    MealRequestManager.getMealRequestManager().updateReqWithEmployee(employeeID);
    LanguageRequestManager.getLanguageRequestManager().updateReqWithEmployee(employeeID);
    LanguageInterpreterManager.getLanguageInterpreterManager().updateLanguageRequestWithEmployee(employeeID);
    HighScoreManager.getHighScoreManager().updateHighScoreWithEmployee(employeeID);
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

  public Employee getEmployeeFromName(String lastName, String firstName) throws  SQLException{
    return ed.getEmployeeFromName(lastName, firstName);
  }

  public ArrayList<Employee> getEmployeeListByType(ArrayList<EmployeeType> employeeTypes) throws SQLException{
    return ed.getEmployeeListByType(employeeTypes);
  }

  public Employee getEmployeeByType(EmployeeType employeeType) {
    return ed.getEmployeeType(employeeType);
  }

  public void exportEmpCSV(String filename) {
    ed.exportEmpCSV(filename);
  }
}
