package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

public class LabServiceSR extends SR {

  public LabServiceSR(Request r) {
    super(r);
  }

  public String getRequestType() {
    return "Lab Service";
  }

  @Override
  public String getFormattedInfo() {
    return null;
  }
}
