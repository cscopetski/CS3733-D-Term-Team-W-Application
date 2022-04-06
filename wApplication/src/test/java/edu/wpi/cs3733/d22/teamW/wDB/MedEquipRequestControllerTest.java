package edu.wpi.cs3733.d22.teamW.wDB;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedEquipRequestControllerTest {

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
  void checkStart() throws SQLException {
    Request request = RequestFactory.getRequestFactory().findRequest(5);
    String test = merc.checkStart(request);
    assertNull(test);
  }

  @Test
  void cancelRequest() throws SQLException {
    Request request = RequestFactory.getRequestFactory().findRequest(5);
    merc.cancelRequest(request);
    assertEquals(request.getStatus(), 3);
  }

  @Test
  void completeRequest() throws SQLException {
    Request request = RequestFactory.getRequestFactory().findRequest(8);
    merc.completeRequest(request);
    assertEquals(request.getStatus(), 2);
  }

  @Test
  void checkNext() throws SQLException {
    merc.checkNext("BED001");
  }

  @Test
  void getNext() {
    Request request = merc.getNext("BED001");
    assertEquals(2, request.getRequestID());
  }

  @Test
  void getRequest() {
    Request request = merc.getRequest(2);
    assertEquals(request.getRequestID(), 2);
  }

  @Test
  void addRequest() throws SQLException {
    ArrayList<String> fields3 = new ArrayList<>();

    fields3.add("XRY");
    fields3.add("wSTOR001L1");
    fields3.add("JOE2 NAME");
    fields3.add("" + 0);
    Request request = merc.addRequest(11, fields3);
    assertEquals(request.getRequestID(), 11);
  }

  @Test
  void getAllMedEquipRequests() {
    ArrayList<MedEquipRequest> requests = merc.getAllMedEquipRequests();
    Request request = null;
    for (Request r : requests) {
      if (r.getRequestID() == 2) {
        request = r;
      }
    }
    assertEquals(request.getRequestID(), 2);
  }
}
