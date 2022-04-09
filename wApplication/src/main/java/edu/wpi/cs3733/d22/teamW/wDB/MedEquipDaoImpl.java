package edu.wpi.cs3733.d22.teamW.wDB;

import java.io.*;
import java.sql.*;
import java.util.*;

public class MedEquipDaoImpl implements MedEquipDao {

    Statement statement;

    public MedEquipDaoImpl(Statement statement) {
        this.statement = statement;
    }

    @Override
    public ArrayList<MedEquip> getAllMedEquip() throws SQLException {

        ArrayList<MedEquip> medEquipList = new ArrayList<>();

        try {
            ResultSet medEquipment = statement.executeQuery("SELECT * FROM MEDICALEQUIPMENT");

            // Size of num MedEquip fields
            String[] medEquipData = new String[4];

            while (medEquipment.next()) {

                for (int i = 0; i < medEquipData.length; i++) {
                    medEquipData[i] = medEquipment.getString(i + 1);
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
    public void addMedEquip(String inputID, String type, String nodeID, Integer status)
            throws SQLException {

        statement.executeUpdate(
                String.format(
                        "INSERT INTO MEDICALEQUIPMENT VALUES ('%s','%s','%s',%d)",
                        inputID, type, nodeID, status));
    }

    @Override
    public void deleteMedEquip(String medID) throws SQLException {
        statement.executeUpdate(String.format("DELETE FROM MEDICALEQUIPMENT WHERE MEDID='%s'", medID));
    }

    @Override
    public void changeMedEquip(String medID, String type, String nodeID, Integer status)
            throws SQLException {
        statement.executeUpdate(
                String.format(
                        "UPDATE MEDICALEQUIPMENT SET TYPE = '%s', NODEID = '%s', STATUS = %d WHERE MEDID = '%s'",
                        type, nodeID, status, medID));
    }


    @Override
    public void exportMedCSV(String fileName) {
        File csvOutputFile = new File(fileName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            // print Table headers
            pw.print("medID,type,nodeID,status");

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
