package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import com.sun.jdi.InvalidTypeException;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.UserImageDao;
import edu.wpi.cs3733.d22.teamW.wDB.entity.UserImage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserImageManager {
    private UserImageDao uid;

    private static UserImageManager userImageManager = new UserImageManager();

    public static UserImageManager getUserImageManager() {
        return userImageManager;
    }

    public void setUserImageManagerDao(UserImageDao userImageDao) {
        this.uid = userImageDao;
    }

    private UserImageManager() {
    }

    public ArrayList<UserImage> getALlUserImages() throws SQLException {
        return this.uid.getAllUserImages();
    }

    public String getUserImagePath(String employeeUsername) throws SQLException {
        return this.uid.getUserImagePath(employeeUsername);
    }

    public void addNewUserImage(UserImage userImage) throws SQLException, IOException, InvalidTypeException {
        this.uid.addNewUserImage(userImage);
    }

    public void addUserImage(UserImage userImage) throws SQLException {
        this.uid.addUserImage(userImage);
    }

    public void changeUserImage(UserImage userImage) throws SQLException {
        this.uid.changeUserImage(userImage);
    }

    public void deleteUserImage(String employeeUsername) throws SQLException {
        this.uid.deleteUserImage(employeeUsername);
    }


    public void exportUserImageCSV(String filename) {
        this.uid.exportUserImageCSV(filename);
    }
}
