package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquipRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipType;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MedEquipRequestDao {

  ArrayList<Request> getAllMedEquipRequests() throws SQLException, NonExistingMedEquip;

  ArrayList<MedEquipRequest> getTypeMedEquipRequests(MedEquipType itemType)
      throws SQLException, NonExistingMedEquip;

  //  ArrayList<MedEquipRequest> getNewMedEquipRequests();
  //
  //  ArrayList<MedEquipRequest> getInProgMedEquipRequests();
  //
  //  ArrayList<MedEquipRequest> getCompletedMedEquipRequests();
  //
  //  ArrayList<MedEquipRequest> getNewAndInProgMedEquipRequests();

  // void addMedEquipRequest(Integer emergency, String medID, String employeeName, Location
  // location);

  void addMedEquipRequest(MedEquipRequest mer) throws SQLException;

  void changeMedEquipRequest(MedEquipRequest mer) throws SQLException;

  void deleteMedEquipRequest(Integer requestID) throws SQLException;

  MedEquipRequest getRequest(Integer reqID) throws SQLException, NonExistingMedEquip;

  void exportMedEquipReqCSV(String fileName) throws NonExistingMedEquip;
}
