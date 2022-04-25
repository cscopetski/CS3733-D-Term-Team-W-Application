package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.InternalPatientTransportationRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ComputerServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.InternalPatientTransportationRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

import java.sql.Timestamp;
import java.util.ArrayList;

public class InternalPatientTransportationRequestManager implements RequestManager {
    private InternalPatientTransportationRequestDao iptrd;
    private static InternalPatientTransportationRequestManager internalPatientTransportationRequestManager = new InternalPatientTransportationRequestManager();

    private InternalPatientTransportationRequestManager() {
    }

    public static InternalPatientTransportationRequestManager getInternalPatientTransportationRequestManager() {
        return internalPatientTransportationRequestManager;
    }
    public void setIptrd(InternalPatientTransportationRequestDao iptrd){
        this.iptrd = iptrd;
    }
    @Override
    public void start(Integer requestID) throws Exception {
        InternalPatientTransportationRequest iptr = iptrd.getIPTRequest(requestID);
        if (iptr.getStatus() == RequestStatus.InQueue) {
            iptr.setStatus(RequestStatus.InProgress);
            iptrd.changeIPTRequest(iptr);
        }
    }

    @Override
    public void complete(Integer requestID) throws Exception {
        InternalPatientTransportationRequest iptr = iptrd.getIPTRequest(requestID);
        if (iptr.getStatus() == RequestStatus.InProgress) {
            iptr.setStatus(RequestStatus.Completed);
            iptrd.changeIPTRequest(iptr);
        }
    }

    @Override
    public void cancel(Integer requestID) throws Exception {
        InternalPatientTransportationRequest iptr = iptrd.getIPTRequest(requestID);
        if (iptr.getStatus() != RequestStatus.Completed) {
            iptr.setStatus(RequestStatus.Cancelled);
            iptrd.changeIPTRequest(iptr);
        }
    }

    @Override
    public void reQueue(Integer requestID) throws Exception {
        InternalPatientTransportationRequest iptr = iptrd.getIPTRequest(requestID);
        if (iptr.getStatus() == RequestStatus.Cancelled) {
            iptr.setStatus(RequestStatus.InQueue);
            iptrd.changeIPTRequest(iptr);
        }
    }

    @Override
    public Request getRequest(Integer ID) throws Exception {
        return iptrd.getIPTRequest(ID);
    }

    @Override
    public ArrayList<Request> getAllRequests() throws Exception {
        return iptrd.getAllIPTRequest();
    }

    @Override
    public void changeRequest(Request request) throws Exception {
        iptrd.changeIPTRequest((InternalPatientTransportationRequest) request);
    }

    @Override
    public Request addNewRequest(Integer i, ArrayList<String> fields) throws Exception {
        fields.add(0, Integer.toString(i));
        fields.add(Integer.toString(RequestStatus.InQueue.getValue()));
        fields.add(new Timestamp(System.currentTimeMillis()).toString());
        fields.add(new Timestamp(System.currentTimeMillis()).toString());
        InternalPatientTransportationRequest iptr = new InternalPatientTransportationRequest(fields);

        if (RequestFactory.getRequestFactory().getReqIDList().add(iptr.getRequestID())) {
            iptrd.addIPTRequest(iptr);
        } else {
            iptr = null;
        }
        return iptr;
    }

    @Override
    public Request addExistingRequest(ArrayList<String> fields) throws Exception {
        InternalPatientTransportationRequest iptr = new InternalPatientTransportationRequest(fields);

        if(RequestFactory.getRequestFactory().getReqIDList().add(iptr.getRequestID())){
            iptrd.addIPTRequest(iptr);
        }else{
            iptr = null;
        }
        return iptr;
    }

    @Override
    public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
        return iptrd.getEmployeeRequests(employeeID);
    }

    @Override
    public void exportReqCSV(String filename) throws Exception {
        iptrd.exportIPTRequestCSV(filename);
    }

    @Override
    public void updateReqAtLocation(String nodeID) throws Exception {
        this.iptrd.updateIPTRequestAtLocation(nodeID);
    }

    @Override
    public void updateReqWithEmployee(Integer employeeID) throws Exception {
        this.iptrd.updateIPTRequestWithEmployee(employeeID);
    }
}
