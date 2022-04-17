package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MedRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.InvalidUnit;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoMedicine;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MedRequestManager implements RequestManager {
  private static MedRequestManager mrm = new MedRequestManager();
  private MedRequestDao mrd;

  private MedRequestManager() {}

  public static MedRequestManager getMedRequestManager() {
    return mrm;
  }

  public void setMedRequestDao(MedRequestDao mrd) {
    this.mrd = mrd;
  }

  @Override
  public Request addNewRequest(Integer num, ArrayList<String> fields)
      throws SQLException, NoMedicine, StatusError, InvalidUnit {
    MedRequest mr;
    fields.add(Integer.toString(RequestStatus.InQueue.getValue()));
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    mr = new MedRequest(num, fields);
    // TODO Special Exception
    if (RequestFactory.getRequestFactory().getReqIDList().add(mr.getRequestID())) {
      mrd.addMedRequest(mr);
    } else {
      mr = null;
    }
    return mr;
  }

  @Override
  public Request addExistingRequest(ArrayList<String> fields) throws Exception {
    MedRequest mr;
    mr = new MedRequest(fields);
    if (RequestFactory.getRequestFactory().getReqIDList().add(mr.getRequestID())) {
      mrd.addMedRequest(mr);
    } else {
      mr = null;
    }
    return mr;
  }

  public void start(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    MedRequest request = null;
    try {
      request =
          (MedRequest)
              RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MedicineDelivery);
    } catch (Exception e) {
      e.printStackTrace();
    }
    request.setStatus(RequestStatus.InProgress);
    mrd.changeMedRequest(request);
  }

  public void complete(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    MedRequest request = null;
    try {
      request =
          (MedRequest)
              RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MedicineDelivery);
    } catch (Exception e) {
      e.printStackTrace();
    }
    request.setStatus(RequestStatus.Completed);
    mrd.changeMedRequest(request);
  }

  public void cancel(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    MedRequest request = null;
    try {
      request =
          (MedRequest)
              RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MedicineDelivery);
    } catch (Exception e) {
      e.printStackTrace();
    }
    request.setStatus(RequestStatus.Cancelled);
    mrd.changeMedRequest(request);
  }

  public void reQueue(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    MedRequest request = null;
    try {
      request =
          (MedRequest)
              RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MedicineDelivery);
    } catch (Exception e) {
      e.printStackTrace();
    }
    request.setStatus(RequestStatus.InQueue);
    mrd.changeMedRequest(request);
  }

  public void delete(Integer requestID) throws SQLException {
    mrd.deleteMedRequest(requestID);
  }

  // TODO should or should not have

  public void changeRequest(Request medRequest) throws SQLException {
    mrd.changeMedRequest((MedRequest) medRequest);
  }

  @Override
  public Request getRequest(Integer ID) throws SQLException {
    return mrd.getMedRequest(ID);
  }

  @Override
  public ArrayList<Request> getAllRequests() throws SQLException {
    return mrd.getAllMedRequest();
  }

  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    return mrd.getEmployeeRequests(employeeID);
  }

  public void exportReqCSV(String filename) {
    mrd.exportMedReqCSV(filename);
  }

  @Override
  public void updateReqAtLocation(String nodeID) throws Exception {
    this.mrd.updateMedReqAtLocation(nodeID);
  }

  @Override
  public void updateReqWithEmployee(Integer employeeID) throws Exception {
    this.mrd.updateMedRequestsWithEmployee(employeeID);
  }
}
