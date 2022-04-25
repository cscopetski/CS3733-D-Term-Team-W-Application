package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.ExternalTransportManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.InternalPatientTransportationRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ExternalTransportRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.InternalPatientTransportationRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;

import java.sql.SQLException;

public class InternalTransportSR extends SR{

    public InternalTransportSR(Request r) {
        super(r);
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.InternalPatientTransportationRequest;
    }

    @Override
    public String getRequestTypeS() {
        return "Internal Patient Transport Service";
    }

    @Override
    public String getFormattedInfo() throws SQLException {
        InternalPatientTransportationRequest internalPatientTransportationRequest = null;
        try {
            internalPatientTransportationRequest = (InternalPatientTransportationRequest)
                            InternalPatientTransportationRequestManager.getInternalPatientTransportationRequestManager().getRequest(this.getRequestID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String info = "";
        if (this.getEmergency() == 1) {
            info += "Request marked as an EMERGENCY\n";
        }
        info += "Assigned Employee: " + this.getEmployeeName() + "\n";
        info += "Employee ID: " + this.getEmployeeID() + "\n";
        info += "Destination: " + internalPatientTransportationRequest.getNodeIDTo();
        info += "";
        return info;
    }
}

