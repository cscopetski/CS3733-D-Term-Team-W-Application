package edu.wpi.cs3733.d22.teamW.wDB.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LanguageInterpreter extends Entity{

    private Integer employeeID;
    private String language;


    @Override
    public String toCSVString() {
        return null;
    }

    @Override
    public String toValuesString() {
        return null;
    }
}
