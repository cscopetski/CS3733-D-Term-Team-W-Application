package edu.wpi.cs3733.d22.teamW.wDB;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class CSVController {

  final String locationFileName = "TowerLocations.csv";
  final String medEquipFileName = "MedicalEquipment.csv";
  final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
  final String labServiceRequestFileName = "LabRequests.csv";
  final String employeeFileName = "Employees.csv";
  final String medRequestFileName = "MedRequests.csv";
  final String flowerRequestFileName = "FlowerRequests.csv";
  final String computerServiceRequestFileName = "ComputerServiceRequest.csv";
  final String sanitationRequestFileName = "SanitationRequests.csv";
  final String languageFileName = "Languages.csv";
  final String languageInterpFileName = "LanguageInterpreter.csv";
  final String giftDeliveryRequestFileName = "GiftDeliveryRequest.csv";
  final String cleaningRequestFileName = "CleaningRequest.csv";
  final String mealRequestFileName = "MealRequest.csv";
  final String securityRequestFileName = "SecurityRequest.csv";
  final String languageRequestFileName = "LanguageRequests.csv";
  final String userImageFileName = "UserImages.csv";
  final String internalPatientTransportationRequestFileName = "InternalPatientTransportationRequests.csv";
  final String externalTransporationRequestFileName = "ExternalTransportationRequests.csv";

  final String locationNewFileName = "CSVs/TowerLocations_teamW_new.csv";
  final String medEquipNewFileName = "CSVs/MedicalEquipment_teamW_new.csv";
  final String medEquipRequestNewFileName = "CSVs/MedicalEquipmentRequest_teamW_new.csv";
  final String labServiceRequestNewFileName = "CSVs/LabRequests_teamW_new.csv";
  final String employeeNewFileName = "CSVs/Employees_teamW_new.csv";
  final String medRequestNewFileName = "CSVs/MedRequests_teamW_new.csv";
  final String flowerRequestNewFileName = "CSVs/FlowerRequests_teamW_new.csv";
  final String computerServiceRequestNewFileName = "CSVs/ComputerServiceRequest_teamW_new.csv";
  final String sanitationRequestNewFileName = "CSVs/SanitationRequests_teamW_new.csv";
  final String languageNewFileName = "CSVs/Languages_teamW_new.csv";
  final String languageInterpNewFileName = "CSVs/LanguageInterpreter_teamW_new.csv";
  final String giftDeliveryRequestNewFileName = "CSVs/GiftDeliveryRequest_teamW_new.csv";
  final String cleaningRequestNewFileName = "CSVs/CleaningRequest_teamW_new.csv";
  final String mealRequestNewFileName = "CSVs/MealRequest_teamW_new.csv";
  final String securityRequestNewFileName = "CSVs/SecurityRequest_teamW_new.csv";
  final String languageRequestNewFileName = "CSVs/LanguageRequests_teamW_new.csv";
  final String userImageNewFileName = "CSVs/UserImages_teamW_new.csv";
  final String internalPatientTransportationRequestNewFileName = "CSVs/InternalPatientTransportationRequests_teamW_new.csv";
  final String externalTransporationRequestNewFileName = "CSVs/ExternalTransportationRequests_teamW_new.csv";

  private RequestFactory requestFactory = RequestFactory.getRequestFactory();

  public CSVController() {}

  public void populateTables() throws Exception {
    insertIntoEmpTable(importCSV(employeeFileName, employeeNewFileName));
    insertIntoLocationsTable(importCSV(locationFileName, locationNewFileName));
    insertIntoMedEquipTable(importCSV(medEquipFileName, medEquipNewFileName));
    insertIntoLanguagesTable(importCSV(languageFileName, languageNewFileName));
    insertIntoLanguageInterpreterTable(importCSV(languageInterpFileName, languageInterpNewFileName));

    insertIntoMedEquipReqTable(importCSV(medEquipRequestFileName, medEquipRequestNewFileName));
    insertIntoLabReqTable(importCSV(labServiceRequestFileName, labServiceRequestNewFileName));
    insertMedRequestTable(importCSV(medRequestFileName, medRequestNewFileName));
    insertFlowerRequestTable(importCSV(flowerRequestFileName, flowerRequestNewFileName));
    insertComputerServiceRequestTable(importCSV(computerServiceRequestFileName, computerServiceRequestNewFileName));
    insertSanitationServiceRequestTable(importCSV(sanitationRequestFileName, sanitationRequestNewFileName));
    insertCleaningRequestTable(importCSV(cleaningRequestFileName, cleaningRequestNewFileName));
    insertGiftDeliveryRequestTable(importCSV(giftDeliveryRequestFileName, giftDeliveryRequestNewFileName));
    insertMealRequestTable(importCSV(mealRequestFileName, mealRequestNewFileName));
    insertSecurityRequestTable(importCSV(securityRequestFileName, securityRequestNewFileName));
    insertLanguageRequestTable(importCSV(languageRequestFileName, languageRequestNewFileName));
    insertUserImageTable(importCSV(userImageFileName, userImageNewFileName));
    insertInternalPatientTransportationRequestTable(importCSV(internalPatientTransportationRequestFileName, internalPatientTransportationRequestNewFileName));
    insertExternatlTransportationRequestTable(importCSV(externalTransporationRequestFileName, externalTransporationRequestNewFileName));
  }

  public ArrayList<String[]> importCSV(String backupFileName, String newFileName)
          throws FileNotFoundException {

    String fileName = newFileName;

    InputStream in;
    try {
      in = new FileInputStream(fileName);
    } catch (FileNotFoundException e) {
      System.out.println("Failed to find file " + fileName);
      fileName = backupFileName;

      in = getClass().getClassLoader().getResourceAsStream("edu/wpi/cs3733/d22/teamW/wDB/original/CSVs/" + fileName);
      if (in == null) {
        System.err.println("Failed to find file " + fileName);
        throw (new FileNotFoundException());
      }
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

  private void insertLanguageRequestTable(ArrayList<String[]> tokens) throws Exception {
    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));

      LanguageRequest lr =
          (LanguageRequest) requestFactory.getRequest(RequestType.LanguageRequest, fields, true);

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
    LanguageManager lm = LanguageManager.getLanguageManager();
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
      } catch (Exception exception) {
        exception.printStackTrace();
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

  private void insertSanitationServiceRequestTable(ArrayList<String[]> tokens) throws Exception {
    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));
      SanitationRequest gdr =
          (SanitationRequest)
              requestFactory.getRequest(RequestType.SanitationService, fields, true);
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

  private void insertCleaningRequestTable(ArrayList<String[]> tokens) throws Exception {

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));
      CleaningRequest gdr =
          (CleaningRequest) requestFactory.getRequest(RequestType.CleaningRequest, fields, true);
    }
  }

  private void insertSecurityRequestTable(ArrayList<String[]> tokens) throws Exception {

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));
      SecurityRequest gdr =
          (SecurityRequest) requestFactory.getRequest(RequestType.SecurityService, fields, true);
    }
  }

  private void insertUserImageTable(ArrayList<String[]> tokens) throws SQLException {
    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));
      UserImageManager.getUserImageManager().addUserImage(new UserImage(fields));
    }
  }

  private void insertInternalPatientTransportationRequestTable(ArrayList<String[]> tokens) throws Exception {

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));
      InternalPatientTransportationRequest gdr =
              (InternalPatientTransportationRequest) requestFactory.getRequest(RequestType.InternalPatientTransportationRequest, fields, true);
    }
  }

  private void insertExternatlTransportationRequestTable(ArrayList<String[]> tokens) throws Exception {

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));
      ExternalTransportRequest gdr =
              (ExternalTransportRequest) requestFactory.getRequest(RequestType.ExternalTransportRequest, fields, true);
    }
  }
}
