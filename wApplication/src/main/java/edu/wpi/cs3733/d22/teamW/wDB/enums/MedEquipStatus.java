package edu.wpi.cs3733.d22.teamW.wDB.enums;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;

import java.util.HashMap;
import java.util.Map;

public enum MedEquipStatus {
  Clean(0, "Clean"),
  InUse(1, "In Use"),
  Dirty(2, "Dirty");

  private final int value;
  private final String string;

  private static Map map = new HashMap<>();
  private static Map map2 = new HashMap<>();

  private MedEquipStatus(int value, String string) {
    this.value = value;
    this.string = string;
  }

  static{
    for (MedEquipStatus type: MedEquipStatus.values()){
      map.put(type.value, type);
      map2.put(type.string,type);
    }
  }

  public int getValue() {
    return this.value;
  }

  public String getString() {
    return this.string;
  }

  public static MedEquipStatus getStatus(Integer type) {
    return (MedEquipStatus) map.get(type);
  }
  public static MedEquipStatus getStatus(String type) {
    return (MedEquipStatus) map2.get(type);
  }
}
