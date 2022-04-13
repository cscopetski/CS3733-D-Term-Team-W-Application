package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.*;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import javafx.event.ActionEvent;
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
    rt.setColumnWidth("Employee ID", 100);
    rt.setColumnWidth("Status", 100);
    rt.setEditable(false);
    moreInfo.setText("Select a request to view details.");

    rt.getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              SR request = rt.getSelection();
              try {
                moreInfo.setText(request.getFormattedInfo());
              } catch (SQLException e) {
                e.printStackTrace();
                moreInfo.setText("Error loading request details.");
              }
            });

    try {
      rt.setItems(RequestFactory.getRequestFactory().getAllRequests());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onUnload() {}

  public void cancel(ActionEvent actionEvent) throws SQLException {
    /*if (rt.getSelection().getRequestType().equals(RequestType.MedicalEquipmentRequest)) {
      MedEquipRequestManager.getMedEquipRequestManager()
          .cancel(rt.getSelection().getRequestID());
    } else if (rt.getSelection().getRequestType().equals(RequestType.LabServiceRequest)) {
      LabServiceRequestManager.getLabServiceRequestManager()
          .cancel(rt.getSelection().getRequestID());
    } else if (rt.getSelection().getRequestType().equals(RequestType.MedicineDelivery)) {
      MedRequestManager.getMedRequestManager()
          .cancel(rt.getSelection().getRequestID());
    } else if (rt.getSelection().getRequestType().equals(RequestType.LanguageInterpreter)) {

    } else if (rt.getSelection().getRequestType().equals(RequestType.SecurityService)) {

    }
    System.out.println(rt.getSelection().toString() + "cancelled");
    rt.refresh();
    */
  }
}
