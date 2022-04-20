package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.medEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class EquipmentListController extends LoadableController {
  @FXML private TableView<medEquip> eqTab;
  private medEquip selected;
  private MedEquipManager medEquipManager = MedEquipManager.getMedEquipManager();

  public void setSelected(MouseEvent mouseEvent) {
    selected = eqTab.getSelectionModel().getSelectedItem();
  }

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.EquipList;
  }

  @Override
  public void onLoad() throws SQLException {
    ArrayList<medEquip> eqList = new ArrayList<>();
    ArrayList<MedEquip> equips = medEquipManager.getAllMedEquip();
    for (int i = 0; i < equips.size(); i++) {
      eqList.add(
          new medEquip(
              equips.get(i).getMedID(), equips.get(i).getStatus(), equips.get(i).getNodeID()));
    }
    eqTab.getItems().clear();
    eqTab.getItems().addAll(eqList);
  }

  @Override
  public void onUnload() {}

  public void markClean(ActionEvent actionEvent) throws Exception {
    medEquipManager.markClean(selected.getMedID(), selected.getFloor());
    onLoad();
  }

  public void markInUse(ActionEvent actionEvent) throws Exception {
    medEquipManager.markInUse(selected.getMedID(), selected.getFloor());
    onLoad();
  }

  public void markDirty(ActionEvent actionEvent) throws Exception {
    medEquipManager.markDirty(selected.getMedID(), selected.getFloor());
    onLoad();
  }
}
