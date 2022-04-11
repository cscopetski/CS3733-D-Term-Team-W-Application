package edu.wpi.cs3733.d22.teamW.wDB.entity;

public class Location extends Entity {

  // Test
  private String nodeID;
  private Integer xCoord;
  private Integer yCoord;
  private String floor;
  private String building;
  private String nodeType;
  private String longName;
  private String shortName;

  public Location() {
    nodeID = null;
    xCoord = null;
    yCoord = null;
    floor = null;
    building = null;
    nodeType = null;
    longName = null;
    shortName = null;
  }

  public Location(
      String nodeID,
      Integer xCoord,
      Integer yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
  }

  /**
   * This function was made so that we did not have to use two scanners to read from the CSV Takes
   * an array of string and makes the location based off of the index of values in the array
   * Author:Caleb
   *
   * @param token
   */
  public Location(String[] token) {
    this.nodeID = token[0];

    // If the xcoord and ycoord are null, catch error
    try {
      this.xCoord = Integer.parseInt(token[1]);
      this.yCoord = Integer.parseInt(token[2]);
    } catch (NumberFormatException e) {
      this.xCoord = null;
      this.yCoord = null;
    }

    this.floor = token[3];
    this.building = token[4];
    this.nodeType = token[5];
    this.longName = token[6];
    this.shortName = token[7];
  }

  /**
   * Used for printing a location object
   *
   * @return A formatted string containing the nodeID and all the attributes associated with a
   *     location node
   */
  @Override
  public String toString() {
    return String.format(
        "Location{nodeID='%s', xcoord=%d, ycoord=%d, floor='%s', building='%s', nodeType='%s', longName='%s', shortName='%s'}",
        nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
  }

  /**
   * Takes all fields from a Location and puts it into format to be added into database Author:
   * Hasan
   *
   * @return
   */
  public String toValuesString() {
    return String.format(
        "'%s', %d, %d, '%s', '%s', '%s', '%s', '%s'",
        nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
  }

  /**
   * takes a location and puts all fields into one string with commas separating each field (CSV
   * format) Author Caleb
   *
   * @return list of all string seperated by commas for CSV
   */
  @Override
  public String toCSVString() {
    return String.format(
        "%s,%d,%d,%s,%s,%s,%s,%s",
        nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
  }

  @Override
  public boolean equals(Object other) {
    if (other.getClass().equals(Location.class)) {
      return this.nodeID.equals(((Location) other).nodeID);
    }
    return false;
  }

  public String getNodeID() {
    return this.nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public Integer getxCoord() {
    return xCoord;
  }

  public void setxCoord(Integer xCoord) {
    this.xCoord = xCoord;
  }

  public Integer getyCoord() {
    return yCoord;
  }

  public void setyCoord(Integer yCoord) {
    this.yCoord = yCoord;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getNodeType() {
    return nodeType;
  }

  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }
}
