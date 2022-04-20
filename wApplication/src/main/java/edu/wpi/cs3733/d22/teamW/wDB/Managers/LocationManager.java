package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.LocationDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class LocationManager {
  private LocationDao ldi;

  private final String noneLocationID = "NONE";

  private static LocationManager locationManager = new LocationManager();

  public static LocationManager getLocationManager() {
    return locationManager;
  }

  public ArrayList<Location> getLocationClean() throws SQLException {
    return ldi.getAllCleanLocations();
  }

  private LocationManager() {}

  public String getNoneLocation() {
    return this.noneLocationID;
  }

  public void setLocationDao(LocationDao ldi) {
    this.ldi = ldi;
  }

  public void changeLocation(Location location) throws SQLException {
    ldi.changeLocation(location);
  }

  public void addLocation(Location location) throws SQLException {
    ldi.addLocation(location);
  }

  public void deleteLocation(String nodeID) throws Exception {
    MedEquipManager.getMedEquipManager().updateMedEquipAtLocation(nodeID);
    MedRequestManager.getMedRequestManager().updateReqAtLocation(nodeID);
    MedEquipRequestManager.getMedEquipRequestManager().updateReqAtLocation(nodeID);
    LabServiceRequestManager.getLabServiceRequestManager().updateReqAtLocation(nodeID);
    CleaningRequestManager.getCleaningRequestManager().updateReqAtLocation(nodeID);
    ldi.deleteLocation(nodeID);
  }

  public ArrayList<Location> getAllLocations() throws SQLException {
    return ldi.getAllLocations();
  }

  public void clearLocations() throws Exception {
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

  public void exportLocationsToChosen(File file) {
    try (PrintWriter pw = new PrintWriter(file)) {
      // print Table headers
      pw.print(file);

      ArrayList<Location> locationsList = getAllLocations();

      // print all locations
      for (Location l : locationsList) {
        pw.println();
        pw.print(l.toCSVString());
      }

    } catch (FileNotFoundException | SQLException e) {

      System.out.println(String.format("Error Exporting to File %s", file.getName()));
      e.printStackTrace();
    }
  }

  public Location getLocation(String NodeID) throws SQLException {
    return ldi.getLocation(NodeID);
  }
}
