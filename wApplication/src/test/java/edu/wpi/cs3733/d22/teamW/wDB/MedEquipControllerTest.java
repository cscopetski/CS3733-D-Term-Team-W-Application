package edu.wpi.cs3733.d22.teamW.wDB;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class MedEquipControllerTest {

    MedEquipRequestController merc;
    MedEquipDaoImpl medi;
    MedEquipController mec;

    @BeforeAll
    void setup() throws SQLException, FileNotFoundException {
        final String locationFileName = "edu/wpi/cs3733/d22/teamW/wDB/CSVs/TowerLocations.csv";
        final String medEquipFileName = "MedicalEquipment.csv";
        final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
        final String labServiceRequestFileName = "LabRequests.csv";

        DBController.getDBController();

        CSVController csvController =
                new CSVController(
                        locationFileName, medEquipFileName, medEquipRequestFileName, labServiceRequestFileName);

        try {
            csvController.populateEntityTables();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocationDaoImpl locationDao = new LocationDaoImpl();
        LocationController locationController = new LocationController(locationDao);

        MedEquipDaoImpl medi = new MedEquipDaoImpl();
        MedEquipRequestDaoImpl merdi = new MedEquipRequestDaoImpl();
        MedEquipController medEquipController = new MedEquipController(medi, merdi);
        merc = new MedEquipRequestController(merdi, medi);

        LabServiceRequestDaoImpl labServiceRequestDao = new LabServiceRequestDaoImpl();
        LabServiceRequestController lsrc = new LabServiceRequestController(labServiceRequestDao);

        RequestFactory requestFactory = RequestFactory.getRequestFactory(merc, lsrc);

        csvController.populateRequestTables(requestFactory);

    }

    @Test
    void markClean() {

    }

    @Test
    void markInUse() {
    }

    @Test
    void markDirty() {
    }

    @Test
    void add() {
    }

    @Test
    void delete() {

    }

    @Test
    void getMedEquip() {

    }

    @Test
    void getAll() throws SQLException {
        ArrayList<MedEquip>  medEquips = mec.getAll();

        ResultSet resultSet = DBController.getDBController().executeQuery("SELECT * FROM MEDICALEQUIPMENT");




    }

    @Test
    void exportMedicalEquipmentCSV() {
    }
}