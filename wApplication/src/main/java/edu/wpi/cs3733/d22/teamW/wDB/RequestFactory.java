package edu.wpi.cs3733.d22.teamW.wDB;


import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.util.*;

public class RequestFactory {

  // must be a singleton so that the counter does not get messed up

  // check DB for existing requests when are using external DB and not embedded one
  private MedEquipRequestManager merm = MedEquipRequestManager.getMedEquipRequestManager();
  private LabServiceRequestManager lsrm = LabServiceRequestManager.getLabServiceRequestManager();
  private MedRequestManager mrm = MedRequestManager.getMedRequestManager();
  private CleaningRequestManager crm = CleaningRequestManager.getCleaningRequestManager();
  private FlowerRequestManager frm = FlowerRequestManager.getFlowerRequestManager();
  private ComputerServiceRequestManager csrm =
      ComputerServiceRequestManager.getComputerServiceRequestManager();
  private SanitationRequestManager srm = SanitationRequestManager.getSanitationRequestManager();
  private GiftDeliveryRequestManager gdrm =
      GiftDeliveryRequestManager.getGiftDeliveryRequestManager();
  private MealRequestManager mealRequestManager = MealRequestManager.getMealRequestManager();
  private SecurityRequestManager securityRequestManager =
      SecurityRequestManager.getSecurityRequestManager();
  private LanguageRequestManager languageRequestManager =
      LanguageRequestManager.getLanguageRequestManager();

  private TreeSet<Integer> reqIDList = new TreeSet<>();

  private static RequestFactory requestFactory = new RequestFactory();

  public static RequestFactory getRequestFactory() {
    return requestFactory;
  }

  private RequestFactory() {}

  public void resetTreeSet() {
    this.reqIDList = new TreeSet<>();
  }

  // fields is every field except for request id and itemID

  public Set<Integer> getReqIDList() {
    return reqIDList;
  }

  /**
   * Submits a new request to the database
   *
   * @param requestType Type of request to add
   * @param fields Fields for the request in order (if adding a new request, leave out the request
   *     status and timestamps)
   * @param importingFromCSV True if this method is importing an already existing request (such as
   *     from a CSV), false otherwise
   * @return The added request
   * @throws Exception
   */
  public Request getRequest(
      RequestType requestType, ArrayList<String> fields, boolean importingFromCSV)
      throws Exception {
    int num;
    if (reqIDList.size() == 0) {
      num = 0;
    } else {
      num = reqIDList.last();
    }

    int counter = num + 1;
    Request r = null;
    switch (requestType) {
      case MedicalEquipmentRequest:
        if (importingFromCSV) {
          r = merm.addExistingRequest(fields);
        } else {
          r = merm.addNewRequest(counter, fields);
        }
        break;
      case LabServiceRequest:
        if (importingFromCSV) {
          r = lsrm.addExistingRequest(fields);
        } else {
          r = lsrm.addNewRequest(counter, fields);
        }
        break;
      case MedicineDelivery:
        if (importingFromCSV) {
          r = mrm.addExistingRequest(fields);
        } else {
          r = mrm.addNewRequest(counter, fields);
        }
        break;
      case CleaningRequest:
        if (importingFromCSV) {
          r = crm.addExistingRequest(fields);
        } else {
          r = crm.addNewRequest(counter, fields);
        }
        break;
      case FlowerRequest:
        if (importingFromCSV) {
          r = frm.addExistingRequest(fields);
        } else {
          r = frm.addNewRequest(counter, fields);
        }
        break;
      case ComputerServiceRequest:
        if (importingFromCSV) {
          r = csrm.addExistingRequest(fields);
        } else {
          r = csrm.addNewRequest(counter, fields);
        }
        break;
      case SanitationService:
        if (importingFromCSV) {
          r = srm.addExistingRequest(fields);
        } else {
          r = srm.addNewRequest(counter, fields);
        }
        break;
      case GiftDelivery:
        if (importingFromCSV) {
          r = gdrm.addExistingRequest(fields);
        } else {
          r = gdrm.addNewRequest(counter, fields);
        }
        break;
      case MealDelivery:
        if (importingFromCSV) {
          r = mealRequestManager.addExistingRequest(fields);
        } else {
          r = mealRequestManager.addNewRequest(counter, fields);
        }
        break;
      case SecurityService:
        if (importingFromCSV) {
          r = securityRequestManager.addExistingRequest(fields);
        } else {
          r = securityRequestManager.addNewRequest(counter, fields);
        }
        break;
      case LanguageRequest:
        if (importingFromCSV) {
          r = languageRequestManager.addExistingRequest(fields);
        } else {
          r = languageRequestManager.addNewRequest(counter, fields);
        }
        break;
    }
    return r;
  }
}
