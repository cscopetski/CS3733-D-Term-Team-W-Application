package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@Getter
public class CleaningRequest implements Entity{
    Integer requestID;
    String itemID;
    RequestStatus status;

    public CleaningRequest(Integer requestID , String itemID){
        this.requestID = requestID;
        this.itemID = itemID;
        this.status = RequestStatus.InQueue;
    }

    public CleaningRequest(Integer requestID, ArrayList<String> fields){
        if(fields.size() ==1){
            fields.add("0");
        }
        this.requestID = requestID;
        this.itemID = fields.get(0);
        this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(1)));
    }

    public CleaningRequest(ArrayList<String> fields){
        if(fields.size() ==2){
            fields.add("0");
        }
        this.requestID = Integer.parseInt(fields.get(0));
        this.itemID = fields.get(1);
        this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(2)));
    }

    public CleaningRequest(Integer counter, String itemID, RequestStatus status) {
        this.requestID = requestID;
        this.itemID = itemID;
        this.status = status;
    }


    @Override
    public String toCSVString() {
        return String.format("%d,%s,%d", requestID, itemID, status.getValue());
    }

    @Override
    public String toValuesString() {
        return String.format(
                "%d, '%s', %d",
                requestID, itemID, status.getValue());
    }
}