package edu.wpi.cs3733.d22.teamW.wDB.enums;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.InvalidSanitationType;
import java.util.HashMap;
import java.util.Map;

public enum Sanitation {
  RoomCleaning("Room Cleaning"),
  Mopping("Mopping"),
  PatientExcretion("Patient Excretion"),
  Spill("Spills"),
  ChemicalSpill("Chemical Spill");

  public final String name;

  private static Map map = new HashMap<>();

  private Sanitation(String name) {
    this.name = name;
  }

  static {
    for (Sanitation type : Sanitation.values()) {
      map.put(type.name, type);
    }
  }

  public String getString() {
    return this.name;
  }

  public static Sanitation getSanitation(String type) throws InvalidSanitationType {
    Sanitation sanType = (Sanitation) map.get(type);
    if (sanType == null) {
      throw new InvalidSanitationType();
    }
    return sanType;
  }
}
