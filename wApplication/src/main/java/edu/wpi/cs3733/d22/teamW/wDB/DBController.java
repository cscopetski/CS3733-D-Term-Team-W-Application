package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.*;

public class DBController {

  private String dbName = "myDB";
  private String connectionString;
  private Statement statement;
  private Connection connection;

  private PreparedStatement insertLocation;
  private PreparedStatement insertMedEquip;
  private PreparedStatement insertMedEquipReq;

  private static DBController dbController = new DBController();

  public static DBController getDBController() {
    return dbController;
  }

  private DBController() {
    this.connectionString = "jdbc:derby:" + this.dbName + ";create=true";
    try {
      this.connect();
      this.createTables();
    } catch (SQLException e) {
      e.printStackTrace();
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
  private void createTables() throws SQLException {

    if (statement == null) {
      System.out.println("Connection not established, cannot create table");
      throw (new SQLException());
    } else {
      try {
        statement.execute("DROP TABLE MEDICALEQUIPMENTREQUESTS");
        statement.execute("DROP TABLE MEDICALEQUIPMENT");
        statement.execute("DROP TABLE LOCATIONS");
      } catch (SQLException e) {

      }

      try {
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
        statement.execute(
            "CREATE TABLE MEDICALEQUIPMENT("
                + "medID varchar(25), "
                + "type varchar(25), "
                + "nodeID varchar(25),"
                + "status INT,"
                + "constraint MedEquip_PK primary key (medID),"
                + "constraint Location_FK foreign key (nodeID) references LOCATIONS(nodeID),"
                + "constraint Status_check check (status = 0 or status = 1 or status = 2))");
        statement.execute(
            "CREATE TABLE MEDICALEQUIPMENTREQUESTS("
                + "medReqID INT, "
                + "medID varchar(25),"
                + "equipType varchar(25),"
                + "nodeID varchar(25),"
                + "employeeName varchar(50),"
                + "isEmergency INT,"
                + "reqStatus INT, "
                + "constraint MedReq_MedEquip_FK foreign key (medID) references MEDICALEQUIPMENT(medID) on delete cascade,"
                + "constraint MedReq_Location_FK foreign key (nodeID) references LOCATIONS(nodeID) on delete cascade," // TODO: might want to remove on delete cascade
                + "constraint MedEquipReq_PK primary key (medReqID,medID, nodeID),"
                + "constraint MedEReq_Status_check check (reqStatus = 0 or reqStatus = 1 or reqStatus = 2 or reqStatus = 3),"
                + "constraint IsEmergency_check check (isEmergency = 0 or isEmergency = 1))");
      } catch (SQLException e) {
        System.out.println("Table Creation Failed. Check output console.");
        e.printStackTrace();
        throw (e);
      }
    }
  }

  /*
   */
  /**
   * Deletes any Location in database that has the removeID as it's nodeID Author: Edison
   *
   * @param table
   * @param nodeID
   * @throws SQLException
   */
  /*
  public void deleteLocation(String table, String nodeID) throws SQLException {
    statement.executeUpdate(String.format("DELETE FROM %s WHERE nodeID='%s'", table, nodeID));
  }

  public void cancel(String table, int medReqID) throws SQLException {
    statement.executeUpdate(
        String.format("UPDATE %s SET reqStatus = 3 WHERE medReqID = %d", table, medReqID));
  }

  public void setEmergency(String table, int medReqID, boolean status) throws SQLException {
    statement.executeUpdate(
        String.format("UPDATE %s SET isEmergency=%d WHERE medReqID = %d", table, status, medReqID));
  }

  */
  /**
   * Updates the floor and nodeType of a Location
   *
   * @param nodeID ID of the Location node to update
   * @param newFloor New floor of the Location node
   * @param newNodeType New type of the Location node
   * @author Hasan
   */
  /*
  public void updateNodeFromLocationTable(String nodeID, String newFloor, String newNodeType) {
    try {
      statement.executeUpdate(
          String.format(
              "UPDATE LOCATIONS SET FLOOR='%s', NODETYPE='%s' WHERE NODEID='%s'",
              newFloor, newNodeType, nodeID));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  */
  /**
   * Updates the location and status of a medical equipment
   *
   * @param medID ID of the medical equipment to update
   * @param newLocationID ID of the new location
   * @param newStatus Updated status of the medical equipment
   * @author Hasan
   */
  /*
  public void updateNodeFromMedEquipTable(String medID, String newLocationID, int newStatus) {
    try {
      statement.executeUpdate(
          String.format(
              "UPDATE MEDICALEQUIPMENT SET NODEID='%s', STATUS=%d WHERE MEDID='%s'",
              newLocationID, newStatus, medID));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  */
  /**
   * Updates the item, location, and employee name of a medical equipment request
   *
   * @param requestID ID of the medical equipment request
   * @param newItemID ID of the new item of the request
   * @param newLocationID ID of the new location for the request
   * @param newEmployeeName Name of the new employee for the request
   */
  /*
  public void updateNodeFromMedicalEquipmentRequestsTable(
      int requestID, String newItemID, String newLocationID, String newEmployeeName) {
    try {
      statement.executeUpdate(
          String.format(
              "UPDATE MEDICALEQUIPMENTREQUESTS SET MEDID='%s', NODEID='%s', EMPLOYEENAME='%s' WHERE MEDREQID=%d",
              newItemID, newLocationID, newEmployeeName, requestID));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  */
  /**
   * Updates the nodeID of a Location based off of the new Floor and Type values Currently never
   * used because we commented out the lines that call this in the LocationController class Author:
   * Hasan
   *
   * @param modifyID
   * @param newID
   */
  /*
  public void updateNodeIdFromLocationTable(String modifyID, String newID) {
    try {
      statement.executeUpdate(
          String.format("UPDATE LOCATIONS SET NODEID='%s' WHERE NODEID='%s'", newID, modifyID));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  */
  /**
   * Count the number of nodes one the same floor with the same type to update the nodeID when
   * changing the type and floor of said node
   *
   * <p>Author: Hasan
   *
   * @param floor
   * @param type
   * @return
   */
  /*
  public int countFloorTypeFromTable(String floor, String type) {
    ResultSet rs = null;
    try {
      rs =
          statement.executeQuery(
              String.format(
                  "SELECT COUNT(NODEID) FROM LOCATIONS WHERE NODETYPE='%s' AND FLOOR='%s'",
                  type, floor));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      if (rs.next()) {
        return rs.getInt("1");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public void addEntity(Location newLocation, String inputID) {
    try {
      insertLocation.setString(1, inputID);
      for (int i = 2; i <= 3; i++) {
        insertLocation.setNull(i, Types.INTEGER);
      }
      for (int i = 4; i <= 8; i++) {
        insertLocation.setNull(i, java.sql.Types.VARCHAR);
      }
      insertLocation.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  public void addEntity(MedEquip medequip, String inputID) {
    try {
      insertMedEquip.setString(1, inputID);
      for (int i = 2; i <= 3; i++) {
        insertMedEquip.setNull(i, Types.VARCHAR);
      }
      insertMedEquip.setNull(4, java.sql.Types.INTEGER);
      insertMedEquip.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  public void addEntity(MedEquipRequest medEquipReq) {
    try {
      insertMedEquipReq.setInt(1, medEquipReq.getRequestID());
      insertMedEquipReq.setString(2, medEquipReq.getItemID());
      insertMedEquipReq.setString(3, medEquipReq.getNodeID());
      insertMedEquipReq.setString(4, medEquipReq.getEmployeeName());
      insertMedEquipReq.setInt(
          5, medEquipReq.isEmergency()); // TODO make sure this works as intended
      insertMedEquipReq.setInt(6, medEquipReq.getStatus());

      insertMedEquipReq.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
  }

  */
  /**
   * Build the ArrayList of all Locations in database by looping through set of results (locations)
   * from SQL query
   *
   * @return ArrayList of all Locations in database
   */
  /*
  public ArrayList<Location> getLocationTable() {

    ArrayList<Location> locationsList = new ArrayList<>();



    return locationsList;
  }

  public ArrayList<MedEquip> getMedEquipTable() {
    ArrayList<MedEquip> medEquipList = new ArrayList<>();

    try {
      ResultSet medEquipment = statement.executeQuery("SELECT * FROM MEDICALEQUIPMENT");

      // Size of num MedEquip fields
      String[] medEquipData = new String[4];

      while (medEquipment.next()) {

        for (int i = 0; i < medEquipData.length; i++) {
          medEquipData[i] = medEquipment.getString(i + 1);
        }

        medEquipList.add(new MedEquip(medEquipData));
      }

    } catch (SQLException e) {
      System.out.println("Query from locations table failed");
      e.printStackTrace();
    }
    return medEquipList;
  }

  public ArrayList<MedEquipRequest> getMedEquipReqTable() {
    ArrayList<MedEquipRequest> medEquipRequestList = new ArrayList<>();

    try {
      ResultSet medEquipment = statement.executeQuery("SELECT * FROM MEDICALEQUIPMENTREQUESTS");

      // Size of num MedEquipRequest fields
      String[] medEquipData = new String[6];

      while (medEquipment.next()) {

        for (int i = 0; i < medEquipData.length; i++) {
          medEquipData[i] = medEquipment.getString(i + 1);
        }

        medEquipRequestList.add(new MedEquipRequest(medEquipData));
      }

    } catch (SQLException e) {
      System.out.println("Query from locations table failed");
      e.printStackTrace();
    }
    return medEquipRequestList;
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
