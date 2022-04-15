package edu.wpi.cs3733.d22.teamW.wDB.enums;

import java.util.HashMap;
import java.util.Map;

public enum Units {
  ml("milliliter", "ml"),
  mg("milligram", "mg"),
  unit("unit", "unit");

  private final String full;
  private final String abbrev;

  private static Map map = new HashMap<Integer, RequestType>();
  private static Map map2 = new HashMap<String, RequestType>();

  static {
    for (Units type : Units.values()) {
      map.put(type.full, type);
      map2.put(type.abbrev, type);
    }
  }

  private Units(String value, String string) {
    this.full = value;
    this.abbrev = string;
  }

  public String getUnits() {
    return this.abbrev;
  }

  public String getAbbrev() {
    return this.full;
  }

  public static Units getUnitFromFullName(String type) {
    return (Units) map.get(type);
  }

  public static Units getUnitFromAbb(String type) {
    return (Units) map2.get(type);
  }
}
