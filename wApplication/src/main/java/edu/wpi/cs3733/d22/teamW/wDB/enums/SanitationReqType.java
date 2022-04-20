package edu.wpi.cs3733.d22.teamW.wDB.enums;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.InvalidSanitationType;
import java.util.HashMap;
import java.util.Map;

public enum SanitationReqType {
  RoomCleaning("Room Cleaning"),
  Mopping("Mopping"),
  PatientExcretion("Patient Excretion"),
  Spill("Spills"),
  ChemicalSpill("Chemical Spill");

  public final String name;

  private static Map map = new HashMap<>();

  private SanitationReqType(String name) {
    this.name = name;
  }

  static {
    for (SanitationReqType type : SanitationReqType.values()) {
      map.put(type.name, type);
    }
  }

  public String getString() {
    return this.name;
  }

  public static SanitationReqType getSanitation(String type) throws InvalidSanitationType {
    SanitationReqType sanType = (SanitationReqType) map.get(type);
    if (sanType == null) {
      throw new InvalidSanitationType();
    }
    return sanType;
  }
}
