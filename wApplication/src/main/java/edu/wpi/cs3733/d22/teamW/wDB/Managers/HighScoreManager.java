package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.HighScoreDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.HighScore;
import java.sql.SQLException;
import java.util.ArrayList;

public class HighScoreManager {
  private HighScoreDao hd;

  private static HighScoreManager highScoreManager = new HighScoreManager();

  public static HighScoreManager getHighScoreManager() {
    return highScoreManager;
  }

  private HighScoreManager() {}

  public void setHighScoreDao(HighScoreDao hd) {
    this.hd = hd;
  }

  public ArrayList<HighScore> getAllHighScores() throws SQLException {
    return hd.getAllHighScores();
  }

  public void addHighScore(HighScore hs) throws SQLException {
    hd.addHighScore(hs);
  }

  public void changeHighScore(HighScore hs, int wiggling, int threat) throws SQLException {
    hd.changeHighScore(hs, wiggling, threat);
  }

  public HighScore getHighScore(int empID){
    return hd.getHighScore(empID);
  }


}
