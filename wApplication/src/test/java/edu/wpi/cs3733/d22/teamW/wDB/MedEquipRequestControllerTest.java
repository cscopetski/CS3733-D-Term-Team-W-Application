package edu.wpi.cs3733.d22.teamW.wDB;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
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

  @AfterEach
  void reset() {
    try {
      dbController.createTables();
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
    merc.exportMedEquipRequestCSV("cancelRequest.csv");
    Request request = RequestFactory.getRequestFactory().findRequest(5);
    merc.cancelRequest(request);
    assertEquals(request.getStatus(), 3);
    merc.exportMedEquipRequestCSV("cancelRequest2.csv");
  }

  @Test
  void completeRequest() throws SQLException {
    merc.exportMedEquipRequestCSV("completeRequest1.csv");
    ArrayList<String> fields = new ArrayList<>();

    fields.add("XRY");
    fields.add("wSTOR001L1");
    fields.add("JOE NAME");
    fields.add("" + 0);

    ArrayList<String> fields2 = new ArrayList<>();

    fields2.add("XRY");
    fields2.add("wSTOR001L1");
    fields2.add("JOE2 NAME");
    fields2.add("" + 1);

    ArrayList<String> fields3 = new ArrayList<>();

    fields3.add("XRY");
    fields3.add("wSTOR001L1");
    fields3.add("JOE2 NAME");
    fields3.add("" + 0);
    merc.exportMedEquipRequestCSV("completeRequest2.csv");
    Request test2 = requestFactory.getRequest("MEDEQUIPREQUEST", fields);
    Request test3 = requestFactory.getRequest("MEDEQUIPREQUEST", fields2);
    Request test4 = requestFactory.getRequest("MEDEQUIPREQUEST", fields3);

    Request request = RequestFactory.getRequestFactory().findRequest(5);
    merc.completeRequest(request);
    merc.exportMedEquipRequestCSV("completeRequest3.csv");
    assertEquals(request.getStatus(), 2);
    assertEquals(test3.getStatus(), 1);
  }

  @Test
  void checkNext() throws SQLException {
    ArrayList<String> fields = new ArrayList<>();

    fields.add("XRY");
    fields.add("wSTOR001L1");
    fields.add("JOE NAME");
    fields.add("" + 0);

    ArrayList<String> fields2 = new ArrayList<>();

    fields2.add("XRY");
    fields2.add("wSTOR001L1");
    fields2.add("JOE2 NAME");
    fields2.add("" + 1);

    ArrayList<String> fields3 = new ArrayList<>();

    fields3.add("XRY");
    fields3.add("wSTOR001L1");
    fields3.add("JOE2 NAME");
    fields3.add("" + 0);

    Request test2 = requestFactory.getRequest("MEDEQUIPREQUEST", fields);
    Request test3 = requestFactory.getRequest("MEDEQUIPREQUEST", fields2);
    Request test4 = requestFactory.getRequest("MEDEQUIPREQUEST", fields3);

    Request request = RequestFactory.getRequestFactory().findRequest(5);

    merc.checkNext("XRY001");
    merc.exportMedEquipRequestCSV("checkNext.csv");
    assertEquals(request.getStatus(), 1);
    assertEquals(test2.getRequestID(), 22);
  }

  @Test
  void getNext() throws SQLException {
    ArrayList<String> fields = new ArrayList<>();

    fields.add("XRY");
    fields.add("wSTOR001L1");
    fields.add("JOE NAME");
    fields.add("" + 0);

    ArrayList<String> fields2 = new ArrayList<>();

    fields2.add("XRY");
    fields2.add("wSTOR001L1");
    fields2.add("JOE2 NAME");
    fields2.add("" + 1);

    ArrayList<String> fields3 = new ArrayList<>();

    fields3.add("XRY");
    fields3.add("wSTOR001L1");
    fields3.add("JOE2 NAME");
    fields3.add("" + 0);

    Request test2 = requestFactory.getRequest("MEDEQUIPREQUEST", fields);
    Request test3 = requestFactory.getRequest("MEDEQUIPREQUEST", fields2);
    Request test4 = requestFactory.getRequest("MEDEQUIPREQUEST", fields3);

    Request request2 = merc.getNext("XRY001");
    merc.exportMedEquipRequestCSV("getNext.csv");
    assertEquals(test3.getRequestID(), request2.getRequestID());
  }

  @Test
  void getRequest() {
    Request request = merc.getRequest(2);
    merc.exportMedEquipRequestCSV("getRequest.csv");
    assertEquals(request.getRequestID(), 2);
  }

  @Test
  void addRequest() throws SQLException {
    merc.exportMedEquipRequestCSV("addRequest1.csv");
    ArrayList<String> fields3 = new ArrayList<>();

    fields3.add("XRY");
    fields3.add("wSTOR001L1");
    fields3.add("JOE2 NAME");
    fields3.add("" + 0);
    merc.exportMedEquipRequestCSV("addRequest2.csv");
    Request request = merc.addRequest(11, fields3);
    merc.exportMedEquipRequestCSV("addRequest3.csv");
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
    merc.exportMedEquipRequestCSV("getAllMedEquipRequests.csv");
    assertEquals(request.getRequestID(), 2);
  }
}
