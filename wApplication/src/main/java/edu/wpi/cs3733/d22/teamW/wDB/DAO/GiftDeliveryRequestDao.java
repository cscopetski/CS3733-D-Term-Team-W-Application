package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.GiftDeliveryRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.sql.SQLException;
import java.util.ArrayList;

public interface GiftDeliveryRequestDao {

  ArrayList<Request> getAllGiftDeliveryRequests() throws Exception;

  void addGiftDeliveryRequest(GiftDeliveryRequest sr) throws SQLException;

  GiftDeliveryRequest getGiftDeliveryRequest(Integer requestID) throws Exception;

  void changeGiftDeliveryRequest(GiftDeliveryRequest sr) throws SQLException;

  void deleteGiftDeliveryRequest(Integer requestID) throws SQLException;

  void exportGiftDeliveryReqCSV(String filename) throws Exception;

  ArrayList<Request> getEmployeeRequests(Integer employeeID);

  void updateGiftDeliveryRequestsAtLocation(String nodeID) throws Exception;

  void updateGiftDeliveryRequestsWithEmployee(Integer employeeID) throws Exception;
}
