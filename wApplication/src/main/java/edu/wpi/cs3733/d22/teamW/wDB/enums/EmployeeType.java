package edu.wpi.cs3733.d22.teamW.wDB.enums;

public enum EmployeeType {
    Admin(5,"Administrator"),
    Doctor(3,"Doctor"),
    Nurse(3, "Nurse"),
    Sanitation(2, "Sanitiation Officer"),
    Security(3, "Security Officer"),
    Technician(2, "Technician"),
    NoOne(0, "Denied");
    private final int accessLevel;
    private final String string;

    private EmployeeType(int value, String string) {
        this.accessLevel = value;
        this.string = string;
    }

    public int getAccessLevel() {
        return this.accessLevel;
    }

    public String getString(){
        return this.string;
    }

    public static EmployeeType getEmployeeType(int num){
        EmployeeType type;
        switch (num){
            case 0:
                type = Admin;
                break;
            case 1:
                type = Doctor;
                break;
            case 2:
                type = Nurse;
                break;
            case 3:
                type = Sanitation;
                break;
            case 4:
                type = Security;
                break;
            case 5:
                type = Technician;
                break;
            default:
                type = NoOne;
                break;
        }
        return  type;
    }

    public static EmployeeType getEmployeeType(String type){
        EmployeeType employeeType;
        switch (type){
            case "Administrator":
                employeeType = Admin;
                break;
            case "Doctor":
                employeeType = Doctor;
                break;
            case "Nurse":
                employeeType = Nurse;
                break;
            case "Sanitation Officer":
                employeeType = Sanitation;
                break;
            case "Security Officer":
                employeeType = Security;
                break;
            case "Technician":
                employeeType = Technician;
                break;
            default:
                employeeType = NoOne;
                break;
        }
        return employeeType;
    }
}
