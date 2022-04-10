package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.CleaningRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.LabServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CleaningRequestDaoImpl implements CleaningRequestDao {
    Statement statement;

    CleaningRequestDaoImpl(Statement statement) throws SQLException {
        this.statement = statement;
        dropTable();
    }

    void dropTable() throws SQLException {
        try {
            statement.execute("DROP TABLE CLEANINGREQUESTS");
            System.out.println("Dropped Cleaning Requests Table");
        } catch (SQLException e) {
            System.out.println("Failed to drop Cleaning Requests Table");
            throw (e);
        }
    }

    void createTable() throws SQLException {

        try {
            statement.execute(
                    "CREATE TABLE CLEANINGREQUESTS(\n"
                            + "                ReqID INT,\n"
                            + "                itemID varchar(25),\n"
                            + "                reqStatus INT, \n"
                            + "                constraint cleanReq_itemID_FK foreign key (itemID) references MEDICALEQUIPMENT(medID),\n"
                            + "                constraint cleanReq_PK primary key (ReqID),\n"
                            + "                constraint labReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
                            + "                constraint isEmergency_check check (isEmergency = 0 or isEmergency = 1))");
        } catch (SQLException e) {
            System.out.println("Lab Service Request Table failed to be created!");
            throw (e);
        }
    }

    @Override
    public ArrayList<CleaningRequest> getAllCleaningRequests() {
        ArrayList<CleaningRequest> cleanRequestList = new ArrayList<>();

        try {
            ResultSet cleanRequests = statement.executeQuery("SELECT * FROM CLEANINGREQUESTS");

            // Size of num LabServiceRequest fields
            int size = 6;
            ArrayList<String> cleanRequestData = new ArrayList<String>();

            while (cleanRequests.next()) {

                for (int i = 0; i < size; i++) {
                    cleanRequestData.add(i, cleanRequests.getString(i + 1));
                }

                cleanRequestList.add(new CleaningRequest(cleanRequestData));
            }

        } catch (SQLException e) {
            System.out.println("Query from cleaning request table failed.");
        }
        return cleanRequestList;
    }

    @Override
    public void addCleaningRequest(CleaningRequest cleaningRequest) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO CLEANINGREQUESTS VALUES (%s)", cleaningRequest.toValuesString()));
    }

    @Override
    public void changeCleaningRequest(Integer requestID, String itemID, RequestStatus status)
            throws SQLException{
        statement.executeUpdate(
                String.format(
                        "UPDATE CLEANINGREQUESTS SET ITEMID='%s', REQSTATUS=%d WHERE REQID=%d",
                        itemID, status.getValue(), requestID));
    }

    @Override
    public void deleteCleaningRequest(Integer requestID) throws SQLException {
        statement.executeUpdate(
                String.format("DELETE FROM CLEANINGREQUESTS WHERE REQID=%d", requestID));
    }

    @Override
    public void exportCleaningReqCSV(String fileName) {
        File csvOutputFile = new File(fileName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            // print Table headers
            pw.print("ReqID,itemID,status");

            // print all locations
            for (CleaningRequest m : getAllCleaningRequests()) {
                pw.println();
                pw.print(m.toCSVString());
            }

        } catch (FileNotFoundException e) {

            System.out.println(String.format("Error Exporting to File %s", fileName));
            e.printStackTrace();
        }
    }
}
