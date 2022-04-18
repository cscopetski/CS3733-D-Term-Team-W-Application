package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.SecurityRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.SecurityRequest;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;

public class SecuritySR extends SR {

  public SecuritySR(Request r) {
    super(r);
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.SecurityService;
  }

  @Override
  public String getRequestTypeS() {
    return "Security Service";
  }

  @Override
  public String getFormattedInfo() throws Exception {
    SecurityRequest securityRequest =
        (SecurityRequest)
            SecurityRequestManager.getSecurityRequestManager().getRequest(this.getRequestID());
    String info = "";
    if (this.getEmergency() == 1) {
      info += "Request marked as an EMERGENCY\n";
    }
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    info += "Threat Level: " + securityRequest.getThreatLevel() + "\n";
    info += "";
    return info;
  }
}
