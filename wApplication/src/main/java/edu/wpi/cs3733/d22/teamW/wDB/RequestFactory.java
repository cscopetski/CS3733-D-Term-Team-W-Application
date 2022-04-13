package edu.wpi.cs3733.d22.teamW.wDB;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.CleaningRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LabServiceRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;
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

  public Request getRequest(RequestType requestType, ArrayList<String> fields) throws SQLException {
    int num;
    if (reqIDList.size() == 0) {
      num = 0;
    } else {
      num = reqIDList.last();
    }

    int counter = num + 1;
    if (requestType.equals(RequestType.MedicalEquipmentRequest)) {
      Request mER = merm.addRequest(counter, fields);
      System.out.println(mER.toValuesString());
      return mER;
    } else if (requestType.equals(RequestType.LabServiceRequest)) {
      Request lSR = lsrm.addRequest(counter, fields);
      System.out.println(lSR.toValuesString());
      return lSR;
    } else if (requestType.equals(RequestType.MedicineDelivery)) {
      Request mDR = mrm.addRequest(counter, fields);
      System.out.println(mDR.toValuesString());
      return mDR;
    } else if (requestType.equals(RequestType.CleaningRequest)) {
      Request cr = crm.addRequest(counter, fields);
      System.out.println(cr.toValuesString());
      return cr;
    } else {
      return null;
    }
  }
}
