package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.LabServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LabServiceRequestDaoImpl implements LabServiceRequestDao {

  Statement statement;

  LabServiceRequestDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() throws SQLException {
    try {
      statement.execute("DROP TABLE LABSERVICEREQUESTS");
      System.out.println("Dropped Lab Service Requests Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Lab Service Requests Table");
      throw (e);
    }
  }

  void createTable() throws SQLException {

    try {
      statement.execute(
          "CREATE TABLE LABSERVICEREQUESTS(\n"
              + "                labReqID INT,\n"
              + "                labType varchar(25),\n"
              + "                nodeID varchar(25),\n"
              + "                employeeName varchar(50),\n"
              + "                isEmergency INT,\n"
              + "                reqStatus INT, \n"
              + "                constraint LabReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),\n"
              + "                constraint LabReq_PK primary key (labReqID),\n"
              + "                constraint LabReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
              + "                constraint LabIsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
    } catch (SQLException e) {
      System.out.println("Lab Service Request Table failed to be created!");
      throw (e);
    }
  }

  @Override
  public ArrayList<Request> getAllLabServiceRequests() {
    ArrayList<Request> labServiceRequestList = new ArrayList<>();

    try {
      ResultSet labServiceRequests = statement.executeQuery("SELECT * FROM LABSERVICEREQUESTS");

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
    return labServiceRequestList;
  }

  @Override
  public void addLabServiceRequest(LabServiceRequest lsr) throws SQLException {
    statement.executeUpdate(
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
    statement.executeUpdate(
        String.format(
            "UPDATE LABSERVICEREQUESTS SET LABTYPE='%s', NODEID='%s', EMPLOYEENAME='%s', ISEMERGENCY=%d, REQSTATUS=%d WHERE LABREQID=%d",
            labType, nodeID, employeeName, emergency, status, requestID));
  }

  @Override
  public void deleteLabServiceRequest(Integer requestID) throws SQLException {
    statement.executeUpdate(
        String.format("DELETE FROM LABSERVICEREQUESTS WHERE LABREQID=%d", requestID));
  }

  @Override
  public void exportLabServiceReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print("labReqID,labType,nodeID,employeeName,isEmergency,status");

      // print all locations
      for (Request m : getAllLabServiceRequests()) {
        pw.println();
        pw.print(m.toCSVString());
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }
}
