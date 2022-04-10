package edu.wpi.cs3733.d22.teamW.wApp.controllers;

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
}
