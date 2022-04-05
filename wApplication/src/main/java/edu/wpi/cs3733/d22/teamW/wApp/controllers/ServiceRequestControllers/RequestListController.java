package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.MedicalEquipmentSR;
import edu.wpi.cs3733.d22.teamW.wDB.MedEquipRequest;
import edu.wpi.cs3733.d22.teamW.wDB.Request;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class RequestListController extends LoadableController {
  @FXML private TableView<MedicalEquipmentSR> table;

  private ArrayList<MedicalEquipmentSR> sr = new ArrayList<>();

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.RequestList;
  }

  public void onLoad() {
    ArrayList<Request> requests = RequestFactory.getRequestFactory().getAllRequests();
    sr.clear();
    for (int i = 0; i < requests.size(); i++) {
      Request r = requests.get(i);
      if (MedEquipRequest.class.equals(r.getClass())) {
        MedEquipRequest mer = (MedEquipRequest) r;
        sr.add(new MedicalEquipmentSR(mer));
      }
    }

    table.getItems().clear();
    table.getItems().addAll(sr);

    table.getSelectionModel().clearSelection();
  }

  public void onUnload() {}
}
