package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquipRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MedEquipRequestDao {

  ArrayList<Request> getAllMedEquipRequests() throws SQLException;

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

  void changeMedEquipRequest(int requestID,  String itemID, String itemType, String nodeID, String employeeName, Integer emergency,RequestStatus status)
      throws SQLException;

  void changeMedEquipRequest(MedEquipRequest mER) throws SQLException;

  MedEquipRequest getRequest(Integer reqID) throws SQLException;

  void exportMedReqCSV(String fileName);
}
