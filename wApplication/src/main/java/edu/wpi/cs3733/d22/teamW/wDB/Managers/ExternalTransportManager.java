package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.ExternalTransportRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ComputerServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ExternalTransportRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ExternalTransportManager implements RequestManager{

    private static ExternalTransportManager erm = new ExternalTransportManager();
    private ExternalTransportRequestDao erd;

    private ExternalTransportManager() {}

    public static ExternalTransportManager getRequestManager() {
        return erm;
    }

    public void setExternalTransportManagerDao(ExternalTransportRequestDao erd) {
        this.erd = erd;
    }

    @Override
    public void start(Integer requestID) throws Exception {
        ExternalTransportRequest csr = (ExternalTransportRequest) erd.getExTransportRequest(requestID);
        if (csr.getStatus() == RequestStatus.InQueue) {
            csr.setStatus(RequestStatus.InProgress);
            erd.changeExTransportRequest(csr);
        }
    }

    @Override
    public void complete(Integer requestID) throws Exception {
        ExternalTransportRequest csr = (ExternalTransportRequest) erd.getExTransportRequest(requestID);
        if (csr.getStatus() == RequestStatus.InProgress) {
            csr.setStatus(RequestStatus.Completed);
            erd.changeExTransportRequest(csr);
        }
    }

    @Override
    public void cancel(Integer requestID) throws Exception {
        ExternalTransportRequest csr = (ExternalTransportRequest) erd.getExTransportRequest(requestID);
        if (csr.getStatus() != RequestStatus.Completed && csr.getStatus() != RequestStatus.Cancelled) {
            csr.setStatus(RequestStatus.Cancelled);
            erd.changeExTransportRequest(csr);
        }
    }

    @Override
    public void reQueue(Integer requestID) throws Exception {
        ExternalTransportRequest csr = (ExternalTransportRequest) erd.getExTransportRequest(requestID);
        if (csr.getStatus() == RequestStatus.Cancelled) {
            csr.setStatus(RequestStatus.InQueue);
            erd.changeExTransportRequest(csr);
        }
    }

    @Override
    public Request getRequest(Integer ID) throws Exception {
        return erd.getExTransportRequest(ID);
    }

    @Override
    public ArrayList<Request> getAllRequests() throws Exception {
        return erd.getAllExTransportRequest();
    }

    @Override
    public void changeRequest(Request request) throws Exception {
        erd.changeExTransportRequest((ExternalTransportRequest) request);
    }

    @Override
    public Request addNewRequest(Integer i, ArrayList<String> fields) throws Exception {
        fields.add(0, Integer.toString(i));
        fields.add(Integer.toString(RequestStatus.InQueue.getValue()));
        fields.add(new Timestamp(System.currentTimeMillis()).toString());
        fields.add(new Timestamp(System.currentTimeMillis()).toString());
        ExternalTransportRequest csr = new ExternalTransportRequest(fields);

        if (RequestFactory.getRequestFactory().getReqIDList().add(csr.getRequestID())) {
            erd.addExTransportRequest(csr);
        } else {
            csr = null;
        }
        return csr;
    }

    @Override
    public Request addExistingRequest(ArrayList<String> fields) throws Exception {
        ExternalTransportRequest er = new ExternalTransportRequest(fields);
        if (RequestFactory.getRequestFactory().getReqIDList().add(er.getRequestID())) {
            erd.addExTransportRequest(er);
        } else {
            er = null;
        }

        return er;
    }

    @Override
    public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
        return erd.getEmployeeRequests(employeeID);
    }

    @Override
    public void exportReqCSV(String filename) throws Exception {
        erd.exportExTransportReqCSV(filename);
    }

    @Override
    public void updateReqAtLocation(String nodeID) throws Exception {
        erd.updateExTransportReqAtLocation(nodeID);
    }

    @Override
    public void updateReqWithEmployee(Integer employeeID) throws Exception {
        erd.updateExTransportRequestsWithEmployee(employeeID);
    }
}
