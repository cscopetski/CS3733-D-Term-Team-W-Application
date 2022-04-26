package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.ComputerServiceRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ComputerServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ComputerServiceRequestManager implements RequestManager {
    private ComputerServiceRequestDao csrd;
    private static ComputerServiceRequestManager computerServiceRequestManager =
            new ComputerServiceRequestManager();

    private ComputerServiceRequestManager() {
    }

    public static ComputerServiceRequestManager getComputerServiceRequestManager() {
        return computerServiceRequestManager;
    }

    public void setComputerServiceRequestDao(ComputerServiceRequestDao csrd) {
        this.csrd = csrd;
    }

    @Override
    public void start(Integer requestID) throws Exception {
        ComputerServiceRequest csr = csrd.getComputerServiceRequest(requestID);
        if (csr.getStatus() == RequestStatus.InQueue) {
            csr.setStatus(RequestStatus.InProgress);
            csrd.changeComputerServiceRequest(csr);
        }
    }

    @Override
    public void complete(Integer requestID) throws SQLException, StatusError {
        ComputerServiceRequest csr = csrd.getComputerServiceRequest(requestID);
        if (csr.getStatus() == RequestStatus.InProgress) {
            csr.setStatus(RequestStatus.Completed);
            csrd.changeComputerServiceRequest(csr);
        }
    }

    @Override
    public void cancel(Integer requestID) throws Exception {
        ComputerServiceRequest csr = csrd.getComputerServiceRequest(requestID);
        if (csr.getStatus() != RequestStatus.Completed) {
            csr.setStatus(RequestStatus.Cancelled);
            csrd.changeComputerServiceRequest(csr);
        }
    }

    @Override
    public void reQueue(Integer requestID) throws Exception {
        ComputerServiceRequest csr = csrd.getComputerServiceRequest(requestID);
        if (csr.getStatus() == RequestStatus.Cancelled) {
            csr.setStatus(RequestStatus.InQueue);
            csrd.changeComputerServiceRequest(csr);
        }
    }

    @Override
    public Request getRequest(Integer ID) throws StatusError {
        return csrd.getComputerServiceRequest(ID);
    }

    @Override
    public ArrayList<Request> getAllRequests() {
        return csrd.getAllComputerServiceRequests();
    }

    @Override
    public Request addNewRequest(Integer i, ArrayList<String> fields) throws Exception {
        fields.add(0, Integer.toString(i));
        fields.add(Integer.toString(RequestStatus.InQueue.getValue()));
        fields.add(new Timestamp(System.currentTimeMillis()).toString());
        fields.add(new Timestamp(System.currentTimeMillis()).toString());
        ComputerServiceRequest csr = new ComputerServiceRequest(fields);

        if (RequestFactory.getRequestFactory().getReqIDList().add(csr.getRequestID())) {
            csrd.addComputerServiceRequest(csr);
        } else {
            csr = null;
        }
        return csr;
    }

    @Override
    public Request addExistingRequest(ArrayList<String> fields) throws Exception {
        ComputerServiceRequest csr = new ComputerServiceRequest(fields);

        if (RequestFactory.getRequestFactory().getReqIDList().add(csr.getRequestID())) {
            csrd.addComputerServiceRequest(csr);
        } else {
            csr = null;
        }

        return csr;
    }

    public void changeRequest(Request request) throws SQLException {
        csrd.changeComputerServiceRequest((ComputerServiceRequest) request);
    }

    @Override
    public void exportReqCSV(String filename) throws Exception {
        csrd.exportComputerServiceReqCSV(filename);
    }

    @Override
    public void updateReqAtLocation(String nodeID) throws Exception {
        this.csrd.updateCompServiceRequestsAtLocation(nodeID);
    }

    @Override
    public void updateReqWithEmployee(Integer employeeID) throws Exception {
        this.csrd.updateCompServiceRequestsWithEmployee(employeeID);
    }

    public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
        return this.csrd.getEmployeeRequests(employeeID);
    }
}
