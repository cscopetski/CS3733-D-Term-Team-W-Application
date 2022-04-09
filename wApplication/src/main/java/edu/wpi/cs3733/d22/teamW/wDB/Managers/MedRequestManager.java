package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MedRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.Request;
import java.sql.SQLException;
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
  public Request addRequest(Integer num, ArrayList<String> fields) {
    MedRequest mr;

    if (fields.size() == 4) {
      fields.add("0");
      mr = new MedRequest(num, fields);
    } else {
      mr = new MedRequest(fields);
    }

    return mr;
  }

  public void start(MedRequest request) throws SQLException {
    request.start();
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getMedicine(),
        request.getNodeID(),
        request.getEmployeeName(),
        request.getEmergency(),
        request.getStatusInt());
  }

  public void complete(MedRequest request) throws SQLException {
    request.complete();
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getMedicine(),
        request.getNodeID(),
        request.getEmployeeName(),
        request.getEmergency(),
        request.getStatusInt());
  }

  public void cancel(MedRequest request) throws SQLException {
    request.cancel();
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getMedicine(),
        request.getNodeID(),
        request.getEmployeeName(),
        request.getEmergency(),
        request.getStatusInt());
  }

  public void delete(MedRequest request) throws SQLException {
    mrd.deleteMedRequest(request.getRequestID());
  }

  public void delete(Integer requestID) throws SQLException {
    mrd.deleteMedRequest(requestID);
  }

  public void changeMedRequest(Integer id, String m, String n, String en, Integer ie, Integer rs)
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
