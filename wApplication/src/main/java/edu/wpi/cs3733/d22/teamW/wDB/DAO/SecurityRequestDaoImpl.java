package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.SecurityRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.SecurityRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SecurityRequestDaoImpl implements SecurityRequestDao {
  Statement statement;

  public SecurityRequestDaoImpl(Statement statement) {
    this.statement = statement;
    dropTable();
  }

  public void dropTable() {
    try {
      statement.execute("DROP TABLE SECURITYREQUESTS");
      System.out.println("Dropped Security Service Requests Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Security Service Requests Table");
    }
  }

  String CSVHeaderString =
      "ReqID,nodeID,employeeID,isEmergency,threatLevel,reqStatus,createdTimestamp,updatedTimestamp";

  public void createTable() throws SQLException {
    try {
      statement.execute(
          "CREATE TABLE SECURITYREQUESTS(\n"
              + "ReqID INT,"
              + "nodeID varchar(25),"
              + "employeeID INT,"
              + "isEmergency INT,"
              + "threatLevel INT,"
              + "reqStatus INT,"
              + "createdTimeStamp TIMESTAMP,"
              + "updatedTimeStamp TIMESTAMP,"
              + "constraint secServReq_Employee_FK foreign key (employeeID) references EMPLOYEES(employeeID),"
              + "constraint secServReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),\n"
              + "constraint secServReq_PK primary key (ReqID),\n"
              + "constraint secServReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
              + "constraint secServIsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
    } catch (SQLException e) {
      System.out.println("Security Request Table failed to be created!");
      throw (e);
    }
  }

  @Override
  public void addSecurityRequest(SecurityRequest csr) throws SQLException {
    statement.executeUpdate(
        String.format("INSERT INTO SECURITYREQUESTS VALUES(%s)", csr.toValuesString()));
  }

  @Override
  public void changeSecurityRequest(SecurityRequest csr) throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE SECURITYREQUESTS SET NODEID = '%s', EMPLOYEEID = %d, ISEMERGENCY = %d, THREATLEVEL =%d, REQSTATUS = %d, UPDATEDTIMESTAMP = '%s' WHERE REQID = %d",
            csr.getNodeID(),
            csr.getEmployeeID(),
            csr.getEmergency(),
            csr.getThreatLevel(),
            csr.getStatus().getValue(),
            new Timestamp(System.currentTimeMillis()),
            csr.getRequestID()));
  }

  @Override
  public void deleteSecurityRequest(Integer requestID) throws SQLException {
    statement.executeUpdate(
        String.format("DELETE FROM SECURITYREQUESTS WHERE REQID = %d", requestID));
  }

  @Override
  public void exportSecurityReqCSV(String filename) {
    File csvOutputFile = new File(filename);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (Request m : getAllSecurityRequests()) {
        pw.println();
        pw.print(m.toCSVString());
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s", filename));
      e.printStackTrace();
    }
  }

  @Override
  public SecurityRequest getSecurityRequest(Integer requestID) throws StatusError {
    SecurityRequest csr = null;
    try {
      ResultSet rs =
          statement.executeQuery(
              String.format("SELECT * FROM SECURITYREQUESTS WHERE REQID = %d", requestID));
      while (rs.next()) {
        ArrayList<String> csrData = new ArrayList<String>();
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
          csrData.add(rs.getString(i + 1));
        }
        csr = new SecurityRequest(csrData);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return csr;
  }

  @Override
  public ArrayList<Request> getAllSecurityRequests() {
    ArrayList<Request> csrList = new ArrayList<Request>();
    try {
      ResultSet allSecurityRequests = statement.executeQuery("SELECT * FROM SECURITYREQUESTS");
      while (allSecurityRequests.next()) {
        ArrayList<String> csrData = new ArrayList<String>();
        for (int i = 0; i < allSecurityRequests.getMetaData().getColumnCount(); i++) {
          csrData.add(allSecurityRequests.getString(i + 1));
        }
        csrList.add(new SecurityRequest(csrData));
      }
    } catch (SQLException e) {
      System.out.println("Query from SECURITYREQUESTS table failed.");
      e.printStackTrace();
    } catch (StatusError e) {
      System.out.println("Query from SECURITYREQUESTS table failed.");
      e.printStackTrace();
    }
    return csrList;
  }

  @Override
  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    ArrayList<Request> employeeRequestList = new ArrayList<>();
    try {
      ResultSet compRequests =
          statement.executeQuery(
              String.format("SELECT * FROM SECURITYREQUESTS WHERE EMPLOYEEID = %d", employeeID));
      while (compRequests.next()) {
        ArrayList<String> compReqData = new ArrayList<String>();

        for (int i = 0; i < compRequests.getMetaData().getColumnCount(); i++) {
          compReqData.add(compRequests.getString(i + 1));
        }

        employeeRequestList.add(new SecurityRequest(compReqData));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (StatusError e) {
      e.printStackTrace();
    }
    return employeeRequestList;
  }

  @Override
  public void updateSecurityRequestsAtLocation(String nodeID) throws Exception {
    ResultSet resultSet =
        statement.executeQuery(
            String.format("SELECT ReqID FROM SECURITYREQUESTS WHERE nodeID='%s'", nodeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = resultSet.getInt("ReqID");
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      SecurityRequestManager.getSecurityRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE SECURITYREQUESTS SET NODEID='%s' WHERE NODEID='%s'",
            LocationManager.getLocationManager().getNoneLocation(), nodeID));
  }

  @Override
  public void updateSecurityRequestsWithEmployee(Integer employeeID) throws Exception {
    ResultSet resultSet =
        statement.executeQuery(
            String.format("SELECT ReqID FROM SECURITYREQUESTS WHERE employeeID= %d", employeeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = Integer.parseInt(resultSet.getString("ReqID"));
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      SecurityRequestManager.getSecurityRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE SECURITYREQUESTS SET employeeID=%d WHERE employeeID=%d",
            EmployeeManager.getEmployeeManager().getDeletedEmployee(), employeeID));
  }
}
