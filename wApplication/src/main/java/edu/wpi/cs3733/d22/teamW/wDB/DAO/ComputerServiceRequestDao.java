package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ComputerServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ComputerServiceRequestDao {
  void addComputerServiceRequest(ComputerServiceRequest csr) throws SQLException;

  void changeComputerServiceRequest(ComputerServiceRequest csr) throws SQLException;

  void deleteComputerServiceRequest(Integer requestID) throws SQLException;

  void exportComputerServiceReqCSV(String filename);

  ComputerServiceRequest getComputerServiceRequest(Integer requestID) throws StatusError;

  ArrayList<Request> getAllComputerServiceRequests();

  void updateCompServiceRequestsAtLocation(String nodeID) throws Exception;

  void updateCompServiceRequestsWithEmployee(Integer employeeID) throws Exception;
}
