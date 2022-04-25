package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeChoiceIPTSingleton {

    private String requestID;
    private String origin;
    private String destination;
    private String priority;
    private String employeeID;
    private boolean comfirm;
    private static EmployeeChoiceIPTSingleton employeeChoiceIPTSingleton = new EmployeeChoiceIPTSingleton();

    private EmployeeChoiceIPTSingleton(){}

    public static EmployeeChoiceIPTSingleton getEmployeeChoiceIPTSingleton(){
        return employeeChoiceIPTSingleton;
    }

    public void set(String requestID, String origin, String destination, String priority){
        this.requestID = requestID;
        this.origin = origin;
        this.destination = destination;
        this.priority = priority;
    }
}
