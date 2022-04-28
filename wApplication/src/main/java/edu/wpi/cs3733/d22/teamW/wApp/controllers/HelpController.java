package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {

    @FXML Text DescText;
    @FXML ImageView Pic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onLoad();
    }

    private void onLoad() {
        switch (PageManager.getInstance().getHistoryPages().get(PageManager.getInstance().getHistoryPages().size() - 1)) {
            case APILandingPage:
                DescText.setText("The API Request page displays requests that utilize other hospitals’ request APIs.\n" +
                        "Select one to begin a request of that type.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case LabSR:
                DescText.setText("To submit a Lab Service Request:\n" +
                        "1. Type in or select the name of the employee you would like to assign to this request.\n" +
                        "2. Select a location by filling in the text field, choosing a location from the dropdown list, or selecting the location from the map.\n" +
                        "3. Input the first and last name of the patient who is being tested.\n" +
                        "4. Select the boxes for the relevant tests.\n" +
                        "5. If this request is an emergency, please click the Emergency button.\n" +
                        "6. If you are sure that you have input the correct information, submit the request.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case Gaming:
                DescText.setText("Play these simulations when not busy to strengthen your hand-eye coordination, reaction time, and resourcefulness!\n" +
                        "\n" +
                        "Controls for both simulations:\n" +
                        "W, A, S, D - Up, Down, Left, Right movement\n" +
                        "\n" +
                        "Below the list of simulations is a high score table. Compete to become the best employee!\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case Profile:
                DescText.setText("1. Your name, employee type, email, phone number, and address within the hospital are listed in the \"Info\" section of your user profile to review. Please keep these details consistent and notify an administrator when they change.\n" +
                        "2. Requests you have made are listed in the \"My Requests\" section of your user profile.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case AdminHub:
                DescText.setText("Employee Manager:\n" +
                        "1. The Employee Information Table displays all of the employees in the hospital, and their personal data.\n" +
                        "2. A new employee can be entered into the system by selecting \"Add Employee\" and entering the relevant data.\n" +
                        "3. By selecting an employee and then selecting \"Change Employee,\" their personal information can be edited.\n" +
                        "4. By selecting an employee and then selecting \"Delete Employee,\" they and their personal information will be deleted from the system.\n\n" +
                        "Map Editor:\n" +
                        "5. This tab displays the entire map of the hospital, and allows you to add, edit, and remove locations.\n" +
                        "6. By using the controls, you may drag or zoom into/out of the map.\n" +
                        "7. By selecting the edit mode, you can click on the map to create a new location. Input all of the relevant fields to place the location where you clicked. It may then be used for requests and equipment storage.\n" +
                        "8. The floor dropdown menu allows you to change which floor you are currently viewing.\n" +
                        "9. The location table displays a list of all locations on the current floor, as well as their horizontal and vertical positions on the map (listed as X and Y, respectively.)\n" +
                        "10. The equipment table displays a list of all equipment on the current floor, their IDs, and the horizontal and vertical locations.\n" +
                        "11. Using the filters, you may selectively view only locations, equipment, requests, or any combination of the three.\n" +
                        "12. The locations and the various types of equipment are given different icons that are displayed on the map.\n" +
                        "13. The map may be saved or loaded as a CSV file for archival purposes.\n\n" +
                        "Automation:\n" +
                        "14. This button activates or deactivates automatic request creation based on equipment status for the entire system.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case FlowerSR:
                DescText.setText("To submit a Flower Delivery Request:\n" +
                        "1. Input the first and last name of the patient who is being delivered flowers.\n" +
                        "2. Select a location by filling in the text field, choosing a location from the dropdown list, or selecting the location from the map.\n" +
                        "3. Input or select the type of flower that the patient will receive.\n" +
                        "4. Type in or select the name of the employee you would like to assign to this request.\n" +
                        "5. If this request is an emergency, please click the Emergency button.\n" +
                        "6. If you are sure that you have input the correct information, submit the request.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case Dashboard:
                DescText.setText("1. Select a floor on the vertical map of the hospital to populate the summary and details tables with information.\n" +
                        "2. The Total Clean Equipment Summary table displays the portion of clean equipment in total on all floors for each type.\n" +
                        "3. The Alert Zone displays if there is an overload of dirty equipment that would require emergency attention.\n" +
                        "4. The Floor Summary displays the number of clean and dirty equipment of each type.\n" +
                        "5. The Equipment Details table displays each equipment on the currently selected floor, their status, and their location.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case MainMenu:
                DescText.setText("1. The Map Display allows you to view and edit the entire hospital, and all of the locations, equipment, and requests.\n" +
                        "2. The Dashboard allows you to view the status of equipment throughout the building.\n" +
                        "3. The Service Request Menu allows you to create new service requests.\n" +
                        "4. The Messaging system allows you to chat with your coworkers in 1-on-1 and group text messaging.\n" +
                        "5. The history bar allows you to move forward and backwards between recently viewed pages.\n" +
                        "6. The menu bar contains dropdown menus for the various Menu options, Service Requests, Account information, Map services, and a button to exit the software.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case EquipList:
                DescText.setText("1. The equipment list displays all of the equipment in the hospital, their locations, and their statuses.\n" +
                        "\n" +
                        "2. Use the \"Mark Selected Clean\" button to change the selected equipment’s status to clean.\n" +
                        "3. Use the \"Mark Selected In-Use\" button to change the selected equipment’s status to in-use.\n" +
                        "4. Use the \"Mark Selected Dirty\" button to change the selected equipment’s status to dirty.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case Messaging:
                DescText.setText("To begin a conversation with another employee:\n" +
                        "1. Input or select their name from the list of employees.\n" +
                        "2. Write your message into the text box.\n" +
                        "3. Send the message.\n" +
                        "4. Continue chatting by repeating steps 2 and 3 for each message. Be sure to follow company policy when conversing with other employees!\n" +
                        "\n" +
                        "To begin a group chat:\n" +
                        "5. Click on the \"Create Group Chat\" button\n" +
                        "6. Input or select the name of each employee you wish to chat with from the list of employees.\n" +
                        "7. Click the \"Submit\" button.\n" +
                        "8. Chat with your employees the same way you would with a single-employee chat. Be sure to follow company policy when conversing with other employees!\n" +
                        "\n" +
                        "To check the members of a group chat, click on the \"View members in chat\" button in a group chat.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case MapEditor:
                DescText.setText("This page displays the entire map of the hospital, and allows you to add, edit, and remove locations.\n" +
                        "1. By using the controls, you may drag or zoom into/out of the map.\n" +
                        "2. By selecting the edit mode, you can click on the map to create a new location. Input all of the relevant fields to place the location where you clicked. It may then be used for requests and equipment storage.\n" +
                        "3. The floor dropdown menu allows you to change which floor you are currently viewing.\n" +
                        "4. The location table displays a list of all locations on the current floor, as well as their horizontal and vertical positions on the map (listed as X and Y, respectively.)\n" +
                        "5. The equipment table displays a list of all equipment on the current floor, their IDs, and the horizontal and vertical locations.\n" +
                        "6. Using the filters, you may selectively view only locations, equipment, requests, or any combination of the three.\n" +
                        "7. The locations and the various types of equipment are given different icons that are displayed on the map.\n" +
                        "8. The map may be saved or loaded as a CSV file for archival purposes.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case ComputerSR:
                DescText.setText("To submit a Computer Service Request:\n" +
                        "1. Select a location by filling in the text field, choosing a location from the dropdown list, or selecting the location from the map.\n" +
                        "2. Type in or select the name of the employee you would like to assign to this request.\n" +
                        "3. If this request is an emergency, please click the Emergency button.\n" +
                        "4. If you are sure that you have input the correct information, submit the request.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case RequestHub:
                DescText.setText("The Request Hub Lists all of the currently available request types. Select one to begin a request of that type.\n" +
                        "\n" +
                        "Select \"View Request List\" to view all of the currently tracked requests.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case SecuritySR:
                DescText.setText("To submit a Security Request:\n" +
                        "1. Select a location by filling in the text field, choosing a location from the dropdown list, or selecting the location from the map.\n" +
                        "2. Select the relevant alert level from the dropdown list.\n" +
                        "3. Type in or select the name of the employee you would like to assign to this request.\n" +
                        "4. If this request is an emergency, please click the Emergency button.\n" +
                        "5. If you are sure that you have input the correct information, submit the request. Cancel the request if you no longer need security assistance.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case RequestList:
                DescText.setText("1. The request list displays all of the requests tracked by the request system, their request types, their assigned employee, their status, and any other important details.\n" +
                        "2. To only see requests of certain types, select a filter from the dropdown list in the \"Filter\" box.\n" +
                        "3. Selecting \"Only show emergency requests\" will filter out all non-emergencies and only display emergency requests.\n" +
                        "\n" +
                        "4. Extra details for the currently selected request are listed in the \"More Info\" box.\n" +
                        "\n" +
                        "5. By selecting a request, it may be marked as Started, Complete, or Canceled. \"Requeue\" will return a Canceled request to In-Progress. \"Clear Selection\" will deselect the currently selected request.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case SanitationSR:
                DescText.setText("To submit a Sanitation Service Request:\n" +
                        "1. Select a location by filling in the text field, choosing a location from the dropdown list, or selecting the location from the map.\n" +
                        "2. Input or select the type of accident that needs sanitation.\n" +
                        "3. Type in or select the name of the employee you would like to assign to this request.\n" +
                        "4. If this request is an emergency, please click the Emergency button.\n" +
                        "5. If you are sure that you have input the correct information, submit the request.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case GiftDeliverySR:
                DescText.setText("To submit a Gift Delivery Request:\n" +
                        "1. Input the first and last name of the patient who is being delivered a gift.\n" +
                        "2. Select a location by filling in the text field, choosing a location from the dropdown list, or selecting the location from the map.\n" +
                        "3. Type in or select the name of the employee you would like to assign to this request.\n" +
                        "4. If this request is an emergency, please click the Emergency button.\n" +
                        "5. If you are sure that you have input the correct information, submit the request.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case MealDeliverySR:
                DescText.setText("To submit a Meal Delivery Request:\n" +
                        "1. Select a meal to be delivered.\n" +
                        "2. Select a location by filling in the text field, choosing a location from the dropdown list, or selecting the location from the map.\n" +
                        "3. Type in or select the name of the employee you would like to assign to this request.\n" +
                        "4. Input the first and last name of the patient who is being delivered a meal.\n" +
                        "5. If this request is an emergency, please click the Emergency button.\n" +
                        "6. If you are sure that you have input the correct information, submit the request.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case MedicalEquipmentSR:
                DescText.setText("To submit a Medical Equipment Request:\n" +
                        "1. Select a location by filling in the text field, choosing a location from the dropdown list, or selecting the location from the map.\n" +
                        "2. Type in or select the name of the employee you would like to assign to this request.\n" +
                        "3. Select the type of equipment you would like delivered to the selected location.\n" +
                        "4. If this request is an emergency, please click the Emergency button.\n" +
                        "5. If you are sure that you have input the correct information, submit the request.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case MedicineDeliverySR:
                DescText.setText("To submit a Medicine Delivery Request:\n" +
                        "1. Input the name of the requested medicine, or select it from the dropdown list.\n" +
                        "2. Select a location by filling in the text field, choosing a location from the dropdown list, or selecting the location from the map.\n" +
                        "3. Input the quantity of the requested medicine, then the unit of that quantity.\n" +
                        "4. Type in or select the name of the employee you would like to assign to this request.\n" +
                        "5. If this request is an emergency, please click the Emergency button.\n" +
                        "6. If you are sure that you have input the correct information, submit the request. Cancel the request if you no longer need medicine delivered.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            case LanguageInterpreterSR:
                DescText.setText("To submit a Language Interpreter Request:\n" +
                        "1. Input or select the language to be interpreted.\n" +
                        "2. Select a location by filling in the text field, choosing a location from the dropdown list, or selecting the location from the map.\n" +
                        "3. If this request is an emergency, please click the Emergency button.\n" +
                        "4. If you are sure that you have input the correct information, submit the request.\n");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/mgb_logo.png"));
                break;
            default:
                DescText.setText("This is the Help page!" +
                        "Please open this page when viewing another page to view helpful tips.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/wong.png"));
                break;
        }
    }

    @FXML
    void prevPage(){
        PageManager.getInstance().goBack();
    }
}
