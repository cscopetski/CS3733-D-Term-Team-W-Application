package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class medEquip {
  private SimpleStringProperty MedID = new SimpleStringProperty();
  private SimpleIntegerProperty XCoord = new SimpleIntegerProperty();
  private SimpleIntegerProperty YCoord = new SimpleIntegerProperty();
  private SimpleStringProperty Type = new SimpleStringProperty();

  public medEquip(String ID, String type, Integer X, Integer Y) {
    MedID.set(ID);
    Type.set(type);
    XCoord.set(X);
    YCoord.set(Y);
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
}
