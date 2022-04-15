package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;

public abstract class SR {
  protected final Request REQUEST;

  public SR(Request r) {
    this.REQUEST = r;
  }

  public Integer getRequestID() {
    return REQUEST.getRequestID();
  }

  public Integer getEmergency() {
    return REQUEST.getEmergency();
  }

  public String getNodeID() {
    return REQUEST.getNodeID();
  }

  public String getStatus() {
    return REQUEST.getStatus().getString();
  }

  public String getEmployeeName() throws SQLException {
    Employee e = EmployeeManager.getEmployeeManager().getEmployee(REQUEST.getEmployeeID());
    return e.getFirstName() + ' ' + e.getLastName();
  }

  public Integer getEmployeeID() {
    return REQUEST.getEmployeeID();
  }

  public abstract RequestType getRequestType();

  public abstract String getRequestTypeS();

  // returns a String for the More Info section of the RequestList page
  public abstract String getFormattedInfo() throws SQLException, StatusError;
}
