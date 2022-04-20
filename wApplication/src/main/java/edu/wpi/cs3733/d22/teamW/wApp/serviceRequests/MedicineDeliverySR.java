package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;

public class MedicineDeliverySR extends SR {

  public MedicineDeliverySR(Request r) {
    super(r);
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.MedicineDelivery;
  }

  @Override
  public String getRequestTypeS() {
    return "Medicine Delivery";
  }

  @Override
  public String getFormattedInfo() throws SQLException {
    MedRequest r =
        (MedRequest) MedRequestManager.getMedRequestManager().getRequest(this.getRequestID());
    String info = "";
    if (this.getEmergency() == 1) {
      info += "Request marked as an EMERGENCY\n";
    }
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    info += "Medicine requested: " + r.getMedicineType().getString() + "\n";
    //    info += "Quantity requested: " + r.getQuantity() + "\n";
    info +=
        "Location: "
            + LocationManager.getLocationManager().getLocation(r.getNodeID()).getLongName()
            + "\n";
    return info;
  }
}
