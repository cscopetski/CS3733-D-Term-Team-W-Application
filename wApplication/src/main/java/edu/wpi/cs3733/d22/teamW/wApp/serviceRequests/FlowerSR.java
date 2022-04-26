package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.FlowerRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.FlowerRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;

public class FlowerSR extends SR {

  public FlowerSR(Request r) {
    super(r);
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.FlowerRequest;
  }

  @Override
  public String getRequestTypeS() {
    return "Flower Service";
  }

  @Override
  public String getFormattedInfo() throws SQLException {
    FlowerRequest flowerRequest =
        (FlowerRequest)
            FlowerRequestManager.getFlowerRequestManager().getRequest(this.getRequestID());
    String info = "";
    if (this.getEmergency() == 1) {
      info += "Request marked as an EMERGENCY\n";
    }
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    info +=
            "Location: "
                    + LocationManager.getLocationManager().getLocation(flowerRequest.getNodeID()).getLongName()
                    + "\n";
    info += "Flower Type: " + flowerRequest.getFlower().getString() + "\n";
    info +=
        "Recipient Name: "
            + flowerRequest.getPatientFirst()
            + " "
            + flowerRequest.getPatientLast()
            + "\n";
    return info;
  }
}
