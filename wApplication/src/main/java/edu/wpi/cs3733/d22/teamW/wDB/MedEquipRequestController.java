package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipRequestController implements RequestController {

  private MedEquipRequestDaoImpl merdi;
  private MedEquipDaoImpl medi;

  public MedEquipRequestController(MedEquipRequestDaoImpl merdi, MedEquipDaoImpl medi) {
    this.merdi = merdi;
    this.medi = medi;
  }

  @Override
  public String checkStart(Request request) throws SQLException {
    MedEquipRequest mER = (MedEquipRequest) request;
    return medi.checkTypeAvailable(mER.getItemType());
  }

  // TODO eventually make it set to dirty, for now is just a workaround
  public void completeRequest(Request r) throws SQLException {
    MedEquipRequest request = (MedEquipRequest) r;
    request.complete();
    merdi.changeMedEquipRequest(request);
    medi.changeMedEquip(request.getItemID(), request.getItemType(), request.getNodeID(), 0);
    checkNext(request.getItemID());
  }

  @Override
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

  @Override
  // Get the next request and return it
  public Request getNext(String itemID) {
    String type = itemID.substring(0, 3).toUpperCase();
    ArrayList<MedEquipRequest> list = merdi.getAllMedEquipRequests();
    MedEquipRequest nextRequest;
    for (MedEquipRequest mer : list) {
      if (mer.getEmergency() == 1) {
        if (mer.getItemType().equals(type) && mer.getStatus() == 0) {
          nextRequest = mer;
          return nextRequest;
        }
      }
    }
    for (MedEquipRequest mer : list) {
      if (mer.getItemType().equals(type) && mer.getStatus() == 0) {
        nextRequest = mer;
        return nextRequest;
      }
    }
    return null;
  }

  @Override
  public Request getRequest(Integer reqID) {
    ArrayList<MedEquipRequest> list = merdi.getAllMedEquipRequests();
    for (MedEquipRequest m : list) {
      if (m.getRequestID() == reqID) {
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
    if (mER.getItemID().equals("NONE") && mER.getStatus() == 0) {
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
}
