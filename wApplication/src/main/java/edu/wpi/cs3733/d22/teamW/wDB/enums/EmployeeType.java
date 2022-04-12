package edu.wpi.cs3733.d22.teamW.wDB.enums;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeType {
  Admin(8, 5, "Administrator"),
  Doctor(7, 3, "Doctor"),
  Nurse(6, 3, "Nurse"),
  Sanitation(5, 2, "Sanitation Officer"),
  Security(4, 3, "Security Officer"),
  Technician(3, 2, "Technician"),
  Staff(2, 3, "Staff"),
  LanguageInterpreter(1, 1, "Language Interpreter"),
  NoOne(0, 0, "Denied");
  private final int index;
  private final int accessLevel;
  private final String string;

  private static Map map = new HashMap<>();
  private static Map map2 = new HashMap<>();

  private EmployeeType(int index, int accessLevel, String string) {
    this.index = index;
    this.accessLevel = accessLevel;
    this.string = string;
  }

  static {
    for (EmployeeType type : EmployeeType.values()) {
      map.put(type.index, type);
      map2.put(type.string, type);
    }
  }

  public int getAccessLevel() {
    return this.accessLevel;
  }

  public String getString() {
    return this.string;
  }

  public static EmployeeType getEmployeeType(int type) {
    EmployeeType empType = (EmployeeType) map.get(type);
    if (empType == null) {
      empType = NoOne;
    }
    return empType;
  }

  public static EmployeeType getEmployeeType(String type) {
    EmployeeType empType = (EmployeeType) map2.get(type);
    if (empType == null) {
      empType = NoOne;
    }
    return empType;
  }
}
