package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MedRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    if (fields.size() == 6) {
      fields.add("0");
      fields.add(new Timestamp(System.currentTimeMillis()).toString());
      fields.add(new Timestamp(System.currentTimeMillis()).toString());
      mr = new MedRequest(num, fields);
    } else {
      mr = new MedRequest(fields);
    }
    // TODO Special Exception
    if (RequestFactory.getRequestFactory().getReqIDList().add(mr.getRequestID())) {
      mrd.addMedRequest(mr);
    } else {
      mr = null;
    }
    return mr;
  }

  public void start(Integer requestID) throws SQLException {
    MedRequest request =
        (MedRequest)
            RequestFactory.getRequestFactory().findRequest(requestID, RequestType.MedicineDelivery);
    request.setStatus(RequestStatus.InProgress);
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getMedicine(),
        request.getNodeID(),
        request.getEmployeeID(),
        request.getEmergency(),
        request.getStatus(),
        request.getCreatedTimestamp(),
        new Timestamp(System.currentTimeMillis()));
  }

  public void complete(Integer requestID) throws SQLException {
    MedRequest request =
        (MedRequest)
            RequestFactory.getRequestFactory().findRequest(requestID, RequestType.MedicineDelivery);
    request.setStatus(RequestStatus.InProgress);
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getMedicine(),
        request.getNodeID(),
        request.getEmployeeID(),
        request.getEmergency(),
        request.getStatus(),
        request.getCreatedTimestamp(),
        new Timestamp(System.currentTimeMillis()));
  }

  public void cancel(Integer requestID) throws SQLException {
    MedRequest request =
        (MedRequest)
            RequestFactory.getRequestFactory().findRequest(requestID, RequestType.MedicineDelivery);
    request.setStatus(RequestStatus.InProgress);
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getMedicine(),
        request.getNodeID(),
        request.getEmployeeID(),
        request.getEmergency(),
        request.getStatus(),
        request.getCreatedTimestamp(),
        new Timestamp(System.currentTimeMillis()));
  }

  public void reQueue(Integer requestID) throws SQLException {
    MedRequest request =
        (MedRequest)
            RequestFactory.getRequestFactory().findRequest(requestID, RequestType.MedicineDelivery);
    request.setStatus(RequestStatus.InProgress);
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getMedicine(),
        request.getNodeID(),
        request.getEmployeeID(),
        request.getEmergency(),
        request.getStatus(),
        request.getCreatedTimestamp(),
        new Timestamp(System.currentTimeMillis()));
  }

  public void delete(Integer requestID) throws SQLException {
    mrd.deleteMedRequest(requestID);
  }

  // TODO should or should not have
  /*
  public void changeMedRequest(
      Integer id,
      String m,
      String n,
      Integer en,
      Integer ie,
      RequestStatus rs,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp)
      throws SQLException {
    mrd.changeMedRequest(id, m, n, en, ie, rs, createdTimestamp, updatedTimestamp);
  }*/

  @Override
  public Request getRequest(Integer ID) throws SQLException {
    return mrd.getMedRequest(ID);
  }

  @Override
  public ArrayList<Request> getAllRequests() throws SQLException {
    return mrd.getAllMedRequest();
  }
}
