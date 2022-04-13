package edu.wpi.cs3733.d22.teamW.wApp.mapEditor;

import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipStatus;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class medEquip {
  private SimpleStringProperty MedID = new SimpleStringProperty();
  private SimpleIntegerProperty XCoord = new SimpleIntegerProperty();
  private SimpleIntegerProperty YCoord = new SimpleIntegerProperty();
  private SimpleStringProperty Type = new SimpleStringProperty();
  private SimpleStringProperty Status = new SimpleStringProperty();
  private SimpleStringProperty Floor = new SimpleStringProperty();
  private SimpleStringProperty LocType = new SimpleStringProperty();

  public medEquip(String ID, String type, Integer X, Integer Y) {
    MedID.set(ID);
    Type.set(type);
    XCoord.set(X);
    YCoord.set(Y);
  }

  public medEquip(String ID, String Floor, String LocType, String Status) {
    this.MedID.set(ID);
    this.Floor.set(Floor);
    this.LocType.set(LocType);
    this.Status.set(Status);
  }

  public medEquip(String ID, String type, MedEquipStatus status) {
    MedID.set(ID);
    Type.set(type);
    Status.set(status.getString());
  }

  public int getXCoord() {
    return XCoord.get();
  }

  public int getYCoord() {
    return YCoord.get();
  }

  public String getMedID() {
    return MedID.get();
  }

  public String getType() {
    return Type.get();
  }

  public String getStatus() {
    return Status.get();
  }

  public String getLocType() {
    return LocType.get();
  }

  public SimpleStringProperty locTypeProperty() {
    return LocType;
  }

  public void setLocType(String locType) {
    this.LocType.set(locType);
  }

  public String getFloor() {
    return Floor.get();
  }
}
