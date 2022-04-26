package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.ExternalTransportRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ExternalTransportRequestDao {

    void addExTransportRequest(ExternalTransportRequest mr) throws SQLException;

    void changeExTransportRequest(ExternalTransportRequest mr) throws SQLException;

    void deleteExTransportRequest(Integer id) throws SQLException;

    Request getExTransportRequest(Integer id) throws SQLException;

    ArrayList<Request> getAllExTransportRequest() throws SQLException;

    void exportExTransportReqCSV(String fileName);

    ArrayList<Request> getEmployeeRequests(Integer employeeID);

    void updateExTransportReqAtLocation(String nodeID) throws Exception;

    void updateExTransportRequestsWithEmployee(Integer employeeID) throws Exception;

}
