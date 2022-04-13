package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedEquipRequest extends Request {
  private String itemType;
  private String itemID; // Medical Equipment item

  public MedEquipRequest(
      Integer requestID,
      Integer emergency,
      String itemType,
      String nodeID,
      Integer employeeID,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp) {

    this.requestID = requestID;
    this.emergency = emergency;
    this.itemType = itemType;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.status = RequestStatus.InQueue;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public MedEquipRequest(ArrayList<String> fields) {
    try {
      this.requestID = Integer.parseInt(fields.get(0));
    } catch (NumberFormatException e) {
      this.requestID = null;
    }

    this.itemID = fields.get(1);
    this.itemType = fields.get(2);
    this.nodeID = fields.get(3);
    this.employeeID = Integer.parseInt(fields.get(4));

    try {
      this.emergency = Integer.parseInt(fields.get(5));
    } catch (NumberFormatException e) {
      this.emergency = 0;
    }

    try {
      this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(6)));
    } catch (NumberFormatException e) {
      this.status = RequestStatus.Cancelled;
    }

    this.createdTimestamp = Timestamp.valueOf(fields.get(7));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(8));
  }

  // TODO fixing this constructor, may be out of order??
  public MedEquipRequest(Integer index, ArrayList<String> fields) {
    this.requestID = index;
    this.itemID = "NONE";
    this.itemType = fields.get(0);
    this.nodeID = fields.get(1);
    this.employeeID = Integer.parseInt(fields.get(2));

    try {
      this.emergency = Integer.parseInt(fields.get(3));
    } catch (NumberFormatException e) {
      this.emergency = 0;
    }

    try {
      this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(4)));
    } catch (NumberFormatException e) {
      this.status = RequestStatus.InQueue;
    }

    this.createdTimestamp = Timestamp.valueOf(fields.get(5));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(6));
  }

  public MedEquipRequest(String[] medEquipReqData) {
    this.requestID = Integer.parseInt(medEquipReqData[0]);
    this.itemID = medEquipReqData[1];
    this.itemType = medEquipReqData[2];
    this.nodeID = medEquipReqData[3];
    this.employeeID = Integer.parseInt(medEquipReqData[4]);
    this.emergency = Integer.parseInt(medEquipReqData[5]);
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(medEquipReqData[6]));
    this.createdTimestamp = Timestamp.valueOf(medEquipReqData[7]);
    this.updatedTimestamp = Timestamp.valueOf(medEquipReqData[8]);
  }

  public void dropItem() {
    this.itemID = "NONE";
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.MedicalEquipmentRequest;
  }

  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%s,%d,%d,%d,%s,%s",
        this.requestID,
        this.itemID,
        this.itemType,
        this.nodeID,
        this.employeeID,
        this.emergency,
        this.status.getValue(),
        this.createdTimestamp.toString(),
        this.updatedTimestamp.toString());
  }

  @Override
  public String toValuesString() {

    return String.format(
        "%d, '%s', '%s', '%s', %d, %d, %d, '%s', '%s'",
        this.requestID,
        this.itemID,
        this.itemType,
        this.nodeID,
        this.employeeID,
        this.emergency,
        this.status.getValue(),
        this.createdTimestamp.toString(),
        this.updatedTimestamp.toString());
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

  public void setEmployeeName(Integer ID) {
    this.employeeID = ID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public String getNodeID() {
    return this.nodeID;
  }

  public Integer getEmployeeID() {
    return this.employeeID;
  }

  public String getItemType() {
    return this.itemType;
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
        && this.status == m.getStatus()
        && this.nodeID.equals(m.getNodeID())
        && this.emergency == m.getEmergency()
        && this.employeeID == m.getEmployeeID()
        && this.itemType.equals(m.getItemType())
        && this.itemID.equals(m.getItemID())
        && this.createdTimestamp.equals(m.getCreatedTimestamp())
        && this.updatedTimestamp.equals(m.getUpdatedTimestamp());
  }
}
