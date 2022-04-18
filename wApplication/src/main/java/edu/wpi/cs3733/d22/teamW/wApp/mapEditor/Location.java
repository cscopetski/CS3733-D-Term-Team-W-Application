package edu.wpi.cs3733.d22.teamW.wApp.mapEditor;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Location {
  SimpleStringProperty NodeID = new SimpleStringProperty();
  SimpleIntegerProperty XCoord = new SimpleIntegerProperty();
  SimpleIntegerProperty YCoord = new SimpleIntegerProperty();
  String floor;
  String building;
  String nodeType;
  String longName;
  String shortName;
  edu.wpi.cs3733.d22.teamW.wDB.entity.Location ogLoc;

  public Location(edu.wpi.cs3733.d22.teamW.wDB.entity.Location Loc) {
    NodeID.set(Loc.getNodeID());
    XCoord.set(Loc.getxCoord());
    YCoord.set(Loc.getyCoord());
    floor = Loc.getFloor();
    building = Loc.getBuilding();
    nodeType = Loc.getNodeType();
    longName = Loc.getLongName();
    shortName = Loc.getShortName();
    ogLoc = Loc;
  }

  public Location(String nodeID, Integer xCoord, Integer yCoord) {
    this.NodeID.set(nodeID);
    this.XCoord.set(xCoord);
    this.YCoord.set(yCoord);
  }

  public String getNodeID() {
    return NodeID.get();
  }

  public Integer getXCoord() {
    return XCoord.get();
  }

  public Integer getYCoord() {
    return YCoord.get();
  }

  public void setNodeID(String nodeID) {
    this.NodeID.set(nodeID);
  }

  public void setXCoord(int XCoord) {
    this.XCoord.set(XCoord);
  }

  public void setYCoord(int YCoord) {
    this.YCoord.set(YCoord);
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public void setOgLoc(edu.wpi.cs3733.d22.teamW.wDB.entity.Location ogLoc) {
    this.ogLoc = ogLoc;
  }
}
