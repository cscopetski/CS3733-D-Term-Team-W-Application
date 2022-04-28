package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ComputerServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.InternalPatientTransportationRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InternalPatientTransportationRequestDao {
    void addIPTRequest(InternalPatientTransportationRequest csr) throws SQLException;

    void changeIPTRequest(InternalPatientTransportationRequest csr) throws SQLException;

    void deleteIPTRequest(Integer requestID) throws SQLException;

    void exportIPTRequestCSV(String filename);

    InternalPatientTransportationRequest getIPTRequest(Integer requestID) throws StatusError;

    ArrayList<Request> getAllIPTRequest();

    void updateIPTRequestAtLocation(String nodeID) throws Exception;

    void updateIPTRequestWithEmployee(Integer employeeID) throws Exception;

    ArrayList<Request> getEmployeeRequests(Integer employeeID);
}
