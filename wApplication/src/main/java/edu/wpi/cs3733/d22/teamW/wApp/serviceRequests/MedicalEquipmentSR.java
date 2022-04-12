package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

public class MedicalEquipmentSR extends SR {
  //  MedEquipRequest mer;

  public MedicalEquipmentSR(Request r) {
    super(r);
    //    this.mer = (MedEquipRequest) REQUEST;
  }

  public String getRequestType() {
    return "Medical Equipment";
  }

  @Override
  public String getFormattedInfo() {
    return null;
  }

  /*
  public Integer getEmployeeID() {
    return mer.getEmployeeID();
  }

    public void setEmployeeName(String employeeName) {
      mer.setEmployeeName(employeeName);
    }

  public String getStatus() {
    return mer.getStatus().getString();
  }

  public void setStatus(int status) {
    mer.setStatus(status);
  }
  */
}
