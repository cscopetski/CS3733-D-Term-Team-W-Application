package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class CleaningRequestMax extends Exception {
  public CleaningRequestMax() {
    super("6 Cleaning Requests in a location");
  }
}
