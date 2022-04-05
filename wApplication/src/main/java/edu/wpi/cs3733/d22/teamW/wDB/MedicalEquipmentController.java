package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class MedicalEquipmentController {

  private MedEquipDaoImpl medi;
  private MedEquipRequestDaoImpl merdi;
  private RequestFactory rF = RequestFactory.getRequestFactory();

  public void markClean(MedEquip equip) throws SQLException {
    medi.changeMedEquip(equip.getMedID(), equip.getType(), equip.getNodeID(), 0);
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
    medi.changeMedEquip(inputID, type, nodeID, status);
  }

  public void delete(String inputID) throws SQLException {
    medi.deleteMedEquip(inputID);
  }

  public MedEquip getMedEquip(String medID) {
    for (MedEquip e : medi.getAllMedEquip()) {
      if (e.getMedID().equals(medID)) {
        return e;
      }
    }
    return null;
  }

  public ArrayList<MedEquip> getAll() {
    return medi.getAllMedEquip();
  }
}
