package edu.wpi.cs3733.d22.teamW.wDB;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class CSVController {

  private String locationFileName;
  private String medEquipFileName;
  private String medEquipRequestFileName;
  private String labServiceRequestFileName;
  private String employeeFileName;

  private RequestFactory requestFactory = null;

  public CSVController(
      String locationFileName,
      String medEquipFileName,
      String medEquipRequestFileName,
      String labServiceRequestFileName,
      String employeeFileName) {
    this.locationFileName = locationFileName;
    this.medEquipFileName = medEquipFileName;
    this.medEquipRequestFileName = medEquipRequestFileName;
    this.labServiceRequestFileName = labServiceRequestFileName;
    this.employeeFileName = employeeFileName;
  }

  public void setRequestFactory(RequestFactory requestFactory) {
    this.requestFactory = requestFactory;
  }

  public void populateEntityTables() throws FileNotFoundException, SQLException {
    insertIntoLocationsTable(importCSV(locationFileName));
    insertIntoMedEquipTable(importCSV(medEquipFileName));
    insertIntoEmpTable(importCSV(employeeFileName));
  }

  public void populateRequestTables(RequestFactory requestFactory)
      throws FileNotFoundException, SQLException {
    setRequestFactory(requestFactory);
    insertIntoMedEquipReqTable(importCSV(medEquipRequestFileName));
    insertIntoLabReqTable(importCSV(labServiceRequestFileName));
  }

  private ArrayList<String[]> importCSV(String fileName) throws FileNotFoundException {

    InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
    if (in == null) {
      System.out.println("Failed to find file " + fileName);
      throw (new FileNotFoundException());
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
    return tokensList;
  }

  /**
   * Inserts a list of locations objects into the Location table in the database
   *
   * @param tokens List of Location Objects to populate the Location Table
   * @throws SQLException if insertion fails
   */
  private void insertIntoLocationsTable(ArrayList<String[]> tokens) throws SQLException {

    ArrayList<Location> locationsList = new ArrayList<>();

    for (String[] s : tokens) {
      Location l = new Location(s);
      locationsList.add(l);
    }

    for (Location l : locationsList) {
      // add location objects to database
      try {
        String test = String.format("INSERT INTO LOCATIONS VALUES(%s)", l.toValuesString());

        DBController.getDBController().execute(test);
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
  private void insertIntoMedEquipTable(ArrayList<String[]> tokens) throws SQLException {
    ArrayList<MedEquip> medEquipList = new ArrayList<>();

    for (String[] s : tokens) {
      medEquipList.add(new MedEquip(s));
    }

    for (MedEquip m : medEquipList) {
      // add location objects to database
      try {
        DBController.getDBController()
            .execute("INSERT INTO MEDICALEQUIPMENT VALUES(" + m.toValuesString() + ")");
      } catch (SQLException e) {
        System.out.println("Connection failed. Check output console.");
        e.printStackTrace();
        throw (e);
      }
    }
  }

  private void insertIntoMedEquipReqTable(ArrayList<String[]> tokens) throws SQLException {
    ArrayList<MedEquipRequest> medEquipReqList = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));

      MedEquipRequest mER = (MedEquipRequest) requestFactory.getRequest("MEDEQUIPREQUEST", fields);

      medEquipReqList.add(mER);
    }
  }

  private void insertIntoLabReqTable(ArrayList<String[]> tokens) throws SQLException {
    ArrayList<LabServiceRequest> labReqList = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));

      LabServiceRequest lSR =
          (LabServiceRequest) requestFactory.getRequest("LABSERVICEREQUEST", fields);

      labReqList.add(lSR);
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
        DBController.getDBController()
            .execute("INSERT INTO EMPLOYEES VALUES(" + e.toValuesString() + ")");
      } catch (SQLException s) {
        System.out.println("Connection failed. Check output console.");
        s.printStackTrace();
        throw (s);
      }
    }
  }
}
