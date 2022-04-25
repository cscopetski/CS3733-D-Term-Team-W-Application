package edu.wpi.cs3733.d22.teamW.wDB.entity;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chat extends Entity {

  private Integer chatID;
  private Integer empID;

  public Chat(Integer chatID, Integer empID) {
    this.chatID = chatID;
    this.empID = empID;
  }

  public Chat(ArrayList<Integer> fields) {
    this.chatID = fields.get(0);
    this.empID = fields.get(1);
  }

  @Override
  public String toCSVString() {
    return String.format("%d,%d", chatID, empID);
  }

  @Override
  public String toValuesString() {
    return String.format("%d,%d", chatID, empID);
  }
}
