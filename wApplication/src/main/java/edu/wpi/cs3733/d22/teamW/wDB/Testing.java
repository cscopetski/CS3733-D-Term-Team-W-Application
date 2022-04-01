package edu.wpi.cs3733.d22.teamW.wDB;

public class Testing {

  // This is not ofr proper junit testing, just for the testing that normal people prefer

  /*
  public void locationMenu() {
      boolean looping = true;
      Scanner reader = new Scanner(System.in);
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
                  printLocations();
                  break;
              case 2:
        */
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
                  modifyLocation(modifyID, newFloor, newType);
                  break;
              case 3:
                  // put function for adding location here
                  System.out.println("Input Location ID to add: ");
                  String addID = reader.next();
                  int index = getIndexOf(addID);
                  if (index != -1) {
                      System.out.println("The database already contains a location with the ID: " + addID);
                  } else {
                      try {
                          addLocation(addID);
                      } catch (SQLException e) {
                          e.printStackTrace();
                      }
                  }
                  break;
              case 4:
                  // put function for deleting location here
                  System.out.println("Input Location ID to remove: ");
                  String removeID = reader.next();
                  try {
                      delete(removeID);
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
                  break;
              case 5:
                  // put function for saving locations to CSV here
                  System.out.println("Enter the name of the CSV file");
                  String fileName = reader.next();
                  locationList = dbController.getLocationTable();
                  csvController.exportToCSV(locationList, fileName);
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
