package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoFlower;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.enums.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FlowerRequest extends Request {

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
      Timestamp updatedTimestamp)
      throws NoFlower {
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
    this.flower = Flower.getFlower(fields.get(1));
    this.patientLast = fields.get(2);
    this.patientFirst = fields.get(3);
    this.nodeID = fields.get(4);
    this.employeeID = Integer.parseInt(fields.get(5));
    this.emergency = Integer.parseInt(fields.get(6));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(7)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(8));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(9));
  }

  public FlowerRequest(Integer index, ArrayList<String> fields) throws StatusError, NoFlower {
    this.requestID = index;
    this.flower = Flower.getFlower(fields.get(0));
    this.patientLast = fields.get(1);
    this.patientFirst = fields.get(2);
    this.nodeID = fields.get(3);
    this.employeeID = Integer.parseInt(fields.get(4));
    this.emergency = Integer.parseInt(fields.get(5));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(6)));
    this.createdTimestamp = new Timestamp(System.currentTimeMillis());
    this.updatedTimestamp = new Timestamp(System.currentTimeMillis());
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
        flower.getString(),
        patientLast,
        patientFirst,
        nodeID,
        employeeID,
        emergency,
        status.getValue(),
        createdTimestamp.toString(),
        updatedTimestamp.toString());
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.FlowerRequest;
  }
}
