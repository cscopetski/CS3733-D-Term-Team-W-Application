package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.MedEquipRequest;
import edu.wpi.cs3733.d22.teamW.wDB.Request;
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

  void changeMedEquipRequest(
      int requestID, String newItemID, String newLocationID, String newEmployeeName)
      throws SQLException;

  void changeMedEquipRequest(MedEquipRequest mER) throws SQLException;

  void exportMedReqCSV(String fileName);
}
