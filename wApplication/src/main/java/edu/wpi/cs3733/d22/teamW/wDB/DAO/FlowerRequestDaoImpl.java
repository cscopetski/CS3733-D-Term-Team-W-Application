package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoFlower;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingLabServiceRequestType;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.entity.FlowerRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.LabServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                ArrayList<String> labServiceRequestData = new ArrayList<String>();

                for (int i = 0; i < flowerRequests.getMetaData().getColumnCount(); i++) {
                    labServiceRequestData.add(flowerRequests.getString(i + 1));
                }

                flowerRequestList.add(new FlowerRequest(labServiceRequestData));
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
    public void addFlowerRequest(LabServiceRequest lsr) throws SQLException {

    }

    @Override
    public void changeFlowerRequest(LabServiceRequest lsr) throws SQLException {

    }

    @Override
    public void deleteFlowerRequest(Integer requestID) throws SQLException {

    }

    @Override
    public void exportFlowerReqCSV(String filename) {

    }

    @Override
    public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
        return null;
    }
}
