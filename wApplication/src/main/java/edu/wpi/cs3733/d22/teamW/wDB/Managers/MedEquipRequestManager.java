package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MedEquipRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Automation;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquipRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipRequestManager implements RequestManager {

    Automation automation = Automation.getAutomation();
    private MedEquipRequestDao merd;
    private MedEquipManager mem = MedEquipManager.getMedEquipManager();

    private static MedEquipRequestManager medEquipRequestManager = new MedEquipRequestManager();

    public static MedEquipRequestManager getMedEquipRequestManager() {
        return medEquipRequestManager;
    }

    private MedEquipRequestManager() {
    }

    public void setMedEquipRequestDao(MedEquipRequestDao merdi) {
        this.merd = merdi;
    }


    public void startNext(String itemType) throws SQLException {
        MedEquipRequest mer = getNext(itemType);
        if (mer != null) {
            start(mer.getRequestID());
        }
    }

    public MedEquipRequest getNext(String itemType) throws SQLException {
        ArrayList<MedEquipRequest> requests = merd.getTypeMedEquipRequests(itemType);
        for (MedEquipRequest mer : requests) {
            if (mer.getItemType().equals(itemType) && mer.getEmergency() == 1) {
                return mer;
            }
        }
        for (MedEquipRequest mer : requests) {
            if (mer.getItemType().equals(itemType)) {
                return mer;
            }
        }
        return null;
    }

    public void start(Integer requestID) throws SQLException {
        MedEquipRequest request = (MedEquipRequest) RequestFactory.getRequestFactory().findRequest(requestID);
        //Can only start requests that are in queue
        if (request.getStatus().equals(RequestStatus.InQueue)) {
            //Can only start if a medEquip of that type is available
            MedEquip medEquip = MedEquipManager.getMedEquipManager().getNextFree(request.getItemType());
            if (medEquip != null) {
                //If available, we mark it in use and set the request to in progress
                MedEquipManager.getMedEquipManager().markInUse(medEquip);
                request.setStatus(RequestStatus.InProgress);
                merd.changeMedEquipRequest(
                        request.getRequestID(),
                        request.getItemType(),
                        medEquip.getMedID(),
                        request.getNodeID(),
                        request.getEmployeeName(),
                        request.getEmergency(),
                        request.getStatus());
            }
        }
    }

    public void complete(Integer requestID) throws SQLException {
        MedEquipRequest request = (MedEquipRequest) RequestFactory.getRequestFactory().findRequest(requestID);
        //Can only complete requests that are started
        if (request.getStatus().equals(RequestStatus.InProgress)) {
            MedEquipManager.getMedEquipManager().moveTo(request.getItemID(), request.getNodeID());
            request.setStatus(RequestStatus.Completed);
            merd.changeMedEquipRequest(
                    request.getRequestID(),
                    request.getItemType(),
                    request.getItemID(),
                    request.getNodeID(),
                    request.getEmployeeName(),
                    request.getEmergency(),
                    request.getStatus());
        }
    }

    public void cancel(Integer requestID) throws SQLException {
        MedEquipRequest request = (MedEquipRequest) RequestFactory.getRequestFactory().findRequest(requestID);
        //Cannot cancel requests that are completed bc it makes no sense
        if (!request.getStatus().equals(RequestStatus.Completed)) {
            if (request.getStatus() == RequestStatus.InProgress) {
                MedEquipManager.getMedEquipManager().getMedEquip(request.getItemID()).setStatus(MedEquipStatus.Clean);
                startNext(request.getItemType());
            }
            request.setStatus(RequestStatus.Cancelled);
            merd.changeMedEquipRequest(
                    request.getRequestID(),
                    request.getItemType(),
                    request.getItemID(),
                    request.getNodeID(),
                    request.getEmployeeName(),
                    request.getEmergency(),
                    request.getStatus());
        }
    }

    public void reQueue(Integer requestID) throws SQLException {
        MedEquipRequest request = (MedEquipRequest) RequestFactory.getRequestFactory().findRequest(requestID);
        //Only requeue cancelled requests
        if (!request.getStatus().equals(RequestStatus.Cancelled)) {
            request.setStatus(RequestStatus.InQueue);
            merd.changeMedEquipRequest(
                    request.getRequestID(),
                    request.getItemType(),
                    request.getItemID(),
                    request.getNodeID(),
                    request.getEmployeeName(),
                    request.getEmergency(),
                    request.getStatus());
            if(automation.getAuto()) {
                startNext(request.getItemType());
            }
        }
    }


/*  public String checkStart(Request request) throws SQLException {
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
      request.setStatus(RequestStatus.Cancelled);
      merdi.changeMedEquipRequest(request);
    } else if (request.getStatusInt() == 1) {
      request.setStatus(RequestStatus.Cancelled);
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
  }*/

    // TODO might wanna rework to just use sql
    @Override
    public Request getRequest(Integer reqID) throws SQLException {
        return merd.getRequest(reqID);
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
        merd.addMedEquipRequest(mER);
        if(automation.getAuto()) {
            startNext(mER.getItemType());
        }
        return mER;
    }

    public ArrayList<Request> getAllRequests() throws SQLException {
        return this.merd.getAllMedEquipRequests();
    }

    public void exportMedEquipRequestCSV(String filename) {
        merd.exportMedReqCSV(filename);
    }
}