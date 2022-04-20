package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.InvalidUnit;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoMedicine;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class MedRequestDaoImpl implements MedRequestDao {

  private Statement statement;

  MedRequestDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() {
    try {
      statement.execute("DROP TABLE MEDREQUESTS");
      System.out.println("Dropped Medicine Request Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Medicine Request Table");
    }
  }

  String CSVHeaderString =
      "requestID,medicine,quantity,unit,nodeID,employeeID,emergency,status,createdTimestamp,updatedTimestamp";

  void createTable() throws SQLException {

    try {
      statement.execute(
          "CREATE TABLE MEDREQUESTS("
              + "requestID INT,"
              + "medicine varchar(25),"
              + "quantity DOUBLE,"
              + "Unit varchar(25),"
              + "nodeID varchar(25),"
              + "employeeID INT,"
              + "isEmergency INT,"
              + "reqStatus INT, "
              + "createdTimestamp timestamp, "
              + "updatedTimestamp timestamp, "
              + "constraint MedReq_Employee_FK foreign key (employeeID) references EMPLOYEES(employeeID),"
              + "constraint MEDIREQ_Location_FK foreign key (nodeID) references LOCATIONS,"
              + "constraint MediReq_PK primary key (requestID),"
              + "constraint MediReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
              + "constraint MediIsEmergency_check check (isEmergency = 0 or isEmergency = 1)"
              + ")");
    } catch (SQLException e) {
      System.out.println("Medicine Service Request Table failed to be created!");
      throw (e);
    }
    System.out.println("Medicine Service Request Table created!");
  }

  @Override
  public void addMedRequest(MedRequest mr) throws SQLException {
    statement.executeUpdate(
        String.format("INSERT INTO MEDREQUESTS VALUES (%s)", mr.toValuesString()));
  }

  @Override
  public void changeMedRequest(MedRequest mr) throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE MEDREQUESTS SET MEDICINE='%s', QUANTITY = %.2f, UNIT = '%s', NODEID='%s', EMPLOYEEID=%d, ISEMERGENCY=%d, REQSTATUS=%d, UPDATEDTIMESTAMP = '%s' WHERE REQUESTID=%d",
            mr.getMedicineType(),
            mr.getQuantity(),
            mr.getUnit().getUnits(),
            mr.getNodeID(),
            mr.getEmployeeID(),
            mr.getEmergency(),
            mr.getStatus().getValue(),
            new Timestamp(System.currentTimeMillis()),
            mr.getRequestID()));
  }

  @Override
  public void deleteMedRequest(Integer id) throws SQLException {
    statement.executeUpdate(String.format("DELETE FROM MEDREQUESTS WHERE REQUESTID=%d", id));
  }

  @Override
  public Request getMedRequest(Integer id) throws SQLException {
    MedRequest mr = null;
    try {
      ResultSet medEquipRequests =
          statement.executeQuery(
              String.format("SELECT * FROM MEDREQUESTS WHERE REQUESTID = %d", id));

      medEquipRequests.next();

      ArrayList<String> medRequestFields = new ArrayList<String>();
      for (int i = 0; i < medEquipRequests.getMetaData().getColumnCount(); i++) {
        medRequestFields.add(medEquipRequests.getString(i + 1));
      }
      mr = new MedRequest(medRequestFields);

    } catch (SQLException e) {
      System.out.println("Query from medicine request table failed.");
    } catch (NoMedicine e) {
      e.printStackTrace();
    } catch (StatusError e) {
      e.printStackTrace();
    } catch (InvalidUnit e) {
      e.printStackTrace();
    }
    return mr;
  }

  @Override
  public ArrayList<Request> getAllMedRequest() {
    ArrayList<Request> medRequestList = new ArrayList<Request>();

    try {
      ResultSet medRequests = statement.executeQuery("SELECT * FROM MEDREQUESTS");

      // Size of num LabServiceRequest fields
      ArrayList<String> medRequestData = new ArrayList<String>();

      while (medRequests.next()) {

        for (int i = 0; i < medRequests.getMetaData().getColumnCount(); i++) {
          medRequestData.add(i, medRequests.getString(i + 1));
        }

        medRequestList.add(new MedRequest(medRequestData));
      }

    } catch (SQLException | NoMedicine | StatusError | InvalidUnit e) {
      System.out.println("Query from medicine request table failed.");
    }
    return medRequestList;
  }

  public void updateMedRequestAtLocation(String nodeID) throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE MEDREQUESTS SET NODEID='%s' WHERE NODEID='%s'",
            LocationManager.getLocationManager().getNoneLocation(), nodeID));
  }

  @Override
  public void exportMedReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (Request m : getAllMedRequest()) {
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
      ResultSet medRequests =
          statement.executeQuery(
              String.format("SELECT * FROM MEDREQUESTS WHERE EMPLOYEEID = %d", employeeID));
      while (medRequests.next()) {
        ArrayList<String> medRequestData = new ArrayList<String>();

        for (int i = 0; i < medRequests.getMetaData().getColumnCount(); i++) {
          medRequestData.add(medRequests.getString(i + 1));
        }

        employeeRequestList.add(new MedRequest(medRequestData));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (StatusError e) {
      e.printStackTrace();
    } catch (InvalidUnit e) {
      e.printStackTrace();
    } catch (NoMedicine e) {
      e.printStackTrace();
    }
    return employeeRequestList;
  }

  public void updateMedReqAtLocation(String nodeID) throws Exception {

    ResultSet resultSet =
        statement.executeQuery(
            String.format("SELECT requestID FROM MEDREQUESTS WHERE nodeID='%s'", nodeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = resultSet.getInt("requestID");
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      MedRequestManager.getMedRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE MEDREQUESTS SET NODEID='%s' WHERE NODEID='%s'",
            LocationManager.getLocationManager().getNoneLocation(), nodeID));
  }

  @Override
  public void updateMedRequestsWithEmployee(Integer employeeID) throws Exception {

    ResultSet resultSet =
        statement.executeQuery(
            String.format("SELECT requestID FROM MEDREQUESTS WHERE employeeID=%d", employeeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = resultSet.getInt("requestID");
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      MedRequestManager.getMedRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE MEDREQUESTS SET employeeID=%d WHERE employeeID=%d",
            EmployeeManager.getEmployeeManager().getDeletedEmployee(), employeeID));
  }
}
