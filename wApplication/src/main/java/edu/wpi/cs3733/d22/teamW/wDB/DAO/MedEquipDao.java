package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipType;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MedEquipDao {

  ArrayList<MedEquip> getAllMedEquip() throws SQLException;

  void addMedEquip(MedEquip medEquip) throws SQLException;

  void deleteMedEquip(String medID) throws SQLException;

  public MedEquip getMedEquip(String medID) throws SQLException;

  void changeMedEquip(MedEquip medEquip) throws SQLException;

  void exportMedCSV(String fileName);

  ArrayList<MedEquip> getAllMedEquip(MedEquipType type, MedEquipStatus status) throws SQLException;
}
