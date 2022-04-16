package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.SanitationRequest;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SanitationRequestDao {

  ArrayList<Request> getAllSanitationRequests() throws Exception;

  void addSanitationRequest(SanitationRequest sr) throws SQLException;

  SanitationRequest getSanitationRequest(Integer requestID) throws Exception;

  void changeSanitationRequest(SanitationRequest sr) throws SQLException;

  void deleteSanitationRequest(Integer requestID) throws SQLException;

  void exportSanitationReqCSV(String filename) throws Exception;

  void updateSanitationRequestsAtLocation(String nodeID) throws SQLException, StatusError;

  void updateSanitationRequestsWithEmployee(Integer employeeID) throws Exception;
}
