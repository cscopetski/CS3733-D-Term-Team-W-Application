package edu.wpi.cs3733.d22.teamW.wDB.enums;

public enum Automation {
  Automation;

  boolean auto = true;

  public boolean getAuto() {
    return auto;
  }

  public void on() {
    auto = true;
  }

  public void off() {
    auto = false;
  }
}
