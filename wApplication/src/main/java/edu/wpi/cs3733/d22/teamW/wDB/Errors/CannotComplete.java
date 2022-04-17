package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class CannotComplete extends Exception {
  public CannotComplete() {
    super("Cannot Complete a Request that is not started.");
  }
}
