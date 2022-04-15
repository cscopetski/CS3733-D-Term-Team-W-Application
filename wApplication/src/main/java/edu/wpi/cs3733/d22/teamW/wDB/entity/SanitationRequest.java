package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.Sanitation;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SanitationRequest extends Request {

  Sanitation sanitation;

  public SanitationRequest(
      Integer requestID,
      String sanitation,
      String nodeID,
      Integer employeeID,
      Integer emergency,
      RequestStatus status,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp)
      throws Exception {
    this.requestID = requestID;
    this.sanitation = Sanitation.getSanitation(sanitation);
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public SanitationRequest(
      Integer requestID,
      Sanitation sanitation,
      String nodeID,
      Integer employeeID,
      Integer emergency,
      RequestStatus status,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp)
      throws Exception {
    this.requestID = requestID;
    this.sanitation = sanitation;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public SanitationRequest(ArrayList<String> fields) throws Exception {
    this.requestID = Integer.parseInt(fields.get(0));
    this.sanitation = Sanitation.getSanitation(fields.get(1));
    this.nodeID = fields.get(2);
    this.employeeID = Integer.parseInt(fields.get(3));
    this.emergency = Integer.parseInt(fields.get(4));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(5)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(6));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(7));
  }

  public SanitationRequest(Integer index, ArrayList<String> fields) throws Exception {
    this.requestID = index;
    this.sanitation = Sanitation.getSanitation(fields.get(0));
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
        sanitation.getString(),
        nodeID,
        employeeID,
        emergency,
        status.getValue(),
        this.createdTimestamp.toString(),
        this.updatedTimestamp.toString());
  }

  @Override
  public String toValuesString() {
    return String.format(
        "%d, '%s', '%s', %d, %d, %d, '%s', '%s'",
        requestID,
        sanitation.getString(),
        nodeID,
        employeeID,
        emergency,
        status.getValue(),
        createdTimestamp.toString(),
        updatedTimestamp.toString());
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.SanitationService;
  }
}
