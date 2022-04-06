package edu.wpi.cs3733.d22.teamW.wDB;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MedEquipRequestControllerTest {
    @BeforeAll
    void setUp() throws SQLException, FileNotFoundException {
        final String locationFileName = "TowerLocations.csv";
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
        MedEquipRequestController merc = new MedEquipRequestController(merdi, medi);

        LabServiceRequestDaoImpl labServiceRequestDao = new LabServiceRequestDaoImpl();
        LabServiceRequestController lsrc = new LabServiceRequestController(labServiceRequestDao);

        RequestFactory requestFactory = RequestFactory.getRequestFactory(merc, lsrc);

        csvController.populateRequestTables(requestFactory);
}
    @Test
    void checkStart() {
        
    }

    @Test
    void cancelRequest() {
    }

    @Test
    void completeRequest() {
    }

    @Test
    void checkNext() {
    }

    @Test
    void getNext() {
    }

    @Test
    void getRequest() {
    }

    @Test
    void addRequest() {
    }

    @Test
    void getAllMedEquipRequests() {
    }
}