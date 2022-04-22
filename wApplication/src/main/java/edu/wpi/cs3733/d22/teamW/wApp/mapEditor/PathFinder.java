package edu.wpi.cs3733.d22.teamW.wApp.mapEditor;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import java.sql.SQLException;
import java.util.ArrayList;

public class PathFinder {

  // variables:
  private int radius = 10;
  LocationManager LCM = LocationManager.getLocationManager();
  ArrayList<edu.wpi.cs3733.d22.teamW.wDB.entity.Location> locations = new ArrayList<>();

  public PathFinder() {
    try {
      locations = LCM.getAllLocations();
    } catch (SQLException e) {
      System.out.println("Could not unearth the list of Locations");
    }
  }

  // Returns the shortest path between two ENTITY locations given start and end
  // connects the dots using existing locations
  //
  // To Do: add start and end to an array of path nodes
  //
  public ArrayList<Location> findPath(Location start, Location end) {

    double score = 2000; // distance between two locations
    Location nextPath = new Location();
    ArrayList<edu.wpi.cs3733.d22.teamW.wDB.entity.Location> path = new ArrayList<>();

    for (Location l : locations) {
      // create open set frontier of search using "start":
      ArrayList<edu.wpi.cs3733.d22.teamW.wDB.entity.Location> openSet = getFrontier(start);

      // Explanation:
      // enter openSet:
      //   iterate, finding and comparing scores:
      //     remove from openSet when score is not the lowest
      //
      //   When lowest score found:
      //   add location to path
      //     check if location == end
      //       return path
      //     else make location new start --> keep goin

      for (Location o : openSet) {
        double currScore = distance(start, o);
        if (currScore < score) {
          score = currScore;
          nextPath = o;
        } else {
          openSet.remove(o);
        }
      }

      path.add(nextPath);
      if (nextPath == end) {
        return path; // END FOUND
      }
      start = nextPath;
    }
    return path;
  }

  private ArrayList<Location> getFrontier(Location start) {
    ArrayList<edu.wpi.cs3733.d22.teamW.wDB.entity.Location> set = new ArrayList<>();

    for (Location l : locations) {
      if ((l.getxCoord() - start.getxCoord()) <= radius && l.getFloor().equals(start.getFloor())) {
        set.add(l);
      }
    }

    return set;
  }

  private double distance(Location a, Location b) {
    return Math.sqrt(
        (Math.pow((Math.abs(b.getyCoord() - a.getyCoord())), 2))
            + (Math.pow((Math.abs(b.getxCoord() - a.getxCoord())), 2)));
  }
}
