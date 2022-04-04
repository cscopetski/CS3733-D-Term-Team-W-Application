package edu.wpi.cs3733.d22.teamW.wDB;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class LabServiceRequest extends Request implements Entity {

  private String labType;

  public LabServiceRequest(Integer requestID, String labType, Integer emergency, String nodeID, Integer status, String employeeName) {
    this.requestID = requestID;
    this.labType = labType;
    this.emergency = emergency;
    this.nodeID = nodeID;
    this.status = status;
    this.employeeName = employeeName;
  }

  public LabServiceRequest(ArrayList<String> fields) {
    this.requestID = Integer.parseInt(fields.get(0));
    this.labType = fields.get(1);
    this.emergency = Integer.parseInt(fields.get(2));
    this.nodeID = fields.get(3);
    this.status = Integer.parseInt(fields.get(4));
    this.employeeName = fields.get(5);
  }

  public LabServiceRequest(Integer index, ArrayList<String> fields) {
    this.requestID = index;
    this.labType = fields.get(0);
    this.emergency = Integer.parseInt(fields.get(1));
    this.nodeID = fields.get(2);
    this.status = Integer.parseInt(fields.get(3));
    this.employeeName = fields.get(4);
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
    return String.format("%d, %s, %d, %s, %d, %s", requestID, labType, emergency, nodeID, status, employeeName);
  }

  @Override
  public String toValuesString() {
    return String.format("%d, '%s', %d, '%s', %d, '%s'", requestID, labType, emergency, nodeID, status, employeeName);
  }
}
