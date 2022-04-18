package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class SixDirtyBeds extends Exception {
  public SixDirtyBeds() {
    super("There are 6 dirty beds in one location");
  }
}
