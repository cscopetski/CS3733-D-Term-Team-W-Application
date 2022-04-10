package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MedEquipRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquipRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipRequestManager implements RequestManager {

  private MedEquipRequestDao merdi;
  private MedEquipManager medi = MedEquipManager.getMedEquipManager();

  private static MedEquipRequestManager medEquipRequestManager = new MedEquipRequestManager();

  public static MedEquipRequestManager getMedEquipRequestManager() {
    return medEquipRequestManager;
  }

  private MedEquipRequestManager() {}

  public void setMedEquipRequestDao(MedEquipRequestDao merdi) {
    this.merdi = merdi;
  }

  public String checkStart(Request request) throws SQLException {
    MedEquipRequest mER = (MedEquipRequest) request;
    String mERtype = mER.getItemType();
    ArrayList<MedEquip> medEquipList = medi.getAllMedEquip();
    for (MedEquip m : medEquipList) {
      if (m.getType().equals(mERtype) && (m.getStatus() == 0)) {
        medi.markInUse(m);
        return m.getMedID();
      }
    }
    return (String) null;
  }

  public void cancelRequest(Request r) throws SQLException {
    MedEquipRequest request = (MedEquipRequest) r;
    if (request.getStatusInt() == 0) {
      request.setStatus(RequestStatus.Cancelled.getValue());
      merdi.changeMedEquipRequest(request);
    } else if (request.getStatusInt() == 1) {
      request.setStatus(RequestStatus.Cancelled.getValue());
      merdi.changeMedEquipRequest(request);

      medi.markClean(request.getItemID(), request.getItemType(), request.getNodeID());
      checkNext(request.getItemID());
    }
  }

  // TODO eventually make it set to dirty, for now is just a workaround
  public void completeRequest(Request r) throws SQLException {
    MedEquipRequest request = (MedEquipRequest) r;
    request.setStatus(RequestStatus.Completed.getValue());
    merdi.changeMedEquipRequest(request);
    medi.markClean(request.getItemID(), request.getItemType(), request.getNodeID());
    checkNext(request.getItemID());
  }

  // Should take in itemID to give to next request that needs item of that type (if there is one)
  public void checkNext(String itemID) throws SQLException {
    MedEquipRequest nextReq = (MedEquipRequest) getNext(itemID);
    if (nextReq != null) {
      nextReq.setItemID(itemID);
      checkStart(nextReq);
      nextReq.start(itemID);
      merdi.changeMedEquipRequest(nextReq);
    }
  }

  // TODO might wanna rework to just use sql
  // Get the next request and return it
  public Request getNext(String itemID) throws SQLException {

    String type = itemID.substring(0, 3).toUpperCase();
    ArrayList<Request> list = merdi.getAllMedEquipRequests();
    Request nextRequest;
    for (Request mer : list) {
      if (mer.getEmergency() == 1) {
        if (((MedEquipRequest) mer).getItemType().equals(type) && mer.getStatusInt() == 0) {
          nextRequest = mer;
          return nextRequest;
        }
      }
    }
    for (Request mer : list) {
      if (((MedEquipRequest) mer).getItemType().equals(type) && mer.getStatusInt() == 0) {
        nextRequest = mer;
        return nextRequest;
      }
    }
    return null;
  }

  // TODO might wanna rework to just use sql
  @Override
  public Request getRequest(Integer reqID) throws SQLException {
    ArrayList<Request> list = merdi.getAllMedEquipRequests();
    for (Request m : list) {
      if (m.getRequestID().equals(reqID)) {
        return m;
      }
    }
    return null;
  }

  @Override
  public Request addRequest(Integer num, ArrayList<String> fields) throws SQLException {
    MedEquipRequest mER;
    // Set status to in queue if it is not already included (from CSVs)
    if (fields.size() == 4) {
      fields.add("0");
      mER = new MedEquipRequest(num, fields);
    } else {
      mER = new MedEquipRequest(fields);
    }

    // If the request does not have an item, aka has not been started
    if (mER.getItemID().equals("NONE") && mER.getStatusInt() == 0) {
      // System.out.println("CHECKING REQUEST " + mER.getRequestID());
      String itemID = checkStart(mER);
      if (itemID != null) {
        // System.out.println("STARTING REQUEST " + mER.getRequestID());
        mER.start(itemID);
      }
    }
    merdi.addMedEquipRequest(mER);
    return mER;
  }

  public ArrayList<Request> getAllRequests() throws SQLException {
    return this.merdi.getAllMedEquipRequests();
  }

  public void exportMedEquipRequestCSV(String filename) {
    merdi.exportMedReqCSV(filename);
  }
}
