package edu.wpi.cs3733.d22.teamW.wDB.enums;

public enum ThreatLevels {
  Green(0, "Green"),
  Blue(1, "Blue"),
  Yellow(2, "Yellow"),
  Red(3, "Red"),
  Wong(4, "Wong");

  private final Integer value;
  private final String string;

  ThreatLevels(Integer value, String string) {
    this.value = value;
    this.string = string;
  }

  public Integer getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.string;
  }
}
