package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class CannotRequeue extends Exception {
  public CannotRequeue() {
    super("Can only requeue requests that are cancelled.");
  }
}
