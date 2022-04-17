package edu.wpi.cs3733.d22.teamW.wDB.enums;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import java.util.HashMap;
import java.util.Map;

public enum MedEquipType {
  Bed("BED", "Bed"),
  Recliners("REC", "Recliners"),
  InfusionPump("INP", "Infusion Pump"),
  XRay("XRY", "X-Ray"),
  NONE("NONE", "NONE");

  public final String abb;
  public final String string;

  private static Map map = new HashMap<>();
  private static Map map2 = new HashMap<>();

  private MedEquipType(String abb, String string) {
    this.abb = abb;
    this.string = string;
  }

  static {
    for (MedEquipType type : MedEquipType.values()) {
      map.put(type.abb, type);
      map2.put(type.string, type);
    }
  }

  public String getAbb() {
    return this.abb;
  }

  public String getString() {
    return this.string;
  }

  public static MedEquipType getMedEquipFromAbb(String input) throws NonExistingMedEquip {
    input = input.trim();
    MedEquipType output = (MedEquipType) map.get(input);
    if (output == null) {
      output = (MedEquipType) map2.get(input);
      if (output == null) {
        System.out.println(input);
        throw new NonExistingMedEquip();
      }
    }
    return output;
  }

  public static MedEquipType getMedEquipFromString(String input) throws NonExistingMedEquip {
    input = input.trim();
    MedEquipType output = (MedEquipType) map2.get(input);
    if (output == null) {
      throw new NonExistingMedEquip();
    }
    return output;
  }
}
