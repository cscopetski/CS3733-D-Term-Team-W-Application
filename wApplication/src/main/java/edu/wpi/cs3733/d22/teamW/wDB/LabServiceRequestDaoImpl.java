package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LabServiceRequestDaoImpl implements LabServiceRequestDao {

  DBController dbController = DBController.getDBController();
  ArrayList<LabServiceRequest> labServiceRequestList;

  public LabServiceRequestDaoImpl() {
    setLabServiceRequestList();
  }

  public void setLabServiceRequestList() {
    labServiceRequestList = new ArrayList<LabServiceRequest>();

    try {
      ResultSet labServiceRequests = dbController.executeQuery("SELECT * FROM LABSERVICEREQUESTS");

      // Size of num LabServiceRequest fields
      int size = 6;
      ArrayList<String> labServiceRequestData = new ArrayList<String>();

      while (labServiceRequests.next()) {

        for (int i = 0; i < size; i++) {
          labServiceRequestData.add(i, labServiceRequests.getString(i + 1));
        }

        labServiceRequestList.add(new LabServiceRequest(labServiceRequestData));
      }

    } catch (SQLException e) {
      System.out.println("Query from lab service request table failed.");
    }
  }

  @Override
  public ArrayList<LabServiceRequest> getAllLabServiceRequests() {
    return labServiceRequestList;
  }

  @Override
  public void addLabServiceRequest(LabServiceRequest lsr) throws SQLException {
    labServiceRequestList.add(lsr);
    dbController.executeUpdate(
        String.format("INSERT INTO LABSERVICEREQUESTS VALUES (%s)", lsr.toValuesString()));
  }

  @Override
  public void changeLabServiceRequest(
      Integer requestID,
      String labType,
      String nodeID,
      String employeeName,
      Integer emergency,
      Integer status)
      throws SQLException {
    int index = getIndexOf(requestID);
    if (index == -1) {
      System.out.println(
          "The database dose not contain a lab service request with an ID of " + requestID);
    } else {
      labServiceRequestList.get(index).setLabType(labType);
      labServiceRequestList.get(index).setEmergency(emergency);
      labServiceRequestList.get(index).setNodeID(nodeID);
      labServiceRequestList.get(index).setStatus(status);
      labServiceRequestList.get(index).setEmployeeName(employeeName);
      dbController.executeUpdate(
          String.format(
              "UPDATE LABSERVICEREQUESTS SET LABTYPE='%s', NODEID='%s', EMPLOYEENAME='%s', ISEMERGENCY=%d, REQSTATUS=%d WHERE LABREQID=%d",
              labType, nodeID, employeeName, emergency, status, requestID));
    }
  }

  @Override
  public void deleteLabServiceRequest(Integer requestID) {}

  @Override
  public void exportLabServiceReqCSV(String filename) {}

  private int getIndexOf(int inputID) {
    int size = labServiceRequestList.size();
    boolean found = false;
    for (int i = 0; i < size; i++) {
      if (labServiceRequestList.get(i).getRequestID() == (inputID)) {
        return i;
      }
    }
    return -1;
  }
}
