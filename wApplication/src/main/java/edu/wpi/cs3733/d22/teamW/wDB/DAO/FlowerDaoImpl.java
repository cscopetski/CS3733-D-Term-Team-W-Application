package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.LabServiceRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.sql.SQLException;
import java.util.ArrayList;

public class FlowerDaoImpl implements FlowerDao{

    @Override
    public ArrayList<Request> getFlowerRequests() {
        return null;
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
