package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.LanguageDao;
import java.sql.SQLException;
import java.util.ArrayList;

public class LanguageManager {
  private LanguageDao ld;

  private static LanguageManager languageManager = new LanguageManager();

  public static LanguageManager getLanguageManager() {
    return languageManager;
  }

  private LanguageManager() {}

  public void setLanguageDao(LanguageDao ld) {
    this.ld = ld;
  }

  public void addLanguage(String language) throws SQLException {
    ld.addLanguage(language);
  }

  public void deleteLanguage(String language) throws Exception {
    ld.deleteLanguage(language);
  }

  public ArrayList<String> getAllLocations() throws SQLException {
    return ld.getAllLanguages();
  }

  public void exportLocationsCSV(String filename) {
    ld.exportLanguageCSV(filename);
  }
}
