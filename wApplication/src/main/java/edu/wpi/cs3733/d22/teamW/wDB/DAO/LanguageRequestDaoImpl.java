package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.LanguageRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquipRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LanguageRequestDaoImpl implements LanguageRequestDao {

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
          "CREATE TABLE LANGUAGEREQUESTS("
              + "requestID INT,"
              + "language varchar(25),"
              + "nodeID varchar(25),"
              + "employeeID INT,"
              + "isEmergency INT,"
              + "reqStatus INT, "
              + "createdTimestamp timestamp, "
              + "updatedTimestamp timestamp, "
              + "constraint LangReq_Location_FK foreign key (nodeID) references LOCATIONS,"
              + "constraint LangReq_Employee_FK foreign key (employeeID) references EMPLOYEES,"
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
    statement.executeUpdate(String.format("INSERT INTO LANGUAGEREQUESTS VALUES (%s)", lr.toValuesString()));
  }

  @Override
  public void changeMedRequest(LanguageRequest lr) throws SQLException {}

  @Override
  public void deleteLanguageRequest(Integer id) throws SQLException {
    RequestFactory.getRequestFactory().getReqIDList().remove(id);
    statement.executeUpdate(
            String.format("DELETE FROM LANGUAGEREQUESTS WHERE MEDREQID=%d", id));

  }

  @Override
  public Request getLanguageRequest(Integer id) throws SQLException {
    return null;
  }

  @Override
  public ArrayList<Request> getAllLanguageRequest() throws SQLException {
    ArrayList<Request> languageRequestList = new ArrayList<>();

    try {
      ResultSet langReqs = statement.executeQuery("SELECT * FROM LANGUAGEREQUESTS");

      // Size of num MedEquipRequest fields
      ArrayList<String> langReqData = new ArrayList<>();

      while (langReqs.next()) {

        for (int i = 0; i < langReqs.getMetaData().getColumnCount(); i++) {
          langReqData.add(i, langReqs.getString(i + 1));
        }

        languageRequestList.add(new LanguageRequest(langReqData));
      }

    } catch (SQLException e) {
      System.out.println("Query from med equip request table failed");
      throw (e);
    } catch (StatusError e) {
      e.printStackTrace();
    }
    return languageRequestList;  }

  @Override
  public void exportLanguageReqCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (Request m : getAllLanguageRequest()) {
        pw.println();
        pw.print(m.toCSVString());
      }

    } catch (FileNotFoundException | SQLException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }

  @Override
  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    ArrayList<Request> employeeRequestList = new ArrayList<>();
    try {
      ResultSet languageRequests =
              statement.executeQuery(
                      String.format(
                              "SELECT * FROM LANGUAGEREQUESTS WHERE EMPLOYEEID = %d", employeeID));
      while (languageRequests.next()) {
        ArrayList<String> languageRequestData = new ArrayList<String>();

        for (int i = 0; i < languageRequests.getMetaData().getColumnCount(); i++) {
          languageRequestData.add(languageRequests.getString(i + 1));
        }

        employeeRequestList.add(new LanguageRequest(languageRequestData));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (StatusError e) {
      e.printStackTrace();
    }
    return employeeRequestList;  }

  @Override
  public void updateLangReqAtLocation(String nodeID) throws Exception {}

  @Override
  public void updateLanguageRequestWithEmployee(Integer employeeID) throws Exception {
    ResultSet resultSet =
            statement.executeQuery(
                    String.format(
                            "SELECT REQUESTID FROM LANGUAGEREQUESTS WHERE employeeID=%d", employeeID));

    ArrayList<Integer> reqIDs = new ArrayList<>();
    while (resultSet.next()) {

      Integer reqID = resultSet.getInt("REQUESTID");
      reqIDs.add(reqID);
    }

    for (Integer reqID : reqIDs) {
      MedEquipRequestManager.getMedEquipRequestManager().cancel(reqID);
    }

    statement.executeUpdate(
            String.format(
                    "UPDATE LANGUAGEREQUESTS SET employeeID=%d WHERE employeeID=%d",
                    EmployeeManager.getEmployeeManager().getDeletedEmployee(), employeeID));

  }
}
