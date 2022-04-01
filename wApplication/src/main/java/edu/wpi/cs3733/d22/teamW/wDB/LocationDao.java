package edu.wpi.cs3733.d22.teamW.wDB;

import java.util.ArrayList;

public interface LocationDao {

  ArrayList<Location> getAllLocations();

  void changeLocation(String nodeID, String newFloor, String newType);

  void addLocation(String nodeID);

  void deleteLocation(String nodeID);

  void exportLocationCSV(String fileName);
}
