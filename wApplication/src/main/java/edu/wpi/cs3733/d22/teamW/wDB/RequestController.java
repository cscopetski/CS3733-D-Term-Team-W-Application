package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RequestController {

  //  public String checkStart(Request request) throws SQLException;
  //
  //  public void checkNext(String ID) throws SQLException;
  //
  //  public Request getNext(String ID);

  public Request getRequest(Integer ID);

  public Request addRequest(Integer i, ArrayList<String> fields) throws SQLException;
}