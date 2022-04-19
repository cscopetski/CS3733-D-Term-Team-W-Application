package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.LanguageRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.sql.SQLException;
import java.util.ArrayList;

public interface LanguageRequestDao {

  void addLanguageRequest(LanguageRequest lr) throws SQLException;

  void changeLanguageRequest(LanguageRequest lr) throws SQLException;

  void deleteLanguageRequest(Integer id) throws SQLException;

  Request getLanguageRequest(Integer id) throws SQLException;

  ArrayList<Request> getAllLanguageRequest() throws SQLException;

  void exportLanguageReqCSV(String fileName);

  ArrayList<Request> getEmployeeRequests(Integer employeeID);

  void updateLangReqAtLocation(String nodeID) throws Exception;

  void updateLanguageRequestWithEmployee(Integer employeeID) throws Exception;
}
