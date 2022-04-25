package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.ExternalTransportManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.FlowerRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ExternalTransportRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.FlowerRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;

import java.sql.SQLException;

public class ExternalTransporationSR extends SR{
    public ExternalTransporationSR(Request r) {
        super(r);
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.ExternalTransportRequest;
    }

    @Override
    public String getRequestTypeS() {
        return "External Patient Transport Service";
    }

    @Override
    public String getFormattedInfo() throws SQLException {
        ExternalTransportRequest externalTransportRequest = null;
        try {
            externalTransportRequest=
                    (ExternalTransportRequest)
                            ExternalTransportManager.getRequestManager().getRequest(this.getRequestID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String info = "";
        if (this.getEmergency() == 1) {
            info += "Request marked as an EMERGENCY\n";
        }
        info += "Assigned Employee: " + this.getEmployeeName() + "\n";
        info += "Employee ID: " + this.getEmployeeID() + "\n";
        info += "Transport Type: " + externalTransportRequest.getTransportType().getString() + "\n";
        info +=
                "Patient: "
                        + externalTransportRequest.getPatientID() + "\n";
        info += "Departure Date: "
                + externalTransportRequest.getDepartureDate().toString();
        info += "";
        return info;
    }
}
