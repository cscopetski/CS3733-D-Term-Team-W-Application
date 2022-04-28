package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.UserImageManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import javafx.scene.image.Image;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;

public class EmployeeImageGenerator {

    public static Image generatePlaceHolderImage() {
        return new Image(
                MessagingPageController.class
                        .getClassLoader()
                        .getResource("edu/wpi/cs3733/d22/teamW/wApp/assets/Icons/profilePicture.png")
                        .toString());
    }

    public static Image generateEmployeeImage(Integer empID) {
        Employee selectedEmployee = null;
        try {
            selectedEmployee = EmployeeManager.getEmployeeManager().getEmployee(empID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (selectedEmployee == null) {
            return generatePlaceHolderImage();
        }
        File employeeImageFile;
        try {
            employeeImageFile = new File(Paths.get(UserImageManager.getUserImageManager().getUserImagePath(selectedEmployee.getUsername())).toUri());
        } catch (SQLException e) {
            return generatePlaceHolderImage();
        }
        if (!employeeImageFile.exists()) return generatePlaceHolderImage();
        return new Image(employeeImageFile.toURI().toString());
    }
}
