package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingLabServiceRequestType;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.LabServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class LabServiceRequestDaoImpl implements LabServiceRequestDao {

  Statement statement;

  LabServiceRequestDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() {
    try {
      statement.execute("DROP TABLE LABSERVICEREQUESTS");
      System.out.println("Dropped Lab Service Requests Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Lab Service Requests Table");
    }
  }

  String CSVHeaderString =
      "labReqID,labType,nodeID,employeeID,isEmergency,reqStatus,createdTimestamp,updatedTimestamp";

  void createTable() throws SQLException {

    try {
      statement.execute(
          "CREATE TABLE LABSERVICEREQUESTS(\n"
              + "                labReqID INT,\n"
              + "                labType varchar(25),\n"
              + "                nodeID varchar(25),\n"
              + "                employeeID INT,\n"
              + "                isEmergency INT,\n"
              + "                reqStatus INT, \n"
              + "                createdTimestamp timestamp, \n"
              + "                updatedTimestamp timestamp, \n"
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

      while (labServiceRequests.next()) {
        ArrayList<String> labServiceRequestData = new ArrayList<String>();

        for (int i = 0; i < labServiceRequests.getMetaData().getColumnCount(); i++) {
          labServiceRequestData.add(labServiceRequests.getString(i + 1));
        }

        labServiceRequestList.add(new LabServiceRequest(labServiceRequestData));
      }

    } catch (SQLException e) {
      System.out.println("Query from lab service request table failed.");
    } catch (StatusError e) {
      e.printStackTrace();
    } catch (NonExistingLabServiceRequestType e) {
      e.printStackTrace();
    }
    return labServiceRequestList;
  }

  @Override
  public void addLabServiceRequest(LabServiceRequest lsr) throws SQLException {
    statement.executeUpdate(
        String.format("INSERT INTO LABSERVICEREQUESTS VALUES (%s)", lsr.toValuesString()));
  }

  @Override
  public void changeLabServiceRequest(LabServiceRequest lsr) throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE LABSERVICEREQUESTS SET LABTYPE='%s', NODEID='%s', EMPLOYEEID= %d, ISEMERGENCY=%d, REQSTATUS=%d, UPDATEDTIMESTAMP = '%s' WHERE LABREQID=%d",
            lsr.getLabType().getString(),
            lsr.getNodeID(),
            lsr.getEmployeeID(),
            lsr.getEmergency(),
            lsr.getStatus().getValue(),
            new Timestamp(System.currentTimeMillis()),
            lsr.getRequestID()));
  }

  @Override
  public void deleteLabServiceRequest(Integer requestID) throws SQLException {
    RequestFactory.getRequestFactory().getReqIDList().remove(requestID);
    statement.executeUpdate(
        String.format("DELETE FROM LABSERVICEREQUESTS WHERE LABREQID=%d", requestID));
  }

  @Override
  public void exportLabServiceReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

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

  @Override
  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    ArrayList<Request> employeeRequestList = new ArrayList<>();
    try {
      ResultSet empLabRequests =
          statement.executeQuery(
              String.format("SELECT * FROM LABSERVICEREQUESTS WHERE EMPLOYEEID = %d", employeeID));
      while (empLabRequests.next()) {
        ArrayList<String> labServiceRequestData = new ArrayList<String>();

        for (int i = 0; i < empLabRequests.getMetaData().getColumnCount(); i++) {
          labServiceRequestData.add(empLabRequests.getString(i + 1));
        }

        employeeRequestList.add(new LabServiceRequest(labServiceRequestData));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (StatusError e) {
      e.printStackTrace();
    } catch (NonExistingLabServiceRequestType e) {
      e.printStackTrace();
    }
    return employeeRequestList;
  }
}
