package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Automation;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquipRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MedEquipRequestDaoImpl implements MedEquipRequestDao {

  Statement statement;


  MedEquipRequestDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() {
    try {
      statement.execute("DROP TABLE MEDICALEQUIPMENTREQUESTS");
      System.out.println("Dropped Medical Equipment Requests Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Medical Equipment Requests Table");
    }
  }

  void createTable() throws SQLException {
    try {
      statement.execute(
          "CREATE TABLE MEDICALEQUIPMENTREQUESTS("
              + "medReqID INT, "
              + "medID varchar(25),"
              + "equipType varchar(25),"
              + "nodeID varchar(25),"
              + "employeeID INT,"
              + "isEmergency INT,"
              + "reqStatus INT, "
              + "constraint MedReq_MedEquip_FK foreign key (medID) references MEDICALEQUIPMENT(medID),"
              + "constraint MedReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),"
              + "constraint MedEquipReq_PK primary key (medReqID),"
              + "constraint MedEReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),"
              + "constraint IsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
    } catch (SQLException e) {
      System.out.println("Medical Equipment Request Table failed to be created!");
      throw (e);
    }
    System.out.println("Medical Equipment Request Table created!");
  }

  @Override
  public ArrayList<Request> getAllMedEquipRequests() throws SQLException {
    ArrayList<Request> medEquipRequestList = new ArrayList<>();

    try {
      ResultSet medEquipment = statement.executeQuery("SELECT * FROM MEDICALEQUIPMENTREQUESTS");

      // Size of num MedEquipRequest fields
      int size = 7;
      ArrayList<String> medEquipData = new ArrayList<>();

      while (medEquipment.next()) {

        for (int i = 0; i < size; i++) {
          medEquipData.add(i, medEquipment.getString(i + 1));
        }

        medEquipRequestList.add(new MedEquipRequest(medEquipData));
      }

    } catch (SQLException e) {
      System.out.println("Query from med equip request table failed");
      throw (e);
    }
    return medEquipRequestList;
  }

  //
  //    @Override
  //    public ArrayList<MedEquipRequest> getNewMedEquipRequests() {
  //        return null;
  //    }
  //
  //    @Override
  //    public ArrayList<MedEquipRequest> getInProgMedEquipRequests() {
  //        return null;
  //    }
  //
  //    @Override
  //    public ArrayList<MedEquipRequest> getCompletedMedEquipRequests() {
  //        return null;
  //    }
  //
  //    @Override
  //    public ArrayList<MedEquipRequest> getNewAndInProgMedEquipRequests() {
  //        return null;
  //    }

  @Override
  public void addMedEquipRequest(MedEquipRequest mer) throws SQLException {
    statement.executeUpdate(
        String.format("INSERT INTO MEDICALEQUIPMENTREQUESTS VALUES (%s)", mer.toValuesString()));
  }

  @Override
  public void changeMedEquipRequest(
          int requestID, String itemID, String itemType, String nodeID, String employeeName, Integer emergency, RequestStatus status)
      throws SQLException {

    statement.executeUpdate(
        String.format(
                "UPDATE MEDICALEQUIPMENTREQUESTS SET MEDID = '%s', EQUIPTYPE = '%s', NODEID = '%s', EMPLOYEENAME = '%s', ISEMERGENCY = %d , REQSTATUS = %d WHERE MEDREQID = %d",
            itemID, itemType, nodeID, employeeName, emergency, status.getValue(), requestID));
  }

  public void changeMedEquipRequest(MedEquipRequest mER) throws SQLException {
    // TODO could re-purpose this to change request factory list?
    //    int index = getIndexOf(mER.getRequestID());
    //    if (index == -1) {
    //      System.out.println(
    //          String.format(
    //              "The database does not contain a request with the ID: %d", mER.getRequestID()));
    //    } else {
    //      MedEquipRequest listmER = medEquipRequestList.get(index);
    //      listmER.setEmergency(mER.getEmergency());
    //      listmER.setEmployeeName(mER.getEmployeeName());
    //      listmER.setStatus(mER.getStatusInt());
    //      listmER.setItemID(mER.getItemID());
    //      listmER.setNodeID(mER.getNodeID());
    //      listmER.setItemType(mER.getItemType());

    statement.executeUpdate(
        String.format(
            "UPDATE MEDICALEQUIPMENTREQUESTS SET MEDID = '%s', EQUIPTYPE = '%s', NODEID = '%s', EMPLOYEEID = %d, ISEMERGENCY = %d , REQSTATUS = %d WHERE MEDREQID = %d",
            mER.getItemID(),
            mER.getItemType(),
            mER.getNodeID(),
            mER.getEmployeeID(),
            mER.getEmergency(),
            mER.getStatusInt(),
            mER.getRequestID()));
  }

  @Override
  public MedEquipRequest getRequest(Integer reqID) throws SQLException {
    MedEquipRequest mr = null;
    try {
      ResultSet medEquipRequests =
              statement.executeQuery(
                      String.format("SELECT * FROM MEDREQUESTS WHERE REQUESTID = %d", reqID));

      // Size of num LabServiceRequest fields
      int size = 6;
      ArrayList<String> medEquipRequestData = new ArrayList<String>();

      while (medEquipRequests.next()) {

        for (int i = 0; i < size; i++) {
          medEquipRequestData.add(i, medEquipRequests.getString(i + 1));
        }
        mr = new MedEquipRequest(medEquipRequestData);
      }
    } catch (SQLException e) {
      System.out.println("Query from medicine request table failed.");
    }
    return mr;
  }

  @Override
  public void exportMedReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print("medReqID,medID,equipType,nodeID,employeeName,isEmergency,status");

      // print all locations
      for (Request m : getAllMedEquipRequests()) {
        pw.println();
        pw.print(m.toCSVString());
      }

    } catch (FileNotFoundException | SQLException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }


  @Override
  public ArrayList<MedEquipRequest> getTypeMedEquipRequests(String itemType) throws SQLException {
    ArrayList<MedEquipRequest> medEquipRequestList = new ArrayList<>();

    try {
      ResultSet medEquipment = statement.executeQuery(String.format("SELECT * FROM MEDICALEQUIPMENTREQUESTS WHERE EQUIPTYPE='%s'", itemType));

      // Size of num MedEquipRequest fields
      int size = 7;
      ArrayList<String> medEquipData = new ArrayList<>();

      while (medEquipment.next()) {

        for (int i = 0; i < size; i++) {
          medEquipData.add(i, medEquipment.getString(i + 1));
        }

        medEquipRequestList.add(new MedEquipRequest(medEquipData));
      }

    } catch (SQLException e) {
      System.out.println("Query from med equip request table failed");
      throw (e);
    }
    return medEquipRequestList;
  }

  @Override
  public Integer countItemType(String itemType){

    return 0;
  }
}
