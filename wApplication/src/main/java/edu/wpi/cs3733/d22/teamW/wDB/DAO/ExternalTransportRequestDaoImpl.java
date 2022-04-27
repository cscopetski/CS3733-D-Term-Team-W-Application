package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoTransport;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.ComputerServiceRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.ExternalTransportManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ComputerServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.ExternalTransportRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ExternalTransportRequestDaoImpl implements ExternalTransportRequestDao {

    private Statement statement;

    public ExternalTransportRequestDaoImpl(Statement statement) {
        this.statement = statement;
        dropTable();
    }

    public void dropTable() {
        try {
            statement.execute("DROP TABLE ExternalTransportRequests");
            System.out.println("Dropped External Transport Requests Table");
        } catch (SQLException e) {
            System.out.println("Failed to drop External Transport Requests Table");
        }
    }

    String CSVHeaderString =
            "ReqID,nodeID,patient,destinationLocation,departureDate,transportType,employeeID,isEmergency,reqStatus,createdTimestamp,updatedTimestamp";

    public void createTable() throws SQLException {
        try {

            statement.execute(
                    "CREATE TABLE ExternalTransportRequests(\n"
                            + "ReqID INT,"
                            + "nodeID varchar(25)," +
                            "patient varchar(50)," +
                            "destinationLocation varchar(50)," +
                            "departureDate DATE," +
                            "transportType varchar(25),"
                            + "employeeID INT,"
                            + "isEmergency INT,"
                            + "reqStatus INT,"
                            + "createdTimeStamp TIMESTAMP,"
                            + "updatedTimeStamp TIMESTAMP,"
                            + "constraint exTransportReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),\n"
                            + "constraint exTransportReq_Employee_FK foreign key (employeeID) references EMPLOYEES(employeeID),"
                            + "constraint exTransportReq_PK primary key (ReqID),\n"
                            + "constraint exTransportReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
                            + "constraint exTransportReqIsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
        } catch (SQLException e) {
            System.out.println("External Transport Requests Table failed to be created!");
            throw (e);
        }
    }


    @Override
    public void addExTransportRequest(ExternalTransportRequest mr) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO ExternalTransportRequests VALUES(%s)", mr.toValuesString()));
    }

    @Override
    public void changeExTransportRequest(ExternalTransportRequest mr) throws SQLException {

        statement.executeUpdate(
                String.format(
                        "UPDATE ExternalTransportRequests SET NODEID = '%s', EMPLOYEEID = %d, ISEMERGENCY = %d, REQSTATUS = %d, UPDATEDTIMESTAMP = '%s', patient = '%s', destinationLocation ='%s', departureDate ='%s', transportType ='%s' WHERE REQID = %d",
                        mr.getNodeID(),
                        mr.getEmployeeID(),
                        mr.getEmergency(),
                        mr.getStatus().getValue(),
                        new Timestamp(System.currentTimeMillis()),
                        mr.getPatientID(),
                        mr.getDestinationLocation(),
                        mr.getDepartureDate().toString(),
                        mr.getTransportType().getString(),
                        mr.getRequestID()));

    }

    @Override
    public void deleteExTransportRequest(Integer id) throws SQLException {
        RequestFactory.getRequestFactory().getReqIDList().remove(id);
        statement.executeUpdate(
                String.format("DELETE FROM ExternalTransportRequests WHERE REQID = %d", id));
    }

    @Override
    public Request getExTransportRequest(Integer id) throws SQLException {
        ExternalTransportRequest csr = null;
        try {
            ResultSet rs =
                    statement.executeQuery(
                            String.format("SELECT * FROM ExternalTransportRequests WHERE REQID = %d", id));
            while (rs.next()) {
                ArrayList<String> csrData = new ArrayList<String>();
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    csrData.add(rs.getString(i + 1));
                }
                try {
                    csr = new ExternalTransportRequest(csrData);
                } catch (StatusError | NoTransport e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return csr;
    }

    @Override
    public ArrayList<Request> getAllExTransportRequest() {
        ArrayList<Request> csrList = new ArrayList<Request>();
        try {
            ResultSet allComputerServiceRequests =
                    statement.executeQuery("SELECT * FROM ExternalTransportRequests");
            while (allComputerServiceRequests.next()) {
                ArrayList<String> csrData = new ArrayList<String>();
                for (int i = 0; i < allComputerServiceRequests.getMetaData().getColumnCount(); i++) {
                    csrData.add(allComputerServiceRequests.getString(i + 1));
                }
                csrList.add(new ExternalTransportRequest(csrData));
            }
        } catch (SQLException e) {
            System.out.println("Query from request transport request table failed.");
            e.printStackTrace();
        } catch (StatusError e) {
            System.out.println("Query from request transport request table failed.");
            e.printStackTrace();
        } catch (NoTransport noTransport) {
            noTransport.printStackTrace();
        }
        return csrList;
    }

    @Override
    public void exportExTransportReqCSV(String fileName) {
        File csvOutputFile = new File(fileName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            // print Table headers
            pw.print(CSVHeaderString);

            // print all locations
            for (Request m : getAllExTransportRequest()) {
                pw.println();
                pw.print(m.toCSVString());
            }

        } catch (FileNotFoundException e) {

            System.out.println(String.format("Error Exporting to File %s", fileName));
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
        ArrayList<Request> employeeRequestList = new ArrayList<>();
        try {
            ResultSet compRequests =
                    statement.executeQuery(
                            String.format(
                                    "SELECT * FROM ExternalTransportRequests WHERE EMPLOYEEID = %d", employeeID));
            while (compRequests.next()) {
                ArrayList<String> compReqData = new ArrayList<String>();

                for (int i = 0; i < compRequests.getMetaData().getColumnCount(); i++) {
                    compReqData.add(compRequests.getString(i + 1));
                }

                employeeRequestList.add(new ExternalTransportRequest(compReqData));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (StatusError | NoTransport e) {
            e.printStackTrace();
        }
        return employeeRequestList;

    }

    @Override
    public void updateExTransportReqAtLocation(String nodeID) throws Exception {
        ResultSet resultSet =
                statement.executeQuery(
                        String.format("SELECT ReqID FROM ExternalTransportRequests WHERE nodeID='%s'", nodeID));

        ArrayList<Integer> reqIDs = new ArrayList<>();
        while (resultSet.next()) {

            Integer reqID = resultSet.getInt("ReqID");
            reqIDs.add(reqID);
        }

        for (Integer reqID : reqIDs) {
            ExternalTransportManager.getRequestManager().cancel(reqID);
        }

        statement.executeUpdate(
                String.format(
                        "UPDATE ExternalTransportRequests SET NODEID='%s' WHERE NODEID='%s'",
                        LocationManager.getLocationManager().getNoneLocation(), nodeID));
    }

    @Override
    public void updateExTransportRequestsWithEmployee(Integer employeeID) throws Exception {
        ResultSet resultSet =
                statement.executeQuery(
                        String.format(
                                "SELECT ReqID FROM ExternalTransportRequests WHERE employeeID= %d", employeeID));

        ArrayList<Integer> reqIDs = new ArrayList<>();
        while (resultSet.next()) {

            Integer reqID = Integer.parseInt(resultSet.getString("ReqID"));
            reqIDs.add(reqID);
        }

        for (Integer reqID : reqIDs) {
            ExternalTransportManager.getRequestManager().cancel(reqID);
        }

        statement.executeUpdate(
                String.format(
                        "UPDATE ExternalTransportRequests SET employeeID=%d WHERE employeeID=%d",
                        EmployeeManager.getEmployeeManager().getDeletedEmployee(), employeeID));
    }
}
