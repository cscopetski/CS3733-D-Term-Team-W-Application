package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.FlowerRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FlowerRequestDao {

    ArrayList<Request> getFlowerRequests();

    void addFlowerRequest(FlowerRequest fr) throws SQLException;

    void changeFlowerRequest(FlowerRequest fr) throws SQLException;

    void deleteFlowerRequest(Integer requestID) throws SQLException;

    void exportFlowerReqCSV(String filename);

    ArrayList<Request> getEmployeeRequests(Integer employeeID);
}
