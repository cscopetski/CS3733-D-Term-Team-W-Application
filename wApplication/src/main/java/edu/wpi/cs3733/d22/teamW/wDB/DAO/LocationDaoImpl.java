package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import java.io.*;
import java.sql.*;
import java.util.*;

public class LocationDaoImpl implements LocationDao {

  Statement statement;

  LocationDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  private void dropTable() {
    try {
      statement.execute("DROP TABLE LOCATIONS");
      System.out.println("Dropped Locations Table");
    } catch (SQLException e) {
      System.out.println("Failed Dropped Locations Table");
    }
  }

  String CSVHeaderString = "nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName";

  void createTable() throws SQLException {

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
    } catch (SQLException e) {
      System.out.println("Locations Table failed to be created!");
      throw (e);
    }
    System.out.println("Locations Table created");
  }

  @Override
  public ArrayList<Location> getAllLocations() throws SQLException {
    ArrayList<Location> locationsList = new ArrayList<>();

    try {
      ResultSet locations = statement.executeQuery("SELECT * FROM LOCATIONS");

      while (locations.next()) {
        ArrayList<String> locationData = new ArrayList<String>();

        for (int i = 0; i < locations.getMetaData().getColumnCount(); i++) {
          locationData.add(locations.getString(i + 1));
        }

        locationsList.add(new Location(locationData));
      }

    } catch (SQLException e) {
      System.out.println("Query from locations table failed");
      throw (e);
    }

    return locationsList;
  }

  @Override
  public void addLocation(
      String inputID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName)
      throws SQLException {
    Location newLocation =
        new Location(inputID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
    statement.executeUpdate(
        String.format("INSERT INTO LOCATIONS VALUES (%s)", newLocation.toValuesString()));
  }

  @Override
  public void deleteLocation(String nodeID) throws SQLException {
    statement.executeUpdate(String.format("DELETE FROM LOCATIONS WHERE nodeID='%s'", nodeID));
  }

  @Override
  public void changeLocation(
      String nodeID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName)
      throws SQLException {
    // Check for valid data
    if (!isValidFloor(floor)) {
      System.out.println("Invalid floor entered");
      return;
    }
    if (!isValidNodeType(nodeType)) {
      System.out.println("Invalid node type entered");
      return;
    }

    statement.executeUpdate(
        String.format(
            "UPDATE LOCATIONS SET XCOORD = %d, YCOORD = %d, FLOOR = '%s', BUILDING = '%s', NODETYPE = '%s', LONGNAME = '%s', SHORTNAME = '%s' WHERE nodeID = '%s'",
            xCoord, yCoord, floor, building, nodeType, longName, shortName, nodeID));

    // Disabled automatic id updating per Matthew
    /*
    int newIDNumber = dbController.countFloorTypeFromTable(newFloor, newType) + 1;
    String newIDNumberString;
    if (0 <= newIDNumber && newIDNumber <= 9) {
        newIDNumberString = "00" + Integer.toString(newIDNumber);
    } else if (10 <= newIDNumber && newIDNumber <= 99) {
        newIDNumberString = "0" + Integer.toString(newIDNumber);
    } else if (100 <= newIDNumber && newIDNumber <= 999) {
        newIDNumberString = Integer.toString(newIDNumber);
    } else {
        newIDNumberString = "999";
    }
    String newID = "W" + newType + newIDNumberString + newFloor;
     */
    // Disabled automatic id updating per Matthew
    // locationList.get(modifyIndex).nodeID = newID;
    // Disabled automatic id updating per Matthew
    // dbController.updateNodeIdFromLocationTable(modifyID, newID);
  }

  @Override
  public void exportLocationCSV(String fileName) {
    File csvOutputFile = new File(String.valueOf(getClass().getResource("/" + fileName).getPath()));
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      ArrayList<Location> locationsList = getAllLocations();

      // print all locations
      for (Location l : locationsList) {
        pw.println();
        pw.print(l.toCSVString());
      }

    } catch (FileNotFoundException | SQLException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }

  private boolean isValidNodeType(String nodeType) {
    return nodeType.length() == 4 && nodeType.equals(nodeType.toUpperCase(Locale.ROOT));
  }

  private boolean isValidFloor(String floor) {
    return true;

    /*
    return floor.equals("01")
        || floor.equals("02")
        || floor.equals("03")
        || floor.equals("L1")
        || floor.equals("L2");

     */
  }

  public Location getLocation(String nodeID) throws SQLException {
    Location loc = null;
    ResultSet set =
        statement.executeQuery(
            String.format("SELECT * FROM LOCATIONS WHERE NODEID = '%s'", nodeID));
    set.next(); // bypasses column headers
    ArrayList<String> locationFields = new ArrayList<String>();
    for (int i = 0; i < set.getMetaData().getColumnCount(); i++) {
      locationFields.add(set.getString(i + 1));
    }
    loc = new Location(locationFields);
    return loc;
  }
}
