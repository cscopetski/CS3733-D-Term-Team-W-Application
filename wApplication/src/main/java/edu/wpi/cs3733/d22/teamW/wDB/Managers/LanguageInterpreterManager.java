package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.LanguageInterpreterDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.LanguageInterpreter;
import java.sql.SQLException;
import java.util.ArrayList;

public class LanguageInterpreterManager {

  private LanguageInterpreterDao lid;

  private static LanguageInterpreterManager languageInterpreterManager =
      new LanguageInterpreterManager();

  public static LanguageInterpreterManager getLanguageInterpreterManager() {
    return languageInterpreterManager;
  }

  private LanguageInterpreterManager() {}

  public void setLanguageInterpreterDao(LanguageInterpreterDao lid) {
    this.lid = lid;
  }

  public void addLanguageInterpreter(
      Integer employeeID, String firstname, String lastname, String language) throws SQLException {
    lid.addLanguageInterpreter(employeeID, firstname, lastname, language);
  }

  public void deleteLanguageInterpreter(Integer employeeID) throws SQLException {
    lid.deleteLanguageInterpreter(employeeID);
  }

  public void deleteLanguageInterpreterOneInstance(Integer employeeID, String language)
      throws SQLException {
    lid.deleteLanguageInterpreterOneInstance(employeeID, language);
  }

  public void changeLanguageInterpreter(
      Integer employeeID, String firstname, String lastname, String language) throws SQLException {
    lid.changeLanguageInterpreter(employeeID, firstname, lastname, language);
  }

  public ArrayList<LanguageInterpreter> getAllLanguageInterpreters() throws SQLException {
    return lid.getAllLanguageInterpreters();
  }

  public void exportLanguageInterpreterCSV(String filename) {
    lid.exportLanguageInterpreterCSV(filename);
  }
}
