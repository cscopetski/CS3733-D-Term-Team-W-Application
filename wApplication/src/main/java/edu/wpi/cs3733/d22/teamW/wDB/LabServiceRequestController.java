package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class LabServiceRequestController implements RequestController {

  private LabServiceRequestDaoImpl lsrdi;

  public LabServiceRequestController(LabServiceRequestDaoImpl lsrdi) {
    this.lsrdi = lsrdi;
  }

  @Override
  public Request getRequest(Integer reqID) {
    ArrayList<LabServiceRequest> list = lsrdi.getAllLabServiceRequests();

    for (LabServiceRequest l : list) {
      if (l.getRequestID() == reqID) {
        return l;
      }
    }
    return null;
  }

  @Override
  public Request addRequest(Integer num, ArrayList<String> fields) throws SQLException {
    LabServiceRequest lSR;
    // Set status to in queue if it is not already included (from CSVs)
    if (fields.size() == 4) {
      fields.add("0");
      lSR = new LabServiceRequest(num, fields);
    } else {
      lSR = new LabServiceRequest(fields);
    }

    lsrdi.addLabServiceRequest(lSR);
    return lSR;
  }

  public ArrayList<LabServiceRequest> getAllLabServiceRequests() {
    return this.lsrdi.getAllLabServiceRequests();
  }

  public void exportLabServiceRequestCSV(String filename) {
    lsrdi.exportLabServiceReqCSV(filename);
  }
}
