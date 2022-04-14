package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.*;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RequestListController extends LoadableController {
  @FXML public RequestTable rt;
  @FXML public TextArea moreInfo;
  @FXML public HBox selectionButtons;
  @FXML public AutoCompleteInput filterList;

  @FXML public VBox filterGroup;
  private final ArrayList<RequestType> filters = new ArrayList<>();

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
              try {
                moreInfo.setText(request.getFormattedInfo());
              } catch (SQLException e) {
                e.printStackTrace();
                moreInfo.setText("Error loading request details.");
              }
              selectionButtons.setVisible(newSelection != null);
            });

    filterList.loadValues(
        (ArrayList<String>)
            Arrays.stream(RequestType.values())
                .map(RequestType::getString)
                .collect(Collectors.toList()));
    filterList
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((e, o, n) -> addFilter(RequestType.getRequestType(n)));
  }

  private void addFilter(RequestType requestType) {
    if (!filters.contains(requestType)) {
      filters.add(requestType);
      resetItems();
      ToggleButton filter = new ToggleButton(requestType.getString());
      filter.setOnAction(e -> {
        if (filter.getText().equals("Remove?")) {
          filterGroup.getChildren().remove(filter);
        }else if (filter.getText().equals(requestType.getString()));
      });
    }
  }

  private void removeFilter(RequestType requestType) {
    if (filters.contains(requestType)) {
      filters.remove(requestType);
      resetItems();
    }
  }

  public void onLoad() {
    rt.setColumnWidth("Req. ID", 60);
    rt.setColumnWidth("Request Type", 130);
    rt.setColumnWidth("Employee Name", 140);
    rt.setColumnWidth("Status", 80);
    rt.setEditable(false);
    moreInfo.setText("Select a request to view details.");
    resetItems();
  }

  public void resetItems() {
    try {
      if (filters.size() != 0) {
        rt.setItems(
            RequestFacade.getRequestFacade()
                .getRequests(filters.toArray(new RequestType[filters.size()])));
      } else {
        rt.setItems(RequestFacade.getRequestFacade().getRequests());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onUnload() {}

  public void cancel(ActionEvent actionEvent) throws Exception {
    RequestFacade.getRequestFacade()
        .cancelRequest(rt.getSelection().getRequestID(), rt.getSelection().getRequestType());
    resetItems();
  }

  public void confirm(ActionEvent event) throws Exception {
    RequestFacade.getRequestFacade()
        .completeRequest(
            rt.getSelection().getRequestID(),
            rt.getSelection().getRequestType(),
            rt.getSelection().getNodeID());
    resetItems();
  }

  public void clearSelection() {
    rt.getSelectionModel().clearSelection();
    selectionButtons.setVisible(false);
  }

  public void start() throws Exception {
    if (!RequestFacade.getRequestFacade()
        .startRequest(rt.getSelection().getRequestID(), rt.getSelection().getRequestType())) {
      Alert alert =
          new Alert(
              Alert.AlertType.WARNING,
              "Equipment Not Available: "
                  + ((MedicalEquipmentSR) rt.getSelection()).getOriginal().getItemType(),
              ButtonType.OK);
      alert.showAndWait();
    }
    resetItems();
  }
}
