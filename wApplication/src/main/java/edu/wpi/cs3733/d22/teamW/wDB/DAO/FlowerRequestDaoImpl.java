package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoFlower;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.FlowerRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class FlowerRequestDaoImpl implements FlowerRequestDao {

    Statement statement;

    FlowerRequestDaoImpl(Statement statement) throws SQLException {
        this.statement = statement;
        dropTable();
    }

    void dropTable() {
        try {
            statement.execute("DROP TABLE FLOWERREQUESTS");
            System.out.println("Dropped Flower Requests Table");
        } catch (SQLException e) {
            System.out.println("Failed to drop Flower Requests Table");
        }
    }

    String CSVHeaderString =
            "flowerReqID,flowerType,nodeID,employeeID,isEmergency,reqStatus,createdTimestamp,updatedTimestamp";

    void createTable() throws SQLException {

        try {
            statement.execute(
                    "CREATE TABLE FLOWERREQUESTS(\n"
                            + "                flowerReqID INT,\n"
                            + "                flowerType varchar(25),\n"
                            + "                nodeID varchar(25),\n"
                            + "                employeeID INT,\n"
                            + "                isEmergency INT,\n"
                            + "                reqStatus INT, \n"
                            + "                createdTimestamp timestamp, \n"
                            + "                updatedTimestamp timestamp, \n"
                            + "                constraint FlowerReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),\n"
                            + "                constraint FlowerReq_Employee_FK foreign key (employeeID) references EMPLOYEES(employeeID),\n"
                            + "                constraint FlowReq_PK primary key (flowerReqID),\n"
                            + "                constraint FlowerReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
                            + "                constraint FlowerIsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
        } catch (SQLException e) {
            System.out.println("Flower Request Table failed to be created!");
            throw (e);
        }
    }

    @Override
    public ArrayList<Request> getFlowerRequests() {
        ArrayList<Request> flowerRequestList = new ArrayList<>();

        try {
            ResultSet flowerRequests = statement.executeQuery("SELECT * FROM FLOWERREQUESTS");

            while (flowerRequests.next()) {
                ArrayList<String> flowerRequestData = new ArrayList<String>();

                for (int i = 0; i < flowerRequests.getMetaData().getColumnCount(); i++) {
                    flowerRequestData.add(flowerRequests.getString(i + 1));
                }

                flowerRequestList.add(new FlowerRequest(flowerRequestData));
            }

        } catch (SQLException e) {
            System.out.println("Query from flower request table failed.");
        } catch (StatusError e) {
            e.printStackTrace();
        } catch (NoFlower e) {
            e.printStackTrace();
        }
        return flowerRequestList;
    }

    @Override
    public void addFlowerRequest(FlowerRequest flowerRequest) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO FLOWERREQUESTS VALUES (%s)", flowerRequest.toValuesString()));
    }

    @Override
    public void changeFlowerRequest(FlowerRequest fr) throws SQLException {
        statement.executeUpdate(
                String.format(
                        "UPDATE FLOWERREQUESTS SET FLOWERTYPE='%s', NODEID='%s', EMPLOYEEID= %d, ISEMERGENCY=%d, REQSTATUS=%d, UPDATEDTIMESTAMP = '%s' WHERE LABREQID=%d",
                        fr.getFlower().getString(),
                        fr.getNodeID(),
                        fr.getEmployeeID(),
                        fr.getEmergency(),
                        fr.getStatus().getValue(),
                        new Timestamp(System.currentTimeMillis()),
                        fr.getRequestID()));

    }

    @Override
    public void deleteFlowerRequest(Integer requestID) throws SQLException {
        RequestFactory.getRequestFactory().getReqIDList().remove(requestID);
        statement.executeUpdate(
                String.format("DELETE FROM FLOWERREQUESTS WHERE FLOWERREQID=%d", requestID));
    }

    @Override
    public void exportFlowerReqCSV(String fileName) {
        File csvOutputFile = new File(fileName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            // print Table headers
            pw.print(CSVHeaderString);

            // print all locations
            for (Request f : getFlowerRequests()) {
                pw.println();
                pw.print(f.toCSVString());
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
            ResultSet flowerRequests =
                    statement.executeQuery(
                            String.format("SELECT * FROM FLOWERREQUESTS WHERE EMPLOYEEID = %d", employeeID));
            while (flowerRequests.next()) {
                ArrayList<String> flowerRequestData = new ArrayList<String>();

                for (int i = 0; i < flowerRequests.getMetaData().getColumnCount(); i++) {
                    flowerRequestData.add(flowerRequests.getString(i + 1));
                }

                employeeRequestList.add(new FlowerRequest(flowerRequestData));
            }
        } catch (SQLException | NoFlower | StatusError e) {
            e.printStackTrace();
        }
        return employeeRequestList;
    }

}
