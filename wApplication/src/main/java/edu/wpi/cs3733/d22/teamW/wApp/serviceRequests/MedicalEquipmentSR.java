package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

public class MedicalEquipmentSR extends SR {
<<<<<<< HEAD
=======
  //  MedEquipRequest mer;

>>>>>>> Merged
  public MedicalEquipmentSR(Request r) {
    super(r);
    //    this.mer = (MedEquipRequest) REQUEST;
  }

  public String getRequestType() {
    return "Medical Equipment";
  }

  /*
  public Integer getEmployeeID() {
    return REQUEST.getEmployeeID();
  }

  @Override
  public String getFormattedInfo() {
    String info = "";
    if (this.getEmergency() == 1) {
      info += "Request marked as an EMERGENCY\n";
    }
    info += "Requested by: " + this.getEmployeeName() + "\n";
    info += "";
    return info;
  }

    public void setEmployeeName(String employeeName) {
      mer.setEmployeeName(employeeName);
    }

<<<<<<< HEAD
=======
  public String getStatus() {
    return mer.getStatus().getString();
  }

>>>>>>> Merged
  public void setStatus(int status) {
    mer.setStatus(status);
  }
  */
}
