package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class NoFlower extends Exception {
  public NoFlower() {
    super("Flower does not exist in our list of flowers");
  }
}
