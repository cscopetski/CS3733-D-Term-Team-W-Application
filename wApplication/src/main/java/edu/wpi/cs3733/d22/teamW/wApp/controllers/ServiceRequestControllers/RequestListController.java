package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.*;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LabServiceRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RequestListController extends LoadableController {
  @FXML public RequestTable rt;

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.RequestList;
  }

  public void onLoad() {
    rt.setColumnWidth("Request Type", 130);
    rt.setColumnWidth("Employee ID", 100);
    rt.setColumnWidth("Status", 100);
    rt.setEditable(false);
  }

  @Override
  public void onUnload() {}
    
/*
  public void cancel(ActionEvent actionEvent) throws SQLException {
    if (rt.getSelection().getRequestType().equals(RequestType.MedicalEquipmentRequest)) {
      MedEquipRequestManager.getMedEquipRequestManager()
          .cancel(rt.getSelection().getREQUEST().getRequestID());
    } else if (rt.getSelection().getRequestType().equals(RequestType.LabServiceRequest)) {
      LabServiceRequestManager.getLabServiceRequestManager()
          .cancel(rt.getSelection().getREQUEST().getRequestID());
    } else if (rt.getSelection().getRequestType().equals(RequestType.MedicineDelivery)) {
      MedRequestManager.getMedRequestManager()
          .cancel(rt.getSelection().getREQUEST().getRequestID());
    } else if (rt.getSelection().getRequestType().equals(RequestType.LanguageInterpreter)) {

    } else if (rt.getSelection().getRequestType().equals(RequestType.SecurityService)) {

    }
    System.out.println(rt.getSelection().toString() + "cancelled");
    rt.refresh();
  }

  */
}
