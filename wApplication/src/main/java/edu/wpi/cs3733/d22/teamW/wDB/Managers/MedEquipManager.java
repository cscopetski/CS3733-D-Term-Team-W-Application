package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MedEquipDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.MarkingInUseEquipmentAsClean;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.AlertInfoWrapper;
import edu.wpi.cs3733.d22.teamW.wDB.entity.CleaningRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class MedEquipManager {

  private MedEquipDao medi;

  private static HashMap<MedEquipType, TreeSet<Integer>> equipID = new HashMap<>();

  static{
    for(MedEquipType type : MedEquipType.values()){
      equipID.put(type, new TreeSet<Integer>());
    }
  }


  private final String noneEquipment = "NONE";
  private final String deletedEquipment = "DELETED";

  private static MedEquipManager medEquipManager = new MedEquipManager();
  private CleaningRequestManager crm = CleaningRequestManager.getCleaningRequestManager();

  private MedEquipManager() {}

  public static MedEquipManager getMedEquipManager() {
    return medEquipManager;
  }

  public void setMedEquipDao(MedEquipDao medi) {
    this.medi = medi;
  }

  public String getDeletedEquipment() {
    return this.deletedEquipment;
  }

  public String getNoneEquipment() {
    return this.noneEquipment;
  }

  public void markClean(String medID, String nodeID) throws Exception {
    MedEquip medEquip = medi.getMedEquip(medID);
    if (medEquip.getStatus().equals(MedEquipStatus.Dirty)) {
      medEquip.setNodeID(nodeID);
      medEquip.setStatus(MedEquipStatus.Clean);
      medi.changeMedEquip(medEquip);
      CleaningRequestManager.getCleaningRequestManager().markComplete(medID, nodeID);
    } else if (medEquip.getStatus().equals(MedEquipStatus.InUse)) {
      throw new MarkingInUseEquipmentAsClean();
    }
  }

  public void markCleanThroughRequest(String medID, String nodeID) throws Exception {
    MedEquip medEquip = medi.getMedEquip(medID);
      medEquip.setNodeID(nodeID);
      medEquip.setStatus(MedEquipStatus.Clean);
      medi.changeMedEquip(medEquip);
      // CleaningRequestManager.getCleaningRequestManager().markComplete(medID, nodeID);
  }

  public void markInUse(String medID, String nodeID) throws Exception {
    MedEquip medEquip = medi.getMedEquip(medID);
    if (medEquip.getStatus().equals(MedEquipStatus.Clean)) {
      medEquip.setNodeID(nodeID);
      medEquip.setStatus(MedEquipStatus.InUse);
      medi.changeMedEquip(medEquip);
      //CleaningRequestManager.getCleaningRequestManager().checkStart();
    } else if (medEquip.getStatus().equals(MedEquipStatus.Dirty)) {
      throw new MarkingInUseEquipmentAsClean();
    }
  }

  public void markDirty(String medID, String nodeID) throws Exception {
    MedEquip me = medi.getMedEquip(medID);
    System.out.println(me.toValuesString());
    if (!me.getStatus().equals(MedEquipStatus.Dirty)) {
      me.setNodeID(nodeID);
      me.setStatus(MedEquipStatus.Dirty);
      medi.changeMedEquip(me);
      ArrayList<String> fields = new ArrayList<>();
      Employee employee =
          EmployeeManager.getEmployeeManager().getEmployeeByType(EmployeeType.Sanitation);
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
    if((medEquip.getMedID().equals("DELETED") || medEquip.getMedID().equals("NONE"))) {
      medi.addMedEquip(medEquip);
    } else{
      if (equipID.get(medEquip.getType()).add(Integer.parseInt(medEquip.getMedID().substring(3)))) {
        medi.addMedEquip(medEquip);
      }
    }
  }

  public MedEquip add(MedEquipType medEquipType, String nodeID) throws SQLException, NonExistingMedEquip, StatusError {
    Integer num = (equipID.get(medEquipType).last() + 1);
    equipID.get(medEquipType).add(num);
    String idNum = String.format("%d", num);
    if(num < 100){
      idNum = "0" +idNum;
      if(num < 10){
        idNum = "0" +idNum;
      }
    }
    idNum = medEquipType.getAbb()+idNum;
    MedEquip medEquip = new MedEquip(idNum, medEquipType, nodeID, 0);
    medi.addMedEquip(medEquip);
    return medEquip;
  }

  public void change(MedEquip medEquip) throws SQLException {
    medi.changeMedEquip(medEquip);
  }

  public ArrayList<AlertInfoWrapper> check() throws Exception {
    return CleaningRequestManager.getCleaningRequestManager().checkForAlert();
  }

  public void delete(String inputID) throws Exception {
    MedEquipRequestManager.getMedEquipRequestManager().updateReqWithEquipment(inputID);
    CleaningRequestManager.getCleaningRequestManager().updateReqWithEquipment(inputID);
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

  public void updateMedEquipAtLocation(String nodeID) throws Exception {
    this.medi.updateMedEquipsAtLocation(nodeID);
  }

  public void exportMedicalEquipmentCSV(String filename) {
    medi.exportMedCSV(filename);
  }

  public void resetTreeSets(){
    equipID = new HashMap<>();
    for(MedEquipType type : MedEquipType.values()){
      equipID.put(type, new TreeSet<Integer>());
    }
  }

}
