package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class TenDirtyInfusionPumps extends Exception {
  public TenDirtyInfusionPumps() {
    super("There are 10 dirty infusion pumps in one area");
  }
}
