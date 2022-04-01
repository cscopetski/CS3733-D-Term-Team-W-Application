package edu.wpi.cs3733.d22.teamW.wDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipDaoImpl implements MedEquipDao {

  ArrayList<MedEquip> medEquipList;
  DBController dbController;

  public MedEquipDaoImpl(DBController dbController) {
    this.dbController = dbController;
    this.medEquipList = dbController.getMedEquipTable();
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
