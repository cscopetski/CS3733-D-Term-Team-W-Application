package edu.wpi.cs3733.d22.teamW.wMid;

//import edu.wpi.cs3733.D22.teamB.api.DatabaseController;
//import edu.wpi.cs3733.D22.teamB.api.IPTEmployee;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.HospitalMap;
import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.*;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws Exception {

        DBConnectionMode.INSTANCE.setEmbeddedConnection();

        DBController.getDBController();

        CSVController csvController = new CSVController();
        try {
            csvController.populateTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        EmployeeMessageManager.getEmployeeMessageManager()
//                .sendAllEmployeesMessage(2, "Welcome to the Wumbo Whowies application!");
//
//        edu.wpi.cs3733.D22.teamB.api.DatabaseController dbController = new edu.wpi.cs3733.D22.teamB.api.DatabaseController();
//
//        LinkedList<edu.wpi.cs3733.D22.teamB.api.Location> APILocationList = dbController.listLocations();
//
//        for (edu.wpi.cs3733.D22.teamB.api.Location l : APILocationList) {
//            dbController.delete(l);
//        }
//
//        LinkedList<IPTEmployee> employees = dbController.listEmployees();
//        for (IPTEmployee employee : employees) {
//            dbController.delete(employee);
//        }
//
//        for (Location location : LocationManager.getLocationManager().getAllLocations()) {
//            edu.wpi.cs3733.D22.teamB.api.Location loc = new edu.wpi.cs3733.D22.teamB.api.Location(location.getNodeID(), location.getxCoord(), location.getyCoord(), location.getFloor(), location.getBuilding(), location.getNodeType(), location.getLongName(), location.getShortName());
//            int result = dbController.add(loc);
//            if (result == -1) {
//                System.out.println("Failed");
//            }
//        }
//
//        for (Employee employee : EmployeeManager.getEmployeeManager().getAllEmployees()) {
//            edu.wpi.cs3733.D22.teamB.api.IPTEmployee itpemployee = new edu.wpi.cs3733.D22.teamB.api.IPTEmployee(employee.getEmployeeID().toString(), employee.getLastName(), employee.getFirstName(), employee.getType().getString(), employee.getType().getString());
//            dbController.add(itpemployee);
//        }

        for (int i = 1; i <= EmployeeManager.getEmployeeManager().getAllEmployees().size(); i++) {
           try {
                HighScoreManager.getHighScoreManager().addHighScore(new HighScore(i, 0, 0));
           } catch (SQLException e) {
                e.printStackTrace();
           }
       }

        //Create folder to store CSVs in on exit
        Files.createDirectories(Paths.get("CSVs"));

        //Create outside folder to store images, and load images from resources into it if they dont exist
        try {
            Files.createDirectory(Paths.get("UserImages"));
            for(UserImage userImage : UserImageManager.getUserImageManager().getALlUserImages()) {
                InputStream in = Main.class.getClassLoader().getResourceAsStream(String.format("edu/wpi/cs3733/d22/teamW/wDB/original/%s", userImage.getPathToImage()));
                File savedImageOutside = new File(userImage.getPathToImage());
                OutputStream outStream = new FileOutputStream(savedImageOutside);
                try {
                    outStream.write(in.readAllBytes());
                } catch (Exception e) {
                    System.out.println(String.format("Unable to locate resource for %s", userImage.getPathToImage()));
                }
            }
        } catch (FileAlreadyExistsException e) {
            System.out.println("Images already loaded.");
        }

        App.launch(App.class, args);
    }
}
