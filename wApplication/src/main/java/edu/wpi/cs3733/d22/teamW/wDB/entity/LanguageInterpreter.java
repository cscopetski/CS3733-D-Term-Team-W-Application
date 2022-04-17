package edu.wpi.cs3733.d22.teamW.wDB.entity;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LanguageInterpreter extends Entity {

  private Integer employeeID;
  private String language;

  public LanguageInterpreter(Integer employeeID, String language) {
    this.employeeID = employeeID;
    this.language = language;
  }

  public LanguageInterpreter(ArrayList<String> fields) {
    this.employeeID = Integer.parseInt(fields.get(0));
    this.language = fields.get(1);
  }

  @Override
  public String toCSVString() {
    return String.format("%d,%s", employeeID, language);
  }

  @Override
  public String toValuesString() {
    return String.format("%d, '%s'", employeeID, language);
  }
}
