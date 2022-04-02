package edu.wpi.cs3733.d22.teamW.wDB;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class CSVController {

  private String locationFileName;
  private String medEquipFileName;
  private String medEquipRequestFileName;

  public CSVController(
      String locationFileName, String medEquipFileName, String medEquipRequestFileName) {
    this.locationFileName = locationFileName;
    this.medEquipFileName = medEquipFileName;
    this.medEquipRequestFileName = medEquipRequestFileName;
  }

  public void populateTables() throws FileNotFoundException, SQLException {
    insertIntoLocationsTable(importCSV(locationFileName));
    insertIntoMedEquipTable(importCSV(medEquipFileName));
    insertIntoMedEquipReqTable(importCSV(medEquipRequestFileName));
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
      medEquipReqList.add(new MedEquipRequest(s));
    }

    for (MedEquipRequest m : medEquipReqList) {
      // add location objects to database
      try {
        DBController.getDBController()
            .execute("INSERT INTO MEDICALEQUIPMENTREQUESTS VALUES(" + m.toValuesString() + ")");
      } catch (SQLException e) {
        System.out.println("Connection failed. Check output console.");
        e.printStackTrace();
        throw (e);
      }
    }
  }
}
