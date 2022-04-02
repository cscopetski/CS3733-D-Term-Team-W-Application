package edu.wpi.cs3733.d22.teamW.wDB;

import java.io.*;
import java.sql.*;
import java.util.*;

public class MedEquipDaoImpl implements MedEquipDao {

  DBController dbController = DBController.getDBController();
  ArrayList<MedEquip> medEquipList;

  public MedEquipDaoImpl() throws SQLException {
    setMedEquipList();
  }

  public void setMedEquipList() throws SQLException {
    medEquipList = new ArrayList<>();

    try {
      ResultSet medEquipment = dbController.executeQuery("SELECT * FROM MEDICALEQUIPMENT");

      // Size of num MedEquip fields
      String[] medEquipData = new String[4];

      while (medEquipment.next()) {

        for (int i = 0; i < medEquipData.length; i++) {
          medEquipData[i] = medEquipment.getString(i + 1);
        }

        medEquipList.add(new MedEquip(medEquipData));
      }

    } catch (SQLException e) {
      System.out.println("Query from medical equipment table failed");
      e.printStackTrace();
      throw(e);
    }
  }

  @Override
  public ArrayList<MedEquip> getAllMedEquip() {
    return this.medEquipList;
  }

  @Override
  public void addMedEquip(String inputID) {
    MedEquip param = new MedEquip();
    int index = getIndexOf(inputID);
    if (index != -1) {
      System.out.println(
          "The database already contains a piece of medical equipment with the ID: " + inputID);
    } else {
      MedEquip newMedEquip = new MedEquip(inputID, null, null, null);
      medEquipList.add(newMedEquip);
      dbController.addEntity(param, inputID); // addition in database
    }
  }

  @Override
  public void deleteMedEquip(String medID) {
    int index = getIndexOf(medID);
    if (index == -1) {
      System.out.println("The database does not contain medical equipment with the ID: " + medID);
    } else {
      medEquipList.remove(medEquipList.get(index));
      try {
        dbController.deleteLocation("MEDICALEQUIPMENT", medID);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void changeMedEquip(String medID, String newLocationID, int newStatus) {
    int index = getIndexOf(medID);
    if (index == -1) {
      System.out.println(String.format("medID [%s] not found", medID));
    } else {
      dbController.updateNodeFromMedEquipTable(medID, newLocationID, newStatus);
      medEquipList.get(index).setNodeID(newLocationID);
      medEquipList.get(index).setStatus(newStatus);
    }
  }

  @Override
  public void exportMedCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print("medID,type,nodeID,status");

      // print all medEquipment
      for (MedEquip l : medEquipList) {
        pw.println();
        pw.print(l.toCSVString());
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }

  public void setStatus(Integer status, String itemID) throws SQLException {
    medEquipList.get(getIndexOf(itemID)).setStatus(0);
    dbController.executeUpdate(
        String.format("UPDATE MEDICALEQUIPMENT SET STATUS=%d WHERE MEDID= '%s'", status, itemID));
  }

  /**
   * Loops through the ArrayList, checking for matching nodeID. If found, returns that index.
   * Otherwise, returns -1.
   *
   * @param inputID is the nodeID of the object that we are looking for
   * @return index of location or -1 if it does not exist
   */
  private int getIndexOf(String inputID) {
    int size = medEquipList.size();
    boolean found = false;
    for (int i = 0; i < size; i++) {
      if (medEquipList.get(i).getMedID().equals(inputID)) {
        return i;
      }
    }
    return -1;
  }
}
