package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;

@Getter
@Setter
public class InternalPatientTransportationRequest extends Request{

    String nodeIDTo;

    public InternalPatientTransportationRequest(ArrayList<String> fields) throws StatusError {
        this.requestID = Integer.parseInt(fields.get(0));
        this.nodeID = fields.get(1);
        this.nodeIDTo = fields.get(2);
        this.employeeID = Integer.parseInt(fields.get(3));
        this.emergency = Integer.parseInt(fields.get(4));
        this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(5)));
        this.createdTimestamp = Timestamp.valueOf(fields.get(6));
        this.updatedTimestamp = Timestamp.valueOf(fields.get(7));
    }

    public InternalPatientTransportationRequest(Integer requestID, ArrayList<String> fields) throws StatusError {
        this.requestID = requestID;
        this.nodeID = fields.get(0);
        this.nodeIDTo = fields.get(1);
        this.employeeID = Integer.parseInt(fields.get(2));
        this.emergency = Integer.parseInt(fields.get(3));
        this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(4)));
        this.createdTimestamp = new Timestamp(System.currentTimeMillis());
        this.updatedTimestamp = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toCSVString() {
        return String.format("%d,%s,%s,%d,%d,%s,%s,%s",
                requestID,
                nodeID,
                nodeIDTo,
                employeeID,
                emergency,
                status.getString(),
                createdTimestamp,
                updatedTimestamp);
    }

    @Override
    public String toValuesString() {
        return String.format("%d, '%s', '%s', %d, %d, '%s', '%s', '%s'",
                requestID,
                nodeID,
                nodeIDTo,
                employeeID,
                emergency,
                status.getString(),
                createdTimestamp,
                updatedTimestamp);
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.InternalPatientTransportationRequest;
    }
}
