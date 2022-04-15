package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipStatus;
import java.util.ArrayList;

public class MedEquip extends Entity {

  private String medID;

  private String type;

  private String nodeID;

  private MedEquipStatus status;

  public MedEquip() {
    this.medID = null;
    this.type = null;
    this.nodeID = null;
    this.status = null;
  }

  public MedEquip(String ID, String type, String nodeID, Integer status) throws StatusError {
    this.medID = ID;
    this.type = type;
    this.nodeID = nodeID;
    this.status = MedEquipStatus.getStatus(status);
  }

  public MedEquip(ArrayList<String> medEquipData) {

    this.medID = medEquipData.get(0);
    this.type = medEquipData.get(1);
    this.nodeID = medEquipData.get(2);
    try {
      this.status = MedEquipStatus.getStatus(Integer.parseInt(medEquipData.get(3)));
    } catch (NumberFormatException | StatusError e) {
      this.status = null;
    }
  }

  public String toValuesString() {

    return String.format("'%s', '%s', '%s', %d", medID, type, nodeID, status.getValue());
  }

  public String toCSVString() {
    return String.format("%s,%s,%s,%d", medID, type, nodeID, status.getValue());
  }

  public void setMedID(String medID) {
    this.medID = medID;
  }

  public String getMedID() {
    return this.medID;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public MedEquipStatus getStatus() {
    return status;
  }

  public void setStatus(MedEquipStatus status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }

    if (!(o instanceof MedEquip)) {
      return false;
    }

    MedEquip m = (MedEquip) o;

    return this.medID.equals(m.getMedID())
        && this.type.equals(m.getType())
        && this.nodeID.equals(m.getNodeID())
        && this.status.equals(m.getStatus());
  }
}
