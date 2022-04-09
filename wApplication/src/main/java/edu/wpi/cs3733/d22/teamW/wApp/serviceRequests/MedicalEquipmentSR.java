package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.MedEquipRequest;

public class MedicalEquipmentSR {
  MedEquipRequest mer;

  public MedicalEquipmentSR(MedEquipRequest mer) {
    this.mer = mer;
  }

  public Integer getRequestID() {
    return mer.getRequestID();
  }

  /*public void setRequestID(Integer requestID) {
    mer.setRequestID(requestID);
  }*/

  public String getRequestType() {
    return "Medical Equipment";
  }

  public String getEmployeeName() {
    return mer.getEmployeeName();
  }
  /*
    public void setEmployeeName(String employeeName) {
      mer.setEmployeeName(employeeName);
    }
  */
  public int getStatus() {
    return mer.getStatusInt();
  }
  /*
  public void setStatus(int status) {
    mer.setStatus(status);
  }
  */
}
