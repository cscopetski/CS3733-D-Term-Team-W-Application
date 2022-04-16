package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.GiftDeliveryRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.GiftDeliveryRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class GiftDeliveryRequestManager implements RequestManager {

  private GiftDeliveryRequestDao giftDeliveryRequestDao;
  private static GiftDeliveryRequestManager giftDeliveryRequestManager =
      new GiftDeliveryRequestManager();

  private GiftDeliveryRequestManager() {}

  public static GiftDeliveryRequestManager getGiftDeliveryRequestManager() {
    return giftDeliveryRequestManager;
  }

  public void setGiftDeliveryRequestDao(GiftDeliveryRequestDao csrd) {
    this.giftDeliveryRequestDao = csrd;
  }

  public void changeReq(Request r) throws SQLException {
    this.giftDeliveryRequestDao.changeGiftDeliveryRequest((GiftDeliveryRequest) r);
  }

  @Override
  public void start(Integer requestID) throws Exception {
    GiftDeliveryRequest sr = giftDeliveryRequestDao.getGiftDeliveryRequest(requestID);
    if (sr.getStatus() == RequestStatus.InQueue) {
      sr.setStatus(RequestStatus.InProgress);
      giftDeliveryRequestDao.changeGiftDeliveryRequest(sr);
    }
  }

  @Override
  public void complete(Integer requestID) throws Exception {
    GiftDeliveryRequest csr = giftDeliveryRequestDao.getGiftDeliveryRequest(requestID);
    if (csr.getStatus() == RequestStatus.InProgress) {
      csr.setStatus(RequestStatus.Completed);
      giftDeliveryRequestDao.changeGiftDeliveryRequest(csr);
    }
  }

  @Override
  public void cancel(Integer requestID) throws Exception {
    GiftDeliveryRequest csr = giftDeliveryRequestDao.getGiftDeliveryRequest(requestID);
    if (csr.getStatus() != RequestStatus.Completed) {
      csr.setStatus(RequestStatus.Cancelled);
      giftDeliveryRequestDao.changeGiftDeliveryRequest(csr);
    }
  }

  @Override
  public void reQueue(Integer requestID) throws Exception {
    GiftDeliveryRequest csr = giftDeliveryRequestDao.getGiftDeliveryRequest(requestID);
    if (csr.getStatus() == RequestStatus.Cancelled) {
      csr.setStatus(RequestStatus.InQueue);
      giftDeliveryRequestDao.changeGiftDeliveryRequest(csr);
    }
  }

  @Override
  public Request getRequest(Integer ID) throws Exception {
    return this.giftDeliveryRequestDao.getGiftDeliveryRequest(ID);
  }

  @Override
  public ArrayList<Request> getAllRequests() throws Exception {
    return this.giftDeliveryRequestDao.getAllGiftDeliveryRequests();
  }

  @Override
  public Request addNewRequest(Integer i, ArrayList<String> fields) throws Exception {
    fields.add(0, Integer.toString(i));
    fields.add(Integer.toString(RequestStatus.InQueue.getValue()));
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    GiftDeliveryRequest csr = new GiftDeliveryRequest(fields);

    if (RequestFactory.getRequestFactory().getReqIDList().add(csr.getRequestID())) {
      this.giftDeliveryRequestDao.addGiftDeliveryRequest(csr);
    } else {
      csr = null;
    }
    return csr;
  }

  @Override
  public Request addExistingRequest(ArrayList<String> fields) throws Exception {
    GiftDeliveryRequest csr = new GiftDeliveryRequest(fields);

    if (RequestFactory.getRequestFactory().getReqIDList().add(csr.getRequestID())) {
      this.giftDeliveryRequestDao.addGiftDeliveryRequest(csr);
    } else {
      csr = null;
    }

    return csr;
  }

  @Override
  public void exportReqCSV(String filename) throws Exception {
    this.giftDeliveryRequestDao.exportGiftDeliveryReqCSV(filename);
  }

  @Override
  public void updateReqAtLocation(String nodeID) throws Exception {
    this.giftDeliveryRequestDao.updateGiftDeliveryRequestsAtLocation(nodeID);
  }

  @Override
  public void updateReqWithEmployee(Integer employeeID) throws Exception {
    this.giftDeliveryRequestDao.updateGiftDeliveryRequestsWithEmployee(employeeID);
  }
}
