package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LocationDao {

  ArrayList<Location> getAllLocations();

  void changeLocation(String inputID, int xCoord, int yCoord, String floor, String building, String nodeType, String longName, String shortName) throws SQLException;

  void addLocation(String inputID, int xCoord, int yCoord, String floor, String building, String nodeType, String longName, String shortName) throws SQLException;

  void deleteLocation(String nodeID) throws SQLException;

  void exportLocationCSV(String fileName);
}
