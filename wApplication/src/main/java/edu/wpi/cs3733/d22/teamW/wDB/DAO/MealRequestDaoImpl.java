package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoMeal;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MealRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MealRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MealRequestDaoImpl implements MealRequestDao {

  Statement statement;

  MealRequestDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() {
    try {
      statement.execute("DROP TABLE MEALREQUESTS");
      System.out.println("Dropped Meal Requests Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Meal Requests Table");
    }
  }

  String CSVHeaderString =
      "ReqID,mealType,lastName,firstName,nodeID,employeeID,isEmergency,reqStatus,createdTimestamp,updatedTimestamp";

  void createTable() throws SQLException {

    try {
      statement.execute(
          "CREATE TABLE MEALREQUESTS(\n"
              + "                ReqID INT,\n"
              + "                mealType varchar(25),\n"
              + "                lastName varchar(25),\n"
              + "                firstName varchar(25),\n"
              + "                nodeID varchar(25),\n"
              + "                employeeID INT,\n"
              + "                isEmergency INT,\n"
              + "                reqStatus INT, \n"
              + "                createdTimestamp timestamp, \n"
              + "                updatedTimestamp timestamp, \n"
              + "                constraint MealReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),\n"
              + "                constraint MealReq_Employee_FK foreign key (employeeID) references EMPLOYEES(employeeID),\n"
              + "                constraint MealReq_PK primary key (ReqID),\n"
              + "                constraint MealReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
              + "                constraint MealIsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
    } catch (SQLException e) {
      System.out.println("Meal Request Table failed to be created!");
      throw (e);
    }
  }

  @Override
  public ArrayList<Request> getAllMealServiceRequests() {
    ArrayList<Request> mealRequestList = new ArrayList<>();

    try {
      ResultSet mealRequests = statement.executeQuery("SELECT * FROM MEALREQUESTS");

      while (mealRequests.next()) {
        ArrayList<String> mealRequestData = new ArrayList<String>();

        for (int i = 0; i < mealRequests.getMetaData().getColumnCount(); i++) {
          mealRequestData.add(mealRequests.getString(i + 1));
        }

        mealRequestList.add(new MealRequest(mealRequestData));
      }

    } catch (SQLException e) {
      System.out.println("Query from meal request table failed.");
    } catch (StatusError e) {
      e.printStackTrace();
    } catch (NoMeal e) {
      e.printStackTrace();
    }
    return mealRequestList;
  }

  @Override
  public void addMealServiceRequest(MealRequest mealRequest) throws SQLException {
    statement.executeUpdate(
        String.format("INSERT INTO MEALREQUESTS VALUES (%s)", mealRequest.toValuesString()));
  }

  @Override
  public void changeMealServiceRequest(MealRequest mealRequest) throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE MEALREQUESTS SET MEALTYPE='%s', NODEID='%s', EMPLOYEEID= %d, ISEMERGENCY=%d, REQSTATUS=%d, UPDATEDTIMESTAMP = '%s' WHERE REQID=%d",
            mealRequest.getMealType().getString(),
            mealRequest.getNodeID(),
            mealRequest.getEmployeeID(),
            mealRequest.getEmergency(),
            mealRequest.getStatus().getValue(),
            new Timestamp(System.currentTimeMillis()),
            mealRequest.getRequestID()));
  }

  @Override
  public void deleteMealServiceRequest(Integer requestID) throws SQLException {
    RequestFactory.getRequestFactory().getReqIDList().remove(requestID);
    statement.executeUpdate(String.format("DELETE FROM MEALREQUESTS WHERE REQID=%d", requestID));
  }

  @Override
  public void exportMealServiceReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (Request f : this.getAllMealServiceRequests()) {
        pw.println();
        pw.print(f.toCSVString());
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s ", fileName));
      e.printStackTrace();
    }
  }

  @Override
  public Request getMealRequest(Integer id) {
    MealRequest fr = null;
    try {
      ResultSet mealRequests =
          statement.executeQuery(String.format("SELECT * FROM MEALREQUESTS WHERE ReqID = %d", id));

      mealRequests.next();

      ArrayList<String> mealRequestFields = new ArrayList<String>();
      for (int i = 0; i < mealRequests.getMetaData().getColumnCount(); i++) {
        mealRequestFields.add(mealRequests.getString(i + 1));
      }
      fr = new MealRequest(mealRequestFields);

    } catch (SQLException | NoMeal | StatusError e) {
      System.out.println("Query from meal request table failed.");
    }
    return fr;
  }

  @Override
  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    ArrayList<Request> employeeRequestList = new ArrayList<>();
    try {
      ResultSet mealRequests =
          statement.executeQuery(
              String.format("SELECT * FROM MEALREQUESTS WHERE EMPLOYEEID = %d", employeeID));
      while (mealRequests.next()) {
        ArrayList<String> mealData = new ArrayList<String>();

        for (int i = 0; i < mealRequests.getMetaData().getColumnCount(); i++) {
          mealData.add(mealRequests.getString(i + 1));
        }

        employeeRequestList.add(new MealRequest(mealData));
      }
    } catch (SQLException | NoMeal | StatusError e) {
      e.printStackTrace();
    }
    return employeeRequestList;
  }

  @Override
  public void updateMealServiceRequestsAtLocation(String nodeID) throws Exception {

    ResultSet resultSet =
        statement.executeQuery(
            String.format("SELECT ReqID FROM MEALREQUESTS WHERE nodeID='%s'", nodeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = resultSet.getInt("ReqID");
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      MealRequestManager.getMealRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE MEALREQUESTS SET NODEID='%s' WHERE NODEID='%s'",
            LocationManager.getLocationManager().getNoneLocation(), nodeID));
  }

  @Override
  public void updateMealServiceRequestsWithEmployee(Integer employeeID) throws Exception {
    ResultSet resultSet =
        statement.executeQuery(
            String.format("SELECT ReqID FROM MEALREQUESTS WHERE employeeID=%d", employeeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = resultSet.getInt("ReqID");
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      MealRequestManager.getMealRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
        String.format(
            "UPDATE MEALREQUESTS SET employeeID=%d WHERE employeeID=%d",
            EmployeeManager.getEmployeeManager().getDeletedEmployee(), employeeID));
  }
}
