package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MedRequest;

public class MedicineDeliverySR {
  MedRequest mdr;

  public MedicineDeliverySR(MedRequest mdr) {
    this.mdr = mdr;
  }

  @Override
  public String getRequestType() {
    return "Medicine Delivery";
  }
}
