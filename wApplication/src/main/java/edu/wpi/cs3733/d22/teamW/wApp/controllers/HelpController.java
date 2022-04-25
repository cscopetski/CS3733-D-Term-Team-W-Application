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
        switch (PageManager.getInstance().getPrevious()){
            case LabSR:
                DescText.setText("This is placeholder Lab Request text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case Gaming:
                DescText.setText("This is placeholder Gaming text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case Profile:
                DescText.setText("This is placeholder Profile text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case AdminHub:
                DescText.setText("This is placeholder Admin Hub text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case FlowerSR:
                DescText.setText("This is placeholder Flower Request text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case Dashboard:
                DescText.setText("This is placeholder Dashboard text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case MainMenu:
                DescText.setText("This is placeholder Main Menu text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case EquipList:
                DescText.setText("This is placeholder Equipment List text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case Messaging:
                DescText.setText("This is placeholder Messaging text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case MapEditor:
                DescText.setText("This is placeholder Map Editor text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case ComputerSR:
                DescText.setText("This is placeholder Computer Request text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case RequestHub:
                DescText.setText("This is placeholder Request Hub text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case SecuritySR:
                DescText.setText("This is placeholder Security Request text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case RequestList:
                DescText.setText("This is placeholder Request List text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case SanitationSR:
                DescText.setText("This is placeholder Sanitation Request text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case GiftDeliverySR:
                DescText.setText("This is placeholder Gift Delivery Request text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case MealDeliverySR:
                DescText.setText("This is placeholder Meal Delivery Request text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case MedicalEquipmentSR:
                DescText.setText("This is placeholder Medical Equipment Request text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case MedicineDeliverySR:
                DescText.setText("This is placeholder Medicine Delivery Request text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            case LanguageInterpreterSR:
                DescText.setText("This is placeholder Language Interpreter Request text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
            default:
                DescText.setText("This is the default text. Please replace this.");
                Pic.setImage(new Image("edu/wpi/cs3733/d22/teamW/wApp/assets/burgerFoodItem.png"));
                break;
        }
    }

    @FXML
    void prevPage(){
        PageManager.getInstance().loadPage(PageManager.getInstance().getPrevious());
    }
}
