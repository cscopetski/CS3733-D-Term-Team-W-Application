package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipStatus;
import java.io.*;
import java.sql.*;
import java.util.*;

public class MedEquipDaoImpl implements MedEquipDao {

  Statement statement;

  MedEquipDaoImpl(Statement statement) throws SQLException {
    this.statement = statement;
    dropTable();
  }

  void dropTable() {
    try {
      statement.execute("DROP TABLE MEDICALEQUIPMENT");
      System.out.println("Dropped Medical Equipment Table");
    } catch (SQLException e) {
      System.out.println("Failed to drop Medical Equipment Table");
    }
  }

  String CSVHeaderString = "medID,type,nodeID,status";

  void createTable() throws SQLException {

    try {
      statement.execute(
          "CREATE TABLE MEDICALEQUIPMENT("
              + "medID varchar(25), "
              + "type varchar(25), "
              + "nodeID varchar(25),"
              + "status INT,"
              + "constraint MedEquip_PK primary key (medID),"
              + "constraint Location_FK foreign key (nodeID) references LOCATIONS(nodeID),"
              + "constraint Status_check check (status = 0 or status = 1 or status = 2))");
    } catch (SQLException e) {
      System.out.println("Medical Equipment Table failed to be created!");
    }
  }

  @Override
  public ArrayList<MedEquip> getAllMedEquip() throws SQLException {

    ArrayList<MedEquip> medEquipList = new ArrayList<>();

    try {
      ResultSet medEquipment = statement.executeQuery("SELECT * FROM MEDICALEQUIPMENT");

      while (medEquipment.next()) {
        ArrayList<String> medEquipData = new ArrayList<String>();

        for (int i = 0; i < medEquipment.getMetaData().getColumnCount(); i++) {
          medEquipData.add(medEquipment.getString(i + 1));
        }

        medEquipList.add(new MedEquip(medEquipData));
      }

    } catch (SQLException e) {
      System.out.println("Query from medical equipment table failed");
      throw (e);
    }

    return medEquipList;
  }

  @Override
  public MedEquip getMedEquip(String medID) throws SQLException {

    MedEquip medEquip = null;

    try {
      ResultSet medEquipment =
          statement.executeQuery(
              String.format("SELECT * FROM MEDICALEQUIPMENT WHERE MEDID = '%s'", medID));

      while (medEquipment.next()) {
        ArrayList<String> medEquipData = new ArrayList<String>();

        for (int i = 0; i < medEquipment.getMetaData().getColumnCount(); i++) {
          medEquipData.add(medEquipment.getString(i + 1));
        }

        medEquip = new MedEquip(medEquipData);
      }

    } catch (SQLException e) {
      System.out.println("Query from medical equipment table failed");
      throw (e);
    }

    return medEquip;
  }

  @Override
  public void addMedEquip(String inputID, String type, String nodeID, MedEquipStatus status)
      throws SQLException {

    statement.executeUpdate(
        String.format(
            "INSERT INTO MEDICALEQUIPMENT VALUES ('%s','%s','%s',%d)",
            inputID, type, nodeID, status.getValue()));
  }

  @Override
  public void deleteMedEquip(String medID) throws SQLException {
    statement.executeUpdate(String.format("DELETE FROM MEDICALEQUIPMENT WHERE MEDID='%s'", medID));
  }

  @Override
  public void changeMedEquip(String medID, String type, String nodeID, MedEquipStatus status)
      throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE MEDICALEQUIPMENT SET TYPE = '%s', NODEID = '%s', STATUS = %d WHERE MEDID = '%s'",
            type, nodeID, status.getValue(), medID));
  }

  @Override
  public void exportMedCSV(String fileName) {
    File csvOutputFile = new File(fileName);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      // print Table headers
      pw.print(CSVHeaderString);

      ArrayList<MedEquip> medEquipList = getAllMedEquip();

      // print all medEquipment
      for (MedEquip l : medEquipList) {
        pw.println();
        pw.print(l.toCSVString());
      }

    } catch (FileNotFoundException | SQLException e) {

      System.out.println(String.format("Error Exporting to File %s", fileName));
      e.printStackTrace();
    }
  }
}
