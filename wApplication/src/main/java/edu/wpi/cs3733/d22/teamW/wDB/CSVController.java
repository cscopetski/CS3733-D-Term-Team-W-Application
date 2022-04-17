package edu.wpi.cs3733.d22.teamW.wDB;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class CSVController {

  private String locationFileName;
  private String medEquipFileName;
  private String medEquipRequestFileName;
  private String labServiceRequestFileName;
  private String employeeFileName;
  private String medRequestFileName;
  private String flowerRequestFileName;
  private String computerServiceRequestFileName;
  private String sanitationRequestFileName;
  private String languageFileName;
  private String languageInterpFileName;
  private String giftDeliveryRequestFileName;
  private String mealRequestFileName;

  private RequestFactory requestFactory = RequestFactory.getRequestFactory();

  public CSVController(
      String locationFileName,
      String medEquipFileName,
      String medEquipRequestFileName,
      String labServiceRequestFileName,
      String employeeFileName,
      String medRequestFileName,
      String flowerRequestFileName,
      String computerServiceRequestFileName,
      String sanitationRequestFileName,
      String languageFileName,
      String languageInterpFileName,
      String giftDeliveryRequestFileName,
      String mealRequestFileName) {

    this.locationFileName = locationFileName;
    this.medEquipFileName = medEquipFileName;
    this.medEquipRequestFileName = medEquipRequestFileName;
    this.labServiceRequestFileName = labServiceRequestFileName;
    this.employeeFileName = employeeFileName;
    this.medRequestFileName = medRequestFileName;
    this.flowerRequestFileName = flowerRequestFileName;
    this.computerServiceRequestFileName = computerServiceRequestFileName;
    this.sanitationRequestFileName = sanitationRequestFileName;
    this.languageFileName = languageFileName;
    this.languageInterpFileName = languageInterpFileName;
    this.giftDeliveryRequestFileName = giftDeliveryRequestFileName;
    this.mealRequestFileName = mealRequestFileName;
  }

  public void populateTables() throws Exception {
    insertIntoEmpTable(importCSV(employeeFileName));
    insertIntoLocationsTable(importCSV(locationFileName));
    insertIntoMedEquipTable(importCSV(medEquipFileName));
    insertIntoLanguagesTable(importCSV(languageFileName));
    insertIntoLanguageInterpreterTable(importCSV(languageInterpFileName));

    insertIntoMedEquipReqTable(importCSV(medEquipRequestFileName));
    insertIntoLabReqTable(importCSV(labServiceRequestFileName));
    insertMedRequestTable(importCSV(medRequestFileName));
    insertFlowerRequestTable(importCSV(flowerRequestFileName));
    insertComputerServiceRequestTable(importCSV(computerServiceRequestFileName));
    insertGiftDeliveryRequestTable(importCSV(giftDeliveryRequestFileName));
    insertMealRequestTable(importCSV(mealRequestFileName));
  }

  public ArrayList<String[]> importCSV(String fileName) throws FileNotFoundException {

    InputStream in =
        getClass()
            .getClassLoader()
            .getResourceAsStream("edu/wpi/cs3733/d22/teamW/wDB/CSVs/" + fileName);
    if (in == null) {
      System.out.println("Failed to find file " + fileName);
      throw (new FileNotFoundException());
    }
    Scanner sc = new Scanner(in);
    System.out.println("Found File" + fileName);
    // Skip headers
    try {
      sc.next();
    } catch (NoSuchElementException e) {
      System.out.println(String.format("FILE %s IS EMPTY", fileName));
    }

    ArrayList<String[]> tokensList = new ArrayList<>();

    while (sc.hasNextLine()) {
      String line = "" + sc.nextLine();
      if (!line.isEmpty()) {
        String[] tokens = line.split(",");
        tokensList.add(tokens);
      }
    }
    sc.close(); // closes the scanner
    return tokensList;
  }

  public ArrayList<String[]> importCSVfromFile(File file) throws FileNotFoundException {

    InputStream in = new FileInputStream(file);
    Scanner sc = new Scanner(in);
    // Skip headers
    try {
      sc.next();
    } catch (NoSuchElementException e) {
      System.out.println(String.format("FILE IS EMPTY"));
    }

    ArrayList<String[]> tokensList = new ArrayList<>();

    while (sc.hasNextLine()) {
      String line = "" + sc.nextLine();
      if (!line.isEmpty()) {
        String[] tokens = line.split(",");
        tokensList.add(tokens);
      }
    }
    sc.close(); // closes the scanner
    return tokensList;
  }

  /**
   * Inserts a list of locations objects into the Location table in the database
   *
   * @param tokens List of Location Objects to populate the Location Table
   * @throws SQLException if insertion fails
   */
  public void insertIntoLocationsTable(ArrayList<String[]> tokens) throws SQLException {

    ArrayList<Location> locationsList = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<String>();
      fields.addAll(Arrays.asList(s));
      locationsList.add(new Location(fields));
    }

    for (Location l : locationsList) {
      // add location objects to database
      try {
        LocationManager.getLocationManager().addLocation(l);

      } catch (SQLException e) {
        System.out.println("Connection failed. Check output console.");
        e.printStackTrace();
        throw (e);
      }
    }
  }

  /**
   * Inserts a list of locations objects into the Location table in the database
   *
   * @param tokens List of Medical Equipment Objects to populate the Location Table
   * @throws SQLException if insertion fails
   */
  private void insertIntoMedEquipTable(ArrayList<String[]> tokens)
      throws SQLException, NonExistingMedEquip {
    ArrayList<MedEquip> medEquipList = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<String>();
      fields.addAll(Arrays.asList(s));
      medEquipList.add(new MedEquip(fields));
    }

    for (MedEquip m : medEquipList) {
      // add location objects to database
      try {
        MedEquipManager.getMedEquipManager().add(m);
      } catch (SQLException e) {
        System.out.println("Connection failed. Check output console.");
        e.printStackTrace();
        throw (e);
      }
    }
  }

  private void insertIntoMedEquipReqTable(ArrayList<String[]> tokens) throws Exception {
    ArrayList<MedEquipRequest> medEquipReqList = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));

      MedEquipRequest mER =
          (MedEquipRequest)
              requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields, true);

      medEquipReqList.add(mER);
    }
  }

  private void insertIntoLabReqTable(ArrayList<String[]> tokens) throws Exception {

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));

      LabServiceRequest lSR =
          (LabServiceRequest)
              requestFactory.getRequest(RequestType.LabServiceRequest, fields, true);
    }
  }

  private void insertMedRequestTable(ArrayList<String[]> tokens) throws Exception {
    // ArrayList<MedRequest> medReqLists = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));

      MedRequest mr =
          (MedRequest) requestFactory.getRequest(RequestType.MedicineDelivery, fields, true);

      // medReqLists.add(mr);
    }
  }

  private void insertFlowerRequestTable(ArrayList<String[]> tokens) throws Exception {

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));

      FlowerRequest fr =
          (FlowerRequest) requestFactory.getRequest(RequestType.FlowerRequest, fields, true);
    }
  }

  private void insertIntoLanguagesTable(ArrayList<String[]> tokens) throws SQLException {
    LanguageManager lm = LanguageManager.getLocationManager();
    for (String[] s : tokens) {
      lm.addLanguage(s[0]);
    }
  }

  private void insertIntoLanguageInterpreterTable(ArrayList<String[]> tokens) throws SQLException {
    ArrayList<LanguageInterpreter> langInterps = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<String>();
      fields.addAll(Arrays.asList(s));
      langInterps.add(new LanguageInterpreter(fields));
    }

    for (LanguageInterpreter e : langInterps) {
      // add location objects to database
      try {
        LanguageInterpreterManager.getLanguageInterpreterManager().addLanguageInterpreter(e);
      } catch (SQLException s) {
        System.out.println("Connection failed. Check output console.");
        s.printStackTrace();
        throw (s);
      }
    }
  }

  private void insertIntoEmpTable(ArrayList<String[]> tokens) throws SQLException {
    ArrayList<Employee> employees = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<String>();
      fields.addAll(Arrays.asList(s));
      employees.add(new Employee(fields));
    }

    for (Employee e : employees) {
      // add location objects to database
      try {
        EmployeeManager.getEmployeeManager().addEmployee(e);
      } catch (SQLException s) {
        System.out.println("Connection failed. Check output console.");
        s.printStackTrace();
        throw (s);
      }
    }
  }

  private void insertComputerServiceRequestTable(ArrayList<String[]> tokens) throws Exception {

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));
      ComputerServiceRequest csr =
          (ComputerServiceRequest)
              requestFactory.getRequest(RequestType.ComputerServiceRequest, fields, true);
    }
  }

  private void insertGiftDeliveryRequestTable(ArrayList<String[]> tokens) throws Exception {

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));
      GiftDeliveryRequest gdr =
          (GiftDeliveryRequest) requestFactory.getRequest(RequestType.GiftDelivery, fields, true);
    }
  }

  private void insertMealRequestTable(ArrayList<String[]> tokens) throws Exception {

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));
      MealRequest gdr =
          (MealRequest) requestFactory.getRequest(RequestType.MealDelivery, fields, true);
    }
  }
}
