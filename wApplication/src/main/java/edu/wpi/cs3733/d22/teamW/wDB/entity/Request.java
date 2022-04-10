package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Request implements Entity {

  protected String[] statusType = {"In Queue", "In Progress", "Completed", "Cancelled"};
  protected Integer
      requestID; // Maybe switch to Hexatrigesimal uses 0-9 and then A to Z, so it allows a large
  // number of req
  // With 6 char, it will allow 36^6 = 2.18 billion
  protected Integer emergency;
  protected String nodeID; // Location
  protected Integer status; // 0 enqueue; 1 in progress; 2 done; 3 cancelled
  protected Integer employeeID; // Will be changed to employee ID starting in sprint 1

  /*
  public Request(Integer requestID, Integer emergency, String nodeID, Integer status, String employeeName) {
    this.requestID = requestID;
    this.emergency = emergency;
    this.nodeID = nodeID;
    this.status = status;
    this.employeeName = employeeName;
  }
   */

  public abstract Integer getStatusInt();

  public String getStatus() {
    return statusType[this.getStatusInt()];
  }

  public abstract void start();

  public abstract void complete();

  public abstract void cancel();

  public abstract RequestType getRequestType();
}
