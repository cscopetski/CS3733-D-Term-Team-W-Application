package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.LanguageRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.LanguageRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class LanguageRequestManager implements  RequestManager{
    private static LanguageRequestManager lrm = new LanguageRequestManager();
    private LanguageRequestDao lrd;

    private LanguageRequestManager() {}

    public static LanguageRequestManager getLanguageRequestManager() {
        return lrm;
    }

    public void setLanguageRequestDao(LanguageRequestDao lrd) { this.lrd = lrd; }

    @Override
    public Request addNewRequest(Integer num, ArrayList<String> fields)
            throws SQLException, StatusError {
        LanguageRequest lr;
        fields.add(Integer.toString(RequestStatus.InQueue.getValue()));
        fields.add(new Timestamp(System.currentTimeMillis()).toString());
        fields.add(new Timestamp(System.currentTimeMillis()).toString());
        lr = new LanguageRequest(num, fields);
        // TODO Special Exception
        if (RequestFactory.getRequestFactory().getReqIDList().add(lr.getRequestID())) {
            lrd.addLanguageRequest(lr);
        } else {
            lr = null;
        }
        return lr;
    }

    @Override
    public Request addExistingRequest(ArrayList<String> fields) throws Exception {
        LanguageRequest lr;
        lr = new LanguageRequest(fields);
        if (RequestFactory.getRequestFactory().getReqIDList().add(lr.getRequestID())) {
            lrd.addLanguageRequest(lr);
        } else {
            lr = null;
        }
        return lr;
    }

    public void start(Integer requestID) throws SQLException {
        LanguageRequest request = null;
        try {
            request =
                    (LanguageRequest)
                            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.LanguageRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setStatus(RequestStatus.InProgress);
        lrd.changeLanguageRequest(request);
    }

    public void complete(Integer requestID) throws SQLException {
        LanguageRequest request = null;
        try {
            request =
                    (LanguageRequest)
                            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.LanguageRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setStatus(RequestStatus.Completed);
        lrd.changeLanguageRequest(request);
    }

    public void cancel(Integer requestID) throws SQLException {
        LanguageRequest request = null;
        try {
            request =
                    (LanguageRequest)
                            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.LanguageRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setStatus(RequestStatus.Cancelled);
        lrd.changeLanguageRequest(request);
    }

    public void reQueue(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
        LanguageRequest request = null;
        try {
            request =
                    (LanguageRequest)
                            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.LanguageRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setStatus(RequestStatus.InQueue);
        lrd.changeLanguageRequest(request);
    }

    public void delete(Integer requestID) throws SQLException {
        lrd.deleteLanguageRequest(requestID);
    }

    // TODO should or should not have

    public void changeRequest(Request langRequest) throws SQLException {
        lrd.changeLanguageRequest((LanguageRequest) langRequest);
    }

    @Override
    public Request getRequest(Integer ID) throws SQLException {
        return lrd.getLanguageRequest(ID);
    }

    @Override
    public ArrayList<Request> getAllRequests() throws SQLException {
        return lrd.getAllLanguageRequest();
    }

    public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
        return lrd.getEmployeeRequests(employeeID);
    }

    public void exportReqCSV(String filename) {
        lrd.exportLanguageReqCSV(filename);
    }

    @Override
    public void updateReqAtLocation(String nodeID) throws Exception {
        lrd.updateLangReqAtLocation(nodeID);
    }

    @Override
    public void updateReqWithEmployee(Integer employeeID) throws Exception {
        this.lrd.updateLanguageRequestWithEmployee(employeeID);
    }
}
