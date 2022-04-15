package edu.wpi.cs3733.d22.teamW.wDB.enums;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingLabServiceRequestType;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum LabServiceRequestType {
  BloodSamples(0, "Blood Sample"),
  UrineSamples(1, "Urine Sample"),
  XRays(2, "X-Rays"),
  CATScans(3, "CAT Scans"),
  MRIs(4, "MRIs");

  private final int value;
  private final String string;

  private static Map map = new HashMap<>();
  private static Map map2 = new HashMap<>();

  private LabServiceRequestType(int value, String string) {
    this.value = value;
    this.string = string;
  }

  static {
    for (LabServiceRequestType labServiceRequestType : LabServiceRequestType.values()) {
      map.put(labServiceRequestType.value, labServiceRequestType);
      map2.put(labServiceRequestType.string, labServiceRequestType);
    }
  }

  public int getValue() {
    return this.value;
  }

  public String getString() {
    return this.string;
  }

  public static LabServiceRequestType getLabServiceRequestType(int type)
      throws NonExistingLabServiceRequestType {
    LabServiceRequestType output = (LabServiceRequestType) map.get(type);
    if (output == null) {
      throw new NonExistingLabServiceRequestType();
    }
    return output;
  }

  public static LabServiceRequestType getLabServiceRequestType(String type)
      throws NonExistingLabServiceRequestType {
    LabServiceRequestType output = (LabServiceRequestType) map2.get(type);
    if (output == null) {
      throw new NonExistingLabServiceRequestType();
    }
    return output;
  }
}
