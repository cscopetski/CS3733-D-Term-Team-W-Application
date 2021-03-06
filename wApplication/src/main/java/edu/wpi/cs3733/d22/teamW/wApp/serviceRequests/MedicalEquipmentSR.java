package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquipRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;

public class MedicalEquipmentSR extends SR {
  //  MedEquipRequest mer;

  public MedicalEquipmentSR(Request r) {
    super(r);
    //    this.mer = (MedEquipRequest) REQUEST;
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.MedicalEquipmentRequest;
  }

  public String getRequestTypeS() {
    return "Medical Equipment";
  }

  @Override
  public String getFormattedInfo() throws SQLException, NonExistingMedEquip {
    MedEquipRequest r =
        (MedEquipRequest)
            MedEquipRequestManager.getMedEquipRequestManager().getRequest(this.getRequestID());
    String info = "";
    if (this.getEmergency() == 1) {
      info += "Request marked as an EMERGENCY\n";
    }
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    info +=
            "Location: "
                    + LocationManager.getLocationManager().getLocation(r.getNodeID()).getLongName()
                    + "\n";
    info += "Requested Item: " + r.getItemType().getString() + "\n";
    info += "Requested Item ID: " + r.getItemID() + "\n";
    return info;
  }

  public MedEquipRequest getOriginal() {
    return (MedEquipRequest) REQUEST;
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
