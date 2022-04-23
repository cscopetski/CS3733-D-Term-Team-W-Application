package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.HighScore;
import java.sql.SQLException;
import java.util.ArrayList;

public interface HighScoreDao {

  void addHighScore(HighScore highScore) throws SQLException;

  void changeHighScore(HighScore highScore, int scoreWiggling, int scoreThreat) throws SQLException;

  void deleteHighScore(HighScore hs) throws SQLException;

  ArrayList<HighScore> getAllHighScores() throws SQLException;

  void exportHighScoreCSV(String fileName);

  ArrayList<HighScore> getHighestScoreWiggling() throws SQLException;

  ArrayList<HighScore> getHighestScoreThreat() throws SQLException;

    HighScore getHighScore(int empID);
}
