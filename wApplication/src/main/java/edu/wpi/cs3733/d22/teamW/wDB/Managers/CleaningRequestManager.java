package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.CleaningRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.Automation;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CleaningRequestManager {
  private CleaningRequestDao crd;
  private static CleaningRequestManager cleaningRequestManager = new CleaningRequestManager();

  private CleaningRequestManager() {}

  public static CleaningRequestManager getCleaningRequestManager() {
    return cleaningRequestManager;
  }

  public void setCleaningRequestDao(CleaningRequestDao crd) {
    this.crd = crd;
  }

  public CleaningRequest getRequest(Integer reqID) throws StatusError {
    return crd.getCleaningRequest(reqID);
  }

  public ArrayList<Request> getAllRequests() {
    return crd.getAllCleaningRequests();
  }

  //  public CleaningRequest addRequest(ArrayList<String> fields) throws SQLException {
  //    CleaningRequest cr;
  //    fields.add(String.format("%d", RequestStatus.InQueue.getValue()));
  //    cr = new CleaningRequest(fields);
  //    if (RequestFactory.getRequestFactory().getReqIDList().add(cr.getRequestID())) {
  //      crd.addCleaningRequest(cr);
  //      checkStart();
  //    } else {
  //      cr = null;
  //    }
  //    return cr;
  //  }

  // TODO auto start all cleaning requests at that location when it is 6
  public CleaningRequest addNewRequest(Integer num, ArrayList<String> fields)
      throws SQLException, StatusError {
    CleaningRequest cr;
    fields.add(Integer.toString(RequestStatus.InQueue.getValue()));
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    cr = new CleaningRequest(num, fields);
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
    if (RequestFactory.getRequestFactory().getReqIDList().add(cr.getRequestID())) {
      crd.addCleaningRequest(cr);

      if (Automation.Automation.getAuto()) {
        checkStart();
      }
    } else {
      cr = null;
    }

    return cr;
  }

  public CleaningRequest addExistingRequest(ArrayList<String> fields)
      throws SQLException, StatusError {
    CleaningRequest cr = new CleaningRequest(fields);
    // TODO special exception
    if (RequestFactory.getRequestFactory().getReqIDList().add(cr.getRequestID())) {
      crd.addCleaningRequest(cr);

      if (Automation.Automation.getAuto()) {
        checkStart();
      }
    } else {
      cr = null;
    }

    return cr;
  }

  // TODO Ask Caleb how to get OR Bed PARK
  // What happens if the OR BED PARK is deleted this function would break
  public void start(Integer requestID) throws SQLException, StatusError {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr.getStatus() == RequestStatus.InQueue) {
      cr.setStatus(RequestStatus.InProgress);
      cr.setNodeID("wSTOR001L1");
      cr.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
      crd.changeCleaningRequest(cr);
      MedEquip item = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
      MedEquipManager.getMedEquipManager().moveTo(item.getMedID(), "wSTOR001L1");
    }
  }

  public void complete(Integer requestID, String nodeID) throws Exception {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr == null) {
      System.out.println("TRYING TO COMPLETE A NULL CLEANING REQUEST ID");
      return;
    }
    if (cr.getStatus() == RequestStatus.InProgress) {
      cr.setStatus(RequestStatus.Completed);
      cr.setNodeID(nodeID);
      cr.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
      crd.changeCleaningRequest(cr);
      MedEquip item = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
      MedEquipManager.getMedEquipManager().moveTo(item.getMedID(), nodeID);
      MedEquipManager.getMedEquipManager().markClean(item.getMedID(), nodeID);
      if (Automation.Automation.getAuto()) {
        MedEquipRequestManager.getMedEquipRequestManager().startNext(item.getType());
      }
    }
  }

  public void cancel(Integer requestID) throws SQLException, StatusError {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr.getStatus() != RequestStatus.Completed) {
      cr.setStatus(RequestStatus.Cancelled);
      cr.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
      crd.changeCleaningRequest(cr);
    }
  }

  public void reQueue(Integer requestID) throws SQLException, StatusError {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr.getStatus() == RequestStatus.Cancelled) {
      cr.setStatus(RequestStatus.InQueue);
      cr.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
      crd.changeCleaningRequest(cr);
    }
  }

  public void checkStart() throws SQLException, StatusError {
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
