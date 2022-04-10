package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MedRequestDao {

  void addMedRequest(MedRequest mr) throws SQLException;

  void changeMedRequest(Integer id, String m, String n, String en, Integer ie, RequestStatus rs)
      throws SQLException;

  void deleteMedRequest(Integer id) throws SQLException;

  Request getMedRequest(Integer id) throws SQLException;

  ArrayList<Request> getAllMedRequest() throws SQLException;
}