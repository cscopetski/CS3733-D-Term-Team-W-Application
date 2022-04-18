package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipType;
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
              + "constraint type_Check check ("
              + getTypeList()
              + "),"
              + "constraint Location_FK foreign key (nodeID) references LOCATIONS(nodeID),"
              + "constraint Status_check check (status = 0 or status = 1 or status = 2))");
    } catch (SQLException e) {
      System.out.println("Medical Equipment Table failed to be created!");
    }
  }

  private String getTypeList() {
    String list = "";
    MedEquipType[] medEquipTypeArrayList = MedEquipType.values();
    for (int index = 0; index < medEquipTypeArrayList.length; index++) {
      list += "type = '";
      list += medEquipTypeArrayList[index].getAbb();
      list += "'";
      if (index < medEquipTypeArrayList.length - 1) {
        list += " or ";
      }
    }
    return list;
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
    } catch (NonExistingMedEquip e) {
      e.printStackTrace();
    }

    return medEquipList;
  }

  @Override
  public ArrayList<MedEquip> getAllMedEquip(MedEquipType type, MedEquipStatus status)
      throws SQLException {

    ArrayList<MedEquip> medEquipList = new ArrayList<>();

    String queury = "SELECT * FROM MEDICALEQUIPMENT ";
    if (type == null && status != null) {
      queury += "WHERE STATUS = ";
      queury += status.getValue();
    } else if (type != null && status == null) {
      queury += "WHERE TYPE = '";
      queury += type.getAbb();
      queury += "'";
    } else if (type != null && status != null) {
      queury += "WHERE ( TYPE = '";
      queury += type.getAbb();
      queury += "' AND STATUS = ";
      queury += status.getValue();
      queury += ")";
    }
    try {
      ResultSet medEquipment = statement.executeQuery(queury);

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
    } catch (NonExistingMedEquip e) {
      e.printStackTrace();
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
    } catch (NonExistingMedEquip e) {
      e.printStackTrace();
    }

    return medEquip;
  }

  @Override
  public void addMedEquip(MedEquip medEquip) throws SQLException {

    statement.executeUpdate(
        String.format("INSERT INTO MEDICALEQUIPMENT VALUES(%s)", medEquip.toValuesString()));
  }

  @Override
  public void deleteMedEquip(String medID) throws SQLException {
    statement.executeUpdate(String.format("DELETE FROM MEDICALEQUIPMENT WHERE MEDID='%s'", medID));
  }

  @Override
  public void changeMedEquip(MedEquip medEquip) throws SQLException {
    statement.executeUpdate(
        String.format(
            "UPDATE MEDICALEQUIPMENT SET TYPE = '%s', NODEID = '%s', STATUS = %d WHERE MEDID = '%s'",
            medEquip.getType().getAbb(),
            medEquip.getNodeID(),
            medEquip.getStatus().getValue(),
            medEquip.getMedID()));
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

  @Override
  public void updateMedEquipsAtLocation(String nodeID) throws SQLException {

    statement.executeUpdate(
        String.format(
            "UPDATE MEDICALEQUIPMENT SET NODEID='%s' WHERE NODEID='%s'",
            LocationManager.getLocationManager().getNoneLocation(), nodeID));
  }
}
