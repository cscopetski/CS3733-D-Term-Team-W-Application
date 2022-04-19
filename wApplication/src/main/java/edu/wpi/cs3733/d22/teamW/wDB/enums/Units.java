package edu.wpi.cs3733.d22.teamW.wDB.enums;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.InvalidUnit;
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

  public static Units getUnitFromFullName(String type) throws InvalidUnit {
    type = type.trim();
    Units output = (Units) map.get(type);
    if (output == null) {
      throw new InvalidUnit();
    }
    return output;
  }

  public static Units getUnitFromAbb(String type) throws InvalidUnit {
    type = type.trim();
    Units output = (Units) map2.get(type);
    if (output == null) {
      throw new InvalidUnit();
    }
    return output;
  }
}
