package edu.wpi.cs3733.d22.teamW.wDB;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DBControllerTest {

  DBController dbController;

  @Test
  void getDBController() {
    dbController = DBController.getDBController();

    assertTrue(dbController != null);
  }
}
