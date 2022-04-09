package edu.wpi.cs3733.d22.teamW.wDB;

import java.util.ArrayList;
import java.sql.*;

public class MedRequestDaoImpl implements MedRequestDao {
    private Statement statement;

    public MedRequestDaoImpl(Statement statement){
        this.statement = statement;
        createTable();
    }

    private void createTable() {
        try {
            statement.execute(
                    "CREATE TABLE MEDREQUESTS("
                            + "requestID INT,"
                            + "medicine varchar(25),"
                            + "nodeID varchar(25),"
                            + "employeeName varchar(50),"
                            + "isEmergency INT,"
                            + "reqStatus INT, "
                            + "constraint MEDREQ_Location_FK foreign key (nodeID) references LOCATIONS,"
                            + "constraint MedReq_PK primary key (requestID),"
                            + "constraint MedReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
                            + "constraint MedIsEmergency_check check (isEmergency = 0 or isEmergency = 1)"
                            +")"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMedRequest(MedRequest mr) throws SQLException {
        statement.executeUpdate(String.format("INSERT INTO MEDREQUESTS VALUES (%s)", mr.toValuesString()));
    }

    @Override
    public void changeMedRequest(Integer id,String m, String n, String en, Integer ie, Integer rs) throws SQLException {
        statement.executeUpdate(String.format("UPDATE MEDREQUESTS SET MEDICINE='%s', NODEID='%s', EMPLOYEENAME='%s', ISEMERGENCY=%d, REQSTATUS=%d WHERE REQUESTID=%d",
                m,n,en,ie,rs,id));
    }

    @Override
    public void deleteMedRequest(Integer id) throws SQLException {
        statement.executeUpdate(String.format("DELETE FROM MEDREQUESTS WHERE REQUESTID=%d", id));
    }

    @Override
    public Request getMedRequest(Integer id) throws SQLException {
        MedRequest mr = null;
        try {
            ResultSet medRequests = statement.executeQuery(String.format("SELECT * FROM MEDREQUESTS WHERE REQUESTID = %d", id));

            // Size of num LabServiceRequest fields
            int size = 6;
            ArrayList<String> medRequestData = new ArrayList<String>();

            while (medRequests.next()) {

                for (int i = 0; i < size; i++) {
                    medRequestData.add(i, medRequests.getString(i + 1));
                }
                mr = new MedRequest(medRequestData);
            }
        }catch (SQLException e) {
            System.out.println("Query from medicine request table failed.");
        }
        return mr;
    }
    @Override
    public ArrayList<Request> getAllMedRequest() {
        ArrayList<Request> medRequestList = new ArrayList<Request>();

        try {
            ResultSet medRequests = statement.executeQuery("SELECT * FROM MEDREQUESTS");

            // Size of num LabServiceRequest fields
            int size = 6;
            ArrayList<String> medRequestData = new ArrayList<String>();

            while (medRequests.next()) {

                for (int i = 0; i < size; i++) {
                    medRequestData.add(i, medRequests.getString(i + 1));
                }

                medRequestList.add(new MedRequest(medRequestData));
            }

        } catch (SQLException e) {
            System.out.println("Query from medicine request table failed.");
        }
        return medRequestList;
    }
}
