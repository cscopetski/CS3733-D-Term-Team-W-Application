package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RequestManager {

  //  public String checkStart(Request request) throws SQLException;
  //
  //  public void checkNext(String ID) throws SQLException;
  //
  //  public Request getNext(String ID);

  public Request getRequest(Integer ID) throws SQLException;

  public ArrayList<Request> getAllRequests() throws SQLException;

  public Request addRequest(Integer i, ArrayList<String> fields) throws SQLException;
}