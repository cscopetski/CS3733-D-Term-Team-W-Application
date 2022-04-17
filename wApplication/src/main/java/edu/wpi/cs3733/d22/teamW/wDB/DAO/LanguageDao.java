package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LanguageDao {

  void addLanguage(String language) throws SQLException;

  void deleteLanguage(String Language) throws SQLException;

  ArrayList<String> getAllLanguages();

  void exportLanguageCSV(String fileName);
}
