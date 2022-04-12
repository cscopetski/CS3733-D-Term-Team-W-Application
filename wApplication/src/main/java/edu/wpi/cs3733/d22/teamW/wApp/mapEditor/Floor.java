package edu.wpi.cs3733.d22.teamW.wApp.mapEditor;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Floor {
  SimpleStringProperty Floor = new SimpleStringProperty();
  SimpleIntegerProperty BedCount = new SimpleIntegerProperty(0);
  SimpleIntegerProperty XrayCount = new SimpleIntegerProperty(0);
  SimpleIntegerProperty PumpCount = new SimpleIntegerProperty(0);
  SimpleIntegerProperty ReclinCount = new SimpleIntegerProperty(0);

  public Floor(
      String floor, Integer bedCount, Integer xrayCount, Integer pumpCount, Integer reclinCount) {
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

  public int getBedCount() {
    return BedCount.get();
  }

  public SimpleIntegerProperty bedCountProperty() {
    return BedCount;
  }

  public void setBedCount(int bedCount) {
    this.BedCount.set(bedCount);
  }

  public int getXrayCount() {
    return XrayCount.get();
  }

  public SimpleIntegerProperty xrayCountProperty() {
    return XrayCount;
  }

  public void setXrayCount(int xrayCount) {
    this.XrayCount.set(xrayCount);
  }

  public int getPumpCount() {
    return PumpCount.get();
  }

  public SimpleIntegerProperty pumpCountProperty() {
    return PumpCount;
  }

  public void setPumpCount(int pumpCount) {
    this.PumpCount.set(pumpCount);
  }

  public int getReclinCount() {
    return ReclinCount.get();
  }

  public SimpleIntegerProperty reclinCountProperty() {
    return ReclinCount;
  }

  public void setReclinCount(int reclinCount) {
    this.ReclinCount.set(reclinCount);
  }
}
