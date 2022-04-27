package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.AccountManager;
import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.FilterControl;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.*;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.*;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class RequestListController implements Initializable {
  @FXML public RequestTable rt;
  @FXML public TextArea moreInfo;
  @FXML public HBox selectionButtons;
  @FXML public FilterControl<RequestType> filter;
  @FXML public CheckBox emergencyFilter;

  @Override
  public void initialize(URL location, ResourceBundle rb) {
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
    emergencyFilter.selectedProperty().addListener(c -> resetItems());
    onLoad();
    PageManager.getInstance().attachOnLoad(PageManager.Pages.RequestList, this::onLoad);
  }

  private void resetItems() {
    try {
      ArrayList<Request> items = new ArrayList<Request>();

      if(emergencyFilter.isSelected()){
        for (Request r:RequestFacade.getRequestFacade()
                .getRequests(filter.getEnabledValues().toArray(new RequestType[] {}))) {
          if(r.getEmergency() == 1){
            items.add(r);
          }
        }
      }
      else{
        items = RequestFacade.getRequestFacade()
                .getRequests(filter.getEnabledValues().toArray(new RequestType[] {}));
      }

      rt.setItems(items);

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
    resetItems();
  }

  public void cancel(ActionEvent actionEvent) {
    if (AccountManager.getInstance().getEmployee().getType().getAccessLevel() == 5) {
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
      String nodeID = rt.getSelection().getNodeID();
      if(rt.getSelection().getRequestType().equals(RequestType.CleaningRequest)){
        WindowManager.getInstance().openWindow("popUpViews/LocationChoice.fxml");
        nodeID = (String) WindowManager.getInstance().getData("LocationChoice");
        System.out.println(nodeID);
      }
      RequestFacade.getRequestFacade()
          .completeRequest(
              rt.getSelection().getRequestID(),
              rt.getSelection().getRequestType(),
              nodeID);
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
