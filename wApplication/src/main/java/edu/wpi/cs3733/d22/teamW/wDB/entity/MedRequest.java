package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedRequest extends Request {
  String medicine;

  public MedRequest(
      Integer requestID,
      String medicine,
      String nodeID,
      String employeeName,
      Integer emergency,
      Integer status) {
    this.requestID = requestID;
    this.medicine = medicine;
    this.nodeID = nodeID;
    this.employeeName = employeeName;
    this.emergency = emergency;
    this.status = status;
  }

  public MedRequest(ArrayList<String> fields) {
    this.requestID = Integer.parseInt(fields.get(0));
    this.medicine = fields.get(1);
    this.nodeID = fields.get(2);
    this.employeeName = fields.get(3);
    this.emergency = Integer.parseInt(fields.get(4));
    this.status = Integer.parseInt(fields.get(5));
  }

  public MedRequest(Integer index, ArrayList<String> fields) {
    this.requestID = index;
    this.medicine = fields.get(0);
    this.nodeID = fields.get(1);
    this.employeeName = fields.get(2);
    this.emergency = Integer.parseInt(fields.get(3));
    this.status = Integer.parseInt(fields.get(4));
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%s,%d,%d", requestID, medicine, nodeID, employeeName, emergency, status);
  }

  @Override
  public String toValuesString() {
    return String.format(
        "%d, '%s',  '%s', '%s', %d, %d",
        requestID, medicine, nodeID, employeeName, emergency, status);
  }

  @Override
  public Integer getStatusInt() {
    return status;
  }

  @Override
  public void start() {
    if (status == 0) {
      this.status = 1;
    }
  }

  @Override
  public void complete() {
    if (status == 1) {
      this.status = 2;
    }
  }

  @Override
  public void cancel() {
    if (status != 2) {
      this.status = 3;
    }
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.MedicineDelivery;
  }
}
