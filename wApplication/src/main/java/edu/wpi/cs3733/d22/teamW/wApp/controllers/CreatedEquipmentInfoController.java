package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.mapEditor.medEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipType;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreatedEquipmentInfoController implements Initializable {

    @FXML Label location;
    @FXML Label equipType;
    @FXML
    TableView equipTable;
    @FXML
    TableColumn equipID;
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

    public void submitButton(ActionEvent actionEvent) throws SQLException {
                fadeOut.playFromStart();
                Stage stage = ((Stage)WindowManager.getInstance().getData("stage"));
                stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            onLoad();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onLoad() throws SQLException {
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        String loc = (String) WindowManager.getInstance().getData("LocationOfAddedEquip");
        location.setText(loc);
        equipType.setText((String) WindowManager.getInstance().getData("EquipType"));
        ArrayList<String> list = (ArrayList<String>) WindowManager.getInstance().getData("ListOfProducedEquip");
        ArrayList<medEquip> equipList = new ArrayList<>();
        for (String id : list){
            equipList.add(new medEquip(id, MedEquipStatus.Clean, loc));
        }

/*
        for(String item : list){
            System.out.println(item);
        }
        TableColumn column = new TableColumn("Equipment ID:");

        column.setCellValueFactory(new PropertyValueFactory());
*/

//        equipID.setItems((ObservableList) list);
        equipID.setCellValueFactory(new PropertyValueFactory<medEquip,String>("MedID"));
        equipTable.setItems(getEquipLis(loc));
    }

    public ObservableList<medEquip> getEquipLis(String loc){
        ObservableList<medEquip> listOfEquip = FXCollections.observableArrayList();
        ArrayList<String> list = (ArrayList<String>) WindowManager.getInstance().getData("ListOfProducedEquip");
        for (String id : list){
            listOfEquip.add(new medEquip(id, MedEquipStatus.Clean, loc));
        }
        return listOfEquip;
    }

}
