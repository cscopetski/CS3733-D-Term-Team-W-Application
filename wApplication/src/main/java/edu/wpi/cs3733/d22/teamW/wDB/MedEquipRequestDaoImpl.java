package edu.wpi.cs3733.d22.teamW.wDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipRequestDaoImpl implements MedEquipRequestDao {

  DBController dbController = DBController.getDBController();
  ArrayList<MedEquipRequest> medEquipRequestList = dbController.getMedEquipReqTable();
  Integer x = 1;

  public MedEquipRequestDaoImpl() {
  }

  public MedEquipRequestDaoImpl(MedEquipDaoImpl medEquipDaoImpl) {
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
      Integer emergency, String itemType, String employeeName, Location location) {
    Integer ID = x++;
    MedEquipRequest param =
        new MedEquipRequest(ID, emergency, itemType, location.getNodeID(), employeeName);
    medEquipRequestList.add(param);
    dbController.addEntity(param); // addition in database
    Integer count = 0;
    try {
      count = dbController.executeQuery(String.format("SELECT COUNT (*) AS COUNT FROM MEDICALEQUIPMENT WHERE (STATUS = 0 AND TYPE = '%s'", itemType)).getInt("COUNT");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void cancelMedEquipRequest(int requestID) {
    int index = getIndexOf(requestID);
    if (index == -1) {
      System.out.println(
          String.format("The database does not contain a request with the ID: %d", requestID));
    } else {
      MedEquipRequest medReq = medEquipRequestList.get(index);
      if(medReq.getStatus()==0){
        medReq.cancel();
      } else if(medReq.getStatus()==1){
        medReq.cancel();
      }
      try {
        dbController.cancel("MEDICALEQUIPMENTREQUESTS", requestID);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void changeMedEquipRequest(
      int requestID, String newItemType, String newLocationID, String newEmployeeName) {
    int index = getIndexOf(requestID);
    if (index == -1) {
      System.out.println(
          String.format("The database does not contain a request with the ID: %d", requestID));
    } else {
      dbController.updateNodeFromMedicalEquipmentRequestsTable(
          requestID, newItemType, newLocationID, newEmployeeName);
      medEquipRequestList.get(index).setItemID(newItemType);
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

  public void start(Integer requestID){
    int index = getIndexOf(requestID);
    if (index == -1) {
      System.out.println(
              String.format("The database does not contain a request with the ID: %d", requestID));
    } else {
      dbController.executeUpdate(String.format("UPDATE MEDICALEQUIPMENTREQUESTS SET STATUS = 1 WHERE REQUESTID = %d",requestID));

      medEquipRequestList.get(index).start();
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
}
