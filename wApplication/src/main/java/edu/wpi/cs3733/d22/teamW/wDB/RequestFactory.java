package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class RequestFactory {

  // must be a singleton so that the counter does not get messed up

  // check DB for existing requests when are using external DB and not embedded one
  private ArrayList<Request> requests = new ArrayList<>();
  private MedEquipRequestController merc;

  private static RequestFactory requestFactory;

  public static RequestFactory getRequestFactory(MedEquipRequestController merc) {

    if (requestFactory == null) {
      requestFactory = new RequestFactory(merc);
    }
    return requestFactory;
  }

  public static RequestFactory getRequestFactory() {

    return requestFactory;
  }

  private RequestFactory(MedEquipRequestController merc) {
    this.merc = merc;
  }

  // fields is every field except for request id and itemID

  public Request getRequest(String requestType, ArrayList<String> fields) throws SQLException {
    int counter = requests.size() + 1;
    if (requestType.equalsIgnoreCase("MEDEQUIPREQUEST")) {
      Request mER = merc.addRequest(counter, fields);
      requests.add(mER);
      return mER;
    } else if (requestType.equalsIgnoreCase("LABSERVICEREQUEST")) {
      return new LabServiceRequest();
    } else {
      return null;
    }
  }

  public Request findRequest(Integer requestID) {
    return requests.get(requestID - 1);
  }

  public ArrayList<Request> getAllRequests() {
    return this.requests;
  }
}