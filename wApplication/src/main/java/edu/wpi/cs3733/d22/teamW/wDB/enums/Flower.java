package edu.wpi.cs3733.d22.teamW.wDB.enums;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoFlower;
import java.util.HashMap;
import java.util.Map;

public enum Flower {
  Rose("Rose"),
  Lilac("Lilac"),
  Daisy("Daisy"),
  Orchid("Orchid"),
  Succulent("Succulent");

  public final String string;

  private static Map map = new HashMap<>();

  private Flower(String string) {
    this.string = string;
  }

  static {
    for (Flower type : Flower.values()) {
      map.put(type.string, type);
    }
  }

  public String getString() {
    return this.string;
  }

  public static Flower getFlower(String flower) throws NoFlower {
    Flower type = null;
    type = (Flower) map.get(flower);
    if (type == null) {
      throw new NoFlower();
    }
    return type;
  }
}
