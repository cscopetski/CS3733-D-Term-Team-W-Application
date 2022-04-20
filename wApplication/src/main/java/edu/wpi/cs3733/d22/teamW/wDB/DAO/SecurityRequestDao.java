package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.SecurityRequest;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SecurityRequestDao {

  void addSecurityRequest(SecurityRequest csr) throws SQLException;

  void changeSecurityRequest(SecurityRequest csr) throws SQLException;

  void deleteSecurityRequest(Integer requestID) throws SQLException;

  void exportSecurityReqCSV(String filename);

  SecurityRequest getSecurityRequest(Integer requestID) throws StatusError;

  ArrayList<Request> getAllSecurityRequests();

  ArrayList<Request> getEmployeeRequests(Integer employeeID);

  void updateSecurityRequestsAtLocation(String nodeID) throws Exception;

  void updateSecurityRequestsWithEmployee(Integer employeeID) throws Exception;
}
