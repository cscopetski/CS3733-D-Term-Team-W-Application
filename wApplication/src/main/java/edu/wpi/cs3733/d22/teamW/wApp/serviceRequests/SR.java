package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Request;

public abstract class SR {
  private final Request REQUEST;

  public SR(Request r) {
    this.REQUEST = r;
  }

  public Integer getRequestID() {
    return REQUEST.getRequestID();
  }

  public Integer getEmergency() {
    return REQUEST.getEmergency();
  }

  public String getNodeID() {
    return REQUEST.getNodeID();
  }

  public String getStatus() {
    switch (REQUEST.getStatus()) {
      case 0:
        return "Enqueue";
      case 1:
        return "InProgress";
      case 2:
        return "Done";
      case 3:
        return "Cancelled";
      default:
        return "null";
    }
  }

  public String getEmployeeName() {
    return REQUEST.getEmployeeName();
  }

  public abstract String getRequestType();
}
