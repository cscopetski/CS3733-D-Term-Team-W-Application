package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabServiceRequest extends Request {

  private String labType;

  public LabServiceRequest(
      Integer requestID,
      String labType,
      String nodeID,
      Integer employeeID,
      Integer emergency,
      RequestStatus status) {
    this.requestID = requestID;
    this.labType = labType;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
  }

  public LabServiceRequest(ArrayList<String> fields) {
    this.requestID = Integer.parseInt(fields.get(0));
    this.labType = fields.get(1);
    this.nodeID = fields.get(2);
    this.employeeID = Integer.parseInt(fields.get(3));
    this.emergency = Integer.parseInt(fields.get(4));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(5)));
  }

  public LabServiceRequest(Integer index, ArrayList<String> fields) {
    this.requestID = index;
    this.labType = fields.get(0);
    this.nodeID = fields.get(1);
    this.employeeID = Integer.parseInt(fields.get(2));
    this.emergency = Integer.parseInt(fields.get(3));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(4)));
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.LabServiceRequest;
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%d,%d,%d", requestID, labType, nodeID, employeeID, emergency, status.getValue());
  }

  @Override
  public String toValuesString() {
    return String.format(
        "%d, '%s',  '%s', %d, %d, %d",
        requestID, labType, nodeID, employeeID, emergency, status.getValue());
  }
}
