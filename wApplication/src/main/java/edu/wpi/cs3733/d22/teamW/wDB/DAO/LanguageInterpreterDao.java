package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.LanguageInterpreter;
import java.sql.SQLException;
import java.util.ArrayList;

public interface LanguageInterpreterDao {

  ArrayList<LanguageInterpreter> getAllLanguageInterpreters() throws SQLException;

  void addLanguageInterpreter(
      Integer employeeID, String firstname, String lastname, String language) throws SQLException;

  void deleteLanguageInterpreter(Integer empID) throws SQLException;

  void deleteLanguageInterpreterOneInstance(Integer empID, String language) throws SQLException;

  void changeLanguageInterpreter(
      Integer employeeID, String firstname, String lastname, String language) throws SQLException;

  void exportLanguageInterpreterCSV(String fileName);
}
