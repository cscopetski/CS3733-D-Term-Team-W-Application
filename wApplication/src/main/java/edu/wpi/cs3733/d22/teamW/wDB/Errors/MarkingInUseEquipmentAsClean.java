package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class MarkingInUseEquipmentAsClean extends Exception {
  public MarkingInUseEquipmentAsClean() {
    super("In Use item has to be marked dirty to be cleaned before marking as clean");
  }
}
