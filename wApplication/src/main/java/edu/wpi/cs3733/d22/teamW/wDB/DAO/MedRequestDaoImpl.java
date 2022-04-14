package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoMedicine;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.Units;
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

  void createTable() throws SQLException {

    try {
      statement.execute(
          "CREATE TABLE MEDREQUESTS("
              + "requestID INT,"
              + "patientLast varchar(25),"
              + "patientFirst varchar(25),"
              + "medicine varchar(25),"
              + "quantity DOUBLE,"
              + "Unit varchar(25),"
              + "nodeID varchar(25),"
              + "BedNum INT,"
              + "employeeID INT,"
              + "isEmergency INT,"
              + "reqStatus INT, "
              + "createdTimestamp timestamp, "
              + "updatedTimestamp timestamp, "
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
  public void changeMedRequest(
      Integer requestID,
      String patientLast,
      String patientFirst,
      String medicine,
      Double quantity,
      Units unit,
      String nodeID,
      Integer bedNumber,
      Integer employeeID,
      Integer emergency,
      RequestStatus status)
      throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE MEDREQUESTS SET PATIENTLAST='%s', PATIENTFIRST='%s', MEDICINE='%s', QUANTITY = %.2f, UNIT = '%s', NODEID='%s', BEDNUM = %d, EMPLOYEEID=%d, ISEMERGENCY=%d, REQSTATUS=%d, UPDATEDTIMESTAMP = '%s' WHERE REQUESTID=%d",
            patientLast,
            patientFirst,
            medicine,
            quantity,
            unit.getUnits(),
            nodeID,
            bedNumber,
            employeeID,
            emergency,
            status.getValue(),
            new Timestamp(System.currentTimeMillis()),
            requestID));
  }

  @Override
  public void deleteMedRequest(Integer id) throws SQLException {
    statement.executeUpdate(String.format("DELETE FROM MEDREQUESTS WHERE REQUESTID=%d", id));
  }

  @Override
  public Request getMedRequest(Integer id) throws SQLException {
    MedRequest mr = null;
    try {
      ResultSet medRequest =
          statement.executeQuery(
              String.format("SELECT * FROM MEDREQUESTS WHERE REQUESTID = %d", id));

      medRequest.next();

      Integer medreqID = medRequest.getInt("REQUESTID");
      String patientLast = medRequest.getString("PATIENTLAST");
      String patientFirst = medRequest.getString("PATIENTFIRST");
      String medicine = medRequest.getString("MEDICINE");
      Double quantity = medRequest.getDouble("QUANTITY");
      String unit = medRequest.getString("Unit");
      String nodeID = medRequest.getString("NODEID");
      Integer bedNum = medRequest.getInt("BEDNUM");
      Integer employeeID = medRequest.getInt("EMPLOYEEID");
      Integer isEmergency = medRequest.getInt("ISEMERGENCY");
      Integer reqStatus = medRequest.getInt("REQSTATUS");
      String createdTimeStamp = medRequest.getString("CREATEDTIMESTAMP");
      String updatedTimeStamp = medRequest.getString("UPDATEDTIMESTAMP");
      ArrayList<String> medEquipRequestData = new ArrayList<String>();
      medEquipRequestData.add(String.format("%d", medreqID));
      medEquipRequestData.add(patientLast);
      medEquipRequestData.add(patientFirst);
      medEquipRequestData.add(medicine);
      medEquipRequestData.add(String.format("%.2f", quantity));
      medEquipRequestData.add(unit);
      medEquipRequestData.add(nodeID);
      medEquipRequestData.add(String.format("%d", bedNum));
      medEquipRequestData.add(String.format("%d", employeeID));
      medEquipRequestData.add(String.format("%d", isEmergency));
      medEquipRequestData.add(String.format("%d", reqStatus));
      medEquipRequestData.add(createdTimeStamp);
      medEquipRequestData.add(updatedTimeStamp);

      mr = new MedRequest(medEquipRequestData);

    } catch (SQLException e) {
      System.out.println("Query from medicine request table failed.");
    } catch (NoMedicine e) {
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
      int size = 8;
      ArrayList<String> medRequestData = new ArrayList<String>();

      while (medRequests.next()) {

        for (int i = 0; i < size; i++) {
          medRequestData.add(i, medRequests.getString(i + 1));
        }

        medRequestList.add(new MedRequest(medRequestData));
      }

    } catch (SQLException | NoMedicine e) {
      System.out.println("Query from medicine request table failed.");
    }
    return medRequestList;
  }

  @Override
  public void exportMedReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(
          "reqID,medicine,nodeID,employeeID,emergency,status,createdTimestamp,updatedTimestamp");

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
}
