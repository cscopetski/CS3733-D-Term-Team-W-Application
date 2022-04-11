package edu.wpi.cs3733.d22.teamW.wApp.mapEditor;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Requests {
  private SimpleStringProperty Status = new SimpleStringProperty();
  private SimpleIntegerProperty EmployeeID = new SimpleIntegerProperty();
  private SimpleIntegerProperty Emergency = new SimpleIntegerProperty();
  private SimpleIntegerProperty RequestID = new SimpleIntegerProperty();

  public Requests(String status, Integer empID, Integer emergency, Integer reqID) {
    this.Status.set(status);
    this.EmployeeID.set(empID);
    this.Emergency.set(emergency);
    this.RequestID.set(reqID);
  }

  public int getEmployeeID() {
    return EmployeeID.get();
  }

  public int getRequestID() {
    return RequestID.get();
  }

  public void setRequestID(int requestID) {
    this.RequestID.set(requestID);
  }

  public int getEmergency() {
    return Emergency.get();
  }

  public void setEmergency(int emergency) {
    this.Emergency.set(emergency);
  }

  public void setEmployeeID(int employeeID) {
    this.EmployeeID.set(employeeID);
  }

  public String getStatus() {
    return Status.get();
  }

  public void setStatus(String status) {
    this.Status.set(status);
  }
}
