package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import java.sql.SQLException;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HighScore extends Entity {

  private Integer employeeID;
  private int scoreWiggling;
  private int scoreThreat;

  public HighScore(Integer employeeID, int scoreWiggling, int scoreThreat) {
    this.employeeID = employeeID;
    this.scoreWiggling = scoreWiggling;
    this.scoreThreat = scoreThreat;
  }

  public HighScore(ArrayList<String> fields) {
    this.employeeID = Integer.parseInt(fields.get(0));
    this.scoreWiggling = Integer.parseInt(fields.get(1));
    this.scoreThreat = Integer.parseInt(fields.get(2));
  }

  @Override
  public String toCSVString() {
    return String.format("%d,%d,%d", employeeID, scoreWiggling, scoreThreat);
  }

  @Override
  public String toValuesString() {
    return String.format("%d, %d, %d", employeeID, scoreWiggling, scoreThreat);
  }

  public String getName() throws SQLException {
    return EmployeeManager.getEmployeeManager().getEmployee(this.employeeID).getFirstName()
        + " "
        + EmployeeManager.getEmployeeManager().getEmployee(this.employeeID).getLastName();
  }
}
