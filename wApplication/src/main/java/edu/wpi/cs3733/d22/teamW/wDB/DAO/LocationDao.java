package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import java.sql.SQLException;
import java.util.ArrayList;

public interface LocationDao {

  ArrayList<Location> getAllLocations() throws SQLException;

  void changeLocation(
      String inputID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName)
      throws SQLException;

  void addLocation(
      String inputID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName)
      throws SQLException;

  void deleteLocation(String nodeID) throws SQLException;

  void exportLocationCSV(String fileName);

  Location getLocation(String nodeID) throws SQLException;
}
