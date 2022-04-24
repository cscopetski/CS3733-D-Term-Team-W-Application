package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.medEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class EquipmentListController implements Initializable {
  @FXML private TableView<medEquip> eqTab;
  private medEquip selected;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    onLoad();
  }

  public void onLoad() {
    ArrayList<medEquip> eqList = new ArrayList<>();
    ArrayList<MedEquip> equips;
    try {
      equips = MedEquipManager.getMedEquipManager().getAllMedEquip();
    } catch (SQLException e) {
      e.printStackTrace();
      return;
    }
    for (int i = 0; i < equips.size(); i++) {
      eqList.add(
          new medEquip(
              equips.get(i).getMedID(), equips.get(i).getStatus(), equips.get(i).getNodeID()));
    }
    eqTab.getItems().clear();
    eqTab.getItems().addAll(eqList);
  }

  public void setSelected() {
    selected = eqTab.getSelectionModel().getSelectedItem();
  }

  public void markClean() throws Exception {
    MedEquipManager.getMedEquipManager().markClean(selected.getMedID(), selected.getFloor());
    onLoad();
  }

  public void markInUse() throws Exception {
    MedEquipManager.getMedEquipManager().markInUse(selected.getMedID(), selected.getFloor());
    onLoad();
  }

  public void markDirty() throws Exception {
    MedEquipManager.getMedEquipManager().markDirty(selected.getMedID(), selected.getFloor());
    onLoad();
  }
}
