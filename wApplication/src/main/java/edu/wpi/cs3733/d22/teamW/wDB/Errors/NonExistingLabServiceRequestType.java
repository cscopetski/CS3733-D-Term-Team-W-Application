package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class NonExistingLabServiceRequestType extends Exception {
  public NonExistingLabServiceRequestType() {
    super("Lab Service Request Does Not Exist in the Database");
  }
}
