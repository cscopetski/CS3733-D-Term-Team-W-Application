package edu.wpi.cs3733.d22.teamW.wDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipRequestDaoImpl implements MedEquipRequestDao {
  ArrayList<MedEquipRequest> medEquipRequestList;
  DBController dbController;

  public MedEquipRequestDaoImpl(DBController dbController) {

    this.dbController = dbController;
    this.medEquipRequestList = dbController.getMedEquipReqTable();
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
  public void addMedEquipRequest(
      Integer emergency, String medID, String employeeName, Location location) throws SQLException {
    Integer ID = medEquipRequestList.size() + 1;
    MedEquipRequest param =
        new MedEquipRequest(ID, emergency, medID, location.getNodeID(), employeeName);
    medEquipRequestList.add(param);
    dbController.addEntity(param); // addition in database
  }

  @Override
  public void deleteMedEquipRequest(int requestID) {
    int index = getIndexOf(requestID);
    if (index == -1) {
      System.out.println(
          String.format("The database does not contain a request with the ID: %d", requestID));
    } else {
      medEquipRequestList.get(index).cancel();
      try {
        dbController.cancel("MEDICALEQUIPMENTREQUESTS", requestID);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void changeMedEquipRequest(
      int requestID, String newItemID, String newLocationID, String newEmployeeName) {
    int index = getIndexOf(requestID);
    if (index == -1) {
      System.out.println(
          String.format("The database does not contain a request with the ID: %d", requestID));
    } else {
      dbController.updateNodeFromMedicalEquipmentRequestsTable(
          requestID, newItemID, newLocationID, newEmployeeName);
      medEquipRequestList.get(index).setItemID(newItemID);
      medEquipRequestList.get(index).setNodeID(newLocationID);
      medEquipRequestList.get(index).setEmployeeName(newEmployeeName);
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
      try {
        dbController.setEmergency("MEDICALEQUIPMENTREQUESTS", requestID, true);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private int getIndexOf(int inputID) {
    int size = medEquipRequestList.size();
    boolean found = false;
    for (int i = 0; i < size; i++) {
      if (medEquipRequestList.get(i).getRequestID().equals(inputID)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void exportMedReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print("medReqID,medID,nodeID,employeeName,isEmergency,status");

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
}
