package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import java.sql.*;

public class DBController {

  private String dbName = "myDB";
  private String connectionString;
  private Statement statement;
  private Connection connection;

  //  private LocationDao locationDao;
  //  private MedEquipDao medEquipDao;
  //  private MedEquipRequestDao medEquipRequestDao;
  //  private LabServiceRequestDao labServiceRequestDao;
  //  private EmployeeDao employeeDao;
  //  private MedRequestDao medRequestDao;

  //  private LocationManager locationManager;
  //  private MedEquipManager medEquipManager;
  //  private MedEquipRequestManager medEquipRequestManager;
  //  private LabServiceRequestManager labServiceRequestManager;
  //  private EmployeeManager employeeManager;
  //  private MedRequestManager medRequestManager;

  private static DBController dbController = new DBController();

  public static DBController getDBController() {
    return dbController;
  }

  private DBController() {
    this.connectionString = "jdbc:derby:" + this.dbName + ";create=true";
    try {
      this.connect();

      // Create Daos (tables are dropped automatically when daos are created)
      // *ORDER MATTERS BECAUSE OF FOREIGN KEYS*
      MedRequestDao medRequestDao = new MedRequestDaoImpl(statement);
      LabServiceRequestDao labServiceRequestDao = new LabServiceRequestDaoImpl(statement);
      MedEquipRequestDao medEquipRequestDao = new MedEquipRequestDaoImpl(statement);
      MedEquipDao medEquipDao = new MedEquipDaoImpl(statement);
      EmployeeDao employeeDao = new EmployeeDaoImpl(statement);
      LocationDao locationDao = new LocationDaoImpl(statement);

      // Assign Daos to Managers
      EmployeeManager.getEmployeeManager().setEmployeeDao(employeeDao);
      LocationManager.getLocationManager().setLocationDao(locationDao);
      MedEquipManager.getMedEquipManager().setMedEquipDao(medEquipDao);
      MedEquipRequestManager.getMedEquipRequestManager().setMedEquipRequestDao(medEquipRequestDao);
      MedRequestManager.getMedRequestManager().setMedRequestDao(medRequestDao);
      LabServiceRequestManager.getLabServiceRequestManager()
          .setLabServiceRequestDao(labServiceRequestDao);

      // *ORDER MATTERS BECAUSE OF FOREIGN KEYS*
      ((EmployeeDaoImpl) employeeDao).createTable();
      ((LocationDaoImpl) locationDao).createTable();
      ((MedEquipDaoImpl) medEquipDao).createTable();
      ((LabServiceRequestDaoImpl) labServiceRequestDao).createTable();
      ((MedEquipRequestDaoImpl) medEquipRequestDao).createTable();
      ((MedRequestDaoImpl) medRequestDao).createTable();

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Table Creation Failed");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public ResultSet executeQuery(String sql) throws SQLException {
    return statement.executeQuery(sql);
  }

  public int executeUpdate(String sql) throws SQLException {
    return statement.executeUpdate(sql);
  }

  public boolean execute(String sql) throws SQLException {
    return statement.execute(sql);
  }

  /**
   * Establishes connection with embedded Apache Derby Database
   *
   * @return Returns a new connection to an embedded Apache Derby Database
   * @throws SQLException if unable to connect to database
   * @throws ClassNotFoundException if Apache Derby installation not found
   */
  private void connect() throws SQLException, ClassNotFoundException {
    System.out.println("-------Embedded Apache Derby Connection Testing --------");
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
      System.out.println("For IntelliJ do the following:");
      System.out.println("File | Project Structure, Modules, Dependency tab");
      System.out.println("Add by clicking on the green plus icon on the right of the window");
      System.out.println(
          "Select JARs or directories. Go to the folder where the database JAR is located");
      System.out.println("Click OK, now you can compile your program and run it.");
      e.printStackTrace();
      throw (e);
    }
    System.out.println("Apache Derby driver registered!");

    try {
      connection = DriverManager.getConnection(connectionString);
      statement = connection.createStatement();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      throw (e);
    }

    System.out.println("Apache Derby connection established!");
  }

  /**
   * Creates the table of Locations in the database (First attempts to drop the table in case of
   * reruns)
   *
   * @throws SQLException if Location Table fails to be created
   */
  /*public void createTables() throws SQLException {

  if (statement == null) {
    System.out.println("Connection not established, cannot create table");
    throw (new SQLException());
  } else {
    try {
      statement.execute("DROP TABLE LABSERVICEREQUESTS");
      statement.execute("DROP TABLE MEDREQUESTS");
      statement.execute("DROP TABLE MEDICALEQUIPMENTREQUESTS");
      statement.execute("DROP TABLE MEDICALEQUIPMENT");
      statement.execute("DROP TABLE LOCATIONS");
      statement.execute("DROP TABLE EMPLOYEES");
    } catch (SQLException e) {

    }

    try {
      /*
      statement.execute(
          "CREATE TABLE LOCATIONS("
              + "nodeID varchar(25),"
              + "xcoord INT, "
              + "ycoord  INT, "
              + "floor varchar(25), "
              + "building varchar(25), "
              + "nodeType varchar(25), "
              + "longName varchar(255), "
              + "shortName varchar(255),"
              + "constraint Locations_PK primary key (nodeID))");

       */
  /*
  statement.execute(
      "CREATE TABLE MEDICALEQUIPMENT("
          + "medID varchar(25), "
          + "type varchar(25), "
          + "nodeID varchar(25),"
          + "status INT,"
          + "constraint MedEquip_PK primary key (medID),"
          + "constraint Location_FK foreign key (nodeID) references LOCATIONS(nodeID),"
          + "constraint Status_check check (status = 0 or status = 1 or status = 2))");

   */
  /*
  statement.execute(
      "CREATE TABLE MEDICALEQUIPMENTREQUESTS("
          + "medReqID INT, "
          + "medID varchar(25),"
          + "equipType varchar(25),"
          + "nodeID varchar(25),"
          + "employeeName varchar(50),"
          + "isEmergency INT,"
          + "reqStatus INT, "
          + "constraint MedReq_MedEquip_FK foreign key (medID) references MEDICALEQUIPMENT(medID),"
          + "constraint MedReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID),"
          + "constraint MedEquipReq_PK primary key (medReqID),"
          + "constraint MedEReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),"
          + "constraint IsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
          */
  /*statement.execute(
     "CREATE TABLE LABSERVICEREQUESTS(\n"
         + "                labReqID INT,\n"
         + "                labType varchar(25),\n"
         + "                nodeID varchar(25),\n"
         + "                employeeName varchar(50),\n"
         + "                isEmergency INT,\n"
         + "                reqStatus INT, \n"
         + "                constraint LabReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID) on delete cascade,\n"
         + "                constraint LabReq_PK primary key (labReqID),\n"
         + "                constraint LabReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),\n"
         + "                constraint LabIsEmergency_check check (isEmergency = 0 or isEmergency = 1))");

  */
  /*
        statement.execute(
            "CREATE TABLE EMPLOYEES(\n"
                + "employeeID INT, \n "
                + "firstname varchar(25), \n "
                + "lastname varchar(25), \n "
                + "employeetype varchar(25), \n "
                + "username varchar(25), \n "
                + "password varchar(25), \n "
                + "constraint Employees_PK primary key (employeeID),"
                + "constraint username_uq unique(username))");


      } catch (SQLException e) {
        System.out.println("Table Creation Failed. Check output console.");
        e.printStackTrace();
        throw (e);
      }
    }
  }*/

  /**
   * closes the connection to the embedded database
   *
   * @throws SQLException
   */
  public void closeConnection() throws SQLException {
    try {
      connection.close();
    } catch (SQLException e) {
      throw (e);
    }
  }
}
