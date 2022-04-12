package edu.wpi.cs3733.d22.teamW.wDB.enums;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeType {
  Admin(7, 5, "Administrator"),
  Doctor(6, 3, "Doctor"),
  Nurse(5, 3, "Nurse"),
  Sanitation(4, 2, "Sanitiation Officer"),
  Security(3, 3, "Security Officer"),
  Technician(2, 2, "Technician"),
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
      map2.put(type.index, type);
    }
  }

  public int getAccessLevel() {
    return this.accessLevel;
  }

  public String getString() {
    return this.string;
  }

  public static EmployeeType getEmployeeType(int type) {
    return (EmployeeType) map.get(type);
  }

  public static EmployeeType getEmployeeType(String type) {
    return (EmployeeType) map2.get(type);
  }
}
