package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CleaningRequest extends Request {

  String itemID;

  public CleaningRequest(
      Integer requestID,
      Integer emergency,
      String nodeID,
      Integer employeeID,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp) {

    this.requestID = requestID;
    this.emergency = emergency;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.status = RequestStatus.InQueue;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public CleaningRequest(
      Integer requestID,
      Integer emergency,
      String nodeID,
      Integer employeeID,
      Integer status,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp) {

    this.requestID = requestID;
    this.emergency = emergency;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.status = RequestStatus.getRequestStatus(status);
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public CleaningRequest(Integer requestID, ArrayList<String> fields) {
    this.requestID = requestID;
    this.itemID = fields.get(0);
    this.nodeID = fields.get(1);
    this.employeeID = Integer.parseInt(fields.get(2));

    try {
      this.emergency = Integer.parseInt(fields.get(3));
    } catch (NumberFormatException e) {
      this.emergency = 0;
    }

    try {
      this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(4)));
    } catch (NumberFormatException e) {
      this.status = RequestStatus.InQueue;
    }

    this.createdTimestamp = new Timestamp(System.currentTimeMillis());
    this.updatedTimestamp = new Timestamp(System.currentTimeMillis());
  }

  public CleaningRequest(ArrayList<String> fields) {
    try {
      this.requestID = Integer.parseInt(fields.get(0));
    } catch (NumberFormatException e) {
      this.requestID = null;
    }

    this.itemID = fields.get(1);
    this.nodeID = fields.get(2);
    this.employeeID = Integer.parseInt(fields.get(3));

    try {
      this.emergency = Integer.parseInt(fields.get(4));
    } catch (NumberFormatException e) {
      this.emergency = 0;
    }

    try {
      this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(5)));
    } catch (NumberFormatException e) {
      this.status = RequestStatus.Cancelled;
    }

    this.createdTimestamp = Timestamp.valueOf(fields.get(6));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(7));
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%d,%d,%d,%s,%s",
        this.requestID,
        this.itemID,
        this.nodeID,
        this.employeeID,
        this.emergency,
        this.status.getValue(),
        this.createdTimestamp.toString(),
        this.updatedTimestamp.toString());
  }

  @Override
  public String toValuesString() {

    return String.format(
        "%d, '%s', '%s', %d, %d, %d, '%s', '%s'",
        this.requestID,
        this.itemID,
        this.nodeID,
        this.employeeID,
        this.emergency,
        this.status.getValue(),
        this.createdTimestamp.toString(),
        this.updatedTimestamp.toString());
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.CleaningRequest;
  }

}
