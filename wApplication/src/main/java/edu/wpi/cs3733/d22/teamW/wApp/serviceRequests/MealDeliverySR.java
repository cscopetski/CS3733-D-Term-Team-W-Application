package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;

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
  public String getFormattedInfo() throws SQLException {
    String info = "";
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    info += "";
    return info;
  }
}
