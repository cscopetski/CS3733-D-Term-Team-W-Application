package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import com.sun.jdi.InvalidTypeException;
import edu.wpi.cs3733.d22.teamW.wDB.entity.UserImage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserImageDao {
    void dropTable();

    void createTable();

    ArrayList<UserImage> getAllUserImages() throws SQLException;

    String getUserImagePath(String employeeUsername) throws SQLException;

    void addNewUserImage(UserImage userImage) throws SQLException, InvalidTypeException, IOException;

    void addUserImage(UserImage userImage) throws SQLException;

    void changeUserImage(UserImage userImage) throws SQLException;

    void deleteUserImage(String employeeUsername) throws SQLException;

    void exportUserImageCSV(String filename);
}
