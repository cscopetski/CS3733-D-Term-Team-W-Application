package edu.wpi.cs3733.d22.teamW.wDB;

import java.util.ArrayList;

public interface LabServiceRequestDao {
  ArrayList<LabServiceRequest> getAllLabServiceRequests();

  void addLabServiceRequest(LabServiceRequest lsr);

  void changeLabServiceRequest(
      Integer requestID,
      String labType,
      Integer emergency,
      String nodeID,
      Integer status,
      String employeeName);

  void deleteLabServiceRequest(Integer requestID);

  void exportLabServiceReqCSV(String filename);
}
