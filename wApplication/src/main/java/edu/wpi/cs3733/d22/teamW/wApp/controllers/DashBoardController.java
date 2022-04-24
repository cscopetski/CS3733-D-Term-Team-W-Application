package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.medEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashBoardController {
  @FXML
  public ProgressIndicator piBed;
  public ProgressIndicator piXray;
  public ProgressIndicator piPump;
  public ProgressIndicator piRec;
  public Label bedFloorClean;
  public Label bedFloorDirty;
  public Label xrayFloorClean;
  public Label xrayFloorDirty;
  public Label pumpFloorClean;
  public Label pumpFloorDirty;
  public Label recFloorClean;
  public Label recFloorDirty;
  double[] totalEquip = {0,0,0,0}; // 0 - Bed, 1 - XRay, 2 - Pump, 3 - Recliner
  ArrayList<MedEquip> totalEquipAL = new ArrayList<>();
  ArrayList<ArrayList<MedEquip>> equipByType = new ArrayList<>(); // 0 - Bed, 1 - XRay, 2 - Pump, 3 - Recliner
  ArrayList<ArrayList<ArrayList<MedEquip>>> equipAtFloor = new ArrayList<>(); // Floor > EquipType > MedEquip
  double[] cleanTotalEquip= {0,0,0,0}; // 0 - Bed, 1 - XRay, 2 - Pump, 3 - Recliner
  double[] cleanBed = {0,0,0,0,0}; // 0- F1, 1 - F2, 2 - F3, 3 - F4, 4 - F5
  double[] cleanXRay = {0,0,0,0,0}; // 0- F1, 1 - F2, 2 - F3, 3 - F4, 4 - F5
  double[] cleanPump = {0,0,0,0,0}; // 0- F1, 1 - F2, 2 - F3, 3 - F4, 4 - F5
  double[] cleanRec = {0,0,0,0,0}; // 0- F1, 1 - F2, 2 - F3, 3 - F4, 4 - F5
  private MedEquipManager equipController = MedEquipManager.getMedEquipManager();

  public void  initialize() throws SQLException {
    calculateProgressTotal();
    sortByType();


  }
  public void calculateProgressTotal() throws SQLException {

    piBed.setProgress(cleanTotalEquip[0]/totalEquip[0]);
    piXray.setProgress(cleanTotalEquip[1]/totalEquip[1]);
    piPump.setProgress(cleanTotalEquip[2]/totalEquip[2]);
    piRec.setProgress(cleanTotalEquip[3]/totalEquip[3]);

  }
  public void sortByType() throws SQLException {
    ArrayList<MedEquip> eqList = equipController.getAllMedEquip();
    ArrayList<MedEquip> beds = new ArrayList<>();
    ArrayList<MedEquip> xrays = new ArrayList<>();
    ArrayList<MedEquip> pumps = new ArrayList<>();
    ArrayList<MedEquip> recs = new ArrayList<>();
    totalEquipAL = eqList;
    for (int j = 0; j < eqList.size(); j++) {
      switch(eqList.get(j).getType().getString()){
        case "Recliners":
          recs.add(eqList.get(j));
          totalEquip[3]++;
          if(eqList.get(j).getStatus().getString().equals("Clean")){
            cleanTotalEquip[3] ++;
            cleanRec[Character.getNumericValue(eqList.get(j).getNodeID().charAt((eqList.get(j).getNodeID().length() -1)))] ++;
          }
          break;
        case "Infusion Pump":
          pumps.add(eqList.get(j));
          totalEquip[2]++;
          if(eqList.get(j).getStatus().getString().equals("Clean")){
            cleanTotalEquip[2] ++;
            cleanPump[Character.getNumericValue(eqList.get(j).getNodeID().charAt((eqList.get(j).getNodeID().length() -1)))] ++;
          }
          break;
        case "X-Ray":
          xrays.add(eqList.get(j));
          totalEquip[1]++;
          if(eqList.get(j).getStatus().getString().equals("Clean")){
            cleanTotalEquip[1] ++;
            cleanXRay[Character.getNumericValue(eqList.get(j).getNodeID().charAt((eqList.get(j).getNodeID().length() -1)))] ++;
          }
          break;
        case "Bed":
          beds.add(eqList.get(j));
          totalEquip[0]++;
          if(eqList.get(j).getStatus().getString().equals("Clean")){
            cleanTotalEquip[0] ++;
            cleanBed[Character.getNumericValue(eqList.get(j).getNodeID().charAt((eqList.get(j).getNodeID().length() -1)))] ++;
          }
          break;
        default:
          break;
      }


    }
    equipByType.add(beds);
    equipByType.add(xrays);
    equipByType.add(pumps);
    equipByType.add(recs);
    summaryEquipmentEachFloor();

  }
  public void displaySummary(int floor){
   // bedFloorClean.setText(String.valueOf(cleanBed[floor-1]));
   // bedFloorDirty.setText(String.valueOf(equipAtFloor.get(floor-1).get(0).size() - cleanBed[floor-1]));

  }
  public void summaryEquipmentEachFloor(){
    ArrayList<ArrayList<MedEquip>> f1 = new ArrayList<>();
    ArrayList<ArrayList<MedEquip>> f2 = new ArrayList<>();
    ArrayList<ArrayList<MedEquip>> f3 = new ArrayList<>();
    ArrayList<ArrayList<MedEquip>> f4 = new ArrayList<>();
    ArrayList<ArrayList<MedEquip>> f5 = new ArrayList<>();

    ArrayList<MedEquip> bed1 = new ArrayList<>();
    ArrayList<MedEquip> xray1 = new ArrayList<>();
    ArrayList<MedEquip> p1 = new ArrayList<>();
    ArrayList<MedEquip> r1 = new ArrayList<>();
    f1.add(bed1);
    f1.add(xray1);
    f1.add(p1);
    f1.add(r1);

    ArrayList<MedEquip> bed2 = new ArrayList<>();
    ArrayList<MedEquip> xray2 = new ArrayList<>();
    ArrayList<MedEquip> p2 = new ArrayList<>();
    ArrayList<MedEquip> r2 = new ArrayList<>();
    f2.add(bed2);
    f2.add(xray2);
    f2.add(p2);
    f2.add(r2);

    ArrayList<MedEquip> bed3 = new ArrayList<>();
    ArrayList<MedEquip> xray3 = new ArrayList<>();
    ArrayList<MedEquip> p3 = new ArrayList<>();
    ArrayList<MedEquip> r3 = new ArrayList<>();
    f3.add(bed3);
    f3.add(xray3);
    f3.add(p3);
    f3.add(r3);

    ArrayList<MedEquip> bed4 = new ArrayList<>();
    ArrayList<MedEquip> xray4 = new ArrayList<>();
    ArrayList<MedEquip> p4 = new ArrayList<>();
    ArrayList<MedEquip> r4 = new ArrayList<>();
    f4.add(bed4);
    f4.add(xray4);
    f4.add(p4);
    f4.add(r4);

    ArrayList<MedEquip> bed5 = new ArrayList<>();
    ArrayList<MedEquip> xray5 = new ArrayList<>();
    ArrayList<MedEquip> p5 = new ArrayList<>();
    ArrayList<MedEquip> r5 = new ArrayList<>();
    f5.add(bed5);
    f5.add(xray5);
    f5.add(p5);
    f5.add(r5);

    for (int i = 0; i < equipByType.size(); i++) {
      for (int j = 0; j < equipByType.get(i).size(); j++) {
        MedEquip currentEquip = equipByType.get(i).get(j);
        int currentFloor = Character.getNumericValue(currentEquip.getNodeID().charAt(currentEquip.getNodeID().length()-1));
        switch(currentFloor){
          case 1:
            f1.get(i).add(currentEquip);
            break;
          case 2:
            f2.get(i).add(currentEquip);
            break;
          case 3:
            f3.get(i).add(currentEquip);
            break;
          case 4:
            f4.get(i).add(currentEquip);
            break;
          case 5:
            f5.get(i).add(currentEquip);
            break;
          default:
            break;
        }
    }
    }
    equipAtFloor.add(f1);
    equipAtFloor.add(f2);
    equipAtFloor.add(f3);
    equipAtFloor.add(f4);
    equipAtFloor.add(f5);

  }
  @FXML
   void F5Click(ActionEvent actionEvent) {
    displaySummary(5);
  }
  @FXML
   void F4Click(ActionEvent actionEvent) {
    displaySummary(4);
  }
  @FXML
   void F3Click(ActionEvent actionEvent) {
    displaySummary(3);
  }
  @FXML
   void F2Click(ActionEvent actionEvent) {
    displaySummary(2);
  }
  @FXML
   void F1Click(ActionEvent actionEvent) {
    displaySummary(1);
  }

  public void LL1Click(ActionEvent actionEvent) {
  }

  public void LL2Click(ActionEvent actionEvent) {
  }
}