package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class NoMedicine extends Exception {
  public NoMedicine() {
    super("Medicine does not exist in our list of medicine Enum");
  }
}
