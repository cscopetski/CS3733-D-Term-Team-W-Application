package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;

import java.util.ArrayList;

public class locationMedEquip {

    String NodeID;
    ArrayList<MedEquip> equipList;

    public locationMedEquip(String loc) {
        loc = this.NodeID;
    }

    public void setNodeID(String nodeID){
        nodeID = this.NodeID;
    }
    public String getNodeID(){
        return NodeID;
    }
    public void setEquipList(ArrayList<MedEquip> list){
        list = this.equipList;
    }
    public void addEquipList(MedEquip medEquip){
        equipList.add(medEquip);
    }
    public ArrayList<MedEquip> getEquipList(){
        return equipList;
    }



}
