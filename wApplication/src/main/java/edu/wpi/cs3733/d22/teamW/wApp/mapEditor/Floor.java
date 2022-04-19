package edu.wpi.cs3733.d22.teamW.wApp.mapEditor;

import javafx.beans.property.SimpleStringProperty;

public class Floor {
  SimpleStringProperty Floor = new SimpleStringProperty();
  SimpleStringProperty BedCount = new SimpleStringProperty("");
  SimpleStringProperty XrayCount = new SimpleStringProperty("");
  SimpleStringProperty PumpCount = new SimpleStringProperty("");
  SimpleStringProperty ReclinCount = new SimpleStringProperty("");
  Integer DirtyBedCount = 0;

  public Integer getDirtyBedCount() {
    return DirtyBedCount;
  }

  public void setDirtyBedCount(Integer dirtyBedCount) {
    DirtyBedCount = dirtyBedCount;
  }

  public Integer getDirtyPumpCount() {
    return DirtyPumpCount;
  }

  public void setDirtyPumpCount(Integer dirtyPumpCount) {
    DirtyPumpCount = dirtyPumpCount;
  }

  public Integer getDirtyReclinCount() {
    return DirtyReclinCount;
  }

  public void setDirtyReclinCount(Integer dirtyReclinCount) {
    DirtyReclinCount = dirtyReclinCount;
  }

  public Integer getDirtyXrayCount() {
    return DirtyXrayCount;
  }

  public void setDirtyXrayCount(Integer dirtyXrayCount) {
    DirtyXrayCount = dirtyXrayCount;
  }

  public Integer getCleanBedCount() {
    return cleanBedCount;
  }

  public void setCleanBedCount(Integer cleanBedCount) {
    this.cleanBedCount = cleanBedCount;
  }

  public Integer getCleanPumpCount() {
    return cleanPumpCount;
  }

  public void setCleanPumpCount(Integer cleanPumpCount) {
    this.cleanPumpCount = cleanPumpCount;
  }

  public Integer getCleanReclinCount() {
    return cleanReclinCount;
  }

  public void setCleanReclinCount(Integer cleanReclinCount) {
    this.cleanReclinCount = cleanReclinCount;
  }

  public Integer getCleanXrayCount() {
    return cleanXrayCount;
  }

  public void setCleanXrayCount(Integer cleanXrayCount) {
    this.cleanXrayCount = cleanXrayCount;
  }

  Integer DirtyPumpCount = 0;
  Integer DirtyReclinCount = 0;
  Integer DirtyXrayCount = 0;
  Integer cleanBedCount = 0;
  Integer cleanPumpCount = 0;
  Integer cleanReclinCount = 0;
  Integer cleanXrayCount = 0;

  public Floor(
      String floor, String bedCount, String xrayCount, String pumpCount, String reclinCount) {
    Floor.set(floor);
    BedCount.set(bedCount);
    XrayCount.set(xrayCount);
    PumpCount.set(pumpCount);
    ReclinCount.set(reclinCount);
  }

  public Floor(String floor) {
    Floor.set(floor);
  }

  public String getFloor() {
    return Floor.get();
  }

  public SimpleStringProperty floorProperty() {
    return Floor;
  }

  public void setFloor(String floor) {
    this.Floor.set(floor);
  }

  public String getBedCount() {
    return getCleanBedCount() + " | " + getDirtyBedCount();
  }

  public SimpleStringProperty bedCountProperty() {
    return BedCount;
  }

  public void setBedCount(String bedCount) {
    this.BedCount.set(bedCount);
  }

  public String getXrayCount() {
    return getCleanXrayCount() + " | " + getDirtyXrayCount();
  }

  public SimpleStringProperty xrayCountProperty() {
    return XrayCount;
  }

  public void setXrayCount(String xrayCount) {
    this.XrayCount.set(xrayCount);
  }

  public String getPumpCount() {
    return getCleanPumpCount() + " | " + getDirtyPumpCount();
  }

  public SimpleStringProperty pumpCountProperty() {
    return PumpCount;
  }

  public void setPumpCount(int pumpCount) {
    this.PumpCount.set(String.valueOf(pumpCount));
  }

  public String getReclinCount() {
    return getCleanReclinCount() + " | " + getDirtyReclinCount();
  }

  public void setReclinCount(String reclinCount) {
    this.ReclinCount.set(reclinCount);
  }
}
