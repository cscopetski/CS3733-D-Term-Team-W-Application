package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

public class MedicalEquipmentSR extends SR {
  public MedicalEquipmentSR(Request r) {
    super(r);
  }

  public String getRequestType() {
    return "Medical Equipment";
  }

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
  /*
    public void setEmployeeName(String employeeName) {
      mer.setEmployeeName(employeeName);
    }

  public void setStatus(int status) {
    mer.setStatus(status);
  }
  */
}
