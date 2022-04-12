package edu.wpi.cs3733.d22.teamW.wDB.entity;

public class                    Automation {
  boolean auto;
  static Automation automation = new Automation();

  private Automation() {
    this.auto = true;
  }

  public static Automation getAutomation() {
    return automation;
  }

  public boolean getAuto() {
    return this.auto;
  }

  public void on() {
    this.auto = true;
  }

  public void off() {
    this.auto = false;
  }
}
