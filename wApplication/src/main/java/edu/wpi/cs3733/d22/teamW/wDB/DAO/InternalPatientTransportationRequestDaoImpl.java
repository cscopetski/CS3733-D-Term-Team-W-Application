package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.ComputerServiceRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.InternalPatientTransportationRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ComputerServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.InternalPatientTransportationRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.module.ResolutionException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class InternalPatientTransportationRequestDaoImpl implements InternalPatientTransportationRequestDao{
    Statement statement;

    public InternalPatientTransportationRequestDaoImpl(Statement statement){
        this.statement = statement;
        dropTable();
    }

    public void dropTable(){
        try{
            statement.execute("DROP TABLE INTERNALPATIENTTRANSPORTATIONREQUESTS");
            System.out.println("Dropped Internal Patient Transportation Requests Table");
        } catch (SQLException e) {
            System.out.println("Dailed to drop Internal Patient Transportation Request Table");
        }
    }

    String CSVHeaderString = "ReqID,patient,nodeID,nodeIDTo,employeeID,emergency,status,createdTimestamp,updatedTimestamp";

    public void createTable() throws SQLException{
        try {
            statement.execute("CREATE TABLE INTERNALPATIENTTRANSPORTATIONREQUESTS(\n"
                    + "ReqID INT,"
                    + "patient varchar(25),"
                    + "nodeID varchar(25),"
                    + "nodeIDTo varchar(25),"
                    + "employeeID INT,"
                    + "isEmergency INT,"
                    + "reqStatus INT,"
                    + "createdTimeStamp TIMESTAMP,"
                    + "updatedTimeStamp TIMESTAMP,"
                    + "constraint IPTReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),\n"
                    + "constraint IPTReq_Employee_FK foreign key (employeeID) references EMPLOYEES(employeeID),"
                    + "constraint IPTReq_PK primary key (ReqID),\n"
                    + "constraint IPTReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
                    + "constraint IPTReq_IsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
        }catch (SQLException e){
            System.out.println("Internal Patient Transportation Request failed to be created!");
            throw (e);
        }
    }

    @Override
    public void addIPTRequest(InternalPatientTransportationRequest iptr) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO INTERNALPATIENTTRANSPORTATIONREQUESTS VALUES(%s)", iptr.toValuesString())
        );
    }

    @Override
    public void changeIPTRequest(InternalPatientTransportationRequest iptr) throws SQLException {
        statement.executeUpdate(
                String.format("UPDATE INTERNALPATIENTTRANSPORTATIONREQUESTS SET PATIENT = '%s', NODEID = 's', NODEIDTO = 's', EMPLOYEEID = %d, ISEMERGENCY = %d, REQSTATUS = %d, UPDATEDTIMESTAMP = '%s' WHERE REQID = %d",
                        iptr.getPatient(),
                        iptr.getNodeID(),
                        iptr.getNodeIDTo(),
                        iptr.getEmployeeID(),
                        iptr.getEmergency(),
                        iptr.getStatus().getValue(),
                        new Timestamp(System.currentTimeMillis()),
                        iptr.getRequestID()
                        )
        );
    }

    @Override
    public void deleteIPTRequest(Integer requestID) throws SQLException {
        RequestFactory.getRequestFactory().getReqIDList().remove(requestID);
        statement.executeUpdate(String.format("DELETE FROM INTERNALPATIENTTRANSPORTATIONREQUESTS WHERE REQID = %d", requestID));
    }

    @Override
    public void exportIPTRequestCSV(String filename) {
        File csvOutputFile = new File(filename);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)){
            pw.print(CSVHeaderString);
            for (Request m : getAllIPTRequest()){
                pw.println();
                pw.print(m.toCSVString());
            }
        } catch (FileNotFoundException e) {
            System.out.println(String.format("Error Exporting to File %s", filename));
            e.printStackTrace();
        }
    }

    @Override
    public InternalPatientTransportationRequest getIPTRequest(Integer requestID) throws StatusError {
        InternalPatientTransportationRequest iptr = null;
        try {
            ResultSet rs = statement.executeQuery(String.format("SELECT * FROM INTERNALPATIENTTRANSPORTATIONREQUESTS WHERE REQID = %d", requestID));
            while(rs.next()){
                ArrayList<String> iptrData = new ArrayList<>();
                for(int i = 0; i < rs.getMetaData().getColumnCount(); i++){
                   iptrData.add(rs.getString(i+1));
                }
                iptr = new InternalPatientTransportationRequest(iptrData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return iptr;
    }

    @Override
    public ArrayList<Request> getAllIPTRequest() {
        ArrayList<Request> iptrList = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM INTERNALPATIENTTRANSPORTATIONREQUESTS");
            while(rs.next()){
                ArrayList<String> iptrData = new ArrayList<>();
                for(int i = 0; i < rs.getMetaData().getColumnCount(); i++){
                    iptrData.add(rs.getString(i+1));
                }
                iptrList.add(new InternalPatientTransportationRequest(iptrData));
            }
        } catch (SQLException | StatusError e) {
            System.out.println("Query from Internal Patient Transportation Request Table failed");
            e.printStackTrace();
        }

        return iptrList;
    }

    @Override
    public void updateIPTRequestAtLocation(String nodeID) throws Exception {
        ResultSet resultSet =
                statement.executeQuery(
                        String.format("SELECT ReqID FROM INTERNALPATIENTTRANSPORTATIONREQUESTS WHERE nodeID='%s'", nodeID));

        ArrayList<Integer> reqIDs = new ArrayList<>();
        while (resultSet.next()) {

            Integer reqID = resultSet.getInt("ReqID");
            reqIDs.add(reqID);
        }

        for (Integer reqID : reqIDs) {
            InternalPatientTransportationRequestManager.getInternalPatientTransportationRequestManager().cancel(reqID);
        }

        statement.executeUpdate(
                String.format(
                        "UPDATE INTERNALPATIENTTRANSPORTATIONREQUESTS SET NODEID='%s' WHERE NODEID='%s'",
                        LocationManager.getLocationManager().getNoneLocation(), nodeID));
    }

    @Override
    public void updateIPTRequestWithEmployee(Integer employeeID) throws Exception {
        ResultSet resultSet =
                statement.executeQuery(
                        String.format(
                                "SELECT ReqID FROM INTERNALPATIENTTRANSPORTATIONREQUESTS WHERE employeeID= %d", employeeID));

        ArrayList<Integer> reqIDs = new ArrayList<>();
        while (resultSet.next()) {

            Integer reqID = Integer.parseInt(resultSet.getString("ReqID"));
            reqIDs.add(reqID);
        }

        for (Integer reqID : reqIDs) {
            InternalPatientTransportationRequestManager.getInternalPatientTransportationRequestManager().cancel(reqID);
        }

        statement.executeUpdate(
                String.format(
                        "UPDATE INTERNALPATIENTTRANSPORTATIONREQUESTS SET employeeID=%d WHERE employeeID=%d",
                        EmployeeManager.getEmployeeManager().getDeletedEmployee(), employeeID));
    }

    @Override
    public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
        ArrayList<Request> employeeRequestList = new ArrayList<>();
        try {
            ResultSet iptrRequests =
                    statement.executeQuery(
                            String.format(
                                    "SELECT * FROM INTERNALPATIENTTRANSPORTATIONREQUESTS WHERE EMPLOYEEID = %d", employeeID));
            while (iptrRequests.next()) {
                ArrayList<String> compReqData = new ArrayList<String>();

                for (int i = 0; i < iptrRequests.getMetaData().getColumnCount(); i++) {
                    compReqData.add(iptrRequests.getString(i + 1));
                }

                employeeRequestList.add(new InternalPatientTransportationRequest(compReqData));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (StatusError e) {
            e.printStackTrace();
        }
        return employeeRequestList;
    }
}
