package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RequestManager {

  //  public String checkStart(Request request) throws SQLException;
  //
  //  public void checkNext(String ID) throws SQLException;
  //
  //  public Request getNext(String ID);

  public abstract void start(Integer requestID) throws Exception;

  public abstract void complete(Integer requestID)
      throws SQLException, StatusError, NonExistingMedEquip;

  public abstract void cancel(Integer requestID) throws Exception;

  public abstract void reQueue(Integer requestID) throws Exception;

  public Request getRequest(Integer ID) throws Exception;

  public ArrayList<Request> getAllRequests() throws Exception;

  public void changeRequest(Request request) throws  Exception;

  public Request addNewRequest(Integer i, ArrayList<String> fields) throws Exception;

  public Request addExistingRequest(ArrayList<String> fields) throws Exception;

  public void exportReqCSV(String filename) throws Exception;

  public void updateReqAtLocation(String nodeID) throws Exception;

  public void updateReqWithEmployee(Integer employeeID) throws Exception;
}
