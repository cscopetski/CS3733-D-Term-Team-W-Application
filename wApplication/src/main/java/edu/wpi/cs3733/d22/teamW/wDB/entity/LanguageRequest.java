package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.Timestamp;
import java.util.ArrayList;

public class LanguageRequest extends Request {

  private String language;

  public LanguageRequest(
      Integer requestID,
      String language,
      String nodeID,
      Integer employeeID,
      Integer emergency,
      RequestStatus status,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp) {
    this.requestID = requestID;
    this.language = language;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public LanguageRequest(ArrayList<String> fields) throws StatusError {
    this.requestID = Integer.parseInt(fields.get(0));
    this.language = fields.get(1);
    this.nodeID = fields.get(2);
    this.employeeID = Integer.parseInt(fields.get(3));
    this.emergency = Integer.parseInt(fields.get(4));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(5)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(6));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(7));
  }

  public LanguageRequest(Integer index, ArrayList<String> fields) throws StatusError {
    this.requestID = index;
    this.language = fields.get(0);
    this.nodeID = fields.get(1);
    this.employeeID = Integer.parseInt(fields.get(2));
    this.emergency = Integer.parseInt(fields.get(3));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(4)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(5));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(6));
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%d,%d,%d,%s,%s",
        requestID,
        language,
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
        "%d, '%s', '%s', %d, %d, %d, '%s', '%s'",
        requestID,
        language,
        nodeID,
        employeeID,
        emergency,
        status.getValue(),
        createdTimestamp.toString(),
        updatedTimestamp.toString());
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.LanguageRequest;
  }
}
