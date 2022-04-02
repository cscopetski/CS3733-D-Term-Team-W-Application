package edu.wpi.cs3733.d22.teamW.wDB;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class MedEquipRequest extends Entity implements Request{
  private Integer
      requestID; // Maybe switch to Hexatrigesimal uses 0-9 and then A to Z, so it allows a large
  // number of req
  // With 6 char, it will allow 36^6 = 2.18 billion
  private Integer emergency;
  private String itemType;
  private String itemID; // Medical Equipment item
  private String nodeID; // Location
  private Integer status; // 0 enqueue; 1 in progress; 2 done; 3 clean; 4 cancelled
  private String employeeName; // Will be changed to employee ID starting in sprint 1

  public MedEquipRequest(
      Integer requestID, Integer emergency, String itemType, String nodeID, String employeeName) {
    this.requestID = requestID;
    this.emergency = emergency;
    this.itemType = itemType;
    this.nodeID = nodeID;
    this.employeeName = employeeName;
    this.status = 0;
  }

  public MedEquipRequest(String[] medReqData) {
    try {
      this.requestID = Integer.parseInt(medReqData[0]);
    } catch (NumberFormatException e) {
      this.requestID = null;
    }

    this.itemType = medReqData[1];
    this.nodeID = medReqData[2];
    this.employeeName = medReqData[3];

    try {
      this.emergency = Integer.parseInt(medReqData[4]);
    } catch (NumberFormatException e) {
      this.emergency = 0;
    }

    try {
      this.status = Integer.parseInt(medReqData[5]);
    } catch (NumberFormatException e) {
      this.status = 3;
    }
  }

  public MedEquipRequest(Integer index, ArrayList<String> fields){
    this.requestID = index;
    this.itemType = fields.get(0);
    this.nodeID = fields.get(1);
    this.employeeName = fields.get(2);

    try {
      this.emergency = Integer.parseInt(fields.get(3));
    } catch (NumberFormatException e) {
      this.emergency = 0;
    }

    try {
      this.status = Integer.parseInt(fields.get(4));
    } catch (NumberFormatException e) {
      this.status = 0;
    }

  }


  @Override
  public void start() {}

  //TODO we also need to change this to our version of start
  public void start(String medID) {
    if (this.status == 0) {
      this.status = 1;
      this.itemID = medID;
    } else {
      // Tells the user that it is in progress or completed
      // Could be a pop-up to the user when they click the start button or something
    }
  }

  public void complete() {
    if (this.status == 1) {
      this.status = 2;
    } else {
      // The complete button should only appear if it is in progress
    }
  }

  public void cancel() {
    if (this.status != 2) {
      this.status = 3;
    } else {
    }
  }

  public String toCSVString() {
    return String.format(
        "%d,%d,%s,%s,%d,%s",
        this.requestID, this.emergency, this.itemID, this.nodeID, this.status, this.employeeName);
  }

  @Override
  public String toValuesString() {

    return String.format(
        "%d, '%s', '%s', '%s', %d, %d",
        this.requestID, this.itemID, this.nodeID, this.employeeName, this.emergency, this.status);
  }


}
