package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class NonExistingRequestID extends Exception {
  public NonExistingRequestID() {
    super("This Request ID does not exist.");
  }
}
