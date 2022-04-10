package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MedEquipDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipManager {

  private MedEquipDao medi;

  private static MedEquipManager medEquipManager = new MedEquipManager();

  private MedEquipManager() {}

  public static MedEquipManager getMedEquipManager() {
    return medEquipManager;
  }

  public void setMedEquipDao(MedEquipDao medi) {
    this.medi = medi;
  }

  public void markClean(MedEquip equip) throws SQLException {
    medi.changeMedEquip(equip.getMedID(), equip.getType(), equip.getNodeID(), 0);
  }

  public void markClean(String medID, String type, String nodeID) throws SQLException {
    medi.changeMedEquip(medID, type, nodeID, 0);
  }

  public void markInUse(MedEquip equip) throws SQLException {
    medi.changeMedEquip(equip.getMedID(), equip.getType(), equip.getNodeID(), 1);
  }

  public void markDirty(MedEquip equip) throws SQLException {
    medi.changeMedEquip(equip.getMedID(), equip.getType(), equip.getNodeID(), 2);
    // Creates request to move it to dirty location
    // ArrayList<String> fields = {}
    // rF.getRequest("Cleaning Request", );
  }

  public void add(String inputID, String type, String nodeID, Integer status) throws SQLException {
    medi.addMedEquip(inputID, type, nodeID, status);
  }

  public void delete(String inputID) throws SQLException {
    medi.deleteMedEquip(inputID);
  }

  // TODO change to just use sql in impl
  public MedEquip getMedEquip(String medID) throws SQLException {
    for (MedEquip e : medi.getAllMedEquip()) {
      if (e.getMedID().equals(medID)) {
        return e;
      }
    }
    return null;
  }

  public ArrayList<MedEquip> getAllMedEquip() throws SQLException {
    return medi.getAllMedEquip();
  }

  public void exportMedicalEquipmentCSV(String filename) {
    medi.exportMedCSV(filename);
  }
}
