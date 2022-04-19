package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class StatusError extends Exception {
  public StatusError() {
    super("Status does not exist");
  }
}
