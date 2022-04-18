package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.CleaningRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CleaningRequestDaoImpl implements CleaningRequestDao {
  Statement statement;

  CleaningRequestDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() throws SQLException {
    try {
      statement.execute("DROP TABLE CLEANINGREQUESTS");
      System.out.println("Dropped Cleaning Requests Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Cleaning Requests Table");
    }
  }

  String CSVHeaderString =
      "ReqID,itemID,nodeID,employeeID,isEmergency,reqStatus,createdTimestamp,updatedTimestamp";

  void createTable() throws SQLException {

    try {
      statement.execute(
          "CREATE TABLE CLEANINGREQUESTS(\n"
              + "ReqID INT,\n"
              + "itemID varchar(25),\n"
              + "nodeID varchar(25),"
              + "employeeID INT,"
              + "isEmergency INT,"
              + "reqStatus INT,"
              + "createdTimeStamp TIMESTAMP,"
              + "updatedTimeStamp TIMESTAMP,"
              + "constraint cleanReq_itemID_FK foreign key (itemID) references MEDICALEQUIPMENT(medID),\n"
              + "constraint cleanReq_Employee_FK foreign key (employeeID) references EMPLOYEES(employeeID),"
              + "constraint cleanReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),\n"
              + "constraint cleanReq_PK primary key (ReqID),\n"
              + "constraint cleaningReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
              + "constraint CleanIsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
    } catch (SQLException e) {
      System.out.println("Lab Service Request Table failed to be created!");
      throw (e);
    }
  }

  @Override
  public CleaningRequest getCleaningRequest(Integer requestID) throws StatusError {
    CleaningRequest cr = null;

    try {
      ResultSet cleanRequests =
          statement.executeQuery(
              String.format("SELECT * FROM CLEANINGREQUESTS WHERE REQID = %d", requestID));

      // Size of num LabServiceRequest fields

      while (cleanRequests.next()) {
        ArrayList<String> cleanRequestData = new ArrayList<String>();

        for (int i = 0; i < cleanRequests.getMetaData().getColumnCount(); i++) {
          cleanRequestData.add(cleanRequests.getString(i + 1));
        }

        cr = new CleaningRequest(cleanRequestData);
      }

    } catch (SQLException e) {
      System.out.println("Query from cleaning request table failed.");
    }
    return cr;
  }

  @Override
  public ArrayList<String> getCleaningLocation() throws SQLException {
    ArrayList<String> listOfNodeID = new ArrayList<>();
    ResultSet info = statement.executeQuery("SELECT DISTINCT NODEID FROM CLEANINGREQUESTS");

    // ArrayList<String> cleanRequestData = new ArrayList<String>();

    while (info.next()) {
      listOfNodeID.add(info.getString(1));
    }

    return listOfNodeID;
  }

  @Override
  public ArrayList<Integer> CleaningRequestAtLocation(String nodeID) throws SQLException {
    ArrayList<Integer> count = new ArrayList<>();

    ResultSet results =
        statement.executeQuery(
            String.format(
                "SELECT REQID FROM CLEANINGREQUESTS WHERE NODEID = '%s' AND reqStatus = 0",
                nodeID));

    while (results.next()) {
      count.add(results.getInt("REQID"));
    }
    return count;
  }

  @Override
  public ArrayList<Request> getAllCleaningRequests() {
    ArrayList<Request> cleanRequestList = new ArrayList<>();

    try {
      ResultSet cleanRequests = statement.executeQuery("SELECT * FROM CLEANINGREQUESTS");

      while (cleanRequests.next()) {
        ArrayList<String> cleanRequestData = new ArrayList<String>();

        for (int i = 0; i < cleanRequests.getMetaData().getColumnCount(); i++) {
          cleanRequestData.add(cleanRequests.getString(i + 1));
        }

        cleanRequestList.add(new CleaningRequest(cleanRequestData));
      }

    } catch (SQLException e) {
      System.out.println("Query from cleaning request table failed.");
    } catch (StatusError e) {
      e.printStackTrace();
    }
    return cleanRequestList;
  }

  @Override
  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    ArrayList<Request> employeeRequestList = new ArrayList<>();
    try {
      ResultSet cleanRequests =
          statement.executeQuery(
              String.format("SELECT * FROM CLEANINGREQUESTS WHERE EMPLOYEEID = %d", employeeID));
      while (cleanRequests.next()) {
        ArrayList<String> cleanRequestData = new ArrayList<String>();

        for (int i = 0; i < cleanRequests.getMetaData().getColumnCount(); i++) {
          cleanRequestData.add(cleanRequests.getString(i + 1));
        }

        employeeRequestList.add(new CleaningRequest(cleanRequestData));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (StatusError e) {
      e.printStackTrace();
    }
    return employeeRequestList;
  }

  @Override
  public void addCleaningRequest(CleaningRequest cleaningRequest) throws SQLException {
    statement.executeUpdate(
        String.format(
            "INSERT INTO CLEANINGREQUESTS VALUES (%s)", cleaningRequest.toValuesString()));
  }

  @Override
  public void changeCleaningRequest(CleaningRequest cr) throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE CLEANINGREQUESTS SET ITEMID='%s', NODEID='%s', EMPLOYEEID=%d, ISEMERGENCY=%d, REQSTATUS=%d, UPDATEDTIMESTAMP='%s' WHERE REQID=%d",
            cr.getItemID(),
            cr.getNodeID(),
            cr.getEmployeeID(),
            cr.getEmergency(),
            cr.getStatus().getValue(),
            new Timestamp(System.currentTimeMillis()),
            cr.getRequestID()));
  }

  @Override
  public void updateCleaningRequestsAtLocation(String nodeID) throws Exception {
    ResultSet resultSet =
        statement.executeQuery(
            String.format("SELECT REQID FROM CLEANINGREQUESTS WHERE nodeID='%s'", nodeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = resultSet.getInt("REQID");
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      CleaningRequestManager.getCleaningRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE CLEANINGREQUESTS SET NODEID='%s' WHERE NODEID='%s'",
            LocationManager.getLocationManager().getNoneLocation(), nodeID));
  }

  @Override
  public void updateCleaningRequestsWithEquipment(String medID) throws Exception {

    ResultSet resultSet =
        statement.executeQuery(
            String.format("SELECT ReqID FROM CLEANINGREQUESTS WHERE itemID='%s'", medID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = resultSet.getInt("ReqID");
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      CleaningRequestManager.getCleaningRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE CLEANINGREQUESTS SET ITEMID='%s' WHERE ITEMID='%s'",
            MedEquipManager.getMedEquipManager().getDeletedEquipment(), medID));
  }

  @Override
  public void updateCleaningRequestsWithEmployee(Integer employeeID) throws Exception {

    ResultSet resultSet =
        statement.executeQuery(
            String.format("SELECT ReqID FROM CLEANINGREQUESTS WHERE employeeID= %d", employeeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = Integer.parseInt(resultSet.getString("ReqID"));
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      CleaningRequestManager.getCleaningRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE CLEANINGREQUESTS SET employeeID=%d WHERE employeeID=%d",
            EmployeeManager.getEmployeeManager().getDeletedEmployee(), employeeID));
  }

  @Override
  public void deleteCleaningRequest(Integer requestID) throws SQLException {
    RequestFactory.getRequestFactory().getReqIDList().remove(requestID);
    statement.executeUpdate(
        String.format("DELETE FROM CLEANINGREQUESTS WHERE REQID=%d", requestID));
  }

  @Override
  public void exportCleaningReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (Request m : getAllCleaningRequests()) {
        pw.println();
        pw.print(m.toCSVString());
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }

  @Override
  public CleaningRequest getCleaningRequest(String itemID) throws StatusError {
    CleaningRequest cr = null;
    /*   String queury =
    String.format(
        "SELECT * FROM CLEANINGREQUESTS WHERE (ITEMID = '%s' AND (REQSTATUS = %d OR REQSTATUS %d))",
        itemID, RequestStatus.InQueue.getValue(), RequestStatus.InProgress.getValue());*/
    String queury =
        String.format(
            "SELECT * FROM CLEANINGREQUESTS WHERE (ITEMID = '%s' AND REQSTATUS = %d)",
            itemID, RequestStatus.InQueue.getValue());
    System.out.println(queury);
    try {
      ResultSet cleanRequests = statement.executeQuery(queury);

      // Size of num LabServiceRequest fields

      while (cleanRequests.next()) {
        ArrayList<String> cleanRequestData = new ArrayList<String>();

        for (int i = 0; i < cleanRequests.getMetaData().getColumnCount(); i++) {
          cleanRequestData.add(cleanRequests.getString(i + 1));
        }

        cr = new CleaningRequest(cleanRequestData);
      }

    } catch (SQLException e) {
      System.out.println("Query from cleaning request table failed.");
    }
    return cr;
  }
}
