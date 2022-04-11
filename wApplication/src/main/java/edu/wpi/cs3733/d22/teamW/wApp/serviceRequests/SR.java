package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

public abstract class SR {
  protected final Request REQUEST;

  public SR(Request r) {
    this.REQUEST = r;
  }

  public Integer getRequestID() {
    return REQUEST.getRequestID();
  }

  // 0 represents no emergency
  // 1 represents an emergency
  public Integer getEmergency() {
    return REQUEST.getEmergency();
  }

  public String getNodeID() {
    return REQUEST.getNodeID();
  }

  public String getStatus() {
    return REQUEST.getStatus();
  }

  public Integer getEmployeeName() {
    return REQUEST.getEmployeeID();
  }

  public abstract String getRequestType();

  // returns a String for the More Info section of the RequestList page
  public abstract String getFormattedInfo();
}
