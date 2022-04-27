package edu.wpi.cs3733.d22.teamW.wDB.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class UserImage extends Entity {
    private String employeeUsername;
    private String pathToImage;

    public UserImage(String employeeUsername, String pathToImage) {
        this.employeeUsername = employeeUsername;
        this.pathToImage = pathToImage;
    }

    public UserImage(ArrayList<String> fields) {
        this.employeeUsername = fields.get(0);
        this.pathToImage = fields.get(1);
    }

    @Override
    public String toCSVString() {
        return String.format("%s,%s", this.employeeUsername, this.pathToImage);
    }

    @Override
    public String toValuesString() {
        return String.format("'%s','%s'", this.employeeUsername, this.pathToImage);
    }
}
