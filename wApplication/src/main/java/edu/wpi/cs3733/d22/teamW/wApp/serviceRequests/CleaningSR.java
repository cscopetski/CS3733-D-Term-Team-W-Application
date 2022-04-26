package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.CleaningRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.SanitationRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.CleaningRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.SanitationRequest;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;

public class CleaningSR extends SR {

  public CleaningSR(Request r) {
    super(r);
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.CleaningRequest;
  }

  public String getRequestTypeS() {
    return "Cleaning";
  }

  @Override
  public String getFormattedInfo() throws SQLException, StatusError {
    CleaningRequest cleaningRequest =
            (CleaningRequest)
                    CleaningRequestManager.getCleaningRequestManager().getRequest(this.getRequestID());
    String itemID =
        CleaningRequestManager.getCleaningRequestManager()
            .getRequest(this.getRequestID())
            .getItemID();

    String info = "Automated cleaning request for equipment.\n";
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    info +=
            "Location: "
                    + LocationManager.getLocationManager().getLocation(cleaningRequest.getNodeID()).getLongName()
                    + "\n";
//    info += "Location: " + MedEquipManager.getMedEquipManager().getMedEquip(itemID).getNodeID() + "\n";
    info +=
        "Equipment type: "
            + MedEquipManager.getMedEquipManager().getMedEquip(itemID).getType().getString()
            + "\n";
    info += "Equipment ID: " + itemID + "\n";
    return info;
  }
}
