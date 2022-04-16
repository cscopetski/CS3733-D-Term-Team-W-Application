package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ComputerServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ComputerServiceRequestDaoImpl implements ComputerServiceRequestDao {
  Statement statement;

  public ComputerServiceRequestDaoImpl(Statement statement) {
    this.statement = statement;
    dropTable();
  }

  public void dropTable() {
    try {
      statement.execute("DROP TABLE COMPUTERSERVICEREQUESTS");
      System.out.println("Dropped Computer Service Requests Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Computer Service Requests Table");
    }
  }

  String CSVHeaderString =
      "ReqID,nodeID,employeeID,isEmergency,reqStatus,createdTimestamp,updatedTimestamp";

  public void createTable() throws SQLException {
    try {
      statement.execute(
          "CREATE TABLE COMPUTERSERVICEREQUESTS(\n"
              + "ReqID INT,"
              + "nodeID varchar(25),"
              + "employeeID INT,"
              + "isEmergency INT,"
              + "reqStatus INT,"
              + "createdTimeStamp TIMESTAMP,"
              + "updatedTimeStamp TIMESTAMP,"
              + "constraint computerServReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),\n"
              + "constraint computerServReq_PK primary key (ReqID),\n"
              + "constraint computerServReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
              + "constraint ComputerServIsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
    } catch (SQLException e) {
      System.out.println("Computer Service Request Table failed to be created!");
      throw (e);
    }
  }

  @Override
  public void addComputerServiceRequest(ComputerServiceRequest csr) throws SQLException {
    statement.executeUpdate(
        String.format("INSERT INTO COMPUTERSERVICEREQUESTS VALUES(%s)", csr.toValuesString()));
  }

  // requestID, nodeID, employeeID, emergency, status, createdTimestamp, updatedTimestamp
  @Override
  public void changeComputerServiceRequest(ComputerServiceRequest csr) throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE COMPUTERSERVICEREQUESTS SET NODEID = '%s', EMPLOYEEID = %d, ISEMERGENCY = %d, REQSTATUS = %d, UPDATEDTIMESTAMP = '%s' WHERE REQID = %d",
            csr.getNodeID(),
            csr.getEmployeeID(),
            csr.getEmergency(),
            csr.getStatus().getValue(),
            new Timestamp(System.currentTimeMillis()),
            csr.getRequestID()));
  }

  @Override
  public void deleteComputerServiceRequest(Integer requestID) throws SQLException {
    statement.executeUpdate(
        String.format("DELETE FROM COMPUTERSERVICEREQUESTS WHERE REQID = %d", requestID));
  }

  @Override
  public void exportComputerServiceReqCSV(String filename) {
    File csvOutputFile = new File(filename);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (Request m : getAllComputerServiceRequests()) {
        pw.println();
        pw.print(m.toCSVString());
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s", filename));
      e.printStackTrace();
    }
  }

  @Override
  public ComputerServiceRequest getComputerServiceRequest(Integer requestID) throws StatusError {
    ComputerServiceRequest csr = null;
    try {
      ResultSet rs =
          statement.executeQuery(
              String.format("SELECT * FROM COMPUTERSERVICEREQUESTS WHERE REQID = %d", requestID));
      while (rs.next()) {
        ArrayList<String> csrData = new ArrayList<String>();
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
          csrData.add(rs.getString(i + 1));
        }
        csr = new ComputerServiceRequest(csrData);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return csr;
  }

  @Override
  public ArrayList<Request> getAllComputerServiceRequests() {
    ArrayList<Request> csrList = new ArrayList<Request>();
    try {
      ResultSet allComputerServiceRequests =
          statement.executeQuery("SELECT * FROM COMPUTERSERVICEREQUESTS");
      while (allComputerServiceRequests.next()) {
        ArrayList<String> csrData = new ArrayList<String>();
        for (int i = 0; i < allComputerServiceRequests.getMetaData().getColumnCount(); i++) {
          csrData.add(allComputerServiceRequests.getString(i + 1));
        }
        csrList.add(new ComputerServiceRequest(csrData));
      }
    } catch (SQLException e) {
      System.out.println("Query from computer service request table failed.");
      e.printStackTrace();
    } catch (StatusError e) {
      System.out.println("Query from computer service request table failed.");
      e.printStackTrace();
    }
    return csrList;
  }

  @Override
  public void updateCompServiceRequestsAtLocation(String nodeID) throws Exception {
    ResultSet resultSet =
        statement.executeQuery(
            String.format("SELECT ReqID FROM COMPUTERSERVICEREQUESTS WHERE nodeID='%s'", nodeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = resultSet.getInt("ReqID");
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      ComputerServiceRequestManager.getComputerServiceRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE COMPUTERSERVICEREQUESTS SET NODEID='%s' WHERE NODEID='%s'",
            LocationManager.getLocationManager().getNoneLocation(), nodeID));
  }

  @Override
  public void updateCompServiceRequestsWithEmployee(Integer employeeID) throws Exception {
    ResultSet resultSet =
        statement.executeQuery(
            String.format(
                "SELECT ReqID FROM COMPUTERSERVICEREQUESTS WHERE employeeID= %d", employeeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = Integer.parseInt(resultSet.getString("ReqID"));
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      ComputerServiceRequestManager.getComputerServiceRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE COMPUTERSERVICEREQUESTS SET employeeID=%d WHERE employeeID=%d",
            EmployeeManager.getEmployeeManager().getDeletedEmployee(), employeeID));
  }

  @Override
  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    ArrayList<Request> employeeRequestList = new ArrayList<>();
    try {
      ResultSet compRequests =
          statement.executeQuery(
              String.format(
                  "SELECT * FROM COMPUTERSERVICEREQUESTS WHERE EMPLOYEEID = %d", employeeID));
      while (compRequests.next()) {
        ArrayList<String> compReqData = new ArrayList<String>();

        for (int i = 0; i < compRequests.getMetaData().getColumnCount(); i++) {
          compReqData.add(compRequests.getString(i + 1));
        }

        employeeRequestList.add(new ComputerServiceRequest(compReqData));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (StatusError e) {
      e.printStackTrace();
    }
    return employeeRequestList;
  }
}
