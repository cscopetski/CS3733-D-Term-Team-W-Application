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
  private Integer empIDto;
  private String messageContent;
  private Timestamp sentTimestamp;
  private Integer isRead;

  public EmployeeMessage(
      Integer messageID,
      Integer empIDfrom,
      Integer empIDto,
      String messageContent,
      Timestamp sentTimestamp,
      Integer isRead) {
    this.messageID = messageID;
    this.empIDfrom = empIDfrom;
    this.empIDto = empIDto;
    this.messageContent = messageContent;
    this.sentTimestamp = sentTimestamp;
    this.isRead = isRead;
  }

  public EmployeeMessage(ArrayList<String> fields) {
    this.messageID = Integer.parseInt(fields.get(0));
    this.empIDfrom = Integer.parseInt(fields.get(1));
    this.empIDto = Integer.parseInt(fields.get(2));
    this.messageContent = fields.get(3);
    this.sentTimestamp = Timestamp.valueOf(fields.get(4));
    this.isRead = Integer.parseInt(fields.get(5));
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
