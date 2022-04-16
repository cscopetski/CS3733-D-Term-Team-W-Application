package edu.wpi.cs3733.d22.teamW.wDB;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.CleaningRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LabServiceRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedRequestManager;
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
    if (requestType.equals(RequestType.MedicalEquipmentRequest)) {
      Request mER;
      if (importingFromCSV) {
        mER = merm.addExistingRequest(fields);
      } else {
        mER = merm.addNewRequest(counter, fields);
      }
      return mER;
    } else if (requestType.equals(RequestType.LabServiceRequest)) {
      Request lSR;
      if (importingFromCSV) {
        lSR = lsrm.addExistingRequest(fields);
      } else {
        lSR = lsrm.addNewRequest(counter, fields);
      }
      return lSR;
    } else if (requestType.equals(RequestType.MedicineDelivery)) {
      Request mDR;
      if (importingFromCSV) {
        mDR = mrm.addExistingRequest(fields);
      } else {
        mDR = mrm.addNewRequest(counter, fields);
      }
      return mDR;
    } else if (requestType.equals(RequestType.CleaningRequest)) {
      Request cr;
      if (importingFromCSV) {
        cr = crm.addExistingRequest(fields);
      } else {
        cr = crm.addNewRequest(counter, fields);
      }
      return cr;
    } else {
      return null;
    }
  }
}
