package edu.wpi.cs3733.d22.teamW.wDB;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.*;

class MedEquipControllerTest {

  DBController dbController = DBController.getDBController();
  CSVController csvController;
  LocationDaoImpl locationDao;
  LocationController locationController;

  MedEquipDaoImpl medi;
  MedEquipRequestDaoImpl merdi;
  MedEquipController medEquipController;
  MedEquipRequestController merc;

  LabServiceRequestDaoImpl labServiceRequestDao;
  LabServiceRequestController lsrc;

  RequestFactory requestFactory;

  @BeforeEach
  void setUp() {
    final String locationFileName = "TowerLocations.csv";
    final String medEquipFileName = "MedicalEquipment.csv";
    final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
    final String labServiceRequestFileName = "LabRequests.csv";

    csvController =
        new CSVController(
            locationFileName, medEquipFileName, medEquipRequestFileName, labServiceRequestFileName);

    try {
      csvController.populateEntityTables();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      locationDao = new LocationDaoImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    locationController = new LocationController(locationDao);

    try {
      medi = new MedEquipDaoImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    merdi = new MedEquipRequestDaoImpl();
    medEquipController = new MedEquipController(medi, merdi);
    merc = new MedEquipRequestController(merdi, medi);

    labServiceRequestDao = new LabServiceRequestDaoImpl();
    lsrc = new LabServiceRequestController(labServiceRequestDao);

    requestFactory = RequestFactory.getRequestFactory(merc, lsrc);

    try {
      csvController.populateRequestTables(requestFactory);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void markClean() {
    String equipID = "BED004";

    MedEquip m = medEquipController.getMedEquip(equipID);
    try {
      medEquipController.markClean(m);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    assertEquals(m.getStatus(), 0);

    ArrayList<MedEquip> medEquips = medi.getAllMedEquip();

    int newStatus = -1;

    for (MedEquip med : medEquips) {
      if (med.getMedID().equals(equipID)) {
        newStatus = med.getStatus();
        break;
      }
    }

    assertEquals(0, newStatus);

    try {
      ResultSet r =
          dbController.executeQuery(
              String.format("SELECT STATUS FROM MEDICALEQUIPMENT WHERE MEDID = '%s'", equipID));

      r.next();
      int status = r.getInt("STATUS");

      assertEquals(status, 0);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void markInUse() {
    String equipID = "BED001";

    MedEquip m = medEquipController.getMedEquip(equipID);
    try {
      medEquipController.markInUse(m);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    assertEquals(m.getStatus(), 1);

    ArrayList<MedEquip> medEquips = medi.getAllMedEquip();

    int newStatus = -1;

    for (MedEquip med : medEquips) {
      if (med.getMedID().equals(equipID)) {
        newStatus = med.getStatus();
        break;
      }
    }

    assertEquals(1, newStatus);

    try {
      ResultSet r =
          dbController.executeQuery(
              String.format("SELECT STATUS FROM MEDICALEQUIPMENT WHERE MEDID = '%s'", equipID));

      r.next();
      int status = r.getInt("STATUS");

      assertEquals(status, 1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void markDirty() {
    String equipID = "BED001";

    MedEquip m = medEquipController.getMedEquip(equipID);
    try {
      medEquipController.markDirty(m);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    assertEquals(m.getStatus(), 2);

    ArrayList<MedEquip> medEquips = medi.getAllMedEquip();

    int newStatus = -1;

    for (MedEquip med : medEquips) {
      if (med.getMedID().equals(equipID)) {
        newStatus = med.getStatus();
        break;
      }
    }

    assertEquals(2, newStatus);

    try {
      ResultSet r =
          dbController.executeQuery(
              String.format("SELECT STATUS FROM MEDICALEQUIPMENT WHERE MEDID = '%s'", equipID));

      r.next();
      int status = r.getInt("STATUS");

      assertEquals(status, 2);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void add() {}

  @Test
  void delete() {}

  @Test
  void getMedEquip() {}

  @Test
  void getAll() throws SQLException {
    ArrayList<MedEquip> medEquips = medEquipController.getAll();

    ArrayList<MedEquip> medDaoEquips = medi.getAllMedEquip();

    for (int i = 0; i < medEquips.size(); i++) {

      assertEquals(medEquips.get(i), medDaoEquips.get(i));
    }

    ResultSet resultSet =
        DBController.getDBController().executeQuery("SELECT * FROM MEDICALEQUIPMENT");

    ArrayList<MedEquip> medEquipList = new ArrayList<>();

    String[] medEquipData = new String[4];

    while (resultSet.next()) {

      for (int i = 0; i < medEquipData.length; i++) {
        medEquipData[i] = resultSet.getString(i + 1);
      }

      medEquipList.add(new MedEquip(medEquipData));
    }

    for (int i = 0; i < medEquips.size(); i++) {

      assertEquals(medEquips.get(i), medEquipList.get(i));
    }
  }

  @Test
  void exportMedicalEquipmentCSV() {}
}
