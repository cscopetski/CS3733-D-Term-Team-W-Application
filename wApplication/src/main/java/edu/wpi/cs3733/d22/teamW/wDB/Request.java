package edu.wpi.cs3733.d22.teamW.wDB;

public interface Request {

  public void start();

  public void start(String s);

  public void complete();

  public void cancel();
}
