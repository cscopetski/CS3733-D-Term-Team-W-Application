package edu.wpi.cs3733.d22.teamW.wDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
  public void addLabServiceRequest(LabServiceRequest lsr) {}

  @Override
  public void changeLabServiceRequest(
      Integer requestID,
      String labType,
      Integer emergency,
      String nodeID,
      Integer status,
      String employeeName) {}

  @Override
  public void deleteLabServiceRequest(Integer requestID) {}

  @Override
  public void exportLabServiceReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print("labReqID,labType,nodeID,employeeName,isEmergency,status");

      // print all locations
      for (LabServiceRequest m : labServiceRequestList) {
        pw.println();
        pw.print(m.toCSVString());
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }
}
