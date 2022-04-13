package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

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
              + "medicine varchar(25),"
              + "nodeID varchar(25),"
              + "employeeName varchar(50),"
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
      Integer id,
      String m,
      String n,
      Integer en,
      Integer ie,
      RequestStatus rs,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp)
      throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE MEDREQUESTS SET MEDICINE='%s', NODEID='%s', EMPLOYEEID=%d, ISEMERGENCY=%d, REQSTATUS=%d, CREATEDTIMESTAMP = '%s', UPDATEDTIMESTAMP = '%s' WHERE REQUESTID=%d",
            m,
            n,
            en,
            ie,
            rs.getValue(),
            createdTimestamp.toString(),
            updatedTimestamp.toString(),
            id));
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
              String.format("SELECT * FROM MEDICALEQUIPMENTREQUESTS WHERE MEDREQID = %d", id));

      medEquipRequests.next();

      Integer medreqID = medEquipRequests.getInt("MEDREQID");
      String medID = medEquipRequests.getString("MEDID");
      String equipType = medEquipRequests.getString("EQUIPTYPE");
      String nodeID = medEquipRequests.getString("NODEID");
      Integer employeeID = medEquipRequests.getInt("EMPLOYEEID");
      Integer isEmergency = medEquipRequests.getInt("ISEMERGENCY");
      Integer reqStatus = medEquipRequests.getInt("REQSTATUS");
      String createdTimeStamp = medEquipRequests.getString("CREATEDTIMESTAMP");
      String updatedTimeStamp = medEquipRequests.getString("UPDATEDTIMESTAMP");
      ArrayList<String> medEquipRequestData = new ArrayList<String>();
      medEquipRequestData.add(String.format("%d", medreqID));
      medEquipRequestData.add(medID);
      medEquipRequestData.add(equipType);
      medEquipRequestData.add(nodeID);
      medEquipRequestData.add(String.format("%d", employeeID));
      medEquipRequestData.add(String.format("%d", isEmergency));
      medEquipRequestData.add(String.format("%d", reqStatus));
      medEquipRequestData.add(createdTimeStamp);
      medEquipRequestData.add(updatedTimeStamp);

      mr = new MedRequest(medEquipRequestData);

    } catch (SQLException e) {
      System.out.println("Query from medicine request table failed.");
    }
    return mr;
  }

  @Override
  public ArrayList<Request> getAllMedRequest() throws SQLException {
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

    } catch (SQLException e) {
      System.out.println("Query from medicine request table failed.");
      throw(e);
    }
    return medRequestList;
  }

  @Override
  public void exportMedReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(
              "reqID,medicine,nodeID,employeeID,isEmergency,status,createdTimestamp,updatedTimestamp");

      // print all locations
      for (Request m : getAllMedRequest()) {
        pw.println();
        pw.print(m.toCSVString());
      }

    } catch (FileNotFoundException | SQLException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }
}
