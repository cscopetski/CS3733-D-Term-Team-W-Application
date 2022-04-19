package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingLabServiceRequestType;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.enums.LabServiceRequestType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabServiceRequest extends Request {

  private LabServiceRequestType labType;
  String patientFirst;
  String patientLast;

  public LabServiceRequest(
      Integer requestID,
      String patientLast,
      String patientFirst,
      LabServiceRequestType labType,
      String nodeID,
      Integer employeeID,
      Integer emergency,
      RequestStatus status,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp) {
    this.requestID = requestID;
    this.patientLast = patientLast;
    this.patientFirst = patientFirst;
    this.labType = labType;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public LabServiceRequest(ArrayList<String> fields)
      throws StatusError, NonExistingLabServiceRequestType {
    this.requestID = Integer.parseInt(fields.get(0));
    this.patientLast = fields.get(1);
    this.patientFirst = fields.get(2);
    this.labType = LabServiceRequestType.getLabServiceRequestType(fields.get(3));
    this.nodeID = fields.get(4);
    this.employeeID = Integer.parseInt(fields.get(5));
    this.emergency = Integer.parseInt(fields.get(6));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(7)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(8));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(9));
  }

  public LabServiceRequest(Integer index, ArrayList<String> fields)
      throws StatusError, NonExistingLabServiceRequestType {
    this.requestID = index;
    this.patientLast = fields.get(0);
    this.patientFirst = fields.get(1);
    this.labType = LabServiceRequestType.getLabServiceRequestType(fields.get(2));
    this.nodeID = fields.get(3);
    this.employeeID = Integer.parseInt(fields.get(4));
    this.emergency = Integer.parseInt(fields.get(5));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(6)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(7));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(8));
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.LabServiceRequest;
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%s,%s,%d,%d,%d,%s,%s",
        requestID,
        patientLast,
        patientFirst,
        labType.getString(),
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
        "%d, '%s','%s','%s',  '%s', %d, %d, %d, '%s', '%s'",
        requestID,
        patientLast,
        patientFirst,
        labType.getString(),
        nodeID,
        employeeID,
        emergency,
        status.getValue(),
        this.createdTimestamp.toString(),
        this.updatedTimestamp.toString());
  }
}
