package edu.wpi.cs3733.d22.teamW.wDB;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabServiceRequest extends Request implements Entity {

  private String labType;

  public LabServiceRequest(
      Integer requestID,
      String labType,
      String nodeID,
      String employeeName,
      Integer emergency,
      Integer status) {
    this.requestID = requestID;
    this.labType = labType;
    this.nodeID = nodeID;
    this.employeeName = employeeName;
    this.emergency = emergency;
    this.status = status;
  }

  public LabServiceRequest(ArrayList<String> fields) {
    this.requestID = Integer.parseInt(fields.get(0));
    this.labType = fields.get(1);
    this.nodeID = fields.get(2);
    this.employeeName = fields.get(3);
    this.emergency = Integer.parseInt(fields.get(4));
    this.status = Integer.parseInt(fields.get(5));
  }

  public LabServiceRequest(Integer index, ArrayList<String> fields) {
    this.requestID = index;
    this.labType = fields.get(0);
    this.nodeID = fields.get(1);
    this.employeeName = fields.get(2);
    this.emergency = Integer.parseInt(fields.get(3));
    this.status = Integer.parseInt(fields.get(4));
  }

  @Override
  public Integer getStatus() {
    return this.status;
  }

  @Override
  public void start() {}

  @Override
  public void start(String s) {}

  @Override
  public void complete() {}

  @Override
  public void cancel() {}

  @Override
  public String getRequestType() {
    return "LABSERVICEREQUEST";
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%s,%d,%d", requestID, labType, nodeID, employeeName, emergency, status);
  }

  @Override
  public String toValuesString() {
    return String.format(
        "%d, '%s',  '%s', '%s', %d, %d",
        requestID, labType, nodeID, employeeName, emergency, status);
  }
}
