package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.CleaningRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.*;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.*;
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
  public CleaningRequest addNewRequest(Integer num, ArrayList<String> fields) throws Exception {
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
        // checkStart();
      }
    } else {
      cr = null;
    }

    return cr;
  }

  public CleaningRequest addExistingRequest(ArrayList<String> fields) throws Exception {
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
  public void start(Integer requestID) throws Exception {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr.getStatus() == RequestStatus.InQueue) {
      cr.setStatus(RequestStatus.InProgress);
      MedEquip item = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
      if (item.getType().equals(MedEquipType.Bed)) {
        cr.setNodeID("wSTOR001L1");
        MedEquipManager.getMedEquipManager().moveTo(item.getMedID(), "wSTOR001L1");
      } else if (item.getType().equals(MedEquipType.InfusionPump)) {
        cr.setNodeID("wSTOR0011");
        MedEquipManager.getMedEquipManager().moveTo(item.getMedID(), "wSTOR0011");
      }
      crd.changeCleaningRequest(cr);
    }
  }

  public void markComplete(String medID, String nodeID) throws Exception {
    CleaningRequest cr = crd.getCleaningRequest(medID, RequestStatus.InProgress);
    if (cr == null) {
      System.out.println("TRYING TO mark COMPLETE A NULL CLEANING REQUEST ID");
      return;
    }
    if (cr.getStatus() == RequestStatus.InProgress
        || cr.getStatus().equals(RequestStatus.InQueue)) {
      cr.setStatus(RequestStatus.Completed);
      cr.setNodeID(nodeID);
      crd.changeCleaningRequest(cr);
      MedEquip item = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
      MedEquipManager.getMedEquipManager().moveTo(item.getMedID(), nodeID);
      MedEquipManager.getMedEquipManager().markClean(item.getMedID(), nodeID);
      if (Automation.Automation.getAuto()) {
        MedEquipRequestManager.getMedEquipRequestManager().startNext(item.getType());
      }
    }
  }

  public void complete(String medID, String nodeID) throws Exception {
    CleaningRequest cr = crd.getCleaningRequest(medID, RequestStatus.InQueue);
    if (cr == null) {
      System.out.println("TRYING TO COMPLETE A NULL CLEANING REQUEST ID using med id");
      return;
    }
    if (cr.getStatus() == RequestStatus.InProgress) {
      cr.setStatus(RequestStatus.Completed);
      cr.setNodeID(nodeID);
      crd.changeCleaningRequest(cr);
      MedEquip item = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
      MedEquipManager.getMedEquipManager().moveTo(item.getMedID(), nodeID);
      MedEquipManager.getMedEquipManager().markClean(item.getMedID(), nodeID);
      if (Automation.Automation.getAuto()) {
        MedEquipRequestManager.getMedEquipRequestManager().startNext(item.getType());
      }
    }
  }

  public void complete(Integer requestID, String nodeID) throws Exception {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr == null) {
      System.out.println("TRYING TO COMPLETE A NULL CLEANING REQUEST ID using request id");
      return;
    }
    if (cr.getStatus() == RequestStatus.InProgress) {
      cr.setStatus(RequestStatus.Completed);
      cr.setNodeID(nodeID);
      crd.changeCleaningRequest(cr);
      MedEquip item = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
      MedEquipManager.getMedEquipManager().moveTo(item.getMedID(), nodeID);
      MedEquipManager.getMedEquipManager().markCleanThroughRequest(item.getMedID(), nodeID);
      if (Automation.Automation.getAuto()) {
        MedEquipRequestManager.getMedEquipRequestManager().startNext(item.getType());
      }
    }
  }

  public void cancel(Integer requestID) throws SQLException, StatusError {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr.getStatus() != RequestStatus.Completed) {
      cr.setStatus(RequestStatus.Cancelled);
      crd.changeCleaningRequest(cr);
    }
  }

  public void reQueue(Integer requestID) throws SQLException, StatusError {
    CleaningRequest cr = crd.getCleaningRequest(requestID);
    if (cr.getStatus() == RequestStatus.Cancelled) {
      cr.setStatus(RequestStatus.InQueue);
      crd.changeCleaningRequest(cr);
    }
  }




  public ArrayList<AlertInfoWrapper> checkForAlert() throws Exception {
    ArrayList<String> cleaningLocations = crd.getCleaningLocation();
    ArrayList<AlertInfoWrapper> listOfAlerts = new ArrayList<>();
    for (String location : cleaningLocations) {
      ArrayList<Integer> requests = crd.CleaningRequestAtLocation(location);
      ArrayList<Integer> ids = new ArrayList<>();
      ArrayList<Integer> ids2 = new ArrayList<>();
      ArrayList<CleaningRequest> cleaningRequests1 = new ArrayList<>();
      ArrayList<CleaningRequest> cleaningRequests2 = new ArrayList<>();
      if (requests.size() >= 6) {
        Integer counter = 0;
        Integer counter2 = 0;
        for (Integer c : requests) {
          CleaningRequest cr =
                  (CleaningRequest)
                          RequestFacade.getRequestFacade().findRequest(c, RequestType.CleaningRequest);
          MedEquip me = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
          if (me.getType().equals(MedEquipType.Bed)) {
            ids.add(c);
            cleaningRequests1.add(cr);
            counter++;
          } else if (me.getType().equals(MedEquipType.InfusionPump)) {
            ids2.add(c);
            cleaningRequests2.add(cr);
            counter2++;
          }
        }
        if (counter >= 6) {
          ArrayList<String> listOfEquipment = new ArrayList<>();
          for (CleaningRequest cleaningRequest : cleaningRequests1) {
            listOfEquipment.add(cleaningRequest.getItemID());
          }
          AlertInfoWrapper alertInfoWrapper1 = new AlertInfoWrapper(listOfEquipment, location, EquipAlertType.SixDirtyBeds);
          listOfAlerts.add(alertInfoWrapper1);
        }
        if (counter2 >= 10) {
          ArrayList<String> listOfEquipment2 = new ArrayList<>();
          for (CleaningRequest cleaningRequest : cleaningRequests2) {
            listOfEquipment2.add(cleaningRequest.getItemID());
          }
          AlertInfoWrapper alertInfoWrapper2 = new AlertInfoWrapper(listOfEquipment2, location, EquipAlertType.MoreTenDirtyInP);
          listOfAlerts.add(alertInfoWrapper2);
        }
      }
    }
    ArrayList<MedEquip> medEquipArrayList =
            MedEquipManager.getMedEquipManager()
                    .getAllMedEquip(MedEquipType.InfusionPump, MedEquipStatus.Clean);
    ArrayList<Location> locations = LocationManager.getLocationManager().getLocationClean();
    for (Location location : locations) {
      ArrayList<String> listOfEquipment3 = new ArrayList<>();
      Integer counter = 0;
      for (MedEquip med : medEquipArrayList) {
        if (med.getNodeID().equals(location.getNodeID())) {
          listOfEquipment3.add(med.getMedID());
          counter++;
        }
      }
      if (counter < 5) {
        AlertInfoWrapper alertInfoWrapper3 = new AlertInfoWrapper(listOfEquipment3, location.getNodeID(), EquipAlertType.FewerFiveInP);
        listOfAlerts.add(alertInfoWrapper3);
      }
    }
    return listOfAlerts;
  }


  public void checkStart() throws Exception {
    ArrayList<String> cleaningLocations = crd.getCleaningLocation();
    for (String location : cleaningLocations) {
      ArrayList<Integer> requests = crd.CleaningRequestAtLocation(location);
      ArrayList<Integer> ids = new ArrayList<>();
      ArrayList<Integer> ids2 = new ArrayList<>();
      if (requests.size() >= 6) {
        Integer counter = 0;
        Integer counter2 = 0;
        for (Integer c : requests) {
          CleaningRequest cr =
              (CleaningRequest)
                  RequestFacade.getRequestFacade().findRequest(c, RequestType.CleaningRequest);
          MedEquip me = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
          if (me.getType().equals(MedEquipType.Bed)) {
            ids.add(c);
            counter++;
          } else if (me.getType().equals(MedEquipType.InfusionPump)) {
            ids2.add(c);
            counter2++;
          }
        }
        if (counter >= 6) {
          for (Integer id : ids) {
            start(id);
          }
          throw new SixDirtyBeds();
        }
        if (counter2 >= 10) {
          for (Integer id : ids2) {
            start(id);
          }
          throw new TenDirtyInfusionPumps();
        }
      }
    }
    ArrayList<MedEquip> medEquipArrayList =
        MedEquipManager.getMedEquipManager()
            .getAllMedEquip(MedEquipType.InfusionPump, MedEquipStatus.Clean);
    ArrayList<Location> locations = LocationManager.getLocationManager().getLocationClean();
    for (Location location : locations) {
      Integer counter = 0;
      for (MedEquip med : medEquipArrayList) {
        if (med.getNodeID().equals(location.getNodeID())) {
          counter++;
        }
      }
      if (counter < 5) {
        throw new FewerThanFiveCleanINP();
      }
    }
  }

  public void changeRequest(Request request) throws SQLException {
    crd.changeCleaningRequest((CleaningRequest) request);
  }

  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    return crd.getEmployeeRequests(employeeID);
  }

  public void updateReqAtLocation(String nodeID) throws Exception {
    this.crd.updateCleaningRequestsAtLocation(nodeID);
  }

  public void updateReqWithEquipment(String nodeID) throws Exception {
    this.crd.updateCleaningRequestsWithEquipment(nodeID);
  }

  public void updateReqWithEmployee(Integer employeeID) throws Exception {
    this.crd.updateCleaningRequestsWithEmployee(employeeID);
  }

  public void exportReqCSV(String filename) {
    crd.exportCleaningReqCSV(filename);
  }
}
