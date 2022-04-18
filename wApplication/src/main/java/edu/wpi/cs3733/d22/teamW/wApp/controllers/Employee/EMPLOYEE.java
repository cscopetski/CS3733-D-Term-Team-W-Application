package edu.wpi.cs3733.d22.teamW.wApp.controllers.Employee;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.RequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.SQLException;

public abstract class EMPLOYEE {
    protected final Employee employee;

    public EMPLOYEE (Employee e) {
        this.employee = e;
    }

    public Integer getID() {
        return employee.getEmployeeID();
    }

    public String getFirstName() {
        return employee.getFirstName();
    }

    public String getLastName() {
        return employee.getLastName();
    }


    public String getAddress() {
        return employee.getAddress();
    }
    public String getEmail(){
        return employee.getEmail();
    }
    public String getType(){
        return employee.getType().toString();
    }

    public abstract RequestType getEmployeeType();

    public abstract String getEmployeeTypeS();

    // returns a String for the More Info section of the EmployeeList Manager
    public abstract String getFormattedInfo() throws Exception;
}
