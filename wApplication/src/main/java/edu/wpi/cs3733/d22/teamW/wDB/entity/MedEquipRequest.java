package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
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
      Integer requestID, Integer emergency, String itemType, String nodeID, Integer employeeID) {

    this.requestID = requestID;
    this.emergency = emergency;
    this.itemType = itemType;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.status = RequestStatus.InQueue;
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
    this.employeeID = Integer.parseInt(medReqData.get(4));

    try {
      this.emergency = Integer.parseInt(medReqData.get(5));
    } catch (NumberFormatException e) {
      this.emergency = 0;
    }

    try {
      this.status = RequestStatus.getRequestStatus(Integer.parseInt(medReqData.get(6)));
    } catch (NumberFormatException e) {
      this.status = RequestStatus.Cancelled;
    }
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
  }

  public MedEquipRequest(String[] medEquipReqData) {
    this.requestID = Integer.parseInt(medEquipReqData[0]);
    this.itemID = medEquipReqData[1];
    this.itemType = medEquipReqData[2];
    this.nodeID = medEquipReqData[3];
    this.employeeID = Integer.parseInt(medEquipReqData[4]);
    this.emergency = Integer.parseInt(medEquipReqData[5]);
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(medEquipReqData[6]));
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.MedicalEquipmentRequest;
  }

  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%s,%d,%d,%d",
        this.requestID,
        this.itemID,
        this.itemType,
        this.nodeID,
        this.employeeID,
        this.emergency,
        this.status.getValue());
  }

  @Override
  public String toValuesString() {

    return String.format(
        "%d, '%s', '%s', '%s', %d, %d, %d",
        this.requestID,
        this.itemID,
        this.itemType,
        this.nodeID,
        this.employeeID,
        this.emergency,
        this.status.getValue());
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
        && this.itemID.equals(m.getItemID());
  }
}
