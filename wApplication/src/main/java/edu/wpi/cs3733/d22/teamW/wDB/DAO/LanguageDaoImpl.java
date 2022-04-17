package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LanguageDaoImpl implements LanguageDao {

  Statement statement;

  LanguageDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() {
    try {
      statement.execute("DROP TABLE LANGUAGES");
      System.out.println("Dropped Languages Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Languages Table");
    }
  }

  String CSVHeaderString = "languages";

  void createTable() throws SQLException {

    try {
      statement.execute(
          "CREATE TABLE LANGUAGES("
              + "language varchar(25),"
              + "constraint Lang_PK primary key (language))");
    } catch (SQLException e) {
      System.out.println("Language Table failed to be created!");
      throw (e);
    }
    System.out.println("Language Table created!");
  }

  @Override
  public void addLanguage(String language) throws SQLException {
    statement.execute(String.format("INSERT INTO LANGUAGES VALUES '%s'", language));
  }

  @Override
  public void deleteLanguage(String language) throws SQLException {
    statement.execute(String.format("DELETE FROM LANGUAGES WHERE LANGUAGE = '%s'", language));
  }

  @Override
  public ArrayList<String> getAllLanguages() {
    ArrayList<String> languages = new ArrayList<String>();

    try {
      ResultSet langList = statement.executeQuery("SELECT * FROM LANGUAGES");

      while (langList.next()) {
        languages.add(langList.getString(1));
      }

    } catch (SQLException e) {
      System.out.println("Query from languages table failed.");
    }
    return languages;
  }

  @Override
  public void exportLanguageCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (String s : getAllLanguages()) {
        pw.println();
        pw.print(s);
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }
}
