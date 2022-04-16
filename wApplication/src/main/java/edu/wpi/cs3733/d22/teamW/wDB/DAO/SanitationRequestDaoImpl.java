package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.SanitationRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SanitationRequestDaoImpl implements SanitationRequestDao {

  Statement statement;

  SanitationRequestDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() {
    try {
      statement.execute("DROP TABLE SANITATIONREQUESTS");
      System.out.println("Dropped Sanitation Requests Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Sanitation Requests Table");
    }
  }

  String CSVHeaderString =
      "ReqID,Sanitation,nodeID,employeeID,isEmergency,reqStatus,createdTimestamp,updatedTimestamp";

  void createTable() throws SQLException {

    try {
      statement.execute(
          "CREATE TABLE SANITATIONREQUESTS(\n"
              + "ReqID INT,\n"
              + "Sanitation varchar(25),\n"
              + "nodeID varchar(25),"
              + "employeeID INT,"
              + "isEmergency INT,"
              + "reqStatus INT,"
              + "createdTimeStamp TIMESTAMP,"
              + "updatedTimeStamp TIMESTAMP,"
              + "constraint sanReq_PK primary key (ReqID),\n"
              + "constraint sanReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),\n"
              + "constraint sanReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
              + "constraint sanIsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
    } catch (SQLException e) {
      System.out.println("Sanitation Request Table failed to be created!");
      throw (e);
    }
  }

  @Override
  public SanitationRequest getSanitationRequest(Integer requestID) throws Exception {
    SanitationRequest sr = null;

    try {
      ResultSet sanRequests =
          statement.executeQuery(
              String.format("SELECT * FROM SANITATIONREQUESTS WHERE REQID = %d", requestID));

      // Size of num LabServiceRequest fields
      ArrayList<String> sanRequestData = new ArrayList<String>();

      while (sanRequests.next()) {

        for (int i = 0; i < sanRequests.getMetaData().getColumnCount(); i++) {
          sanRequestData.add(i, sanRequests.getString(i + 1));
        }

        sr = new SanitationRequest(sanRequestData);
      }

    } catch (SQLException e) {
      System.out.println("Query from cleaning request table failed.");
    }
    return sr;
  }

  @Override
  public ArrayList<Request> getAllSanitationRequests() throws Exception {
    ArrayList<Request> sanRequestList = new ArrayList<>();

    try {
      ResultSet sanRequests = statement.executeQuery("SELECT * FROM SANITATIONREQUESTS");

      // Size of num LabServiceRequest fields
      ArrayList<String> sanRequestData = new ArrayList<String>();

      while (sanRequests.next()) {

        for (int i = 0; i < sanRequests.getMetaData().getColumnCount(); i++) {
          sanRequestData.add(i, sanRequests.getString(i + 1));
        }

        sanRequestList.add(new SanitationRequest(sanRequestData));
      }

    } catch (SQLException e) {
      System.out.println("Query from cleaning request table failed.");
    }
    return sanRequestList;
  }

  @Override
  public void addSanitationRequest(SanitationRequest sr) throws SQLException {
    statement.executeUpdate(
        String.format("INSERT INTO SANITATIONREQUESTS VALUES (%s)", sr.toValuesString()));
  }

  @Override
  public void changeSanitationRequest(SanitationRequest sr) throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE SANITATIONREQUESTS SET SANITATION='%s', NODEID='%s', EMPLOYEEID=%d, ISEMERGENCY=%d, REQSTATUS=%d, UPDATEDTIMESTAMP='%s' WHERE REQID=%d",
            sr.getSanitationReqType().getString(),
            sr.getNodeID(),
            sr.getEmployeeID(),
            sr.getEmergency(),
            sr.getStatus().getValue(),
            new Timestamp(System.currentTimeMillis()),
            sr.getRequestID()));
  }

  @Override
  public void deleteSanitationRequest(Integer requestID) throws SQLException {
    RequestFactory.getRequestFactory().getReqIDList().remove(requestID);
    statement.executeUpdate(
        String.format("DELETE FROM SANITATIONREQUESTS WHERE REQID=%d", requestID));
  }

  @Override
  public void exportSanitationReqCSV(String fileName) throws Exception {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (Request m : getAllSanitationRequests()) {
        pw.println();
        pw.print(m.toCSVString());
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }

  // TODO uncomment this once Edison writes the new DB
  /*
  @Override
  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    ArrayList<Request> employeeRequestList = new ArrayList<>();
    try {
      ResultSet sanitationRequests =
              statement.executeQuery(
                      String.format("SELECT * FROM SANITATIONREQUESTS WHERE EMPLOYEEID = %d", employeeID));
      while (sanitationRequests.next()) {
        ArrayList<String> sanitationRequestData = new ArrayList<String>();

        for (int i = 0; i < sanitationRequests.getMetaData().getColumnCount(); i++) {
          sanitationRequestData.add(sanitationRequests.getString(i + 1));
        }

        employeeRequestList.add(new SanitationRequest(sanitationRequestData));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return employeeRequestList;
  }
   */
}
