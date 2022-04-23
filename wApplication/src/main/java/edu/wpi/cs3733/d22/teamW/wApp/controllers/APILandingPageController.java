package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
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

public class APILandingPageController {


    @FXML Label InternalTransportCredit;
    @FXML Label ExternalTransportCredit;
    @FXML Label LanguageInterpreterCredit;

    @FXML ToggleButton toggleButton;

    public void launchInternalTransportAPI() {
        WindowManager.getInstance().openWindow("popUpViews/APIPopUp.fxml");

    }

    public void launchExternalTransportAPI() {
        WindowManager.getInstance().openWindow("popUpViews/EmergencyPopUp.fxml");

    }

    public void launchLanguageInterpreterAPI() {
        Integer isEmergency = 0;
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Is this request an emergency?");
        a.getButtonTypes().clear();
        a.getButtonTypes().add(ButtonType.YES);
        a.getButtonTypes().add(ButtonType.NO);
        a.showAndWait();

        if (a.getResult() == ButtonType.YES) {
            isEmergency = 1;
        }
        try {
            API.run(0,0,500,500,"","FDEPT00101","");

        } catch (ServiceException e) {
            e.printStackTrace();
        }

        System.out.println("YO");
        for(LanguageRequest lr : API.getAllRequests()){
            System.out.println(lr.toString());
            ArrayList<String> fields = new ArrayList<>();
            fields.add(lr.getLanguage());
            fields.add(lr.getNodeID());
            fields.add(lr.getEmployee().getEmployeeID().toString());
            fields.add(isEmergency.toString());
            try {
                RequestFactory.getRequestFactory().getRequest(RequestType.LanguageRequest,fields,false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("End");
        WindowManager.getInstance().openWindow("popUpViews/APIPopUp.fxml");
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
