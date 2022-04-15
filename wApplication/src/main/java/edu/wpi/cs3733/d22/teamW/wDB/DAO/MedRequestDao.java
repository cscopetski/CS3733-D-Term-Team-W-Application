package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.Units;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MedRequestDao {

  void addMedRequest(MedRequest mr) throws SQLException;

  void changeMedRequest(
      Integer requestID,
      String patientLast,
      String patientFirst,
      String medicine,
      Double quantity,
      Units unit,
      String nodeID,
      Integer bedNumber,
      Integer employeeID,
      Integer emergency,
      RequestStatus status)
      throws SQLException;

  void deleteMedRequest(Integer id) throws SQLException;

  Request getMedRequest(Integer id) throws SQLException;

  ArrayList<Request> getAllMedRequest() throws SQLException;

  void exportMedReqCSV(String fileName);
}
