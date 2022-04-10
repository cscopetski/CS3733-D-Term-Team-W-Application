package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ServiceRequestManager extends RequestManager {

    public abstract void start(Integer requestID)throws SQLException;
    public abstract void complete(Integer requestID)throws SQLException ;
    public abstract void cancel(Integer requestID)throws SQLException ;
    public abstract void reQueue(Integer requestID)throws SQLException ;
    public abstract Request getRequest(Integer ID) throws SQLException;

    public abstract ArrayList<Request> getAllRequests() throws SQLException;

    public abstract Request addRequest(Integer i, ArrayList<String> fields) throws SQLException;

}
