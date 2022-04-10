package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.CleaningRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

import java.sql.SQLException;
import java.util.ArrayList;

public class CleaningRequestManager {
    Automation automation = Automation.getAutomation();
    private CleaningRequestDao crd;
    private static CleaningRequestManager cleaningRequestManager = new CleaningRequestManager();
    private Integer counter = 0;
    private CleaningRequestManager(){}

    public static CleaningRequestManager getCleaningRequestManager(){
        return cleaningRequestManager;
    }

    public void setCleaningRequestDao(CleaningRequestDao crd){
        this.crd = crd;
    }

    public CleaningRequest getRequest(Integer reqID){
        return crd.getCleaningRequest(reqID);
    }

    public ArrayList<CleaningRequest> getAllRequest(){
        return  crd.getAllCleaningRequests();
    }

    public CleaningRequest addRequest(Integer id, ArrayList<String> fields) throws SQLException {
        counter++;
        CleaningRequest cr;
        if(fields.size() == 1){
            fields.add(String.format("%d", RequestStatus.InQueue.getValue()));
        }
        cr = new CleaningRequest(id, fields);
        if(id>counter){
            counter = id;
        }
        crd.addCleaningRequest(cr);
        return cr;
    }

    public CleaningRequest addRequest(ArrayList<String> fields) throws SQLException {
        counter++;
        CleaningRequest cr;
        if(fields.size() == 2){
            fields.add(String.format("%d", RequestStatus.InQueue.getValue()));
        }
        cr = new CleaningRequest(fields);
        if(Integer.parseInt(fields.get(0))>counter){
            counter = Integer.parseInt(fields.get(0));
        }
        crd.addCleaningRequest(cr);
        return cr;
    }

    public CleaningRequest addRequest(String itemID) throws SQLException{
        counter++;
        CleaningRequest cr;
        cr = new CleaningRequest(counter, itemID, RequestStatus.InQueue);
        crd.addCleaningRequest(cr);
        return cr;
    }
//TODO Ask Caleb how to get OR Bed PARK
    //What happens if the OR BED PARK is deleted this function would break
    public void start(Integer requestID) throws SQLException {
        CleaningRequest cr = crd.getCleaningRequest(requestID);
        if(cr.getStatus() == RequestStatus.InQueue) {
            cr.setStatus(RequestStatus.InProgress);
            crd.changeCleaningRequest(requestID, cr.getItemID(), RequestStatus.InProgress);
            MedEquip item = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
            MedEquipManager.getMedEquipManager().moveTo(item.getMedID(), "wSTOR001L1");
        }
    }

    public void complete(Integer requestID, String nodeID) throws SQLException {
        CleaningRequest cr = crd.getCleaningRequest(requestID);
        if(cr.getStatus() == RequestStatus.InProgress) {
            cr.setStatus(RequestStatus.Completed);
            crd.changeCleaningRequest(requestID, cr.getItemID(), RequestStatus.Completed);
            MedEquip item = MedEquipManager.getMedEquipManager().getMedEquip(cr.getItemID());
            MedEquipManager.getMedEquipManager().moveTo(item.getMedID(), nodeID);
            if(automation.getAuto()) {
                MedEquipRequestManager.getMedEquipRequestManager().startNext(item.getType());
            }
        }
    }

    public void cancel(Integer requestID) throws SQLException {
        CleaningRequest cr = crd.getCleaningRequest(requestID);
        if(cr.getStatus() != RequestStatus.Completed) {
            cr.setStatus(RequestStatus.Cancelled);
            crd.changeCleaningRequest(requestID, cr.getItemID(), RequestStatus.Cancelled);
        }
    }

    public void reQueue(Integer requestID) throws SQLException {
        CleaningRequest cr = crd.getCleaningRequest(requestID);
        if(cr.getStatus() == RequestStatus.Cancelled) {
            cr.setStatus(RequestStatus.InQueue);
            crd.changeCleaningRequest(requestID, cr.getItemID(), RequestStatus.InQueue);
        }
    }

    public void exportCleaningRequestCSV(String filename){crd.exportCleaningReqCSV(filename);}

}
