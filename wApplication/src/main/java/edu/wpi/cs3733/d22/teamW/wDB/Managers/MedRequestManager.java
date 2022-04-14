package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MedRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoMedicine;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.Units;
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

  public Request addRequest(Integer num, ArrayList<String> fields) throws SQLException, NoMedicine {
    MedRequest mr;

    if (fields.size() == 6) {
      fields.add("0");
      mr = new MedRequest(num, fields);
    } else {
      mr = new MedRequest(fields);
    }
    System.out.println(mr.toValuesString());
    // TODO Special Exception
    if (RequestFactory.getRequestFactory().getReqIDList().add(mr.getRequestID())) {
      mrd.addMedRequest(mr);
    } else {
      mr = null;
    }
    return mr;
  }

  public boolean start(Integer requestID) throws SQLException {
    MedRequest request =
        (MedRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MedicineDelivery);
    request.setStatus(RequestStatus.InProgress);
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getPatientLast(),
        request.getPatientFirst(),
        request.getMedicine().getString(),
        request.getQuantity(),
        request.getUnit(),
        request.getNodeID(),
        request.getBedNumber(),
        request.getEmployeeID(),
        request.getEmergency(),
        request.getStatus());
    return true;
  }

  public void complete(Integer requestID) throws SQLException {
    MedRequest request =
        (MedRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MedicineDelivery);
    request.setStatus(RequestStatus.Completed);
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getPatientLast(),
        request.getPatientFirst(),
        request.getMedicine().getString(),
        request.getQuantity(),
        request.getUnit(),
        request.getNodeID(),
        request.getBedNumber(),
        request.getEmployeeID(),
        request.getEmergency(),
        request.getStatus());
  }

  public void cancel(Integer requestID) throws SQLException {
    MedRequest request =
        (MedRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MedicineDelivery);
    request.setStatus(RequestStatus.Cancelled);
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getPatientLast(),
        request.getPatientFirst(),
        request.getMedicine().getString(),
        request.getQuantity(),
        request.getUnit(),
        request.getNodeID(),
        request.getBedNumber(),
        request.getEmployeeID(),
        request.getEmergency(),
        request.getStatus());
  }

  public void reQueue(Integer requestID) throws SQLException {
    MedRequest request =
        (MedRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MedicineDelivery);
    request.setStatus(RequestStatus.InQueue);
    mrd.changeMedRequest(
        request.getRequestID(),
        request.getPatientLast(),
        request.getPatientFirst(),
        request.getMedicine().getString(),
        request.getQuantity(),
        request.getUnit(),
        request.getNodeID(),
        request.getBedNumber(),
        request.getEmployeeID(),
        request.getEmergency(),
        request.getStatus());
  }

  public void delete(Integer requestID) throws SQLException {
    mrd.deleteMedRequest(requestID);
  }

  // TODO should or should not have

  public void changeMedRequest(
      Integer requestID,
      String patientLast,
      String patientFirst,
      String medicine,
      Double quantity,
      Units unit,
      String nodeID,
      Integer bedNumber,
      Integer employeeID,
      Integer emergency,
      RequestStatus status)
      throws SQLException {
    mrd.changeMedRequest(
        requestID,
        patientLast,
        patientFirst,
        medicine,
        quantity,
        unit,
        nodeID,
        bedNumber,
        employeeID,
        emergency,
        status);
  }

  @Override
  public Request getRequest(Integer ID) throws SQLException {
    return mrd.getMedRequest(ID);
  }

  @Override
  public ArrayList<Request> getAllRequests() throws SQLException {
    return mrd.getAllMedRequest();
  }

  public void exportReqCSV(String filename) {
    mrd.exportMedReqCSV(filename);
  }
}
