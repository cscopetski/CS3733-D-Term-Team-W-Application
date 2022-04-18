package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.GiftDeliveryRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.GiftDeliveryRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;

public class GiftSR extends SR {

  public GiftSR(Request r) {
    super(r);
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.GiftDelivery;
  }

  @Override
  public String getRequestTypeS() {
    return "Gift Service";
  }

  @Override
  public String getFormattedInfo() throws Exception {
    GiftDeliveryRequest giftDeliveryRequest =
        (GiftDeliveryRequest)
            GiftDeliveryRequestManager.getGiftDeliveryRequestManager()
                .getRequest(this.getRequestID());
    String info = "";
    if (this.getEmergency() == 1) {
      info += "Request marked as an EMERGENCY\n";
    }
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    info +=
        "Recipient Name: "
            + giftDeliveryRequest.getRecipientFirstName()
            + " "
            + giftDeliveryRequest.getRecipientLastName()
            + "\n";
    info += "";
    return info;
  }
}
