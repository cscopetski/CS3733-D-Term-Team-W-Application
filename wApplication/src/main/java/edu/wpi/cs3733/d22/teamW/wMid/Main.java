package edu.wpi.cs3733.d22.teamW.wMid;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.DAO.DBController;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipType;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws Exception {

    DBConnectionMode.INSTANCE.setEmbeddedConnection();

    final String locationFileName = "TowerLocations.csv";
    final String medEquipFileName = "MedicalEquipment.csv";
    final String medEquipRequestFileName = "MedicalEquipmentRequest.csv";
    final String labServiceRequestFileName = "LabRequests.csv";
    final String employeesFileName = "Employees.csv";
    final String medRequestFileName = "MedRequests.csv";
    final String flowerRequestFileName = "FlowerRequests.csv";
    final String computerServiceRequestFileName = "ComputerServiceRequest.csv";
    final String sanitationRequestsFileName = "SanitationRequests.csv";
    final String languageFileName = "Languages.csv";
    final String languageInterpreterFilename = "LanguageInterpreter.csv";
    final String giftDeliveryRequestFileName = "GiftDeliveryRequest.csv";
    final String cleaningRequestFileName = "CleaningRequest.csv";
    final String mealRequestFileName = "MealRequest.csv";
    final String securityRequestFileName = "SecurityRequest.csv";
    final String languageRequestFileName = "LanguageRequests.csv";

    DBController.getDBController();

    CSVController csvController =
        new CSVController(
            locationFileName,
            medEquipFileName,
            medEquipRequestFileName,
            labServiceRequestFileName,
            employeesFileName,
            medRequestFileName,
            flowerRequestFileName,
            computerServiceRequestFileName,
            sanitationRequestsFileName,
            cleaningRequestFileName,
            languageFileName,
            languageInterpreterFilename,
            giftDeliveryRequestFileName,
            mealRequestFileName,
            securityRequestFileName,
            languageRequestFileName);

    try {
      csvController.populateTables();
    } catch (Exception e) {
      e.printStackTrace();
    }
    ArrayList<MedEquip> medEquipArrayList =
        MedEquipManager.getMedEquipManager().getAllMedEquip(MedEquipType.Recliners, null);
    for (MedEquip m : medEquipArrayList) {
      System.out.println(m.toCSVString());
    }

    EmployeeManager em = EmployeeManager.getEmployeeManager();
    Employee newEmployee7 =
        new Employee(
            7,
            "Ethan",
            "Pollack",
            EmployeeType.Staff.getString(),
            "ethan@hospital.com",
            "(123) 456-7890",
            "75 Francis St Boston MA 02115",
            "ethan31",
            "lead");
    Employee newEmployee8 =
        new Employee(
            8,
            "Jason",
            "Odell",
            EmployeeType.Staff.getString(),
            "jason@hospital.com",
            "(123) 456-7890",
            "75 Francis St Boston MA 02115",
            "uginghostdragon",
            "assistant");
    Employee newEmployee9 =
        new Employee(
            9,
            "Edward",
            "Enyedy",
            EmployeeType.Staff.getString(),
            "edward@hospital.com",
            "(123) 456-7890",
            "75 Francis St Boston MA 02115",
            "Tarkus",
            "wumboscrum");
    Employee newEmployee10 =
        new Employee(
            10,
            "Phil",
            "Bui",
            EmployeeType.Staff.getString(),
            "phil@hospital.com",
            "(123) 456-7890",
            "75 Francis St Boston MA 02115",
            "philbui",
            "buiphil");
    Employee newEmployee11 =
        new Employee(
            11,
            "Jack",
            "McEvoy",
            EmployeeType.Staff.getString(),
            "jack@hospital.com",
            "(123) 456-7890",
            "75 Francis St Boston MA 02115",
            "LinkdAether",
            "hi_jack");
    Employee newEmployee12 =
        new Employee(
            12,
            "Leona",
            "Nguyen",
            EmployeeType.Staff.getString(),
            "leona@hospital.com",
            "(123) 456-7890",
            "75 Francis St Boston MA 02115",
            "JustLeona",
            "0822");
    Employee newEmployee13 =
        new Employee(
            13,
            "Dylan",
            "Olmsted",
            EmployeeType.Staff.getString(),
            "dylan@hospital.com",
            "(123) 456-7890",
            "75 Francis St Boston MA 02115",
            "SAFARIOLMY",
            "8499");
    Employee pitbull =
        new Employee(
            14,
            "Armando",
            "Perez",
            EmployeeType.LanguageInterpreter.getString(),
            "mrworldwide@gmail.com",
            "(305) 305-1234",
            "The Entire Earth",
            "MrWorldWide",
            "timber");
    em.addEmployee(newEmployee7);
    em.addEmployee(newEmployee8);
    em.addEmployee(newEmployee9);
    em.addEmployee(newEmployee10);
    em.addEmployee(newEmployee11);
    em.addEmployee(newEmployee12);
    em.addEmployee(newEmployee13);
    em.addEmployee(pitbull);
    em.exportEmpCSV(employeesFileName);

    App.launch(App.class, args);
  }
}
