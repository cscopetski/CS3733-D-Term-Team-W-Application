package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MedEquipDao;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.CleaningRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;

import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipManager {

  private MedEquipDao medi;

  private static MedEquipManager medEquipManager = new MedEquipManager();
  private CleaningRequestManager crm = CleaningRequestManager.getCleaningRequestManager();

  private ArrayList<CleaningRequest> cleaningRequests = new ArrayList<>();

  private MedEquipManager() {}

  public static MedEquipManager getMedEquipManager() {
    return medEquipManager;
  }

  public void setMedEquipDao(MedEquipDao medi) {
    this.medi = medi;
  }

  public void markClean(MedEquip equip) throws SQLException {
    equip.setStatus(MedEquipStatus.Clean);
    markClean(equip.getMedID(), equip.getType(), equip.getNodeID());
  }

  public void markClean(String medID, String type, String nodeID) throws SQLException {
    medi.changeMedEquip(medID, type, nodeID, MedEquipStatus.Clean);
  }

  public void markInUse(MedEquip equip) throws SQLException {
    equip.setStatus(MedEquipStatus.InUse);
    markInUse(equip.getMedID(), equip.getType(), equip.getNodeID());
  }

  public void markInUse(String medID, String type, String nodeID) throws SQLException {
    medi.changeMedEquip(medID, type, nodeID, MedEquipStatus.InUse);
  }

  public void markDirty(MedEquip equip, String nodeID) throws SQLException {
    equip.setStatus(MedEquipStatus.Dirty);
    markDirty(equip.getMedID(), equip.getType(), nodeID);
  }

  public void markDirty(String medID, String type, String nodeID) throws SQLException {
    medi.changeMedEquip(medID, type, nodeID, MedEquipStatus.Dirty);
    ArrayList<String> fields = new ArrayList<>();
    Employee employee = EmployeeManager.getEmployeeManager().getEmployeeType(EmployeeType.Sanitation);
    fields.add(medID);
    fields.add(nodeID);
    fields.add(String.format("%d", employee.getEmployeeID()));
    fields.add(String.format("%d", 0));
    fields.add(String.format("%d",RequestStatus.InQueue.getValue()));
    CleaningRequest cr = (CleaningRequest) RequestFactory.getRequestFactory().getRequest(RequestType.CleaningRequest, fields);
  }

  public void markDirty(String medID, String nodeID) throws SQLException {
    String type = getMedEquip(medID).getType();
    markDirty(medID,type,nodeID);
  }

  public void startCleaningRequests() throws SQLException {
    for (CleaningRequest e : cleaningRequests) {
      e.setStatus(RequestStatus.InProgress);
      crm.start(e.getRequestID());
    }
  }

  public void moveTo(String medID, String nodeID) throws SQLException {
    MedEquip medEquip = medi.getMedEquip(medID);
    medi.changeMedEquip(medID, medEquip.getType(), nodeID, medEquip.getStatus());
  }

  public MedEquip getNextFree(String itemType) throws SQLException {
    for (MedEquip e : medi.getAllMedEquip()) {
      if (e.getType().equals(itemType) && e.getStatus().equals(MedEquipStatus.Clean)) {
        return e;
      }
    }
    return null;
  }

  public void add(String inputID, String type, String nodeID, Integer status) throws SQLException {
    medi.addMedEquip(inputID, type, nodeID, MedEquipStatus.getStatus(status));
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

  public void exportMedicalEquipmentCSV(String filename) {
    medi.exportMedCSV(filename);
  }
}
