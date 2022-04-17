package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityRequest extends Request {

  Integer threatLevel;

  public SecurityRequest(ArrayList<String> fields) throws StatusError {
    this.requestID = Integer.parseInt(fields.get(0));
    this.nodeID = fields.get(1);
    this.employeeID = Integer.parseInt(fields.get(2));
    this.emergency = Integer.parseInt(fields.get(3));
    this.threatLevel = Integer.parseInt(fields.get(4));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(5)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(6));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(7));
  }

  public SecurityRequest(Integer requestID, ArrayList<String> fields) throws StatusError {
    this.requestID = requestID;
    this.nodeID = fields.get(0);
    this.employeeID = Integer.parseInt(fields.get(1));
    this.emergency = Integer.parseInt(fields.get(2));
    this.threatLevel = Integer.parseInt(fields.get(3));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(4)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(5));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(6));
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%d,%d,%d,%d,%s,%s",
        requestID,
        nodeID,
        employeeID,
        emergency,
        threatLevel,
        status.getValue(),
        createdTimestamp.toString(),
        updatedTimestamp.toString());
  }

  @Override
  public String toValuesString() {
    return String.format(
        "%d, '%s', %d, %d, %d, %d, '%s', '%s'",
        requestID,
        nodeID,
        employeeID,
        emergency,
        threatLevel,
        status.getValue(),
        createdTimestamp.toString(),
        updatedTimestamp.toString());
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.SecurityService;
  }
}
