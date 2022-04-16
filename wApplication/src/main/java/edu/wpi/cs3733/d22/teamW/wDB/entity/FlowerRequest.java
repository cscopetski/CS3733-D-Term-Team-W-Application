package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoFlower;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.enums.*;

import java.sql.Timestamp;
import java.util.ArrayList;

public class FlowerRequest extends Request{

    Flower flower;
    String patientFirst;
    String patientLast;

    public FlowerRequest(
            Integer requestID,
            String patientLast,
            String patientFirst,
            String flower,
            String nodeID,
            Integer employeeID,
            Integer emergency,
            RequestStatus status,
            Timestamp createdTimestamp,
            Timestamp updatedTimestamp) throws NoFlower {
        this.requestID = requestID;
        this.patientLast = patientLast;
        this.patientFirst = patientFirst;
        this.flower = Flower.getFlower(flower);
        this.nodeID = nodeID;
        this.employeeID = employeeID;
        this.emergency = emergency;
        this.status = status;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }

    public FlowerRequest(ArrayList<String> fields) throws NoFlower, StatusError {
        this.requestID = Integer.parseInt(fields.get(0));
        this.patientLast = fields.get(1);
        this.patientFirst = fields.get(2);
        this.flower = Flower.getFlower(fields.get(3));
        this.nodeID = fields.get(6);
        this.employeeID = Integer.parseInt(fields.get(8));
        this.emergency = Integer.parseInt(fields.get(9));
        this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(10)));
        this.createdTimestamp = Timestamp.valueOf(fields.get(11));
        this.updatedTimestamp = Timestamp.valueOf(fields.get(12));
    }


    @Override
    public String toCSVString() {
        return String.format(
                "%d,%s,%s,%s,%s,%d,%d,%d,%s,%s",
                requestID,
                patientLast,
                patientFirst,
                flower.getString(),
                nodeID,
                employeeID,
                emergency,
                status.getValue(),
                createdTimestamp.toString(),
                updatedTimestamp.toString());
    }

    @Override
    public String toValuesString() {
        return String.format(
                "%d, '%s', '%s', '%s', '%s', %d, %d, %d, '%s', '%s'",
                requestID,
                patientLast,
                patientFirst,
                flower.getString(),
                nodeID,
                employeeID,
                emergency,
                status.getValue(),
                createdTimestamp.toString(),
                updatedTimestamp.toString());
    }

    @Override
    public RequestType getRequestType()  {
        return RequestType.FlowerRequest;
    }
}
