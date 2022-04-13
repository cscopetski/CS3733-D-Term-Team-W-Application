package edu.wpi.cs3733.d22.teamW.wDB;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.CleaningRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LabServiceRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class RequestFacade {

  private MedEquipRequestManager merm = MedEquipRequestManager.getMedEquipRequestManager();
  private LabServiceRequestManager lsrm = LabServiceRequestManager.getLabServiceRequestManager();
  private MedRequestManager mrm = MedRequestManager.getMedRequestManager();
  private CleaningRequestManager crm = CleaningRequestManager.getCleaningRequestManager();

  private static RequestFacade requestFacade = new RequestFacade();

  private RequestFacade() {}

  public static RequestFacade getRequestFacade() {
    return requestFacade;
  }

  public ArrayList<Request> getAllRequests(RequestType requestType) throws SQLException {
    ArrayList<Request> requests = new ArrayList<Request>();

    switch (requestType) {
      case MedicalEquipmentRequest:
        requests.addAll(merm.getAllRequests());
        break;
      case MedicineDelivery:
        requests.addAll(mrm.getAllRequests());
        break;
      case LabServiceRequest:
        requests.addAll(lsrm.getAllRequests());
        break;
      case CleaningRequest:
        requests.addAll(crm.getAllRequests());
        break;
      default:
    }
    Collections.sort(requests);

    return requests;
  }

  public ArrayList<Request> getAllRequests() throws SQLException {
    ArrayList<Request> requests = new ArrayList<Request>();
    requests.addAll(mrm.getAllRequests());
    requests.addAll(merm.getAllRequests());
    requests.addAll(lsrm.getAllRequests());
    requests.addAll(crm.getAllRequests());
    Collections.sort(requests);
    return requests;
  }

  public Request findRequest(Integer requestID, RequestType type) throws SQLException {
    Request request = null;
    switch (type) {
      case MedicalEquipmentRequest:
        request = merm.getRequest(requestID);
        break;
      case LabServiceRequest:
        request = lsrm.getRequest(requestID);
        break;
      case MedicineDelivery:
        request = mrm.getRequest(requestID);
        break;
      case CleaningRequest:
        request = crm.getRequest(requestID);
      default:
        request = null;
    }
    return request;
  }

  public void completeRequest(Integer requestID, RequestType type, String nodeID)
      throws SQLException {

    switch (type) {
      case MedicalEquipmentRequest:
        merm.complete(requestID);
        break;
      case LabServiceRequest:
        lsrm.complete(requestID);
        break;
      case MedicineDelivery:
        mrm.complete(requestID);
        break;
      case CleaningRequest:
        crm.complete(requestID, nodeID);
    }
  }

  public void cancelRequest(Integer requestID, RequestType type) throws SQLException {

    switch (type) {
      case MedicalEquipmentRequest:
        merm.cancel(requestID);
        break;
      case LabServiceRequest:
        lsrm.cancel(requestID);
        break;
      case MedicineDelivery:
        mrm.cancel(requestID);
        break;
      case CleaningRequest:
        crm.cancel(requestID);
    }
  }

  // TODO might want to change this to use requests
  public void startRequest(Integer requestID, RequestType type) throws SQLException {

    switch (type) {
      case MedicalEquipmentRequest:
        merm.start(requestID);
        break;
      case LabServiceRequest:
        lsrm.start(requestID);
        break;
      case MedicineDelivery:
        mrm.start(requestID);
        break;
      case CleaningRequest:
        crm.start(requestID);
    }
  }

  public void requeueRequest(Integer requestID, RequestType type)
          throws SQLException {

    switch (type) {
      case MedicalEquipmentRequest:
        merm.reQueue(requestID);
        break;
      case LabServiceRequest:
        lsrm.reQueue(requestID);
        break;
      case MedicineDelivery:
        mrm.reQueue(requestID);
        break;
      case CleaningRequest:
        crm.reQueue(requestID);
    }
  }
}
