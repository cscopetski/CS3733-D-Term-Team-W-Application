package edu.wpi.cs3733.d22.teamW.wDB;

import java.io.*;
import org.junit.jupiter.api.*;

class MedEquipManagerTest {
  /*
  DBController dbController = DBController.getDBController();
  CSVController csvController;
  LocationDaoImpl locationDao;
  LocationManager locationManager;

  DBController.MedEquipDaoImpl medi;
  MedEquipRequestDaoImpl merdi;
  MedEquipManager medEquipManager;
  MedEquipRequestManager merc;

  LabServiceRequestDaoImpl labServiceRequestDao;
  LabServiceRequestManager lsrc;

  RequestFactory requestFactory;

  @BeforeEach
  void setUp() {

    try {
      dbController.createTables();
    } catch (SQLException e) {
      e.printStackTrace();
    }

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
    locationManager = new LocationManager(locationDao);

    try {
      medi = new DBController.MedEquipDaoImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    merdi = new MedEquipRequestDaoImpl(statement);
    medEquipManager = new MedEquipManager(medi, merdi);
    merc = new MedEquipRequestManager(merdi, medi);

    labServiceRequestDao = new LabServiceRequestDaoImpl(statement);
    lsrc = new LabServiceRequestManager(labServiceRequestDao);

    requestFactory = RequestFactory.getRequestFactory(merc, lsrc);

    try {
      csvController.populateRequestTables(requestFactory);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @AfterEach
  void reset() {
    requestFactory.resetRequestFactory();
  }

  @Test
  void markClean() {
    String equipID = "BED004";

    MedEquip m = medEquipManager.getMedEquip(equipID);
    try {
      medEquipManager.markClean(m);
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

    MedEquip m = medEquipManager.getMedEquip(equipID);
    try {
      medEquipManager.markInUse(m);
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

    MedEquip m = medEquipManager.getMedEquip(equipID);
    try {
      medEquipManager.markDirty(m);
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
  void add() {

    String medID = "XRY002";

    String type = "XRY";

    String nodeID = "FDEPT00101";

    Integer status = 0;

    try {
      medEquipManager.add(medID, type, nodeID, status);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    MedEquip listEquip = null;

    for (MedEquip m : medi.getAllMedEquip()) {
      if (m.getMedID().equals(medID)) {
        listEquip = m;
      }
    }

    assertEquals(listEquip, new MedEquip(medID, type, nodeID, status));

    try {
      ResultSet r =
          dbController.executeQuery(
              String.format(
                  "SELECT COUNT (*) AS COUNT FROM MEDICALEQUIPMENT WHERE MEDID = '%s'", medID));
      r.next();
      assertEquals(r.getInt("COUNT"), 1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void delete() {

    String medID = "XRY001";

    try {
      medEquipManager.delete(medID);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    MedEquip listEquip = null;

    boolean found = false;

    for (MedEquip m : medi.getAllMedEquip()) {
      if (m.getMedID().equals(medID)) {
        found = true;
      }
    }

    assertEquals(found, false);

    try {
      ResultSet r =
          dbController.executeQuery(
              String.format(
                  "SELECT COUNT (*) AS COUNT FROM MEDICALEQUIPMENT WHERE MEDID = '%s'", medID));
      r.next();
      assertEquals(r.getInt("COUNT"), 0);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getMedEquip() {

    String medID = "XRY001";

    MedEquip med = medEquipManager.getMedEquip(medID);

    MedEquip listEquip = null;

    boolean found = false;

    for (MedEquip m : medi.getAllMedEquip()) {
      if (m.getMedID().equals(medID)) {
        listEquip = m;
      }
    }

    assertEquals(listEquip, med);

    try {
      ResultSet r =
          dbController.executeQuery(
              String.format("SELECT * FROM MEDICALEQUIPMENT WHERE MEDID = '%s'", medID));
      r.next();

      String newMedID = r.getString("MEDID");
      String type = r.getString("TYPE");
      String nodeID = r.getString("NODEID");
      Integer status = r.getInt("STATUS");

      MedEquip newMed = new MedEquip(newMedID, type, nodeID, status);

      assertEquals(newMed, med);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getAll() throws SQLException {
    merdi.setMedEquipRequestList();
    labServiceRequestDao.setLabServiceRequestList();

    ArrayList<MedEquip> medEquips = medEquipManager.getAllMedEquip();

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

      MedEquip controllerMed = medEquips.get(i);
      MedEquip database = medEquipList.get(i);

      assertEquals(controllerMed.equals(database), true);
    }
  }

  @Test
  void exportMedicalEquipmentCSV() {

    String fileName = "TESTMEDEQUIP.csv";
    medEquipManager.exportMedicalEquipmentCSV(fileName);
    ArrayList<MedEquip> medList = medEquipManager.getAllMedEquip();
    File file = new File(fileName);
    InputStream in = null;
    try {
      in = new DataInputStream(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (in == null) {
      System.out.println("Failed to find file " + fileName);
    }

    Scanner sc = new Scanner(in);
    System.out.println("Found File" + fileName);
    // Skip headers
    sc.next();

    ArrayList<String[]> tokensList = new ArrayList<>();

    while (sc.hasNextLine()) {
      String line = "" + sc.nextLine();
      if (!line.isEmpty()) {
        String[] tokens = line.split(",");
        tokensList.add(tokens);
      }
    }
    sc.close(); // closes the scanner

    ArrayList<MedEquip> medEquipList = new ArrayList<>();

    for (String[] s : tokensList) {

      MedEquip med = new MedEquip(s);

      medEquipList.add(med);
    }

    for (int i = 0; i < medList.size(); i++) {
      MedEquip controllerMed = medList.get(i);
      MedEquip csvMed = medEquipList.get(i);

      assertEquals(controllerMed.equals(csvMed), true);
    }
  }
  */
}
