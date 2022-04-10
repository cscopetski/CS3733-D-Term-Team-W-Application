package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Request implements Entity {
  protected Integer
      requestID; // Maybe switch to Hexatrigesimal uses 0-9 and then A to Z, so it allows a large
  // number of req
  // With 6 char, it will allow 36^6 = 2.18 billion
  protected Integer emergency;
  protected String nodeID; // Location
  protected RequestStatus status; // 0 enqueue; 1 in progress; 2 done; 3 cancelled
  protected String employeeName; // Will be changed to employee ID starting in sprint 1

  /*
  public Request(Integer requestID, Integer emergency, String nodeID, Integer status, String employeeName) {
    this.requestID = requestID;
    this.emergency = emergency;
    this.nodeID = nodeID;
    this.status = status;
    this.employeeName = employeeName;
  }
   */



  public Integer getStatusInt(){
    return status.getValue();
  };

  public RequestStatus getStatus() {
    return status;
  }

  public void setStatus(RequestStatus status) {
    this.status = status;
  }

  public abstract RequestType getRequestType();
}
