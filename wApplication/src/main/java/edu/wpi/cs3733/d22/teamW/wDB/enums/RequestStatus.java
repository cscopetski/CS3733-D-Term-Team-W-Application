package edu.wpi.cs3733.d22.teamW.wDB.enums;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import java.util.HashMap;
import java.util.Map;

public enum RequestStatus {
  InQueue(0, "In Queue"),
  InProgress(1, "In Progress"),
  Completed(2, "Completed"),
  Cancelled(3, "Cancelled");

  private final int value;
  private final String string;
  private static Map map = new HashMap<>();
  private static Map map2 = new HashMap<>();

  static {
    for (RequestStatus requestStatus : RequestStatus.values()) {
      map.put(requestStatus.value, requestStatus);
    }
    for (RequestStatus requestStatus : RequestStatus.values()) {
      map2.put(requestStatus.string, requestStatus);
    }
  }

  private RequestStatus(int value, String string) {
    this.value = value;
    this.string = string;
  }

  public int getValue() {
    return this.value;
  }

  public String getString() {
    return this.string;
  }

  public static RequestStatus getRequestStatus(int type) throws StatusError {
    RequestStatus output = (RequestStatus) map.get(type);
    if (output == null) {
      throw new StatusError();
    }
    return output;
  }

  public static RequestStatus getRequestStatus(String type) throws StatusError {

    type = type.trim();
    RequestStatus output = (RequestStatus) map2.get(type);
    if (output == null) {
      throw new StatusError();
    }
    return output;
  }
}
