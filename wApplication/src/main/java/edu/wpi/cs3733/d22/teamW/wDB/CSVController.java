package edu.wpi.cs3733.d22.teamW.wDB;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
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

  private RequestFactory requestFactory = RequestFactory.getRequestFactory();

  public CSVController(
      String locationFileName,
      String medEquipFileName,
      String medEquipRequestFileName,
      String labServiceRequestFileName,
      String employeeFileName,
      String medRequestFileName) {
    this.locationFileName = locationFileName;
    this.medEquipFileName = medEquipFileName;
    this.medEquipRequestFileName = medEquipRequestFileName;
    this.labServiceRequestFileName = labServiceRequestFileName;
    this.employeeFileName = employeeFileName;
    this.medRequestFileName = medRequestFileName;
  }

  public void populateTables() throws FileNotFoundException, SQLException {
    insertIntoEmpTable(importCSV(employeeFileName));
    insertIntoLocationsTable(importCSV(locationFileName));
    insertIntoMedEquipTable(importCSV(medEquipFileName));

    insertIntoMedEquipReqTable(importCSV(medEquipRequestFileName));
    insertIntoLabReqTable(importCSV(labServiceRequestFileName));
    insertMedRequestTable(importCSV(medRequestFileName));
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

  /**
   * Inserts a list of locations objects into the Location table in the database
   *
   * @param tokens List of Location Objects to populate the Location Table
   * @throws SQLException if insertion fails
   */
  public void insertIntoLocationsTable(ArrayList<String[]> tokens) throws SQLException {

    ArrayList<Location> locationsList = new ArrayList<>();

    for (String[] s : tokens) {
      Location l = new Location(s);
      locationsList.add(l);
    }

    for (Location l : locationsList) {
      // add location objects to database
      try {
        LocationManager.getLocationManager()
            .addLocation(
                l.getNodeID(),
                l.getxCoord(),
                l.getyCoord(),
                l.getFloor(),
                l.getBuilding(),
                l.getNodeType(),
                l.getLongName(),
                l.getShortName());

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
        MedEquipManager.getMedEquipManager()
            .add(m.getMedID(), m.getType(), m.getNodeID(), m.getStatus());
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

      MedEquipRequest mER =
          (MedEquipRequest) requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields);

      medEquipReqList.add(mER);
    }
  }

  private void insertIntoLabReqTable(ArrayList<String[]> tokens) throws SQLException {
    // ArrayList<LabServiceRequest> labReqList = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));

      LabServiceRequest lSR =
          (LabServiceRequest) requestFactory.getRequest(RequestType.LabServiceRequest, fields);

      // labReqList.add(lSR);
    }
  }

  private void insertMedRequestTable(ArrayList<String[]> tokens) throws SQLException {
    // ArrayList<MedRequest> medReqLists = new ArrayList<>();

    for (String[] s : tokens) {
      ArrayList<String> fields = new ArrayList<>();
      fields.addAll(Arrays.asList(s));

      MedRequest mr = (MedRequest) requestFactory.getRequest(RequestType.MedicineDelivery, fields);

      // medReqLists.add(mr);
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
        EmployeeManager.getEmployeeManager()
            .addEmployee(
                e.getEmployeeID(),
                e.getFirstName(),
                e.getLastName(),
                e.getType(),
                e.getEmail(),
                e.getPhoneNumber(),
                e.getAddress(),
                e.getUsername(),
                e.getPassword(),
                e.getSalt());
      } catch (SQLException s) {
        System.out.println("Connection failed. Check output console.");
        s.printStackTrace();
        throw (s);
      }
    }
  }
}
