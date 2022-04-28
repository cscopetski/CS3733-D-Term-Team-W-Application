package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import com.sun.jdi.InvalidTypeException;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.UserImage;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.IllegalFormatException;

public class UserImageDaoImpl implements UserImageDao {
    Statement statement;

    public UserImageDaoImpl(Statement statement) {
        this.statement = statement;
        dropTable();
    }

    @Override
    public void dropTable() {
        try {
            statement.execute("DROP TABLE USERIMAGES");
            System.out.println("Dropped table USERIMAGES.");
        } catch (SQLException e) {
            System.out.println("Failed to drop table USERIMAGES.");
        }
    }

    private final String CSVHeaderString = "employeeUsername,pathToImage";

    @Override
    public void createTable() {
        try {
            statement.execute("CREATE TABLE USERIMAGES(" +
                    "employeeUsername varchar(256)," +
                    "pathToImage varchar(256)," +
                    "constraint UserImage_EmpUser_PK primary key (employeeUsername)," +
                    "constraint UserImage_EmpUser_FK foreign key (employeeUsername) references EMPLOYEES(username)" +
                    ")");
            System.out.println("Created table USERIMAGES.");
        } catch (SQLException e) {
            System.out.println("Failed to create table USERIMAGES.");
        }
    }

    @Override
    public ArrayList<UserImage> getAllUserImages() throws SQLException {
        ResultSet userImageQuery = statement.executeQuery(String.format("SELECT * FROM USERIMAGES"));
        ArrayList<UserImage> queryResult = new ArrayList<>();
        while(userImageQuery.next()) {
            ArrayList<String> fields = new ArrayList<>();
            for(int i=0; i<userImageQuery.getMetaData().getColumnCount(); i++) {
                fields.add(userImageQuery.getString(i+1));
            }
            queryResult.add(new UserImage(fields));
        }
        return queryResult;
    }

    @Override
    public String getUserImagePath(String employeeUsername) throws SQLException {
        ResultSet userImageQuery = statement.executeQuery(String.format("SELECT * FROM USERIMAGES WHERE EMPLOYEEUSERNAME='%s'", employeeUsername));
        if(userImageQuery.next()) {
            return userImageQuery.getString(2);
        }
        throw new SQLException();
    }

    /**
     *
     * @param userImage
     * @throws SQLException Duplicate username entry
     * @throws InvalidTypeException File is not a .png
     * @throws IOException Error in copying/loading given file
     */
    @Override
    public void addNewUserImage(UserImage userImage) throws SQLException, InvalidTypeException, IOException {
        //Check if passed in file exists
        String filePath = userImage.getPathToImage();
        File userImageFile = new File(filePath);
        if(userImageFile.exists()) {
            //Check if PNG
            String extension = "";
            int i = filePath.lastIndexOf('.');
            if (i > 0) {
                extension = filePath.substring(i+1);
            } else {
                System.out.println("Error while parsing extension.");
                throw new InvalidTypeException("Error while parsing extension.");
            }
            if(!extension.equals("png")) {
                System.out.println("Saved image must be a png.");
                throw new InvalidTypeException("Saved image must be a png.");
            }
            //Save image
            String savedFilePath = String.format("wApplication/UserImages/%s.png", userImage.getEmployeeUsername());
            File savedFile = new File(savedFilePath);
            Files.copy(userImageFile.toPath(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            if(savedFile.exists()) {
                UserImage savedUserImage = new UserImage(userImage.getEmployeeUsername(), savedFilePath);
                addUserImage(savedUserImage);
                System.out.println("Successfully saved user image in the database.");
            } else {
                System.out.println("Saved image not copied properly.");
            }
        } else {
            System.out.println("Selected image does not exist");
            throw new FileNotFoundException("Selected image does not exist");
        }
    }

    @Override
    public void addUserImage(UserImage userImage) throws SQLException {
        statement.executeUpdate(String.format("INSERT INTO USERIMAGES VALUES(%s)", userImage.toValuesString()));
    }

    @Override
    public void changeUserImage(UserImage userImage) throws SQLException {
        statement.executeUpdate(String.format("UPDATE USERIMAGES SET PATHTOIMAGE='%s' WHERE EMPLOYEEUSERNAME='%s'", userImage.getPathToImage(), userImage.getEmployeeUsername()));
    }

    @Override
    public void deleteUserImage(String employeeUsername) throws SQLException {
        String savedFilePath = String.format("wApplication/UserImages/%s.png", employeeUsername);
        File savedFile = new File(savedFilePath);
        savedFile.delete();
        statement.executeUpdate(String.format("DELETE FROM USERIMAGES WHERE EMPLOYEEUSERNAME='%s'", employeeUsername));
    }

    @Override
    public void exportUserImageCSV(String fileName) {
        File csvOutputFile = new File(fileName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            // print Table headers
            pw.print(CSVHeaderString);

            // print all locations
            for (UserImage userImage : getAllUserImages()) {
                pw.println();
                pw.print(userImage.toCSVString());
            }

        } catch (FileNotFoundException e) {

            System.out.println(String.format("Error Exporting to File %s", fileName));
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
