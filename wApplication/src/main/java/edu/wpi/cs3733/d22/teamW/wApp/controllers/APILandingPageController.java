package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class APILandingPageController {


    @FXML Label InternalTransportCredit;
    @FXML Label ExternalTransportCredit;
    @FXML Label LanguageInterpreterCredit;

    @FXML ToggleButton toggleButton;

    public void launchInternalTransportAPI() {
        PageManager.getInstance().loadPage(PageManager.Pages.MedicineDeliverySR);
    }

    public void launchExternalTransportAPI() {
        PageManager.getInstance().loadPage(PageManager.Pages.LabSR);
    }

    public void launchLanguageInterpreterAPI() {
        PageManager.getInstance().loadPage(PageManager.Pages.MedicalEquipmentSR);
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
