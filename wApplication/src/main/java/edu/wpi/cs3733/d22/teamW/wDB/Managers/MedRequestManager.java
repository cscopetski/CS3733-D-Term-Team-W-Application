package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MedRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

import java.sql.SQLException;
import java.util.ArrayList;

public class MedRequestManager implements ServiceRequestManager {
  private static MedRequestManager mrm = new MedRequestManager();
  private MedRequestDao mrd;

  private MedRequestManager() {}

  public static MedRequestManager getMedRequestManager() {
    return mrm;
  }

  public void setMedRequestDao(MedRequestDao mrd) {
    this.mrd = mrd;
  }

  public Request addRequest(Integer num, ArrayList<String> fields) throws SQLException {
    MedRequest mr;

    if (fields.size() == 4) {
      fields.add("0");
      mr = new MedRequest(num, fields);
    } else {
      mr = new MedRequest(fields);
    }
    mrd.addMedRequest(mr);
    return mr;
  }

  public void start(Integer requestID) throws SQLException {
    MedRequest request = (MedRequest) RequestFactory.getRequestFactory().findRequest(requestID);
    request.setStatus(RequestStatus.InProgress);
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getMedicine(),
        request.getNodeID(),
        request.getEmployeeName(),
        request.getEmergency(),
        request.getStatus());
  }

  public void complete(Integer requestID) throws SQLException {
    MedRequest request = (MedRequest) RequestFactory.getRequestFactory().findRequest(requestID);
    request.setStatus(RequestStatus.InProgress);
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getMedicine(),
        request.getNodeID(),
        request.getEmployeeName(),
        request.getEmergency(),
        request.getStatus());
  }

  public void cancel(Integer requestID) throws SQLException {
    MedRequest request = (MedRequest) RequestFactory.getRequestFactory().findRequest(requestID);
    request.setStatus(RequestStatus.InProgress);
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getMedicine(),
        request.getNodeID(),
        request.getEmployeeName(),
        request.getEmergency(),
        request.getStatus());
  }

  public void reQueue(Integer requestID) throws SQLException {
    MedRequest request = (MedRequest) RequestFactory.getRequestFactory().findRequest(requestID);
    request.setStatus(RequestStatus.InProgress);
    mrd.changeMedRequest(
            request.getRequestID(),
            request.getMedicine(),
            request.getNodeID(),
            request.getEmployeeName(),
            request.getEmergency(),
            request.getStatus());
  }


  public void delete(Integer requestID) throws SQLException {
    mrd.deleteMedRequest(requestID);
  }

  public void changeMedRequest(Integer id, String m, String n, String en, Integer ie, RequestStatus rs)
      throws SQLException {
    mrd.changeMedRequest(id, m, n, en, ie, rs);
  }

  @Override
  public Request getRequest(Integer ID) throws SQLException {
    return mrd.getMedRequest(ID);
  }

  @Override
  public ArrayList<Request> getAllRequests() throws SQLException {
    return mrd.getAllMedRequest();
  }
}
