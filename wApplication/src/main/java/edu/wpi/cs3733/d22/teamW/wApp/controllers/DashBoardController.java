package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.medEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import javafx.event.ActionEvent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.util.ArrayList;

public class DashBoardController {
  
  public ProgressIndicator piBed;
  public ProgressIndicator piXray;
  public ProgressIndicator piPump;
  public ProgressIndicator piRec;
  double[] totalEquip = {0,0,0,0}; // 0 - Bed, 1 - XRay, 2 - Pump, 3 - Recliner
  double[] cleanTotalEquip= {0,0,0,0}; // 0 - Bed, 1 - XRay, 2 - Pump, 3 - Recliner
  private MedEquipManager equipController = MedEquipManager.getMedEquipManager();

  public void  initialize() throws SQLException {
    calculateProgressTotal();
    piBed.getStyleClass().add("button");
  }

  public void calculateProgressTotal() throws SQLException {
    ArrayList<MedEquip> eqList = equipController.getAllMedEquip();
    for (int j = 0; j < eqList.size(); j++) {
      switch(eqList.get(j).getType().getAbb()){
        case "REC":
          totalEquip[3]++;
          if(eqList.get(j).getStatus().getString().equals("Clean")){
            cleanTotalEquip[3] ++;
          }
        case "INP":
          totalEquip[2]++;
          if(eqList.get(j).getStatus().getString().equals("Clean")){
            cleanTotalEquip[2] ++;
          }
        case "XRY":
          totalEquip[1]++;
          if(eqList.get(j).getStatus().getString().equals("Clean")){
            cleanTotalEquip[1] ++;
          }
        case "BED":
          totalEquip[0]++;
          if(eqList.get(j).getStatus().getString().equals("Clean")){
            cleanTotalEquip[0] ++;
          }
      }
    }
    piBed.setProgress(cleanTotalEquip[0]/totalEquip[0]);
    piXray.setProgress(cleanTotalEquip[1]/totalEquip[1]);
    piPump.setProgress(cleanTotalEquip[2]/totalEquip[2]);
    piRec.setProgress(cleanTotalEquip[3]/totalEquip[3]);

  }

  public void F5Click(ActionEvent actionEvent) {
  }

  public void F4Click(ActionEvent actionEvent) {
  }

  public void F3Click(ActionEvent actionEvent) {
  }

  public void F2Click(ActionEvent actionEvent) {
  }

  public void F1Click(ActionEvent actionEvent) {
  }

  public void LL1Click(ActionEvent actionEvent) {
  }

  public void LL2Click(ActionEvent actionEvent) {
  }
}
