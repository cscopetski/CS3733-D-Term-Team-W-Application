package edu.wpi.cs3733.d22.teamW.wDB.enums;

public enum MedEquipStatus {
  Clean(0, "Clean"),
  InUse(1, "In Use"),
  Dirty(2, "Dirty");

  private final int value;
  private final String string;

  private MedEquipStatus(int value, String string) {
    this.value = value;
    this.string = string;
  }

  public int getValue() {
    return this.value;
  }

  public String getString() {
    return this.string;
  }

  public static MedEquipStatus getStatus(Integer num) {
    MedEquipStatus status = null;
    switch (num) {
      case 0:
        status = Clean;
        break;
      case 1:
        status = InUse;
        break;
      case 2:
        status = Dirty;
        break;
      default:
        System.out.println("Not a case for Request Status");
        status = Dirty;
        break;
    }
    return status;
  }
}
