package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import java.awt.*;
import javafx.fxml.FXML;

public class MedicineDeliveryServiceRequestController {

  // FXML connectors:
  @FXML TextField location;
  @FXML TextField employee;
  @FXML TextField medicine;

  public void clearFields() {

  }

  public void submitButton(){
    clearFields();
    createRequest();
  }

  //NEEDS LINK TO DB
  public void createRequest() {
    //
  }
}
