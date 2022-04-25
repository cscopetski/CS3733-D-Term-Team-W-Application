package edu.wpi.cs3733.d22.teamW.wDB.entity;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnreadMessage extends Entity {
  private Integer msgID;
  private Integer empID;

  public UnreadMessage(Integer msgID, Integer empID) {
    this.msgID = msgID;
    this.empID = empID;
  }

  public UnreadMessage(ArrayList<Integer> fields) {
    this.msgID = fields.get(0);
    this.empID = fields.get(0);
  }

  @Override
  public String toCSVString() {
    return String.format("%d,%d", msgID, empID);
  }

  @Override
  public String toValuesString() {
    return String.format("%d,%d", msgID, empID);
  }
}
