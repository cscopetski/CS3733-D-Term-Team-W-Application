package edu.wpi.cs3733.d22.teamW.wDB;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class LocationManagerTest {

  @Test
  void changeLocation() {
    assertTrue(false);
  }

  @Test
  void addLocation() {
    DBController.getDBController();
    try {
      LocationManager lc = new LocationManager(new LocationDaoImpl());
      Location addLoc =
          new Location("newLoc", 10, 10, "01", "testBuilding", "HALL", "test hall", "test");
      lc.addLocation("newLoc", 10, 10, "01", "testBuilding", "HALL", "test hall", "test");
      assertEquals(addLoc, lc.getAllLocations().get(0));
    } catch (SQLException e) {
      e.printStackTrace();
      fail();
    }
    fail();
  }

  @Test
  void deleteLocation() {}

  @Test
  void getAllLocations() {}

  @Test
  void clearLocations() {}

  @Test
  void exportLocationsCSV() {}
}
