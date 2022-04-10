package edu.wpi.cs3733.d22.teamW.wApp.serviceRequests;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;

public class LanguageInterpreterSR extends SR {

  public LanguageInterpreterSR(Request r) {
    super(r);
  }

  @Override
  public String getRequestType() {
    return "Language Interpreter";
  }
}
