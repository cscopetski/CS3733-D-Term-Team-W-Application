package edu.wpi.cs3733.d22.teamW.wDB.enums;

public enum RequestStatus {
  InQueue(0, "In Queue"),
  InProgress(1, "In Progress"),
  Completed(2, "Completed"),
  Cancelled(3, "Cancelled");

  private final int value;
  private final String string;

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

  public static RequestStatus getRequestStatus(Integer num) {
    RequestStatus status = null;
    switch (num) {
      case 0:
        status = InQueue;
        break;
      case 1:
        status = InProgress;
        break;
      case 2:
        status = Completed;
        break;
      case 3:
        status = Cancelled;
        break;
      default:
        System.out.println("Not a case for Request Status");
        status = Cancelled;
        break;
    }
    return status;
  }
}
