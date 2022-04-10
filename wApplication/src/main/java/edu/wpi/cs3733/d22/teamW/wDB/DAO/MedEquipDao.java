package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipStatus;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MedEquipDao {

  ArrayList<MedEquip> getAllMedEquip() throws SQLException;

  void addMedEquip(String inputID, String type, String nodeID, MedEquipStatus status) throws SQLException;

  void deleteMedEquip(String medID) throws SQLException;

  public MedEquip getMedEquip(String medID) throws SQLException;

  void changeMedEquip(String inputID, String type, String nodeID, MedEquipStatus status)
      throws SQLException;

  void exportMedCSV(String fileName);
}
