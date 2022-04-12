package edu.wpi.cs3733.d22.teamW.wApp.controllers.Snake;

public class Position {
  public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT
  }

  private double xPos;
  private double yPos;

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

  public void setXPos(double xPos) {
    this.xPos = xPos;
  }

  public void setYPos(double yPos) {
    this.yPos = yPos;
  }

  @Override
  public String toString() {
    return "Position{" + "xPos=" + xPos + ", yPos=" + yPos + '}';
  }
}
