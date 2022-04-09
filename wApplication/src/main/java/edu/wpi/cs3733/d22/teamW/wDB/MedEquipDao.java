package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MedEquipDao {

  ArrayList<MedEquip> getAllMedEquip() throws SQLException;

  void addMedEquip(String inputID, String type, String nodeID, Integer status) throws SQLException;

  void deleteMedEquip(String medID) throws SQLException;

  void changeMedEquip(String inputID, String type, String nodeID, Integer status)
      throws SQLException;

  void exportMedCSV(String fileName);
}
