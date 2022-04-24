package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.teamW.API;
import edu.wpi.teamW.ServiceException;
import edu.wpi.teamW.dB.LanguageRequest;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import edu.wpi.teamW.API.*;
import javafx.scene.control.Label;
import edu.wpi.cs3733.D22.teamB.api.API.*;
import edu.wpi.cs3733.D22.teamZ.api.API.*;

public class APILandingPageController {


    @FXML Label InternalTransportCredit;
    @FXML Label ExternalTransportCredit;
    @FXML Label LanguageInterpreterCredit;

    @FXML ToggleButton toggleButton;

    boolean isEmergency = false;

    public void launchInternalTransportAPI() {


        WindowManager.getInstance().openWindow("popUpViews/APIPopUp.fxml");
        String locationID = (String)WindowManager.getInstance().getData("locationID");
        isEmergency = (boolean)WindowManager.getInstance().getData("isEmergency");
        edu.wpi.cs3733.D22.teamB.api.API api = new edu.wpi.cs3733.D22.teamB.api.API();
        try {
            api.run(0,0,500,500,"",locationID,"");
        } catch (edu.wpi.cs3733.D22.teamB.api.ServiceException | IOException e) {
            e.printStackTrace();
        }

        WindowManager.getInstance().getPrimaryStage().getScene().getRoot().setEffect(null);
    }

    public void launchExternalTransportAPI() {

        WindowManager.getInstance().openWindow("popUpViews/APIPopUp.fxml");
        String locationID = (String)WindowManager.getInstance().getData("locationID");
        isEmergency = (boolean)WindowManager.getInstance().getData("isEmergency");
        edu.wpi.cs3733.D22.teamZ.api.API api = new edu.wpi.cs3733.D22.teamZ.api.API();
        try {
            api.run(0,0,500,500,"",locationID,"");
        } catch (edu.wpi.cs3733.D22.teamZ.api.exception.ServiceException e) {
            e.printStackTrace();
        }

        WindowManager.getInstance().getPrimaryStage().getScene().getRoot().setEffect(null);
    }

    public void launchLanguageInterpreterAPI() {

        WindowManager.getInstance().openWindow("popUpViews/APIPopUp.fxml");
        String locationID = (String)WindowManager.getInstance().getData("locationID");
        isEmergency = (boolean)WindowManager.getInstance().getData("isEmergency");
        try {
            API.run(0,0,500,500,"",locationID,"");

        } catch (ServiceException e) {
            e.printStackTrace();
        }

        WindowManager.getInstance().getPrimaryStage().getScene().getRoot().setEffect(null);

        for(LanguageRequest lr : API.getAllRequests()){
            ArrayList<String> fields = new ArrayList<>();
            fields.add(lr.getLanguage());
            fields.add(lr.getNodeID());
            fields.add(lr.getEmployee().getEmployeeID().toString());
            if(isEmergency){
                fields.add("1");
            }else{
                fields.add("0");
            }
            try {
                RequestFactory.getRequestFactory().getRequest(RequestType.LanguageRequest,fields,false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void switchToRequestList() {
        PageManager.getInstance().loadPage(PageManager.Pages.RequestList);
    }

    public void creditsToggle() throws IOException {
        if (toggleButton.isSelected()) {
            toggleButton.setText("Show Credits");
            InternalTransportCredit.setVisible(false);
            ExternalTransportCredit.setVisible(false);
            LanguageInterpreterCredit.setVisible(false);
        } else {
            toggleButton.setText("Hide Credits");
            InternalTransportCredit.setVisible(true);
            ExternalTransportCredit.setVisible(true);
            LanguageInterpreterCredit.setVisible(true);
        }
    }
}
