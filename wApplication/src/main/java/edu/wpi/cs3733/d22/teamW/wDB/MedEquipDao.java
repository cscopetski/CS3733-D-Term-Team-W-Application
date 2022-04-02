package edu.wpi.cs3733.d22.teamW.wDB;

import java.util.ArrayList;

public interface MedEquipDao {

  ArrayList<MedEquip> getAllMedEquip();

  void addMedEquip(String inputID, String type, String nodeID, Integer status);

  void deleteMedEquip(String medID);

  void changeMedEquip(String inputID, String type, String nodeID, Integer status);

  void exportMedCSV(String fileName);
}
