package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class NonExistingMedEquip extends Exception {

  public NonExistingMedEquip() {
    super("Medical Equipment does not exist in the database!");
  }
}
