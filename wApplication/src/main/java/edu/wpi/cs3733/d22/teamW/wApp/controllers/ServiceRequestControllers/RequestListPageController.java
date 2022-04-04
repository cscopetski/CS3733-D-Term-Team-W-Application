package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import javafx.fxml.FXML;

import javax.swing.table.TableColumn;

public class RequestListPageController extends LoadableController {
    @FXML TableColumn reqID;
    @FXML TableColumn reqType;
    @FXML TableColumn employeeName;
    @FXML TableColumn numQueue;

    @Override
    protected SceneManager.Scenes GetSceneType() {
        return SceneManager.Scenes.RequestList;
    }

    public void onLoad() {

    }

    public void onUnload() {

    }

}
