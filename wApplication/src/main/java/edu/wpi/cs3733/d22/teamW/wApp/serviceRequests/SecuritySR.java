package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

public class SecuritySR extends SR {

  public SecuritySR(Request r) {
    super(r);
  }

  @Override
  public String getRequestType() {
    return "Security Service";
  }

  @Override
  public String getFormattedInfo() {
    return "";
  }
}
