package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.D22.teamB.api.IPTEmployee;
import edu.wpi.cs3733.D22.teamZ.api.entity.ExternalTransportRequest;
import edu.wpi.cs3733.D22.teamB.api.DatabaseController;
import edu.wpi.cs3733.D22.teamB.api.Request;
import edu.wpi.cs3733.d22.teamW.Managers.PageManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.TransportType;
import edu.wpi.teamW.API;
import edu.wpi.teamW.ServiceException;
import edu.wpi.teamW.dB.LanguageRequest;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import edu.wpi.cs3733.D22.teamZ.api.API.*;

public class APILandingPageController {


    @FXML Label InternalTransportCredit;
    @FXML Label ExternalTransportCredit;
    @FXML Label LanguageInterpreterCredit;

    @FXML ToggleButton toggleButton;

    boolean isEmergency = false;

    public void launchInternalTransportAPI() throws Exception {
        edu.wpi.cs3733.D22.teamB.api.DatabaseController dbController = new edu.wpi.cs3733.D22.teamB.api.DatabaseController();

        LinkedList<edu.wpi.cs3733.D22.teamB.api.Location> APILocationList = dbController.listLocations();
                for (edu.wpi.cs3733.D22.teamB.api.Location l : APILocationList) {
            dbController.delete(l);
        }

        LinkedList<IPTEmployee> employees = dbController.listEmployees();
        for (IPTEmployee employee : employees) {
            dbController.delete(employee);
        }

        for (Location location : LocationManager.getLocationManager().getLocationByType("PATI")) {
            edu.wpi.cs3733.D22.teamB.api.Location loc = new edu.wpi.cs3733.D22.teamB.api.Location(location.getNodeID(), location.getxCoord(), location.getyCoord(), location.getFloor(), location.getBuilding(), location.getNodeType(), location.getLongName(), location.getShortName());
            int result = dbController.add(loc);
            if (result == -1) {
                System.out.println("Failed");
            }
        }

        for (Employee employee : EmployeeManager.getEmployeeManager().getAllEmployees()) {
            edu.wpi.cs3733.D22.teamB.api.IPTEmployee itpemployee = new edu.wpi.cs3733.D22.teamB.api.IPTEmployee(employee.getEmployeeID().toString(), employee.getLastName(), employee.getFirstName(), employee.getType().getString(), employee.getType().getString());
            dbController.add(itpemployee);
        }
        edu.wpi.cs3733.D22.teamB.api.API api = new edu.wpi.cs3733.D22.teamB.api.API();
        try {
            api.run(0,0,500,500,"edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/Standard.css",null,null);
        } catch (edu.wpi.cs3733.D22.teamB.api.ServiceException | IOException e) {
            e.printStackTrace();
        }
        DatabaseController databaseController = new DatabaseController();
        LinkedList<Request> APIRequest = databaseController.listRequests();
        for(Request request : APIRequest){
            ArrayList<String> fields = new ArrayList<>();
            if(request.getEmployee() == null){
                EmployeeChoiceIPTSingleton.getEmployeeChoiceIPTSingleton().set(request.getRequestID(),request.getStartLocation().getLongName(), request.getFinishLocation().getLongName(), String.valueOf(request.getPriority()));
                WindowManager.getInstance().openWindow("popUpViews/EmployeeChoiceIPT.fxml");
            }
            String startFloor = request.getStartLocation().getFloor();
            String finishFloor = request.getFinishLocation().getFloor();

            fields.add(
                    LocationManager.getLocationManager().getLocation(request.getStartLocation().getLongName(), startFloor).getNodeID());
            fields.add(
                    LocationManager.getLocationManager().getLocation(request.getFinishLocation().getLongName(), finishFloor).getNodeID());
            fields.add(EmployeeChoiceIPTSingleton.getEmployeeChoiceIPTSingleton().getEmployeeID());
            fields.add(String.format("%d",(request.getPriority()/3)));
            if(EmployeeChoiceIPTSingleton.getEmployeeChoiceIPTSingleton().isConfirm()){
                edu.wpi.cs3733.d22.teamW.wDB.entity.Request request1 = RequestFactory.getRequestFactory().getRequest(RequestType.InternalPatientTransportationRequest, fields, false);

                System.out.println(request1.toValuesString());
            }
        }
        databaseController.reset();
        WindowManager.getInstance().getPrimaryStage().getScene().getRoot().setEffect(null);

    }

    public void launchExternalTransportAPI() {

        WindowManager.getInstance().openWindow("popUpViews/APIPopUp.fxml");
        String locationID = (String)WindowManager.getInstance().getData("locationID");
        isEmergency = (boolean)WindowManager.getInstance().getData("isEmergency");
        edu.wpi.cs3733.D22.teamZ.api.API api = new edu.wpi.cs3733.D22.teamZ.api.API();
        try {
            api.run(0,0,500,500,"edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/Standard.css","",locationID);

        } catch (edu.wpi.cs3733.D22.teamZ.api.exception.ServiceException e) {
            e.printStackTrace();
        }
        WindowManager.getInstance().getPrimaryStage().getScene().getRoot().setEffect(null);
        for(ExternalTransportRequest e : api.getAllExternalTransportRequests()){
            ArrayList<String> fields = new ArrayList<>();
            fields.add(locationID);
            fields.add(e.getPatientID());
            fields.add(e.getTransportDestination());
            fields.add(e.getDepartureDate().toString());
            fields.add(TransportType.toTransportType(e.getTransportMethod()).getString());
            fields.add(e.getHandlerID());
            if(isEmergency){
                fields.add("1");
            }else{
                fields.add("0");
            }
            try {
                RequestFactory.getRequestFactory().getRequest(RequestType.ExternalTransportRequest,fields,false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            api.deleteExternalTransportRequest(e.getRequestID());
        }
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
