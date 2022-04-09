package edu.wpi.cs3733.d22.teamW.wDB;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedEquipRequestControllerTest {

  DBController dbController = DBController.getDBController();
  CSVController csvController;
  LocationDaoImpl locationDao;
  LocationManager locationManager;

  MedEquipDaoImpl medi;
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
      medi = new MedEquipDaoImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    merdi = new MedEquipRequestDaoImpl(statement);
    medEquipManager = new MedEquipManager(medi, merdi);
    merc = new MedEquipRequestManager(merdi, medi);

    labServiceRequestDao = new LabServiceRequestDaoImpl(statement);
    lsrc = new LabServiceRequestManager(labServiceRequestDao);
    requestFactory = RequestFactory.getRequestFactory();

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
    assertEquals(request.getStatusInt(), 3);

  }

  @Test
  void completeRequest() throws SQLException {

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
    Request test2 = requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields);
    Request test3 = requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields2);
    Request test4 = requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields3);

    Request request = RequestFactory.getRequestFactory().findRequest(5);
    merc.completeRequest(request);
    assertEquals(request.getStatusInt(), 2);
    assertEquals(test3.getStatusInt(), 1);
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
    assertEquals(request.getStatusInt(), 1);
    assertEquals(test3.getRequestID(), 22);
    assertEquals(test3.getStatusInt(), 1);
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
    assertEquals(test3.getRequestID(), request2.getRequestID());
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

  @Test
  void exportMedEquipRequestCSV() {

    String fileName = "TESTMEDEQUIPREQUEST.csv";
    merc.exportMedEquipRequestCSV(fileName);
    ArrayList<MedEquipRequest> medReqList = merc.getAllMedEquipRequests();
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

    ArrayList<MedEquipRequest> medEquipReqList = new ArrayList<>();

    for (String[] s : tokensList) {

      MedEquipRequest medEquipRequest = new MedEquipRequest(s);

      medEquipReqList.add(medEquipRequest);
    }

    for (int i = 0; i < medReqList.size(); i++) {
      MedEquipRequest controllerMedReq = medReqList.get(i);
      MedEquipRequest csvMedReq = medEquipReqList.get(i);
      System.out.println(controllerMedReq.toValuesString());
      System.out.println(csvMedReq.toValuesString());
      assertEquals(controllerMedReq.equals(csvMedReq), true);
    }
  }
}
