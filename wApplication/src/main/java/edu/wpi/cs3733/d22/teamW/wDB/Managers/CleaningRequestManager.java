package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.CleaningRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CleaningRequestManager {
  Automation automation = Automation.getAutomation();
  private CleaningRequestDao crd;
  private static CleaningRequestManager cleaningRequestManager = new CleaningRequestManager();

  private CleaningRequestManager() {}

  public static CleaningRequestManager getCleaningRequestManager() {
    return cleaningRequestManager;
  }

  public void setCleaningRequestDao(CleaningRequestDao crd) {
    this.crd = crd;
  }

  public CleaningRequest getRequest(Integer reqID) {
    return crd.getCleaningRequest(reqID);
  }

  public ArrayList<Request> getAllRequests() {
    return crd.getAllCleaningRequests();
  }

  public CleaningRequest addRequest(ArrayList<String> fields) throws SQLException {
    CleaningRequest cr;
    fields.add(String.format("%d", RequestStatus.InQueue.getValue()));
    cr = new CleaningRequest(fields);
    if (RequestFactory.getRequestFactory().getReqIDList().add(cr.getRequestID())) {
      crd.addCleaningRequest(cr);
      checkStart();
    } else {
      cr = null;
    }
    return cr;
  }
  // TODO auto start all cleaning requests at that location when it is 6
  public CleaningRequest addRequest(Integer num, ArrayList<String> fields) throws SQLException {
    CleaningRequest mER;
    if (fields.size() == 5) {
      fields.add("0");
      fields.add(new Timestamp(System.currentTimeMillis()).toString());
      fields.add(new Timestamp(System.currentTimeMillis()).toString());
      mER = new CleaningRequest(num, fields);
    } else {
      mER = new CleaningRequest(fields);
    }
    /*
    // If the request does not have an item, aka has not been started
    if (mER.getItemID().equals("NONE") && mER.getStatusInt() == 0) {
      // System.out.println("CHECKING REQUEST " + mER.getRequestID());
      String itemID = checkStart(mER);
      if (itemID != null) {
        // System.out.println("STARTING REQUEST " + mER.getRequestID());
        mER.start(itemID);
      }
    }*/
    // TODO special exception
    if (RequestFactory.getRequestFactory().getReqIDList().add(mER.getRequestID())) {
      crd.addCleaningRequest(mER);

      if (automation.getAuto()) {
        checkStart();
      }
    } else {
      mER = null;
    }

    return mER;
  }
  // TODO Ask Caleb how to get OR Bed PARK
  // What happens if the OR BED PARK is deleted this function would break
  public void start(Integer requestID) throws SQLException {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr.getStatus() == RequestStatus.InQueue) {
      cr.setStatus(RequestStatus.InProgress);
      crd.changeCleaningRequest(
          requestID,
          cr.getItemID(),
          "wSTOR001L1",
          cr.getEmployeeID(),
          cr.getEmergency(),
          RequestStatus.InProgress);
      MedEquip item = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
      MedEquipManager.getMedEquipManager().moveTo(item.getMedID(), "wSTOR001L1");
    }
  }

  public void complete(Integer requestID, String nodeID) throws SQLException {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr.getStatus() == RequestStatus.InProgress) {
      cr.setStatus(RequestStatus.Completed);
      crd.changeCleaningRequest(
          requestID,
          cr.getItemID(),
          nodeID,
          cr.getEmployeeID(),
          cr.getEmergency(),
          RequestStatus.Completed);
      MedEquip item = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
      MedEquipManager.getMedEquipManager().moveTo(item.getMedID(), nodeID);
      MedEquipManager.getMedEquipManager().markClean(item.getMedID(), nodeID);
      if (automation.getAuto()) {
        MedEquipRequestManager.getMedEquipRequestManager().startNext(item.getType());
      }
    }
  }

  public void cancel(Integer requestID) throws SQLException {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr.getStatus() != RequestStatus.Completed) {
      cr.setStatus(RequestStatus.Cancelled);
      crd.changeCleaningRequest(
          requestID,
          cr.getItemID(),
          cr.getNodeID(),
          cr.getEmployeeID(),
          cr.getEmergency(),
          RequestStatus.Cancelled);
    }
  }

  public void reQueue(Integer requestID) throws SQLException {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr.getStatus() == RequestStatus.Cancelled) {
      cr.setStatus(RequestStatus.InQueue);
      crd.changeCleaningRequest(
          requestID,
          cr.getItemID(),
          cr.getNodeID(),
          cr.getEmployeeID(),
          cr.getEmergency(),
          RequestStatus.InQueue);
    }
  }

  public void checkStart() throws SQLException {
    ArrayList<String> cleaningLocations = crd.getCleaningLocation();
    for (String location : cleaningLocations) {
      System.out.println(location);
      ArrayList<Integer> requests = crd.CleaningRequestAtLocation(location);
      if (requests.size() >= 6) {
        for (Integer c : requests) {
          System.out.println(c);
          start(c);
        }
      }
    }
  }

  public void exportReqCSV(String filename) {
    crd.exportCleaningReqCSV(filename);
  }
}
