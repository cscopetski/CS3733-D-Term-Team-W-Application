package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CustomControlTesting implements Initializable {
  @FXML public RequestTable table;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    /*ArrayList<Request> requests = RequestFactory.getRequestFactory().getAllRequests();
    table
        .getItems()
        .addAll(
            requests.stream()
                .map(r -> (SR) (new MedicalEquipmentSR(r)))
                .collect(Collectors.toList()));*/
  }
}
