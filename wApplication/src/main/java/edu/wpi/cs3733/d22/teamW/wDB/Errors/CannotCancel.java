package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class CannotCancel extends Exception {
  public CannotCancel() {
    super("Cannot cancel a request that is completed.");
  }
}
