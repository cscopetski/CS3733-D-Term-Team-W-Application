package edu.wpi.cs3733.d22.teamW.wDB.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeMessage extends Entity {
  private Integer messageID;
  private Integer empIDfrom;
  private Integer chatIDto;
  private String messageContent;
  private Timestamp sentTimestamp;

  public EmployeeMessage(
      Integer messageID,
      Integer empIDfrom,
      Integer chatIDto,
      String messageContent,
      Timestamp sentTimestamp) {
    this.messageID = messageID;
    this.empIDfrom = empIDfrom;
    this.chatIDto = chatIDto;
    this.messageContent = messageContent;
    this.sentTimestamp = sentTimestamp;
  }

  public EmployeeMessage(ArrayList<String> fields) {
    this.messageID = Integer.parseInt(fields.get(0));
    this.empIDfrom = Integer.parseInt(fields.get(1));
    this.chatIDto = Integer.parseInt(fields.get(2));
    this.messageContent = fields.get(3);
    this.sentTimestamp = Timestamp.valueOf(fields.get(4));
  }

  @Override
  public String toCSVString() {
    // TODO: make csv work with strings containing commas
    return null;
  }

  @Override
  public String toValuesString() {
    // TODO: Prepared statements to deal with string
    return null;
  }
}
