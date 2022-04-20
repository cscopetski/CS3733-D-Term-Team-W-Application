package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.LabServiceRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.LabServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;

public class LabServiceSR extends SR {

  public LabServiceSR(Request r) {
    super(r);
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.LabServiceRequest;
  }

  public String getRequestTypeS() {
    return "Lab Service";
  }

  @Override
  public String getFormattedInfo() throws SQLException {
    LabServiceRequest r =
        (LabServiceRequest)
            LabServiceRequestManager.getLabServiceRequestManager().getRequest(this.getRequestID());
    String info = "";
    if (this.getEmergency() == 1) {
      info += "Request marked as an EMERGENCY\n";
    }
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    info += "Service requested: " + r.getLabType().getString() + "\n";
    info += "Patient Name: " + r.getPatientFirst() + " " + r.getPatientLast() + "\n";
    info +=
        "Location: "
            + LocationManager.getLocationManager().getLocation(r.getNodeID()).getLongName()
            + "\n";

    return info;
  }
}
