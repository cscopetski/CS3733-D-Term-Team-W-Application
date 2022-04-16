package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.SanitationRequest;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.SanitationReqType;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SanitationRequestDao {

  ArrayList<Request> getAllSanitationRequests() throws Exception;

  void addSanitationRequest(SanitationRequest sr) throws SQLException;

  SanitationRequest getSanitationRequest(Integer requestID) throws Exception;

  void changeSanitationRequest(
      Integer requestID,
      SanitationReqType sanitationReqType,
      String nodeID,
      Integer employeeID,
      Integer emergency,
      RequestStatus status)
      throws SQLException;

  void deleteSanitationRequest(Integer requestID) throws SQLException;

  void exportSanitationReqCSV(String filename) throws Exception;

  // TODO write this once Edison makes the DB
  // ArrayList<Request> getEmployeeRequests(Integer employeeID);
}
