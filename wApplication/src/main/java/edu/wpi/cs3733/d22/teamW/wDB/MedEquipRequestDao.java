package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MedEquipRequestDao {

  ArrayList<MedEquipRequest> getAllMedEquipRequests();

  ArrayList<MedEquipRequest> getNewMedEquipRequests();

  ArrayList<MedEquipRequest> getInProgMedEquipRequests();

  ArrayList<MedEquipRequest> getCompletedMedEquipRequests();

  ArrayList<MedEquipRequest> getNewAndInProgMedEquipRequests();

  //void addMedEquipRequest(Integer emergency, String medID, String employeeName, Location location);

  void addMedEquipRequest(MedEquipRequest mer) throws SQLException;

  void cancelMedEquipRequest(int requestID);

  void changeMedEquipRequest(
      int requestID, String newItemID, String newLocationID, String newEmployeeName) throws SQLException;

  void makeMedEquipEmergency(int requestID);

  void exportMedReqCSV(String fileName);
}
