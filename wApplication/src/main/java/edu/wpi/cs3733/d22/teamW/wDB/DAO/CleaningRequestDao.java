package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.entity.CleaningRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CleaningRequestDao {

  void addCleaningRequest(CleaningRequest lsr) throws SQLException;

  void changeCleaningRequest(CleaningRequest cr) throws SQLException;

  void deleteCleaningRequest(Integer requestID) throws SQLException;

  void exportCleaningReqCSV(String filename);

  CleaningRequest getCleaningRequest(Integer requestID) throws StatusError;

  ArrayList<String> getCleaningLocation() throws SQLException;

  public ArrayList<Integer> CleaningRequestAtLocation(String nodeID) throws SQLException;

  ArrayList<Request> getAllCleaningRequests();

  ArrayList<Request> getEmployeeRequests(Integer employeeID);

  void updateCleaningRequestsAtLocation(String nodeID) throws Exception;

  void updateCleaningRequestsWithEquipment(String medID) throws Exception;

  void updateCleaningRequestsWithEmployee(Integer employeeID) throws Exception;
}
