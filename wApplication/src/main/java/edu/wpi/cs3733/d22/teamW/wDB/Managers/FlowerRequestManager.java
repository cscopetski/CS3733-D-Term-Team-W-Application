package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.FlowerRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.*;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.FlowerRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class FlowerRequestManager implements RequestManager {

  private static FlowerRequestManager frm = new FlowerRequestManager();
  private FlowerRequestDao frd;

  private FlowerRequestManager() {}

  public static FlowerRequestManager getFlowerRequestManager() {
    return frm;
  }

  public void setFlowerRequestDao(FlowerRequestDao frd) {
    this.frd = frd;
  }

  @Override
  public Request addNewRequest(Integer num, ArrayList<String> fields)
      throws SQLException, StatusError, NoFlower {
    FlowerRequest fr;
    fields.add(Integer.toString(RequestStatus.InQueue.getValue()));
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    fr = new FlowerRequest(num, fields);
    // TODO Special Exception
    if (RequestFactory.getRequestFactory().getReqIDList().add(fr.getRequestID())) {
      frd.addFlowerRequest(fr);
    } else {
      fr = null;
    }
    return fr;
  }

  @Override
  public Request addExistingRequest(ArrayList<String> fields) throws Exception {
    FlowerRequest fr;
    fr = new FlowerRequest(fields);
    if (RequestFactory.getRequestFactory().getReqIDList().add(fr.getRequestID())) {
      frd.addFlowerRequest(fr);
    } else {
      fr = null;
    }
    return fr;
  }

  public void start(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    FlowerRequest request =
        (FlowerRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.FlowerRequest);
    request.setStatus(RequestStatus.InProgress);
    frd.changeFlowerRequest(request);
  }

  public void complete(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    FlowerRequest request =
        (FlowerRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.FlowerRequest);
    request.setStatus(RequestStatus.Completed);
    frd.changeFlowerRequest(request);
  }

  public void cancel(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    FlowerRequest request =
        (FlowerRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.FlowerRequest);
    request.setStatus(RequestStatus.Cancelled);
    frd.changeFlowerRequest(request);
  }

  public void reQueue(Integer requestID) throws SQLException, StatusError, NonExistingMedEquip {
    FlowerRequest request =
        (FlowerRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.FlowerRequest);
    request.setStatus(RequestStatus.InQueue);
    frd.changeFlowerRequest(request);
  }

  public void delete(Integer requestID) throws SQLException {
    frd.deleteFlowerRequest(requestID);
  }

  // TODO should or should not have

  public void changeMedRequest(FlowerRequest flowerRequest) throws SQLException {
    frd.changeFlowerRequest(flowerRequest);
  }

  @Override
  public Request getRequest(Integer ID) throws SQLException {
    return frd.getFlowerRequest(ID);
  }

  @Override
  public ArrayList<Request> getAllRequests() throws SQLException {
    return frd.getFlowerRequests();
  }

  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    return frd.getEmployeeRequests(employeeID);
  }

  public void exportReqCSV(String filename) {
    frd.exportFlowerReqCSV(filename);
  }
}
