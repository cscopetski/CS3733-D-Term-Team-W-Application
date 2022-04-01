package edu.wpi.cs3733.d22.teamW.wDB;

import java.util.ArrayList;

public interface MedEquipDao {

  ArrayList<MedEquip> getAllMedEquip();

  void addMedEquip(String medID);

  void deleteMedEquip(String medID);

  void changeMedEquip(String medID, String newLocationID, int newStatus);

  void exportMedCSV(String fileName);
}
