package edu.wpi.cs3733.d22.teamW.wDB;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.LabServiceRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestFactory {

  // must be a singleton so that the counter does not get messed up

  // check DB for existing requests when are using external DB and not embedded one
  private ArrayList<Request> requests = new ArrayList<>();
  private MedEquipRequestManager merc = MedEquipRequestManager.getMedEquipRequestManager();
  private LabServiceRequestManager lsrc = LabServiceRequestManager.getLabServiceRequestManager();

  private static RequestFactory requestFactory = new RequestFactory();

  public static RequestFactory getRequestFactory() {

    return requestFactory;
  }

  public void resetRequestFactory() {
    this.requests = new ArrayList<>();
    this.requestFactory = null;
  }

  private RequestFactory() {}

  // fields is every field except for request id and itemID

  public Request getRequest(RequestType requestType, ArrayList<String> fields) throws SQLException {
    int counter = requests.size() + 1;
    if (requestType.equals(RequestType.MedicalEquipmentRequest)) {
      Request mER = merc.addRequest(counter, fields);
      requests.add(mER);
      return mER;
    } else if (requestType.equals(RequestType.LabServiceRequest)) {
      Request lSR = lsrc.addRequest(counter, fields);
      requests.add(lSR);
      return lSR;
    } else {
      return null;
    }
  }

  public Request findRequest(Integer requestID) {
    return requests.get(requestID - 1);
  }

  public ArrayList<Request> getAllRequests() {
    return this.requests;
  }
}
