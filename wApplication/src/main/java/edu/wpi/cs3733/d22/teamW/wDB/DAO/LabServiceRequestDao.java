package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.LabServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LabServiceRequestDao {

  ArrayList<Request> getAllLabServiceRequests();

  void addLabServiceRequest(LabServiceRequest lsr) throws SQLException;

  void changeLabServiceRequest(
      Integer requestID,
      String labType,
      String nodeID,
      String employeeName,
      Integer emergency,
      RequestStatus status)
      throws SQLException;

  void deleteLabServiceRequest(Integer requestID) throws SQLException;

  void exportLabServiceReqCSV(String filename);
}
