package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.LanguageInterpreterDao;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.InvalidUnit;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoMedicine;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.entity.LanguageInterpreter;
import java.sql.SQLException;
import java.util.ArrayList;

public class LanguageInterpreterManager {

  private static LanguageInterpreterManager lim = new LanguageInterpreterManager();
  private LanguageInterpreterDao lid;

  private LanguageInterpreterManager() {}

  public static LanguageInterpreterManager getLanguageInterpreterManager() {
    return lim;
  }

  public void setLanguageInterpreterDao(LanguageInterpreterDao lid) {
    this.lid = lid;
  }

  public LanguageInterpreter addLanguageInterpreter(ArrayList<String> fields)
      throws SQLException, NoMedicine, StatusError, InvalidUnit {
    LanguageInterpreter li = new LanguageInterpreter(fields);
    lid.addLanguageInterpreter(li);
    return li;
  }

  public void addLanguageInterpreter(LanguageInterpreter li) throws SQLException {
    lid.addLanguageInterpreter(li);
  }

  public void deleteLanguageInterpreter(LanguageInterpreter li) throws SQLException {
    lid.deleteLanguageInterpreter(li);
  }

  public void changeLanguageInterpreter(LanguageInterpreter li, String lang) throws SQLException {
    lid.changeLanguageInterpreter(li, lang);
  }

  public ArrayList<LanguageInterpreter> getAllRequests() throws SQLException {
    return lid.getAllLanguageInterpreters();
  }

  public void exportReqCSV(String filename) {
    lid.exportLangInterpCSV(filename);
  }

  public ArrayList<Integer> getEligibleEmployees(String lanuage) throws SQLException {
    return lid.getEligibleEmployees(lanuage);
  }

  public void updateLanguageRequestWithEmployee(Integer employeeID) throws Exception {
    lid.deleteAllLanguageInterpreter(employeeID);
  }
}
