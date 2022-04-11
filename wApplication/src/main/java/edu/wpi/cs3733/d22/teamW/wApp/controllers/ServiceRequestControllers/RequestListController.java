package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.SR;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class RequestListController extends LoadableController {
  @FXML public RequestTable rt;
  @FXML public TextArea moreInfo;

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.RequestList;
  }

  public void onLoad() {
    rt.setColumnWidth("Request Type", 130);
    rt.setColumnWidth("Employee Name", 150);
    rt.setColumnWidth("Status", 60);

    moreInfo.setText("Select a request to view details.");

    rt.getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              SR request = rt.getSelection();
              moreInfo.setText(request.getFormattedInfo());
            });
  }

  @Override
  public void onUnload() {}
}
