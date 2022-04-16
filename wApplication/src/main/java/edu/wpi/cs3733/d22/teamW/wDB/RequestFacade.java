package edu.wpi.cs3733.d22.teamW.wDB;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
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
  private SanitationRequestManager srm = SanitationRequestManager.getSanitationRequestManager();
  private ComputerServiceRequestManager csrm =
      ComputerServiceRequestManager.getComputerServiceRequestManager();

  private static RequestFacade requestFacade = new RequestFacade();

  private RequestFacade() {}

  public static RequestFacade getRequestFacade() {
    return requestFacade;
  }

  public ArrayList<Request> getAllRequests(RequestType requestType)
      throws SQLException, NonExistingMedEquip {
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
      case ComputerServiceRequest:
        requests.addAll(csrm.getAllRequests());
        break;
      default:
        requests.addAll(getAllRequests());
        break;
    }
    Collections.sort(requests);

    return requests;
  }

  public ArrayList<Request> getAllRequests() throws SQLException, NonExistingMedEquip {
    ArrayList<Request> requests = new ArrayList<Request>();
    requests.addAll(mrm.getAllRequests());
    requests.addAll(merm.getAllRequests());
    requests.addAll(lsrm.getAllRequests());
    requests.addAll(crm.getAllRequests());
    requests.addAll(csrm.getAllRequests());
    Collections.sort(requests);
    return requests;
  }

  public Request findRequest(Integer requestID, RequestType type)
      throws SQLException, StatusError, NonExistingMedEquip {
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
        break;
      case ComputerServiceRequest:
        request = csrm.getRequest(requestID);
        break;
      default:
        request = null;
    }
    return request;
  }

  public void completeRequest(Integer requestID, RequestType type, String nodeID) throws Exception {

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
        break;
      case ComputerServiceRequest:
        csrm.complete(requestID);
        break;
    }
  }

  public void cancelRequest(Integer requestID, RequestType type) throws Exception {

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
        break;
      case ComputerServiceRequest:
        csrm.cancel(requestID);
        break;
    }
  }

  // TODO might want to change this to use requests
  public void startRequest(Integer requestID, RequestType type) throws Exception {

    switch (type) {
      case MedicalEquipmentRequest:
        merm.start(requestID);
        // break;
      case LabServiceRequest:
        lsrm.start(requestID);
      case MedicineDelivery:
        mrm.start(requestID);
      case CleaningRequest:
        crm.start(requestID);
        break;
      case ComputerServiceRequest:
        csrm.start(requestID);
        break;
    }
  }

  public void requeueRequest(Integer requestID, RequestType type) throws Exception {

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
        break;
      case ComputerServiceRequest:
        csrm.reQueue(requestID);
        break;
    }
  }

  public ArrayList<Request> getAllEmployeeRequests(Integer employeeID) {
    ArrayList<Request> employeeRequests = new ArrayList<Request>();

    ArrayList<Request> cleaningReqs = crm.getEmployeeRequests(employeeID);
    employeeRequests.addAll(cleaningReqs);

    ArrayList<Request> labServiceReqs = lsrm.getEmployeeRequests(employeeID);
    employeeRequests.addAll(labServiceReqs);

    ArrayList<Request> medEquipReqs = merm.getEmployeeRequests(employeeID);
    employeeRequests.addAll(medEquipReqs);

    ArrayList<Request> medReqs = mrm.getEmployeeRequests(employeeID);
    employeeRequests.addAll(medReqs);

    ArrayList<Request> compReqs = csrm.getEmployeeRequests(employeeID);
    employeeRequests.addAll(compReqs);

    // TODO once sanitation DB is working
    // ArrayList<Request> sanitationReqs = new ArrayList<Request>();
    // empReqs.addAll(sanitationReqs);

    Collections.sort(employeeRequests);

    return employeeRequests;
  }
}
