package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

public class MedicineDeliverySR extends SR {

  public MedicineDeliverySR(Request r) {
    super(r);
  }

  @Override
  public String getRequestType() {
    return "Medicine Delivery";
  }
}
