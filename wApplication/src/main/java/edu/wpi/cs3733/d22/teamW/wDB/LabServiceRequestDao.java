package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LabServiceRequestDao {
  ArrayList<LabServiceRequest> getAllLabServiceRequests();

  void addLabServiceRequest(LabServiceRequest lsr) throws SQLException;

  void changeLabServiceRequest(
      Integer requestID,
      String labType,
      String nodeID,
      String employeeName,
      Integer emergency,
      Integer status)
      throws SQLException;

  void deleteLabServiceRequest(Integer requestID);

  void exportLabServiceReqCSV(String filename);
}
