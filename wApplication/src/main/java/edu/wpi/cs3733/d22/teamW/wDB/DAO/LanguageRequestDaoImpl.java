package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.LanguageRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LanguageRequestDaoImpl implements  LanguageRequestDao{

    private Statement statement;

    LanguageRequestDaoImpl(Statement statement) throws SQLException {
        this.statement = statement;
        dropTable();
    }

    void dropTable() {
        try {
            statement.execute("DROP TABLE LANGUAGEREQUESTS");
            System.out.println("Dropped Language Request Table");
        } catch (SQLException e) {
            System.out.println("Failed to drop Language Request Table");
        }
    }

    String CSVHeaderString =
            "requestID,language,nodeID,employeeID,emergency,status,createdTimestamp,updatedTimestamp";

    void createTable() throws SQLException {

        try {
            statement.execute(
                    "CREATE TABLE MEDREQUESTS("
                            + "requestID INT,"
                            + "language varchar(25),"
                            + "nodeID varchar(25),"
                            + "employeeID INT,"
                            + "isEmergency INT,"
                            + "reqStatus INT, "
                            + "createdTimestamp timestamp, "
                            + "updatedTimestamp timestamp, "
                            + "constraint LangReq_Location_FK foreign key (nodeID) references LOCATIONS,"
                            + "constraint LangReq_Employee_FK foreign key (employeeID) references LOCATIONS,"
                            + "constraint LangReq_PK primary key (requestID),"
                            + "constraint LangReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
                            + "constraint LangIsEmergency_check check (isEmergency = 0 or isEmergency = 1)"
                            + ")");
        } catch (SQLException e) {
            System.out.println("Medicine Service Request Table failed to be created!");
            throw (e);
        }
        System.out.println("Medicine Service Request Table created!");
    }

    @Override
    public void addLanguageRequest(LanguageRequest lr) throws SQLException {

    }

    @Override
    public void changeMedRequest(LanguageRequest lr) throws SQLException {

    }

    @Override
    public void deleteLanguageRequest(Integer id) throws SQLException {

    }

    @Override
    public Request getLanguageRequest(Integer id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Request> getAllLanguageRequest() throws SQLException {
        return null;
    }

    @Override
    public void exportLanguageReqCSV(String fileName) {

    }

    @Override
    public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
        return null;
    }

    @Override
    public void updateLangReqAtLocation(String nodeID) throws Exception {

    }

    @Override
    public void updateLanguageRequestWithEmployee(Integer employeeID) throws Exception {

    }
}
