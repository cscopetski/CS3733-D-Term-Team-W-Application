package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;

public class LanguageInterpreterSR extends SR {

  public LanguageInterpreterSR(Request r) {
    super(r);
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.LanguageInterpreter;
  }

  @Override
  public String getRequestTypeS() {
    return "Language Interpreter";
  }

  @Override
  public String getFormattedInfo() throws SQLException {
    // LanguageRequest languageRequest =
    // LanguageRequestManager.getLanguageRequestManager.getLanguageRequest(this.getRequestID());
    String info = "";
    if (this.getEmergency() == 1) {
      info += "Request marked as an EMERGENCY\n";
    }
    info += "Assigned Employee: " + this.getEmployeeName() + "\n";
    info += "Employee ID: " + this.getEmployeeID() + "\n";
    // info += "Language: " + languageRequest.getLanguage().getString() + "\n";
    info += "";
    return info;
  }
}
