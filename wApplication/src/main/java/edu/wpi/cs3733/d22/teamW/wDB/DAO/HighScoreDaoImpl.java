package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.HighScore;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

public class HighScoreDaoImpl implements HighScoreDao {

  String CSVHeaderString = "employeeID,score";
  private final Statement statement;

  HighScoreDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() {
    try {
      statement.execute("DROP TABLE HIGHSCORE");
      System.out.println("Dropped High Score Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop High Score Table");
    }
  }

  void createTable() throws SQLException {

    try {
      statement.execute(
          "CREATE TABLE HIGHSCORE("
              + "employeeID INT,"
              + "scoreWiggling INT,"
              + "scoreThreat INT,"
              + "constraint HIGHSCORE_EmployeeID_FK foreign key (employeeID) references EMPLOYEES,"
              + "constraint HIGHSCORE_PK primary key (employeeID,scoreWiggling,scoreThreat)"
              + ")");
    } catch (SQLException e) {
      System.out.println("High Score Table failed to be created!");
      throw (e);
    }
    System.out.println("High Score Table created!");
  }

  @Override
  public void addHighScore(HighScore highScore) throws SQLException {
    statement.executeUpdate(
        String.format("INSERT INTO HIGHSCORE VALUES (%s)", highScore.toValuesString()));
  }

  @Override
  public void changeHighScore(HighScore highScore, int scoreWiggling, int scoreThreat)
      throws SQLException {
    HighScore newHS = new HighScore(highScore.getEmployeeID(), scoreWiggling, scoreThreat);
    deleteHighScore(highScore);
    addHighScore(newHS);
  }

  @Override
  public void deleteHighScore(HighScore hs) throws SQLException {
    statement.executeUpdate(
        String.format(
            "DELETE FROM HIGHSCORE WHERE EMPLOYEEID=%d AND SCOREWIGGLING = %d AND SCORETHREAT = %d ",
            hs.getEmployeeID(), hs.getScoreWiggling(), hs.getScoreThreat()));
  }

  @Override
  public ArrayList<HighScore> getAllHighScores() throws SQLException {
    ArrayList<HighScore> highScoreList = new ArrayList<HighScore>();

    try {
      ResultSet highScores = statement.executeQuery("SELECT * FROM HIGHSCORE");

      // Size of num HIGHSCORE fields
      ArrayList<String> highScoreData = new ArrayList<String>();

      while (highScores.next()) {

        for (int i = 0; i < highScores.getMetaData().getColumnCount(); i++) {
          highScoreData.add(i, highScores.getString(i + 1));
        }

        highScoreList.add(new HighScore(highScoreData));
      }

    } catch (SQLException e) {
      System.out.println("Query from high score table failed.");
    }
    return highScoreList;
  }

  @Override
  public void exportHighScoreCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (HighScore highScore : getAllHighScores()) {
        pw.println();
        pw.print(highScore.toCSVString());
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ArrayList<HighScore> getHighestScoreWiggling() throws SQLException {
    ArrayList<HighScore> hsl = getAllHighScores();
    hsl.sort(
        new Comparator<HighScore>() {
          @Override
          public int compare(HighScore o1, HighScore o2) {
            return o1.getScoreWiggling() - o2.getScoreWiggling();
          }
        });
    return hsl;
  }

  @Override
  public ArrayList<HighScore> getHighestScoreThreat() throws SQLException {
    ArrayList<HighScore> hsl = getAllHighScores();
    hsl.sort(
        new Comparator<HighScore>() {
          @Override
          public int compare(HighScore o1, HighScore o2) {
            return o1.getScoreThreat() - o2.getScoreThreat();
          }
        });
    return hsl;
  }

  @Override
  public HighScore getHighScore(int empID) {
    HighScore hs = null;
    try {
      ResultSet highScores =
          statement.executeQuery(
              String.format("SELECT * FROM HIGHSCORE WHERE EMPLOYEEID = %d", empID));
      // Size of num HIGHSCORE fields
      ArrayList<String> highScoreData = new ArrayList<String>();

      highScores.next();

      for (int i = 0; i < highScores.getMetaData().getColumnCount(); i++) {
        highScoreData.add(i, highScores.getString(i + 1));
      }
      hs = new HighScore(highScoreData);

    } catch (SQLException e) {
      System.out.println("Query from high score table failed.");
    }
    return hs;
  }
}
