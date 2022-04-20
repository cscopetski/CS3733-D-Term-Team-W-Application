package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class NoAvailableEquipment extends Exception {
  public NoAvailableEquipment() {
    super("This type of equipment is not available at the moment.");
  };
}
