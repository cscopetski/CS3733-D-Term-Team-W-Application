package edu.wpi.cs3733.d22.teamW.wDB;

import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedEquipRequest extends Request {
  private String itemType;
  private String itemID; // Medical Equipment item

  public MedEquipRequest(
      Integer requestID, Integer emergency, String itemType, String nodeID, String employeeName) {

    this.requestID = requestID;
    this.emergency = emergency;
    this.itemType = itemType;
    this.nodeID = nodeID;
    this.employeeName = employeeName;
    this.status = 0;
  }

  public MedEquipRequest(ArrayList<String> medReqData) {
    try {
      this.requestID = Integer.parseInt(medReqData.get(0));
    } catch (NumberFormatException e) {
      this.requestID = null;
    }

    this.itemID = medReqData.get(1);
    this.itemType = medReqData.get(2);
    this.nodeID = medReqData.get(3);
    this.employeeName = medReqData.get(4);

    try {
      this.emergency = Integer.parseInt(medReqData.get(5));
    } catch (NumberFormatException e) {
      this.emergency = 0;
    }

    try {
      this.status = Integer.parseInt(medReqData.get(6));
    } catch (NumberFormatException e) {
      this.status = 3;
    }
  }

  public MedEquipRequest(Integer index, ArrayList<String> fields) {
    this.requestID = index;
    this.itemID = "NONE";
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

  public MedEquipRequest(String[] medEquipReqData) {
    this.requestID = Integer.parseInt(medEquipReqData[0]);
    this.itemID = medEquipReqData[1];
    this.itemType = medEquipReqData[2];
    this.nodeID = medEquipReqData[3];
    this.employeeName = medEquipReqData[4];
    this.emergency = Integer.parseInt(medEquipReqData[5]);
    this.status = Integer.parseInt(medEquipReqData[6]);
  }

  @Override
  public Integer getStatusInt() {
    return status;
  }

  @Override
  public void start() {}

  // TODO we also need to change this to our version of start
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
      // TODO eventually make it set to dirty, for now is just a workaround

    } else {
      // The complete button should only appear if it is in progress
    }
  }

  public void cancel() {
    this.status = 3;
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.MedicalEquipmentRequest;
  }

  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%s,%s,%d,%d",
        this.requestID,
        this.itemID,
        this.itemType,
        this.nodeID,
        this.employeeName,
        this.emergency,
        this.status);
  }

  @Override
  public String toValuesString() {

    return String.format(
        "%d, '%s', '%s', '%s', '%s', %d, %d",
        this.requestID,
        this.itemID,
        this.itemType,
        this.nodeID,
        this.employeeName,
        this.emergency,
        this.status);
  }

  public Integer getRequestID() {
    return requestID;
  }

  public Integer getEmergency() {
    return emergency;
  }

  public void setEmergency(Integer emergency) {
    this.emergency = emergency;
  }

  public void setEmployeeName(String name) {
    this.employeeName = name;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public String getNodeID() {
    return this.nodeID;
  }

  public String getEmployeeName() {
    return this.employeeName;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof MedEquipRequest)) {
      return false;
    }
    MedEquipRequest m = (MedEquipRequest) o;
    return this.requestID == m.getRequestID()
        && this.status == m.getStatusInt()
        && this.nodeID.equals(m.getNodeID())
        && this.emergency == m.getEmergency()
        && this.employeeName.equals(m.getEmployeeName())
        && this.itemType.equals(m.getItemType())
        && this.itemID.equals(m.getItemID());
  }
}
