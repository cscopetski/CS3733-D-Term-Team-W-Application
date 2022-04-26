package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MealRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MealRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;

public class MealDeliverySR extends SR {

  public MealDeliverySR(Request r) {
    super(r);
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.MealDelivery;
  }

  @Override
  public String getRequestTypeS() {
    return "Meal Delivery";
  }

  @Override
  public String getFormattedInfo() throws Exception {
    MealRequest mealRequest =
        (MealRequest) MealRequestManager.getMealRequestManager().getRequest(this.getRequestID());
    String info = "";
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    info +=
            "Location: "
                    + LocationManager.getLocationManager().getLocation(mealRequest.getNodeID()).getLongName()
                    + "\n";
    info += "Meal: " + mealRequest.getMealType().getString() + "\n";
    info +=
        "Patient Name: "
            + mealRequest.getPatientFirst()
            + " "
            + mealRequest.getPatientLast()
            + "\n";
    return info;
  }
}
