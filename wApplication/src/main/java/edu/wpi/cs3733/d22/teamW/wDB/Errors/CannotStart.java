package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class CannotStart extends Exception {
  public CannotStart() {
    super("Cannot Start a Request that is not in queue");
  }
}
