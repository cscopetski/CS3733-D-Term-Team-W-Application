package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.LabServiceRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.*;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.LabServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class LabServiceRequestManager implements RequestManager {

  private LabServiceRequestDao lsrdi;

  private static LabServiceRequestManager labServiceRequestManager = new LabServiceRequestManager();

  private LabServiceRequestManager() {}

  public static LabServiceRequestManager getLabServiceRequestManager() {
    return labServiceRequestManager;
  }

  public void setLabServiceRequestDao(LabServiceRequestDao labServiceRequestDao) {
    this.lsrdi = labServiceRequestDao;
  }

  // TODO make this use just SQL
  @Override
  public Request getRequest(Integer reqID) {
    ArrayList<Request> list = lsrdi.getAllLabServiceRequests();

    for (Request l : list) {
      if (l.getRequestID().equals(reqID)) {
        return l;
      }
    }
    return null;
  }

  @Override
  public Request addNewRequest(Integer num, ArrayList<String> fields)
      throws SQLException, StatusError, NonExistingLabServiceRequestType {
    LabServiceRequest lSR;
    // Set status to in queue if it is not already included (from CSVs)
    fields.add(String.format("%d", RequestStatus.InQueue.getValue()));
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    lSR = new LabServiceRequest(num, fields);
    // TODO Special Exception
    if (RequestFactory.getRequestFactory().getReqIDList().add(lSR.getRequestID())) {
      lsrdi.addLabServiceRequest(lSR);
    } else {
      lSR = null;
    }
    return lSR;
  }

  @Override
  public Request addExistingRequest(ArrayList<String> fields)
      throws SQLException, StatusError, NonExistingLabServiceRequestType {
    LabServiceRequest lSR;
    System.out.println("Right before making lSR");
    lSR = new LabServiceRequest(fields);
    // TODO Special Exception
    if (RequestFactory.getRequestFactory().getReqIDList().add(lSR.getRequestID())) {
      lsrdi.addLabServiceRequest(lSR);
    } else {
      lSR = null;
    }
    return lSR;
  }

  public void start(Integer requestID) throws Exception {
    LabServiceRequest request =
        (LabServiceRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.LabServiceRequest);
    if (request != (null)) {
      if (request.getStatus().equals(RequestStatus.InQueue)) {
        request.setStatus(RequestStatus.InProgress);
        lsrdi.changeLabServiceRequest(request);
      }
    }
  }

  public void complete(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    LabServiceRequest request = null;
    try {
      request =
          (LabServiceRequest)
              RequestFacade.getRequestFacade()
                  .findRequest(requestID, RequestType.LabServiceRequest);
    } catch (Exception e) {
      e.printStackTrace();
    }
    request.setStatus(RequestStatus.Completed);
    lsrdi.changeLabServiceRequest(request);
  }

  public void cancel(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    LabServiceRequest request = null;
    try {
      request =
          (LabServiceRequest)
              RequestFacade.getRequestFacade()
                  .findRequest(requestID, RequestType.LabServiceRequest);
    } catch (Exception e) {
      e.printStackTrace();
    }
    request.setStatus(RequestStatus.Cancelled);
    lsrdi.changeLabServiceRequest(request);
  }

  public void changeLoc(LabServiceRequest request, String nodeID) throws SQLException {
    request.setNodeID(nodeID);
    lsrdi.changeLabServiceRequest(request);
  }

  public void reQueue(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    LabServiceRequest request = null;
    try {
      request =
          (LabServiceRequest)
              RequestFacade.getRequestFacade()
                  .findRequest(requestID, RequestType.LabServiceRequest);
    } catch (Exception e) {
      e.printStackTrace();
    }
    request.setStatus(RequestStatus.InQueue);
    lsrdi.changeLabServiceRequest(request);
  }

  public void changeRequest(Request request) throws SQLException {
    lsrdi.changeLabServiceRequest((LabServiceRequest) request);
  }

  @Override
  public ArrayList<Request> getAllRequests() {
    return this.lsrdi.getAllLabServiceRequests();
  }

  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    return lsrdi.getEmployeeRequests(employeeID);
  }

  public void exportReqCSV(String filename) {
    lsrdi.exportLabServiceReqCSV(filename);
  }

  @Override
  public void updateReqAtLocation(String nodeID) throws Exception {
    this.lsrdi.updateLabServiceRequestsAtLocation(nodeID);
  }

  @Override
  public void updateReqWithEmployee(Integer employeeID) throws Exception {
    this.lsrdi.updateLabServiceRequestsWithEmployee(employeeID);
  }
}
