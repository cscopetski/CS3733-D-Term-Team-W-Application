package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.ChatManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Chat;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
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

    App.launch(App.class, args);
  }
}
