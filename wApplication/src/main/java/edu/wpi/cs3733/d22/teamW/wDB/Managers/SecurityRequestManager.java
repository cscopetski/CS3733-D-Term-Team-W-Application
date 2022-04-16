package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.SecurityRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.SecurityRequest;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SecurityRequestManager implements RequestManager {
  private SecurityRequestDao csrd;
  private static SecurityRequestManager securityRequestManager = new SecurityRequestManager();

  private SecurityRequestManager() {}

  public static SecurityRequestManager getSecurityRequestManager() {
    return securityRequestManager;
  }

  public void setSecurityRequestDao(SecurityRequestDao csrd) {
    this.csrd = csrd;
  }

  @Override
  public void start(Integer requestID) throws Exception {
    SecurityRequest csr = csrd.getSecurityRequest(requestID);
    if (csr.getStatus() == RequestStatus.InQueue) {
      csr.setStatus(RequestStatus.InProgress);
      csrd.changeSecurityRequest(csr);
    }
  }

  @Override
  public void complete(Integer requestID) throws SQLException, StatusError {
    SecurityRequest csr = csrd.getSecurityRequest(requestID);
    if (csr.getStatus() == RequestStatus.InProgress) {
      csr.setStatus(RequestStatus.Completed);
      csrd.changeSecurityRequest(csr);
    }
  }

  @Override
  public void cancel(Integer requestID) throws Exception {
    SecurityRequest csr = csrd.getSecurityRequest(requestID);
    if (csr.getStatus() != RequestStatus.Completed) {
      csr.setStatus(RequestStatus.Cancelled);
      csrd.changeSecurityRequest(csr);
    }
  }

  @Override
  public void reQueue(Integer requestID) throws Exception {
    SecurityRequest csr = csrd.getSecurityRequest(requestID);
    if (csr.getStatus() == RequestStatus.Cancelled) {
      csr.setStatus(RequestStatus.InQueue);
      csrd.changeSecurityRequest(csr);
    }
  }

  @Override
  public Request getRequest(Integer ID) throws Exception {
    return csrd.getSecurityRequest(ID);
  }

  @Override
  public ArrayList<Request> getAllRequests() throws Exception {
    return csrd.getAllSecurityRequests();
  }

  @Override
  public Request addNewRequest(Integer i, ArrayList<String> fields) throws Exception {
    fields.add(0, Integer.toString(i));
    fields.add(Integer.toString(RequestStatus.InQueue.getValue()));
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    SecurityRequest csr = new SecurityRequest(fields);

    if (RequestFactory.getRequestFactory().getReqIDList().add(csr.getRequestID())) {
      csrd.addSecurityRequest(csr);
    } else {
      csr = null;
    }
    return csr;
  }

  @Override
  public Request addExistingRequest(ArrayList<String> fields) throws Exception {
    SecurityRequest csr = new SecurityRequest(fields);

    if (RequestFactory.getRequestFactory().getReqIDList().add(csr.getRequestID())) {
      csrd.addSecurityRequest(csr);
    } else {
      csr = null;
    }
    return csr;
  }

  public void changeExistingRequest(Request request) throws SQLException {
    csrd.changeSecurityRequest((SecurityRequest) request);
  }

  @Override
  public void exportReqCSV(String filename) throws Exception {
    csrd.exportSecurityReqCSV(filename);
  }

  @Override
  public void updateReqAtLocation(String nodeID) throws Exception {
    this.csrd.updateSecurityRequestsAtLocation(nodeID);
  }

  @Override
  public void updateReqWithEmployee(Integer employeeID) throws Exception {
    this.csrd.updateSecurityRequestsWithEmployee(employeeID);
  }
}
