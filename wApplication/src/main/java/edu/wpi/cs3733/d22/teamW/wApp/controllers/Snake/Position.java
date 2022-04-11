package edu.wpi.cs3733.d22.teamW.wApp.controllers.Snake;

public class Position {
  public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT
  }

  private final double xPos;
  private final double yPos;

  public Position(double xPos, double yPos) {
    this.xPos = xPos;
    this.yPos = yPos;
  }

  public double getXPos() {
    return xPos;
  }

  public double getYPos() {
    return yPos;
  }

  @Override
  public String toString() {
    return "Position{" + "xPos=" + xPos + ", yPos=" + yPos + '}';
  }
}
