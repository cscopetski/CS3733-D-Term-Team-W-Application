package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public interface MedRequestDao {

  void addMedRequest(MedRequest mr) throws SQLException;

  void changeMedRequest(
      Integer id,
      String m,
      String n,
      Integer en,
      Integer ie,
      Integer rs,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp)
      throws SQLException;

  void deleteMedRequest(Integer id) throws SQLException;

  Request getMedRequest(Integer id) throws SQLException;

  ArrayList<Request> getAllMedRequest() throws SQLException;
}
