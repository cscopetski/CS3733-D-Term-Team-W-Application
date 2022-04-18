package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.SanitationRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.SanitationRequest;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;

public class SanitationSR extends SR {

  public SanitationSR(Request r) {
    super(r);
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.SanitationService;
  }

  @Override
  public String getRequestTypeS() {
    return "Sanitation Service";
  }

  @Override
  public String getFormattedInfo() throws Exception {
    SanitationRequest sanitationRequest =
        (SanitationRequest)
            SanitationRequestManager.getSanitationRequestManager().getRequest(this.getRequestID());
    String info = "";
    if (this.getEmergency() == 1) {
      info += "Request marked as an EMERGENCY\n";
    }
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    info += "Type of Incident: " + sanitationRequest.getSanitationReqType().getString() + "\n";
    info += "";
    return info;
  }
}
