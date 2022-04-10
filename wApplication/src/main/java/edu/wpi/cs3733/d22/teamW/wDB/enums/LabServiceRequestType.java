package edu.wpi.cs3733.d22.teamW.wDB.enums;

import java.awt.*;

public enum LabServiceRequestType {
  BloodSamples(0,"Blood Sample"),
  UrineSamples(1, "Urine Sample"),
  XRays(2, "X-Rays"),
  CATScans(3, "CAT Scans"),
  MRIs(4, "MRIs");
  private final int value;
  private final String string;

  private LabServiceRequestType(int value, String string) {
    this.value = value;
    this.string = string;
  }

  public int getValue() {
    return this.value;
  }

  public String getString(){
    return this.string;
  }
}
