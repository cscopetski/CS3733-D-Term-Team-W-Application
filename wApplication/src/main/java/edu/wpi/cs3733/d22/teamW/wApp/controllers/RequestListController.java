package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.FilterControl;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.*;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.*;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wMid.Account;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class RequestListController extends LoadableController {
  @FXML public RequestTable rt;
  @FXML public TextArea moreInfo;
  @FXML public AutoCompleteInput equipmentSelection;
  @FXML public HBox selectionButtons;
  @FXML public FilterControl<RequestType> filter;

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.RequestList;
  }

  @Override
  public void initialize(URL location, ResourceBundle rb) {
    super.initialize(location, rb);

    rt.getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection == null) {
                moreInfo.setText("Select a request to view details.");
              }
              SR request = rt.getSelection();
              if (request != null) {
                try {
                  moreInfo.setText(request.getFormattedInfo());
                } catch (SQLException e) {
                  e.printStackTrace();
                  moreInfo.setText("Error loading request details.");
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }

              selectionButtons.setVisible(newSelection != null);
            });

    filter.loadValues(RequestType.values());
    filter.addValuesListener(c -> resetItems());
    resetItems();
  }

  private void resetItems() {
    try {
      rt.setItems(
          RequestFacade.getRequestFacade()
              .getRequests(filter.getEnabledValues().toArray(new RequestType[] {})));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void onLoad() {
    /*rt.setColumnWidth("Request ID", 60);
    rt.setColumnWidth("Request Type", 130);
    rt.setColumnWidth("Employee Name", 140);
    rt.setColumnWidth("Status", 80);
    rt.setColumnWidth("Location", 80);
    rt.setColumnWidth("Created", 145);
    rt.setColumnWidth("Last Updated", 145);*/
    rt.distributeColumnWidths();
    rt.setEditable(true);
    moreInfo.setText("Select a request to view details.");
  }

  @Override
  public void onUnload() {}

  public void cancel(ActionEvent actionEvent) {
    if (Account.getInstance().getEmployee().getType().getAccessLevel() == 5) {
      try {
        RequestFacade.getRequestFacade()
            .cancelRequest(rt.getSelection().getRequestID(), rt.getSelection().getRequestType());
      } catch (CannotCancel c) {
        Alert alert =
            new Alert(
                Alert.AlertType.WARNING,
                "Cannot Cancel A Request That Is Complete!",
                ButtonType.OK);
        alert.showAndWait();
      } catch (NonExistingRequestID r) {
        Alert alert =
            new Alert(Alert.AlertType.WARNING, "RequestID Does Not Exist!", ButtonType.OK);
        alert.showAndWait();
      } catch (Exception s) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Error", ButtonType.OK);
        alert.showAndWait();
      }
      resetItems();
    } else {
      Alert alert =
          new Alert(
              Alert.AlertType.WARNING,
              "Sorry only admin or the person create this request can cancel it!",
              ButtonType.OK);
      alert.showAndWait();
    }
  }

  public void confirm(ActionEvent event) {
    try {
      RequestFacade.getRequestFacade()
          .completeRequest(
              rt.getSelection().getRequestID(),
              rt.getSelection().getRequestType(),
              rt.getSelection().getNodeID());
    } catch (CannotComplete c) {
      Alert alert =
          new Alert(
              Alert.AlertType.WARNING,
              "Cannot Complete A Request That Is Not Started!",
              ButtonType.OK);
      alert.showAndWait();
    } catch (NonExistingRequestID r) {
      Alert alert = new Alert(Alert.AlertType.WARNING, "RequestID Does Not Exist!", ButtonType.OK);
      alert.showAndWait();
    } catch (Exception s) {
      Alert alert = new Alert(Alert.AlertType.WARNING, "Error", ButtonType.OK);
      alert.showAndWait();
    }
    resetItems();
  }

  public void clearSelection() {
    rt.getSelectionModel().clearSelection();
    selectionButtons.setVisible(false);
    resetItems();
  }

  public void start() {
    try {
      RequestFacade.getRequestFacade()
          .startRequest(rt.getSelection().getRequestID(), rt.getSelection().getRequestType());
    } catch (NoAvailableEquipment e) {
      Alert alert =
          new Alert(
              Alert.AlertType.WARNING,
              "Equipment Not Available: "
                  + ((MedicalEquipmentSR) rt.getSelection())
                      .getOriginal()
                      .getItemType()
                      .getString(),
              ButtonType.OK);
      alert.showAndWait();
    } catch (CannotStart c) {
      Alert alert =
          new Alert(
              Alert.AlertType.WARNING,
              "Cannot Start A Request That Is Not In Queue!",
              ButtonType.OK);
      alert.showAndWait();
    } catch (NonExistingRequestID r) {
      Alert alert = new Alert(Alert.AlertType.WARNING, "RequestID Does Not Exist!", ButtonType.OK);
      alert.showAndWait();
    } catch (Exception s) {
      Alert alert = new Alert(Alert.AlertType.WARNING, "Error", ButtonType.OK);
      alert.showAndWait();
    }

    resetItems();
  }

  public void requeue(ActionEvent actionEvent) {
    try {
      RequestFacade.getRequestFacade()
          .requeueRequest(rt.getSelection().getRequestID(), rt.getSelection().getRequestType());
    } catch (CannotRequeue c) {
      Alert alert =
          new Alert(
              Alert.AlertType.WARNING, "Cannot Requeue A Request That Is Complete!", ButtonType.OK);
      alert.showAndWait();
    } catch (NonExistingRequestID r) {
      Alert alert = new Alert(Alert.AlertType.WARNING, "RequestID Does Not Exist!", ButtonType.OK);
      alert.showAndWait();
    } catch (Exception s) {
      Alert alert = new Alert(Alert.AlertType.WARNING, "Error", ButtonType.OK);
      alert.showAndWait();
    }
    resetItems();
  }
}
