package edu.wpi.cs3733.d22.teamW.wDB.entity;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LanguageInterpreter implements Entity {

  private Integer employeeID;
  private String firstName;
  private String lastName;
  private String language;

  public LanguageInterpreter(Integer ID, String fname, String lname, String lang) {
    this.employeeID = ID;
    this.firstName = fname;
    this.lastName = lname;
    this.language = lang;
  }

  public LanguageInterpreter(ArrayList<String> fields) {
    this.employeeID = Integer.parseInt(fields.get(0));
    this.firstName = fields.get(1);
    this.lastName = fields.get(2);
    this.language = fields.get(3);
  }

  @Override
  public String toCSVString() {
    return String.format("%d,%s,%s,%s", employeeID, firstName, lastName, language);
  }

  @Override
  public String toValuesString() {
    return String.format("%d, '%s', '%s', '%s'", employeeID, firstName, lastName, language);
  }
}
