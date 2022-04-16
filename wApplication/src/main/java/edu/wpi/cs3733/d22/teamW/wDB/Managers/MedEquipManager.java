package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MedEquipDao;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.CleaningRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipManager {

  private MedEquipDao medi;

  private static MedEquipManager medEquipManager = new MedEquipManager();
  private CleaningRequestManager crm = CleaningRequestManager.getCleaningRequestManager();

  private MedEquipManager() {}

  public static MedEquipManager getMedEquipManager() {
    return medEquipManager;
  }

  public void setMedEquipDao(MedEquipDao medi) {
    this.medi = medi;
  }

  public void markClean(String medID, String nodeID) throws SQLException {
    MedEquip medEquip = medi.getMedEquip(medID);
    medEquip.setNodeID(nodeID);
    medEquip.setStatus(MedEquipStatus.Clean);
    medi.changeMedEquip(medEquip);
  }

  public void markInUse(String medID, String nodeID) throws SQLException {
    MedEquip medEquip = medi.getMedEquip(medID);
    medEquip.setNodeID(nodeID);
    medEquip.setStatus(MedEquipStatus.InUse);
    medi.changeMedEquip(medEquip);
  }

  public void markDirty(String medID, String nodeID) throws Exception {
    MedEquip me = medi.getMedEquip(medID);
    if (!me.getStatus().equals(MedEquipStatus.Dirty)) {
      me.setNodeID(nodeID);
      me.setStatus(MedEquipStatus.Dirty);
      medi.changeMedEquip(me);
      ArrayList<String> fields = new ArrayList<>();
      Employee employee =
          EmployeeManager.getEmployeeManager().getEmployeeType(EmployeeType.Sanitation);
      fields.add(medID);
      fields.add(nodeID);
      fields.add(String.format("%d", employee.getEmployeeID()));
      fields.add(String.format("%d", 0));
      CleaningRequest cr =
          (CleaningRequest)
              RequestFactory.getRequestFactory()
                  .getRequest(RequestType.CleaningRequest, fields, false);
    }
  }

  public void moveTo(String medID, String nodeID) throws SQLException {
    MedEquip medEquip = medi.getMedEquip(medID);
    medEquip.setNodeID(nodeID);
    medi.changeMedEquip(medEquip);
  }

  public MedEquip getNextFree(MedEquipType itemType) throws SQLException {
    ArrayList<MedEquip> list = medi.getAllMedEquip();
    if (list.size() == 0) {
      return null;
    } else {
      for (MedEquip e : list) {
        if (e.getType().equals(itemType) && e.getStatus().equals(MedEquipStatus.Clean)) {
          return e;
        }
      }
    }
    return null;
  }

  public void add(MedEquip medEquip) throws SQLException {
    medi.addMedEquip(medEquip);
  }

  public void change(MedEquip medEquip) throws SQLException {
    medi.changeMedEquip(medEquip);
  }

  public void delete(String inputID) throws SQLException {
    medi.deleteMedEquip(inputID);
  }

  public MedEquip getMedEquip(String medID) throws SQLException {
    return medi.getMedEquip(medID);
  }

  public ArrayList<MedEquip> getAllMedEquip() throws SQLException {
    return medi.getAllMedEquip();
  }

  public ArrayList<MedEquip> getAllMedEquip(MedEquipType type, MedEquipStatus status)
      throws SQLException {
    return medi.getAllMedEquip(type, status);
  }

  public void exportMedicalEquipmentCSV(String filename) {
    medi.exportMedCSV(filename);
  }
}
