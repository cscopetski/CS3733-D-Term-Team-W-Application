package edu.wpi.cs3733.d22.teamW.wDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipRequestDaoImpl implements MedEquipRequestDao {

  DBController dbController = DBController.getDBController();
  ArrayList<MedEquipRequest> medEquipRequestList = new ArrayList<>();
  Integer requestIDTracker = 1;

  public MedEquipRequestDaoImpl() {
    setMedEquipRequestList();
  }

  public void setMedEquipRequestList() {
    ArrayList<MedEquipRequest> medEquipRequestList = new ArrayList<>();

    try {
      ResultSet medEquipment = dbController.executeQuery("SELECT * FROM MEDICALEQUIPMENTREQUESTS");

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
      e.printStackTrace();
    }
  }

  @Override
  public ArrayList<MedEquipRequest> getAllMedEquipRequests() {
    return medEquipRequestList;
  }

  @Override
  public ArrayList<MedEquipRequest> getNewMedEquipRequests() {
    return null;
  }

  @Override
  public ArrayList<MedEquipRequest> getInProgMedEquipRequests() {
    return null;
  }

  @Override
  public ArrayList<MedEquipRequest> getCompletedMedEquipRequests() {
    return null;
  }

  @Override
  public ArrayList<MedEquipRequest> getNewAndInProgMedEquipRequests() {
    return null;
  }

  @Override
  public void addMedEquipRequest(MedEquipRequest mer) throws SQLException {
    medEquipRequestList.add(mer);
    // dbController.addEntity(param); // addition in database
    dbController.executeUpdate(
        String.format("INSERT INTO MEDICALEQUIPMENTREQUESTS VALUES (%s)", mer.toValuesString()));
  }

  @Override
  public void changeMedEquipRequest(
      int requestID, String newItemType, String newLocationID, Integer newEmployeeID)
      throws SQLException {
    int index = getIndexOf(requestID);
    if (index == -1) {
      System.out.println(
          String.format("The database does not contain a request with the ID: %d", requestID));
    } else {
      medEquipRequestList.get(index).setItemID(newItemType);
      medEquipRequestList.get(index).setNodeID(newLocationID);
      medEquipRequestList.get(index).setEmployeeID(newEmployeeID);
      DBController.getDBController()
          .executeUpdate(
              String.format(
                  "UPDATE MEDICALEQUIPMENT SET TYPE = 's', NODEID = 's', STATUS = %d WHERE MEDID = %s",
                  newItemType, newLocationID, newEmployeeID, requestID));
    }
  }

  public void changeMedEquipRequest(MedEquipRequest mER) throws SQLException {
    int index = getIndexOf(mER.getRequestID());
    if (index == -1) {
      System.out.println(
          String.format(
              "The database does not contain a request with the ID: %d", mER.getRequestID()));
    } else {
      MedEquipRequest listmER = medEquipRequestList.get(index);
      listmER.setEmergency(mER.getEmergency());
      listmER.setEmployeeID(mER.getEmployeeID());
      listmER.setStatus(mER.getStatus());
      listmER.setItemID(mER.getItemID());
      listmER.setNodeID(mER.getNodeID());
      listmER.setItemType(mER.getItemType());
      DBController.getDBController()
          .executeUpdate(
              String.format(
                  "UPDATE MEDICALEQUIPMENTREQUESTS SET MEDID = '%s', EQUIPTYPE = '%s', NODEID = '%s', EMPLOYEEID = %d, ISEMERGENCY = %d , REQSTATUS = %d WHERE MEDREQID = %d",
                  listmER.getItemID(),
                  listmER.getItemType(),
                  listmER.getNodeID(),
                  listmER.getEmployeeID(),
                  listmER.getEmergency(),
                  listmER.getStatus(),
                  listmER.getRequestID()));
    }
  }

  @Override
  public void makeMedEquipEmergency(int requestID) {
    int index = getIndexOf(requestID);
    if (index == -1) {
      System.out.println(
          String.format("The database does not contain a request with the ID: %d", requestID));
    } else {
      medEquipRequestList.get(index).setEmergency(1);
      // dbController.setEmergency("MEDICALEQUIPMENTREQUESTS", requestID, true);

    }
  }

  @Override
  public void exportMedReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print("medReqID,medID,equipType,nodeID,employeeID,isEmergency,status");

      // print all locations
      for (MedEquipRequest m : medEquipRequestList) {
        pw.println();
        pw.print(m.toCSVString());
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }

  public void start(Integer requestID) throws SQLException {
    int index = getIndexOf(requestID);
    if (index == -1) {
      System.out.println(
          String.format("The database does not contain a request with the ID: %d", requestID));
    } else {
      dbController.executeUpdate(
          String.format(
              "UPDATE MEDICALEQUIPMENTREQUESTS SET STATUS = 1 WHERE REQUESTID = %d", requestID));

      medEquipRequestList.get(index).start();
    }
  }

  private int getIndexOf(int inputID) {
    int size = medEquipRequestList.size();
    boolean found = false;
    for (int i = 0; i < size; i++) {
      if (medEquipRequestList.get(i).getRequestID() == (inputID)) {
        return i;
      }
    }
    return -1;
  }
}
