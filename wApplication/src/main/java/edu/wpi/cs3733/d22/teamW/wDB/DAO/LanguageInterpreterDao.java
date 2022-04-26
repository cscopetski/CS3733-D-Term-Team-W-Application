package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.LanguageInterpreter;
import java.sql.SQLException;
import java.util.ArrayList;

public interface LanguageInterpreterDao {

  void addLanguageInterpreter(LanguageInterpreter languageInterpreter) throws SQLException;

  void changeLanguageInterpreter(LanguageInterpreter languageInterpreter, String Language)
      throws SQLException;

  void deleteLanguageInterpreter(LanguageInterpreter li) throws SQLException;

  ArrayList<LanguageInterpreter> getAllLanguageInterpreters() throws SQLException;

  void exportLangInterpCSV(String fileName);

  public ArrayList<Integer> getEligibleEmployees(String language) throws SQLException;

  void deleteAllLanguageInterpreter(Integer id) throws SQLException;
}
