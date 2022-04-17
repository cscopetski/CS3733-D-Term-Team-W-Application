package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.SanitationRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.SanitationRequest;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SanitationRequestManager implements RequestManager {

  private SanitationRequestDao srd;

  private static SanitationRequestManager sanitationRequestManager = new SanitationRequestManager();

  private SanitationRequestManager() {}

  public static SanitationRequestManager getSanitationRequestManager() {
    return sanitationRequestManager;
  }

  public void setLabServiceRequestDao(SanitationRequestDao sanitationRequestDao) {
    this.srd = sanitationRequestDao;
  }

  // TODO make this use just SQL
  @Override
  public Request getRequest(Integer reqID) throws Exception {
    return srd.getSanitationRequest(reqID);
  }

  @Override
  public Request addNewRequest(Integer num, ArrayList<String> fields) throws Exception {
    SanitationRequest SR;
    // Set status to in queue if it is not already included (from CSVs)
    fields.add(String.format("%d", RequestStatus.InQueue.getValue()));
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    SR = new SanitationRequest(num, fields);
    // TODO Special Exception
    if (RequestFactory.getRequestFactory().getReqIDList().add(SR.getRequestID())) {
      srd.addSanitationRequest(SR);
    } else {
      SR = null;
    }
    return SR;
  }

  @Override
  public Request addExistingRequest(ArrayList<String> fields) throws Exception {
    SanitationRequest SR;
    SR = new SanitationRequest(fields);
    if (RequestFactory.getRequestFactory().getReqIDList().add(SR.getRequestID())) {
      srd.addSanitationRequest(SR);
    } else {
      SR = null;
    }
    return SR;
  }

  public void start(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    SanitationRequest request =
        (SanitationRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.SanitationService);
    request.setStatus(RequestStatus.InProgress);
    srd.changeSanitationRequest(request);
  }

  public void complete(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    SanitationRequest request =
        (SanitationRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.SanitationService);
    request.setStatus(RequestStatus.Completed);
    srd.changeSanitationRequest(request);
  }

  public void cancel(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    SanitationRequest request =
        (SanitationRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.SanitationService);
    request.setStatus(RequestStatus.Cancelled);
    srd.changeSanitationRequest(request);
  }

  public void reQueue(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    SanitationRequest request =
        (SanitationRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.SanitationService);
    request.setStatus(RequestStatus.InQueue);
    srd.changeSanitationRequest(request);
  }

  public void changeRequest(Request request) throws SQLException {
    srd.changeSanitationRequest((SanitationRequest) request);
  }

  @Override
  public ArrayList<Request> getAllRequests() throws Exception {
    return this.srd.getAllSanitationRequests();
  }

  public void exportReqCSV(String filename) throws Exception {
    srd.exportSanitationReqCSV(filename);
  }

  @Override
  public void updateReqAtLocation(String nodeID)
      throws SQLException, StatusError, NonExistingMedEquip {
    this.srd.updateSanitationRequestsAtLocation(nodeID);
  }

  @Override
  public void updateReqWithEmployee(Integer employeeID) throws Exception {
    this.srd.updateSanitationRequestsWithEmployee(employeeID);
  }
}
