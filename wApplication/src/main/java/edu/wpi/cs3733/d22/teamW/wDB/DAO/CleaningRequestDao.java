package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.CleaningRequest;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CleaningRequestDao {

  void addCleaningRequest(CleaningRequest lsr) throws SQLException;

  void changeCleaningRequest(Integer requestID, String itemID, RequestStatus status)
      throws SQLException;

  void deleteCleaningRequest(Integer requestID) throws SQLException;

  void exportCleaningReqCSV(String filename);

  CleaningRequest getCleaningRequest(Integer requestID);

  ArrayList<CleaningRequest> getAllCleaningRequests();
}
