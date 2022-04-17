package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.LanguageInterpreter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LanguageInterpreterDaoImpl implements LanguageInterpreterDao {

  private Statement statement;

  LanguageInterpreterDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() {
    try {
      statement.execute("DROP TABLE LANGUAGEINTERPRETERS");
      System.out.println("Dropped Language Interpreters Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Language Interpreters Table");
    }
  }

  String CSVHeaderString = "employeeID,language";

  void createTable() throws SQLException {

    try {
      statement.execute(
          "CREATE TABLE LANGUAGEINTERPRETERS("
              + "employeeID INT,"
              + "language varchar(25),"
              + "constraint LANGINTERP_EmployeeID_FK foreign key (employeeID) references EMPLOYEES,"
              + "constraint LANGINTERP_Language_FK foreign key (language) references LANGUAGES,"
              + "constraint LangInterp_PK primary key (employeeID,language)"
              + ")");
    } catch (SQLException e) {
      System.out.println("Language Interpreter Table failed to be created!");
      throw (e);
    }
    System.out.println("Language Interpreter Table created!");
  }

  @Override
  public void addLanguageInterpreter(LanguageInterpreter languageInterpreter) throws SQLException {
    statement.executeUpdate(
        String.format(
            "INSERT INTO LANGUAGEINTERPRETERS VALUES (%s)", languageInterpreter.toValuesString()));
  }

  @Override
  public void changeLanguageInterpreter(LanguageInterpreter languageInterpreter, String language)
      throws SQLException {
    LanguageInterpreter newLI =
        new LanguageInterpreter(languageInterpreter.getEmployeeID(), language);
    addLanguageInterpreter(newLI);
    deleteLanguageInterpreter(languageInterpreter);
  }

  @Override
  public void deleteLanguageInterpreter(LanguageInterpreter li) throws SQLException {
    statement.executeUpdate(
        String.format(
            "DELETE FROM LANGUAGEINTERPRETERS WHERE EMPLOYEEID=%d AND LANGUAGE = '%s'",
            li.getEmployeeID(), li.getLanguage()));
  }

  @Override
  public ArrayList<LanguageInterpreter> getAllLanguageInterpreters() throws SQLException {
    ArrayList<LanguageInterpreter> langInterpList = new ArrayList<LanguageInterpreter>();

    try {
      ResultSet langInterps = statement.executeQuery("SELECT * FROM LANGUAGEINTERPRETERS");

      // Size of num LANGUAGEINTERPRETERS fields
      ArrayList<String> languageInterpData = new ArrayList<String>();

      while (langInterps.next()) {

        for (int i = 0; i < langInterps.getMetaData().getColumnCount(); i++) {
          languageInterpData.add(i, langInterps.getString(i + 1));
        }

        langInterpList.add(new LanguageInterpreter(languageInterpData));
      }

    } catch (SQLException e) {
      System.out.println("Query from language interpreter table failed.");
    }
    return langInterpList;
  }

  @Override
  public void exportLangInterpCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      // print all locations
      for (LanguageInterpreter languageInterpreter : getAllLanguageInterpreters()) {
        pw.println();
        pw.print(languageInterpreter.toCSVString());
      }

    } catch (FileNotFoundException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
