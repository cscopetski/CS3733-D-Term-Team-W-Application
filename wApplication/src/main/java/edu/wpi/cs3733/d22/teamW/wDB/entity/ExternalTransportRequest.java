package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoTransport;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.TransportType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class ExternalTransportRequest extends Request{

    private String destinationLocation;
    private String patientID;
    private LocalDate departureDate;
    private TransportType transportType;

    public ExternalTransportRequest(
            Integer requestID,
            String nodeID,
            Integer employeeID,
            Integer emergency,
            RequestStatus status,
            Timestamp createdTimestamp,
            Timestamp updatedTimestamp) {
        this.requestID = requestID;
        this.nodeID = nodeID;
        this.employeeID = employeeID;
        this.emergency = emergency;
        this.status = status;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }

    public ExternalTransportRequest(ArrayList<String> fields) throws StatusError, NoTransport {
        this.requestID = Integer.parseInt(fields.get(0));
        this.nodeID = fields.get(1);
        this.patientID = fields.get(2);
        this.destinationLocation = fields.get(3);
        this.departureDate = (Date.valueOf(fields.get(4)).toLocalDate());
        System.out.println(fields.get(5));
        this.transportType = TransportType.getTransportType(fields.get(5));
        this.employeeID = Integer.parseInt(fields.get(6));
        this.emergency = Integer.parseInt(fields.get(7));
        this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(8)));
        this.createdTimestamp = Timestamp.valueOf(fields.get(9));
        this.updatedTimestamp = Timestamp.valueOf(fields.get(10));
    }

    // requestID, nodeID, employeeID, emergency, status, createdTimestamp, updatedTimestamp
    @Override
    public String toCSVString() {
        return String.format(
                "%d,%s,%s,%s,%s,%s,%d,%d,%d,%s,%s",
                requestID,
                nodeID,
                patientID,
                destinationLocation,
                departureDate.toString(),
                transportType.getString(),
                employeeID,
                emergency,
                status.getValue(),
                createdTimestamp.toString(),
                updatedTimestamp.toString());
    }

    //"ReqID,nodeID,patient,destinationLocation,departureDate,transportType,employeeID,isEmergency,reqStatus,createdTimestamp,updatedTimestamp";

    @Override
    public String toValuesString() {
        return String.format(
                "%d,'%s','%s','%s','%s','%s',%d,%d,%d,'%s','%s'",
                requestID,
                nodeID,
                patientID,
                destinationLocation,
                departureDate.toString(),
                transportType.getString(),
                employeeID,
                emergency,
                status.getValue(),
                createdTimestamp.toString(),
                updatedTimestamp.toString());
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.ExternalTransportRequest;
    }
}
