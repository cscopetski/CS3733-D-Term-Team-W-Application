package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class MarkingDirtyEquipmentAsInUse extends Exception {
  public MarkingDirtyEquipmentAsInUse() {
    super("Dirty item has to be cleaned before marking as in use");
  }
}
