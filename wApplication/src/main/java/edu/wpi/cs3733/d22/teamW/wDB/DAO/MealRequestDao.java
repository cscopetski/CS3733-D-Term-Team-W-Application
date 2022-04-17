package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MealRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MealRequestDao {

  ArrayList<Request> getAllMealServiceRequests();

  void addMealServiceRequest(MealRequest lsr) throws SQLException;

  void changeMealServiceRequest(MealRequest lsr) throws SQLException;

  void deleteMealServiceRequest(Integer requestID) throws SQLException;

  void exportMealServiceReqCSV(String filename);

  Request getMealRequest(Integer id);

  ArrayList<Request> getEmployeeRequests(Integer employeeID);

  void updateMealServiceRequestsAtLocation(String nodeID) throws Exception;

  void updateMealServiceRequestsWithEmployee(Integer employeeID) throws Exception;
}
