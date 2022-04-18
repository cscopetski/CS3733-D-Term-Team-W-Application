package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class NoMeal extends Exception {
  public NoMeal() {
    super("Meal does not exist in our list of meal Enum");
  }
}
