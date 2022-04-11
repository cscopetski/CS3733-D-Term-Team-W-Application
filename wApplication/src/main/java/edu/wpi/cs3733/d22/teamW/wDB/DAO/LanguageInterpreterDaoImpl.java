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

  public LanguageInterpreterDaoImpl(Statement s) {
    this.statement = s;
  }

  void createTable() throws SQLException {
    try {
      statement.execute(
          "CREATE TABLE LANGUAGEINTERPRETERS(\n"
              + "employeeID INT, \n "
              + "firstName varchar(25), \n "
              + "lastName varchar(25), \n "
              + "language varchar(25), \n "
              + "constraint LANGUAGEINTERP_FK foreign key (employeeID) references EMPLOYEES)");
    } catch (SQLException e) {
      System.out.println("Language Interpreter Table failed to be created!");
      throw (e);
    }
    System.out.println("Language Interpreter Table created");
  }

  @Override
  public ArrayList<LanguageInterpreter> getAllLanguageInterpreters() throws SQLException {
    ArrayList<LanguageInterpreter> languageInterpreterList = new ArrayList<LanguageInterpreter>();

    try {
      ResultSet langInterps = statement.executeQuery("SELECT * FROM EMPLOYEES");

      while (langInterps.next()) {
        ArrayList<String> languageInterpreterData = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
          languageInterpreterData.add(langInterps.getString(i + 1));
        }

        languageInterpreterList.add(new LanguageInterpreter(languageInterpreterData));
      }

    } catch (SQLException e) {
      System.out.println("Query from locations table failed");
      throw (e);
    }
    return languageInterpreterList;
  }

  @Override
  public void addLanguageInterpreter(
      Integer employeeID, String firstname, String lastname, String language) throws SQLException {
    LanguageInterpreter li = new LanguageInterpreter(employeeID, firstname, lastname, language);
    statement.executeUpdate(
        String.format("INSERT INTO LANGUAGEINTERPRETERS VALUES (%s)", li.toValuesString()));
  }

  @Override
  public void deleteLanguageInterpreter(Integer empID) throws SQLException {
    statement.executeUpdate(
        String.format("DELETE FROM LANGUAGEINTERPRETERS WHERE EMPLOYEEID=%d", empID));
  }

  @Override
  public void deleteLanguageInterpreterOneInstance(Integer empID, String language)
      throws SQLException {
    statement.executeUpdate(
        String.format(
            "DELETE FROM LANGUAGEINTERPRETERS WHERE EMPLOYEEID=%d AND LANGUAGE = '%s'",
            empID, language));
  }

  @Override
  public void changeLanguageInterpreter(
      Integer employeeID, String firstname, String lastname, String language) throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE LANGUAGEINTERPRETERS SET FIRSTNAME = '%s', LASTNAME = '%s', LANGUAGE = '%s' WHERE EMPLOYEEID = %d",
            firstname, lastname, language, employeeID));
  }

  @Override
  public void exportLanguageInterpreterCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print("employeeID,firstname,lastname,language");

      // print all locations
      for (LanguageInterpreter e : getAllLanguageInterpreters()) {
        pw.println();
        pw.print(e.toCSVString());
      }

    } catch (FileNotFoundException | SQLException s) {
      System.out.println(String.format("Error Exporting to File %s", fileName));
      s.printStackTrace();
    }
  }
}
