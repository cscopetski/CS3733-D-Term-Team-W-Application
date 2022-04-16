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
public class GiftDeliveryRequest extends Request {

  String recipientFirstName;
  String recipientLastName;

  public GiftDeliveryRequest(
      Integer requestID,
      String recipientFirstName,
      String recipientLastName,
      String nodeID,
      Integer employeeID,
      Integer emergency,
      RequestStatus status,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp) {
    this.requestID = requestID;
    this.recipientFirstName = recipientFirstName;
    this.recipientLastName = recipientLastName;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public GiftDeliveryRequest(ArrayList<String> fields) throws StatusError {
    this.requestID = Integer.parseInt(fields.get(0));
    this.recipientFirstName = fields.get(1);
    this.recipientLastName = fields.get(2);
    this.nodeID = fields.get(3);
    this.employeeID = Integer.parseInt(fields.get(4));
    this.emergency = Integer.parseInt(fields.get(5));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(6)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(7));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(8));
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%s,%d,%d,%d,%s,%s",
        requestID,
        recipientFirstName,
        recipientLastName,
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
        "%d,'%s','%s','%s',%d,%d,%d,'%s','%s'",
        requestID,
        recipientFirstName,
        recipientLastName,
        nodeID,
        employeeID,
        emergency,
        status.getValue(),
        createdTimestamp.toString(),
        updatedTimestamp.toString());
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.GiftDelivery;
  }
}
