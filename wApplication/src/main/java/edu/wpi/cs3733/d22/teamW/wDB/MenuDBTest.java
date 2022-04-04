package edu.wpi.cs3733.d22.teamW.wDB;

public class MenuDBTest {
  /*
  // This is not for proper jUnit testing, just for the testing that normal people prefer
  DBController dbController = DBController.getDBController();
  RequestFactory requestFactory = new RequestFactory();
  MedEquipRequestDaoImpl merdi = new MedEquipRequestDaoImpl();
  Scanner reader;

  public MenuDBTest(Scanner sc) {
    reader = sc;
  }

  public void init() throws SQLException {

    System.out.println(
        "Enter DAO to test:"
            + "\n1. Location"
            + "\n2. MedEquip"
            + "\n3. MedEquipRequest"
            + "\n4. Exit");
    int selection = reader.nextInt();
    if (selection == 1) {
      // locationMenu();
    }
    if (selection == 2) {
      medequipMenu();
    }
    if (selection == 3) {
      medequiprequestMenu();
    }
  }

  public void medequiprequestMenu() throws SQLException {
    boolean looping = true;
    Scanner reader = new Scanner(System.in);

    while (looping) {
      // Displays the numbers and functions of the LocationMenu
      System.out.println("Available Functions:");
      System.out.println("\t1 - Display Medical Equipment Request Information");
      System.out.println("\t2 - Change Medical Equipment Request Information");
      System.out.println("\t3 - Add a Medical Equipment Request");
      System.out.println("\t4 - NOT ALLOWED - Delete a Medical Equipment Request");
      System.out.println("\t5 - Save Medical Equipment Request to CSV");
      System.out.println("\t6 - Exit Program");

      // asks user to input a number based on what they want to do

      System.out.println(
          "Please enter the number that corresponds to the action that you would like to perform: ");
      int functionNumber;
      if (reader.hasNextInt()) {
        functionNumber = reader.nextInt();
      } else {
        functionNumber = -1;
        reader.next();
      }

      switch (functionNumber) {
        case 1:
          for (MedEquipRequest m : merdi.getAllMedEquipRequests()) {
            System.out.println(m.toString());
          }
          break;
        case 2:
          // changing
          System.out.println("Input Medical Equipment Request ID to modify (integer): ");
          int requestID = reader.nextInt();
          System.out.println("Input the new item ID: ");
          String newItemID = reader.next();
          System.out.println("Input the new location ID: ");
          String newLocationID = reader.next();
          System.out.println("Input the new employee name: ");
          String newEmployeeName = reader.next();
          try {
            merdi.changeMedEquipRequest(requestID, newItemID, newLocationID, newEmployeeName);
          } catch (SQLException e) {
            e.printStackTrace();
          }
          break;
        case 3:
          // put function for adding location here
          System.out.println("Enter the medical equipment type to request: ");
          String medID = reader.next();
          System.out.println("Is the request an emergency? (1/0).");
          boolean isEmergency = reader.nextBoolean();

          System.out.println("Enter employee name: ");
          String employeeName = reader.next();

          ArrayList<String> fields = new ArrayList<>();

          fields.add(medID);
          fields.add(employeeName);
          fields.add("" + isEmergency);
          fields.add("0");

          requestFactory.getRequest("MEDEQUIPREQUEST", fields);

          break;
        case 4:
          // put function for deleting location here
          System.out.println("Not allowed as of now.");
          break;
        case 5:
          // put function for saving locations to CSV here
          System.out.println("Enter the name of the CSV file:");
          String fileName = reader.next();
          merdi.exportMedReqCSV(fileName);
          looping = false;
          break;
        case 6:
          // put function for exiting the program here
          looping = false;
          break;
        default:
          System.out.println("You have entered an invalid input, please try again");
      }
    }
  }

  public void medequipMenu() throws SQLException {
    boolean looping = true;
    Scanner reader = new Scanner(System.in);
    MedEquipDaoImpl medi = new MedEquipDaoImpl();
    while (looping) {
      // Displays the numbers and functions of the LocationMenu
      System.out.println("Available Functions:");
      System.out.println("\t1 - Display Medical Equipment Information");
      System.out.println("\t2 - Change Medical Equipment Information");
      System.out.println("\t3 - Add a Medical Equipment");
      System.out.println("\t4 - Delete a Medical Equipment");
      System.out.println("\t5 - Save Medical Equipment to CSV");
      System.out.println("\t6 - Exit Program");

      // asks user to input a number based on what they want to do

      System.out.println(
          "Please enter the number that corresponds to the action that you would like to perform: ");
      int functionNumber;
      if (reader.hasNextInt()) {
        functionNumber = reader.nextInt();
      } else {
        functionNumber = -1;
        reader.next();
      }

      switch (functionNumber) {
        case 1:
          for (MedEquip m : medi.getAllMedEquip()) {
            System.out.println(m.toString());
          }
          break;
        case 2:
          // changing
          System.out.println("Input Medical Equipment ID to modify: ");
          String medID = reader.next();
          System.out.println("Input the new location ID: ");
          String newLocationID = reader.next();
          System.out.println("Input modified status (integer): ");
          int newStatus = reader.nextInt();
          medi.changeMedEquip(medID, medID.substring(0, 3), newLocationID, newStatus);
          break;
        case 3:
          // put function for adding location here
          System.out.println("Input Medical Equipment ID to add: ");
          String addID = reader.next();
          // medi.addMedEquip(addID);
          break;
        case 4:
          // put function for deleting location here
          System.out.println("Input Medical Equipment ID to remove: ");
          String removeID = reader.next();
          medi.deleteMedEquip(removeID);
          break;
        case 5:
          // put function for saving locations to CSV here
          System.out.println("Enter the name of the CSV file:");
          String fileName = reader.next();
          medi.exportMedCSV(fileName);
          looping = false;
          break;
        case 6:
          // put function for exiting the program here
          looping = false;
          break;
        default:
          System.out.println("You have entered an invalid input, please try again");
      }
    }
  }

  /*public void locationMenu() throws SQLException {
  boolean looping = true;
  Scanner reader = new Scanner(System.in);
  LocationDaoImpl ldi = new LocationDaoImpl();
  while (looping) {
    // Displays the numbers and functions of the LocationMenu
    System.out.println("Available Functions:");
    System.out.println("\t1 - Display Location Information");
    System.out.println("\t2 - Change Location Information");
    System.out.println("\t3 - Add a location");
    System.out.println("\t4 - Delete a Location");
    System.out.println("\t5 - Save Locations to CSV");
    System.out.println("\t6 - Exit Program");

    // asks user to input a number based on what they want to do

    System.out.println(
        "Please enter the number that corresponds to the action that you would like to perform: ");
    int functionNumber;
    if (reader.hasNextInt()) {
      functionNumber = reader.nextInt();
    } else {
      functionNumber = -1;
      reader.next();
    }

    switch (functionNumber) {
      case 1:
        for (Location l : ldi.getAllLocations()) {
          System.out.println(l.toString());
        }
        break;
      case 2:

        /*
         The user is prompted for the ID of the location node and then is prompted for the new
         values of the floor and location type. Then the menu is displayed again and the user
         prompted for the next selection.
        */
  /*
          System.out.println("Input Location ID to modify: ");
          String modifyID = reader.next();
          System.out.println("Input the modified floor (01, 02, 03, L1, L2): ");
          String newFloor = reader.next();
          System.out.println("Input modified location type (4 letters): ");
          String newType = reader.next();
          ldi.changeLocation(modifyID, newFloor, newType);
          break;
        case 3:
          // put function for adding location here
          System.out.println("Input Location ID to add: ");
          String addID = reader.next();
          ldi.addLocation(addID);
          break;
        case 4:
          // put function for deleting location here
          System.out.println("Input Location ID to remove: ");
          String removeID = reader.next();
          ldi.deleteLocation(removeID);
          break;
        case 5:
          // put function for saving locations to CSV here
          System.out.println("Enter the name of the CSV file:");
          String fileName = reader.next();
          ldi.exportLocationCSV(fileName);
          looping = false;
          break;
        case 6:
          // put function for exiting the program here
          looping = false;
          break;
        default:
          System.out.println("You have entered an invalid input, please try again");
      }
    }


    reader.close(); // closes scanner
  }
  */
}
