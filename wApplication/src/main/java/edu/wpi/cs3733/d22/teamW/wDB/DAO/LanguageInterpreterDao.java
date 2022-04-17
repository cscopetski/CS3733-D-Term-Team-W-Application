package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LanguageInterpreterDao {

    void addLanguageInterpreter(Integer employeeID, String language) throws SQLException;

    void changeLanguageInterpreter(Integer employeeID, String language) throws SQLException;

    void deleteLanguageInterpreter(Integer id) throws SQLException;

    ArrayList<LanguageInterpreter> getAllMedRequest() throws SQLException;

    void exportMedReqCSV(String fileName);

    ArrayList<Request> getEmployeeRequests(Integer employeeID);

    void updateMedReqAtLocation(String nodeID) throws Exception;

    void updateMedRequestsWithEmployee(Integer employeeID) throws Exception;

}
