package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.GiftDeliveryRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.GiftDeliveryRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.io.File;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class GiftDeliveryRequestDaoImpl implements GiftDeliveryRequestDao {

  Statement statement;

  public GiftDeliveryRequestDaoImpl(Statement statement) {
    this.statement = statement;
    dropTable();
  }

  public void dropTable() {
    try {
      statement.execute("DROP TABLE GIFTDELIVERYREQUESTS");
      System.out.println("Dropped Gift Service Requests Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Gift Service Requests Table");
    }
  }

  String CSVHeaderString =
      "ReqID,recipientFirstName,recipientLastName,nodeID,employeeID,isEmergency,reqStatus,createdTimestamp,updatedTimestamp";

  public void createTable() throws SQLException {
    try {
      statement.execute(
          "CREATE TABLE GIFTDELIVERYREQUESTS(\n"
              + "ReqID INT,"
              + "recipientFirstName varchar(50),"
              + "recipientLastName varchar(50),"
              + "nodeID varchar(25),"
              + "employeeID INT,"
              + "isEmergency INT,"
              + "reqStatus INT,"
              + "createdTimeStamp TIMESTAMP,"
              + "updatedTimeStamp TIMESTAMP,"
              + "constraint giftDeliveryReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),\n"
              + "constraint giftDeliveryReq_Employee_FK foreign key (employeeID) references EMPLOYEES(employeeID),"
              + "constraint giftDeliveryReq_PK primary key (ReqID),\n"
              + "constraint giftDeliveryReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
              + "constraint giftDeliveryIsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
    } catch (SQLException e) {
      System.out.println("Gift Service Request Table failed to be created!");
      throw (e);
    }
  }

  @Override
  public ArrayList<Request> getAllGiftDeliveryRequests() throws Exception {
    ArrayList<Request> srList = new ArrayList<Request>();
    try {
      ResultSet allServiceRequests = statement.executeQuery("SELECT * FROM GIFTDELIVERYREQUESTS");
      while (allServiceRequests.next()) {
        ArrayList<String> csrData = new ArrayList<String>();
        for (int i = 0; i < allServiceRequests.getMetaData().getColumnCount(); i++) {
          csrData.add(allServiceRequests.getString(i + 1));
        }
        srList.add(new GiftDeliveryRequest(csrData));
      }
    } catch (SQLException e) {
      System.out.println("Query from gift service request table failed.");
      e.printStackTrace();
    } catch (StatusError e) {
      System.out.println("Query from gift service request table failed.");
      e.printStackTrace();
    }
    return srList;
  }

  @Override
  public void addGiftDeliveryRequest(GiftDeliveryRequest sr) throws SQLException {
    statement.executeUpdate(
        String.format("INSERT INTO GIFTDELIVERYREQUESTS VALUES(%s)", sr.toValuesString()));
  }

  @Override
  public GiftDeliveryRequest getGiftDeliveryRequest(Integer requestID) throws Exception {
    GiftDeliveryRequest csr = null;
    try {
      ResultSet rs =
          statement.executeQuery(
              String.format("SELECT * FROM GIFTDELIVERYREQUESTS WHERE REQID = %d", requestID));
      while (rs.next()) {
        ArrayList<String> csrData = new ArrayList<String>();
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
          csrData.add(rs.getString(i + 1));
        }
        csr = new GiftDeliveryRequest(csrData);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return csr;
  }

  @Override
  public void changeGiftDeliveryRequest(GiftDeliveryRequest sr) throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE GIFTDELIVERYREQUESTS SET recipientFirstName = '%s', recipientLastName = '%s', NODEID = '%s', EMPLOYEEID = %d, ISEMERGENCY = %d, REQSTATUS = %d, UPDATEDTIMESTAMP = '%s' WHERE REQID = %d",
            sr.getRecipientFirstName(),
            sr.getRecipientLastName(),
            sr.getNodeID(),
            sr.getEmployeeID(),
            sr.getEmergency(),
            sr.getStatus().getValue(),
            new Timestamp(System.currentTimeMillis()),
            sr.getRequestID()));
  }

  @Override
  public void deleteGiftDeliveryRequest(Integer requestID) throws SQLException {
    statement.executeUpdate(
        String.format("DELETE FROM GIFTDELIVERYREQUESTS WHERE REQID = %d", requestID));
  }

  @Override
  public void exportGiftDeliveryReqCSV(String filename) throws Exception {
    File csvOutputFile = new File(filename);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (Request m : getAllGiftDeliveryRequests()) {
        pw.println();
        pw.print(m.toCSVString());
      }
    }
  }

  @Override
  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    ArrayList<Request> employeeRequestList = new ArrayList<>();
    try {
      ResultSet requests =
          statement.executeQuery(
              String.format(
                  "SELECT * FROM GIFTDELIVERYREQUESTS WHERE EMPLOYEEID = %d", employeeID));
      while (requests.next()) {
        ArrayList<String> requestData = new ArrayList<String>();

        for (int i = 0; i < requests.getMetaData().getColumnCount(); i++) {
          requestData.add(requests.getString(i + 1));
        }

        employeeRequestList.add(new GiftDeliveryRequest(requestData));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return employeeRequestList;
  }

  @Override
  public void updateGiftDeliveryRequestsAtLocation(String nodeID) throws Exception {
    ResultSet resultSet =
        statement.executeQuery(
            String.format("SELECT ReqID FROM GIFTDELIVERYREQUESTS WHERE nodeID='%s'", nodeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = resultSet.getInt("ReqID");
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      GiftDeliveryRequestManager.getGiftDeliveryRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE GIFTDELIVERYREQUESTS SET NODEID='%s' WHERE NODEID='%s'",
            LocationManager.getLocationManager().getNoneLocation(), nodeID));
  }

  @Override
  public void updateGiftDeliveryRequestsWithEmployee(Integer employeeID) throws Exception {
    ResultSet resultSet =
        statement.executeQuery(
            String.format(
                "SELECT ReqID FROM GIFTDELIVERYREQUESTS WHERE employeeID= %d", employeeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = Integer.parseInt(resultSet.getString("ReqID"));
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      GiftDeliveryRequestManager.getGiftDeliveryRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE GIFTDELIVERYREQUESTS SET employeeID=%d WHERE employeeID=%d",
            EmployeeManager.getEmployeeManager().getDeletedEmployee(), employeeID));
  }
}
