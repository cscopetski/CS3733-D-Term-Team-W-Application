package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeMessageManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.HighScoreManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.HighScore;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws Exception {

    DBConnectionMode.INSTANCE.setEmbeddedConnection();

    DBController.getDBController();

    CSVController csvController = new CSVController();

    try {
      csvController.populateTables();
    } catch (Exception e) {
      e.printStackTrace();
    }

    for (int i = 1; i <= 14; i++) {
      HighScoreManager.getHighScoreManager().addHighScore(new HighScore(i, 0, 0));
    }

    App.launch(App.class, args);
  }
}
