package edu.wpi.cs3733.d22.teamW.wDB;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.CleaningRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LabServiceRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
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

  public RequestFacade getRequestFacade() {
    return this.requestFacade;
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
}
