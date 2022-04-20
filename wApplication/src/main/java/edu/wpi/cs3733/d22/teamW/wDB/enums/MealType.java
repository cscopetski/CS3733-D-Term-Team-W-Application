package edu.wpi.cs3733.d22.teamW.wDB.enums;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoMeal;
import java.util.HashMap;
import java.util.Map;

public enum MealType {
  Burger("Burger"),
  Burrito("Burrito"),
  Ramen("Ramen");

  public final String string;

  private static Map map = new HashMap<>();

  private MealType(String string) {
    this.string = string;
  }

  static {
    for (MealType type : MealType.values()) {
      map.put(type.string, type);
    }
  }

  public String getString() {
    return this.string;
  }

  public static MealType getMealType(String mealType) throws NoMeal {
    MealType type = null;
    type = (MealType) map.get(mealType);
    if (type == null) {
      throw new NoMeal();
    }
    return type;
  }
}
