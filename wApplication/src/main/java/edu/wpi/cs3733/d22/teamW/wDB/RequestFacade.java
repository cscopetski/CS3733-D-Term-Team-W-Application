package edu.wpi.cs3733.d22.teamW.wDB;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.*;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class RequestFacade {

  private MedEquipRequestManager merm = MedEquipRequestManager.getMedEquipRequestManager();
  private LabServiceRequestManager lsrm = LabServiceRequestManager.getLabServiceRequestManager();
  private MedRequestManager mrm = MedRequestManager.getMedRequestManager();
  private CleaningRequestManager crm = CleaningRequestManager.getCleaningRequestManager();
  private ComputerServiceRequestManager csrm =
      ComputerServiceRequestManager.getComputerServiceRequestManager();
  private MealRequestManager mealRequestManager = MealRequestManager.getMealRequestManager();
  private FlowerRequestManager flowerRequestManager =
      FlowerRequestManager.getFlowerRequestManager();
  private GiftDeliveryRequestManager giftDeliveryRequestManager =
      GiftDeliveryRequestManager.getGiftDeliveryRequestManager();
  private SanitationRequestManager sanitationRequestManager =
      SanitationRequestManager.getSanitationRequestManager();
  private SecurityRequestManager securityRequestManager =
      SecurityRequestManager.getSecurityRequestManager();
  private LanguageRequestManager languageRequestManager =
      LanguageRequestManager.getLanguageRequestManager();

  final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
  final String labServiceRequestFileName = "LabRequests.csv";
  final String medRequestFileName = "MedRequests.csv";
  final String flowerRequestFileName = "FlowerRequests.csv";
  final String computerServiceRequestFileName = "ComputerServiceRequest.csv";
  final String sanitationRequestsFileName = "SanitationRequests.csv";
  final String giftDeliveryRequestFileName = "GiftDeliveryRequest.csv";
  final String cleaningRequestFileName = "CleaningRequest.csv";
  final String mealRequestFileName = "MealRequest.csv";
  final String securityRequestFileName = "SecurityRequest.csv";
  final String languageRequestFileName = "LanguageRequests.csv";

  private File medEquipReqs = new File("edu/wpi/cs3733/d22/teamW/wDB/CSVs/MedicalEquipmentRequest.csv");
  private File labServiceReqs = new File("edu/wpi/cs3733/d22/teamW/wDB/CSVs/LabRequests.csv");
  private File medReqs = new File("edu/wpi/cs3733/d22/teamW/wDB/CSVs/MedRequests.csv");
  private File flowerReqs = new File("edu/wpi/cs3733/d22/teamW/wDB/CSVs/FlowerRequests.csv");
  private File computerServiceReqs = new File("edu/wpi/cs3733/d22/teamW/wDB/CSVs/ComputerServiceRequest.csv");
  private File sanitationReqs = new File("edu/wpi/cs3733/d22/teamW/wDB/CSVs/SanitationRequests.csv");
  private File giftDeliveryReqs = new File("edu/wpi/cs3733/d22/teamW/wDB/CSVs/GiftDeliveryRequest.csv");
  private File cleaningReqs = new File("edu/wpi/cs3733/d22/teamW/wDB/CSVs/CleaningRequest.csv");
  private File mealDeliveryReqs = new File("edu/wpi/cs3733/d22/teamW/wDB/CSVs/MealRequest.csv");
  private File securityReqs = new File("edu/wpi/cs3733/d22/teamW/wDB/CSVs/SecurityRequest.csv");
  private File languageReqs = new File("edu/wpi/cs3733/d22/teamW/wDB/CSVs/LanguageRequest.csv");



  private static RequestFacade requestFacade = new RequestFacade();

  private RequestFacade() {}

  public static RequestFacade getRequestFacade() {
    return requestFacade;
  }

  public ArrayList<Request> getAllRequests(RequestType requestType) throws Exception {
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
      case MealDelivery:
        requests.addAll(mealRequestManager.getAllRequests());
        break;
      case FlowerRequest:
        requests.addAll(flowerRequestManager.getAllRequests());
        break;
      case GiftDelivery:
        requests.addAll(giftDeliveryRequestManager.getAllRequests());
        break;
      case SanitationService:
        requests.addAll(sanitationRequestManager.getAllRequests());
        break;
      case SecurityService:
        requests.addAll(securityRequestManager.getAllRequests());
        break;
      case LanguageRequest:
        requests.addAll(languageRequestManager.getAllRequests());
        break;
      default:
        requests.addAll(getAllRequests());
        break;
    }
    Collections.sort(requests);

    return requests;
  }

  public ArrayList<Request> getAllRequests() throws Exception {
    ArrayList<Request> requests = new ArrayList<Request>();
    requests.addAll(mrm.getAllRequests());
    requests.addAll(merm.getAllRequests());
    requests.addAll(lsrm.getAllRequests());
    requests.addAll(crm.getAllRequests());
    requests.addAll(csrm.getAllRequests());
    requests.addAll(mealRequestManager.getAllRequests());
    requests.addAll(flowerRequestManager.getAllRequests());
    requests.addAll(giftDeliveryRequestManager.getAllRequests());
    requests.addAll(sanitationRequestManager.getAllRequests());
    requests.addAll(securityRequestManager.getAllRequests());
    requests.addAll(languageRequestManager.getAllRequests());
    Collections.sort(requests);
    return requests;
  }

  public Request findRequest(Integer requestID, RequestType type) throws Exception {
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
      case MealDelivery:
        request = mealRequestManager.getRequest(requestID);
        break;
      case FlowerRequest:
        request = flowerRequestManager.getRequest(requestID);
        break;
      case GiftDelivery:
        request = giftDeliveryRequestManager.getRequest(requestID);
        break;
      case SanitationService:
        request = sanitationRequestManager.getRequest(requestID);
        break;
      case SecurityService:
        request = securityRequestManager.getRequest(requestID);
        break;
      case LanguageRequest:
        request = languageRequestManager.getRequest(requestID);
        break;
      default:
        request = null;
    }
    if (request.equals(null)) {
      throw new NonExistingRequestID();
    }
    return request;
  }

  public void completeRequest(Integer requestID, RequestType type, String nodeID) throws Exception {
    Request request = findRequest(requestID, type);
    if (!(request.getStatus().equals(RequestStatus.InProgress))) {
      if (!request.getStatus().equals(RequestStatus.Completed)) {
        throw new CannotComplete();
      }
    } else {
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
        case MealDelivery:
          mealRequestManager.complete(requestID);
          break;
        case FlowerRequest:
          flowerRequestManager.complete(requestID);
          break;
        case GiftDelivery:
          giftDeliveryRequestManager.complete(requestID);
          break;
        case SanitationService:
          sanitationRequestManager.complete(requestID);
          break;
        case SecurityService:
          securityRequestManager.complete(requestID);
          break;
        case LanguageRequest:
          languageRequestManager.complete(requestID);
          break;
      }
    }
  }

  public void cancelRequest(Integer requestID, RequestType type) throws Exception {
    Request request = findRequest(requestID, type);
    if (request.getStatus().equals(RequestStatus.Completed)) {
      throw new CannotCancel();
    } else if (!request.getStatus().equals(RequestStatus.Cancelled)) {
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
        case MealDelivery:
          mealRequestManager.cancel(requestID);
          break;
        case FlowerRequest:
          flowerRequestManager.cancel(requestID);
          break;
        case GiftDelivery:
          giftDeliveryRequestManager.cancel(requestID);
          break;
        case SanitationService:
          sanitationRequestManager.cancel(requestID);
          break;
        case SecurityService:
          securityRequestManager.cancel(requestID);
          break;
        case LanguageRequest:
          languageRequestManager.cancel(requestID);
          break;
      }
    }
  }

  // TODO might want to change this to use requests
  public void startRequest(Integer requestID, RequestType type) throws Exception {
    Request request = findRequest(requestID, type);
    if (!(request.getStatus().equals(RequestStatus.InQueue))) {
      if (!request.getStatus().equals(RequestStatus.InProgress)) {
        throw new CannotStart();
      }
    } else {
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
          break;
        case ComputerServiceRequest:
          csrm.start(requestID);
          break;
        case MealDelivery:
          mealRequestManager.start(requestID);
          break;
        case FlowerRequest:
          flowerRequestManager.start(requestID);
          break;
        case GiftDelivery:
          giftDeliveryRequestManager.start(requestID);
          break;
        case SanitationService:
          sanitationRequestManager.start(requestID);
          break;
        case SecurityService:
          securityRequestManager.start(requestID);
          break;
        case LanguageRequest:
          languageRequestManager.start(requestID);
          break;
      }
    }
  }

  public void requeueRequest(Integer requestID, RequestType type) throws Exception {
    Request request = findRequest(requestID, type);
    if (!request.getStatus().equals(RequestStatus.Cancelled)) {
      throw new CannotStart();
    } else {
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
        case MealDelivery:
          mealRequestManager.reQueue(requestID);
          break;
        case FlowerRequest:
          flowerRequestManager.reQueue(requestID);
          break;
        case GiftDelivery:
          giftDeliveryRequestManager.reQueue(requestID);
          break;
        case SanitationService:
          sanitationRequestManager.reQueue(requestID);
          break;
        case SecurityService:
          securityRequestManager.reQueue(requestID);
          break;
        case LanguageRequest:
          languageRequestManager.reQueue(requestID);
          break;
      }
    }
  }

  public ArrayList<Request> getAllEmployeeRequests(Integer employeeID) {
    ArrayList<Request> employeeRequests = new ArrayList<Request>();

    employeeRequests.addAll(crm.getEmployeeRequests(employeeID));
    employeeRequests.addAll(lsrm.getEmployeeRequests(employeeID));
    employeeRequests.addAll(merm.getEmployeeRequests(employeeID));
    employeeRequests.addAll(mrm.getEmployeeRequests(employeeID));
    employeeRequests.addAll(csrm.getEmployeeRequests(employeeID));
    employeeRequests.addAll(mealRequestManager.getEmployeeRequests(employeeID));
    employeeRequests.addAll(flowerRequestManager.getEmployeeRequests(employeeID));
    employeeRequests.addAll(giftDeliveryRequestManager.getEmployeeRequests(employeeID));
    employeeRequests.addAll(sanitationRequestManager.getEmployeeRequests(employeeID));
    employeeRequests.addAll(securityRequestManager.getEmployeeRequests(employeeID));
    employeeRequests.addAll(languageRequestManager.getEmployeeRequests(employeeID));

    Collections.sort(employeeRequests);

    return employeeRequests;
  }

  public void exportAllRequests() {
    try {
      crm.exportReqCSV(cleaningRequestFileName);
      lsrm.exportReqCSV(labServiceRequestFileName);
      merm.exportReqCSV(medEquipRequestFileName);
      mrm.exportReqCSV(medRequestFileName);
      csrm.exportReqCSV(computerServiceRequestFileName);
      mealRequestManager.exportReqCSV(mealRequestFileName);
      flowerRequestManager.exportReqCSV(flowerRequestFileName);
      giftDeliveryRequestManager.exportReqCSV(giftDeliveryRequestFileName);
      sanitationRequestManager.exportReqCSV(sanitationRequestsFileName);
      securityRequestManager.exportReqCSV(securityRequestFileName);
      languageRequestManager.exportReqCSV(languageRequestFileName);
    } catch (NonExistingMedEquip e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  File labReq = new File();

}
