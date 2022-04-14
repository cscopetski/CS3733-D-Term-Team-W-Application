package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.LocationDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import java.sql.SQLException;
import java.util.ArrayList;

public class LocationManager {
  private LocationDao ldi;

  private static LocationManager locationManager = new LocationManager();

  public static LocationManager getLocationManager() {
    return locationManager;
  }

  private LocationManager() {}

  public void setLocationDao(LocationDao ldi) {
    this.ldi = ldi;
  }

  public void changeLocation(
      String inputID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName)
      throws SQLException {
    ldi.changeLocation(inputID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
  }

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
    ldi.addLocation(inputID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
  }

  public void deleteLocation(String nodeID) throws SQLException {
    ldi.deleteLocation(nodeID);
  }

  public ArrayList<Location> getAllLocations() throws SQLException {
    return ldi.getAllLocations();
  }

  public void clearLocations() throws SQLException {
    for (int i = 0; i < getAllLocations().size(); i++) {
      if (getAllLocations().get(i).getNodeID().equalsIgnoreCase("HOLD")) {

      } else {
        deleteLocation(getAllLocations().get(i).getNodeID());
        i--;
      }
    }
  }

  public void exportLocationsCSV(String filename) {
    ldi.exportLocationCSV(filename);
  }

  public Location getLocation(String NodeID) throws SQLException {
    return ldi.getLocation(NodeID);
  }
}
