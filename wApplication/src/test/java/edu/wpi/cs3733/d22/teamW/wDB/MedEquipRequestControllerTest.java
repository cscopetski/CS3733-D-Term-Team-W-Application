package edu.wpi.cs3733.d22.teamW.wDB;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedEquipRequestControllerTest {

  DBController dbController;
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

  @BeforeAll
  void setUp() throws SQLException, FileNotFoundException {
    final String locationFileName = "TowerLocations.csv";
    final String medEquipFileName = "MedicalEquipment.csv";
    final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
    final String labServiceRequestFileName = "LabRequests.csv";

    dbController = DBController.getDBController();

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
  }

  @BeforeEach
  void setup2() throws SQLException, FileNotFoundException {
    locationDao = new LocationDaoImpl();
    locationController = new LocationController(locationDao);

    medi = new MedEquipDaoImpl();
    merdi = new MedEquipRequestDaoImpl();
    medEquipController = new MedEquipController(medi, merdi);
    merc = new MedEquipRequestController(merdi, medi);

    labServiceRequestDao = new LabServiceRequestDaoImpl();
    lsrc = new LabServiceRequestController(labServiceRequestDao);

    requestFactory = RequestFactory.getRequestFactory(merc, lsrc);

    csvController.populateRequestTables(requestFactory);
  }

  @Test
  void checkStart() {
    Request request = RequestFactory.getRequestFactory().findRequest(6);
    String test = merc.checkStart();
  }

  @Test
  void cancelRequest() {}

  @Test
  void completeRequest() {}

  @Test
  void checkNext() {}

  @Test
  void getNext() {}

  @Test
  void getRequest() {}

  @Test
  void addRequest() {}

  @Test
  void getAllMedEquipRequests() {}
}
