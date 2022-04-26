package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.ComputerServiceRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MealRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ComputerServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MealRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;

public class ComputerSR extends SR {

  public ComputerSR(Request r) {
    super(r);
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.ComputerServiceRequest;
  }

  @Override
  public String getRequestTypeS() {
    return "Computer Service";
  }

  @Override
  public String getFormattedInfo() throws SQLException, StatusError, NonExistingMedEquip {
    ComputerServiceRequest computerServiceRequest =
            (ComputerServiceRequest) ComputerServiceRequestManager.getComputerServiceRequestManager().getRequest(this.getRequestID());
    String info = "";
    if (this.getEmergency() == 1) {
      info += "Request marked as an EMERGENCY\n";
    }
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    info +=
            "Location: "
                    + LocationManager.getLocationManager().getLocation(computerServiceRequest.getNodeID()).getLongName()
                    + "\n";
    return info;
  }
}
