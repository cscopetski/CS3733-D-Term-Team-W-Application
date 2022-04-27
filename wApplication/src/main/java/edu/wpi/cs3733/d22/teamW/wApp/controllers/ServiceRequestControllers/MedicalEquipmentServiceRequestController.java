package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
//import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.HospitalMap;
import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.InValidRequestType;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NonExistingMedEquip;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.MedEquipRequestManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MedEquipRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MedicalEquipmentServiceRequestController implements Initializable {
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

    Alert confirm = new ConfirmAlert();
    Alert emptyFields = new EmptyAlert();
    @FXML
    PieChart requestChart;
    @FXML
    AutoCompleteInput equipmentSelection;
    @FXML
    AutoCompleteInput employeeNameComboBox;
    @FXML
    AutoCompleteInput locationComboBox;
    @FXML
    Label chartLabel;
    @FXML
    Label successLabel;
    @FXML
    EmergencyButton emergencyButton;
    @FXML
    Pane map;
    //HospitalMap map;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            onLoad();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            loadPieChart();
        } catch (NonExistingMedEquip e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //map.attachOnSelectionMade(l -> locationComboBox.getSelectionModel().select(l.getLongName()));
    }

    public void loadPieChart() throws NonExistingMedEquip, SQLException {

        ArrayList<Request> medEquipRequests = MedEquipRequestManager.getMedEquipRequestManager().getAllRequests();

        ArrayList<Double> numStatus = new ArrayList<>();

        for (RequestStatus rs : RequestStatus.values()) {
            numStatus.add(rs.getValue(), 0.0);
        }

        for (Request r : medEquipRequests) {
            for (RequestStatus rs : RequestStatus.values()) {
                if (r.getStatus().getValue()==rs.getValue()) {
                    numStatus.set(rs.getValue(), numStatus.get(rs.getValue()) + 1);
                    break;
                }
            }
        }

        for (int i = 0; i < numStatus.size(); i++) {
            double percentI = 100 * numStatus.get(i) / (double) medEquipRequests.size();
            numStatus.set(i, percentI);
        }


        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data(RequestStatus.InQueue.getString(), numStatus.get(RequestStatus.InQueue.getValue())),
                        new PieChart.Data(RequestStatus.InProgress.getString(), numStatus.get(RequestStatus.InProgress.getValue())),
                        new PieChart.Data(RequestStatus.Completed.getString(), numStatus.get(RequestStatus.Completed.getValue())),
                        new PieChart.Data(RequestStatus.Cancelled.getString(), numStatus.get(RequestStatus.Cancelled.getValue())));
        requestChart.setData(pieChartData);
        requestChart.setTitle("Medical Equipment Delivery Request Status");
        requestChart.setStyle("-fx-font: 15 arial;" + "-fx-font-weight: bold;");
        for(PieChart.Data data : requestChart.getData()){
            Tooltip toolTip = new Tooltip(String.format("%.2f",data.getPieValue()) + "%");
            toolTip.setShowDelay(Duration.seconds(0.5));
            toolTip.setHideDelay(Duration.seconds(1));
            toolTip.setStyle("-fx-font-size: 20");
            Tooltip.install(data.getNode(), toolTip);
        }


    }

    public void onLoad() throws SQLException {
        equipmentSelection.loadValues(getEquipList());
        locationComboBox.loadValues(getLocations());
        employeeNameComboBox.loadValues(getEmployeeNames());
    }

    public void submitButton(ActionEvent actionEvent) throws Exception {
        if (!emptyFields()) {
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {
                pushMedEquipToDB();
                clearFields();
                successLabel.setVisible(true);
                fadeOut.playFromStart();
            }
        } else {
            emptyFields.show();
        }
    }

    private boolean emptyFields() {
        return employeeNameComboBox.getSelectionModel().isEmpty()
                || locationComboBox.getSelectionModel().isEmpty()
                || equipmentSelection.getSelectionModel().isEmpty();
    }

    public void switchToRequestList() throws IOException {
        PageManager.getInstance().loadPage(PageManager.Pages.RequestList);
    }

    private void pushMedEquipToDB() throws SQLException {
        ArrayList<String> meFields = new ArrayList<String>();
        meFields.add(equipmentSelection.getSelectionModel().getSelectedItem());
        meFields.add(locationToNodeID(locationComboBox.getSelectionModel().getSelectedItem()));
        meFields.add(getEmployeeID(employeeNameComboBox.getSelectionModel().getSelectedItem()));
        if (emergencyButton.getValue()) {
            meFields.add("1");
        } else {
            meFields.add("0");
        }
        try {
            RequestFactory.getRequestFactory()
                    .getRequest(RequestType.MedicalEquipmentRequest, meFields, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        equipmentSelection.getSelectionModel().clearSelection();
        locationComboBox.getSelectionModel().clearSelection();
        employeeNameComboBox.getSelectionModel().clearSelection();
        emergencyButton.setValue(false);
        try {
            loadPieChart();
        } catch (NonExistingMedEquip e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getEmployeeID(String name) throws SQLException {
        name = name.trim();
        Integer employeeID = null;
        String employeeLastName;
        String employeeFirstName;
        Integer commaIndex = name.indexOf(',');
        employeeLastName = name.substring(0, commaIndex);
        employeeFirstName = name.substring(commaIndex + 2);
        employeeID = EmployeeManager.getEmployeeManager().getEmployeeFromName(employeeLastName, employeeFirstName).getEmployeeID();

        return String.format("%d", employeeID);
    }

    private String locationToNodeID(String target) {
        String nodeID = "FAIL";

        ArrayList<Location> locationsRaw = null;
        try {
            locationsRaw = LocationManager.getLocationManager().getAllLocations();
        } catch (SQLException e) {
            System.out.println("Failed to unearth locations from database");
            e.printStackTrace();
        }
        for (Location l : locationsRaw) {
            if (l.getLongName().equals(target)) {
                nodeID = l.getNodeID();
            }
        }
        return nodeID;
    }

    private ArrayList<String> getEmployeeNames() {
        ArrayList<String> name = new ArrayList<>();
        ArrayList<Employee> employees = null;
        ArrayList<EmployeeType> types = new ArrayList<>();
        types.add(EmployeeType.Staff);
        types.add(EmployeeType.Nurse);
        types.add(EmployeeType.Doctor);
        try {
            employees = EmployeeManager.getEmployeeManager().getEmployeeListByType(types);
        } catch (SQLException e) {
            System.out.println("Failed to unearth employees from database");
            e.printStackTrace();
        }
        for (Employee e : employees) {
            String empName = String.format("%s, %s", e.getLastName(), e.getFirstName());
            name.add(empName);
        }
        return name;
    }

    private ArrayList<String> getLocations() {
        ArrayList<String> locations = new ArrayList<>();
        ArrayList<Location> locationsRaw = null;
        ArrayList<Integer> removeIndexes = new ArrayList<>();
        try {
            locationsRaw = LocationManager.getLocationManager().getAllLocations();
        } catch (SQLException e) {
            System.out.println("Failed to unearth locations from database");
            e.printStackTrace();
        }

        for (Location l : locationsRaw) {
            if (l.getNodeType().equals("NONE")) {
            } else locations.add(l.getLongName());
        }
        return locations;
    }

    private ArrayList<String> getEquipList() {
        ArrayList<String> equip = new ArrayList<>();
        for (MedEquipType s : MedEquipType.values()) {
            if (!s.equals(MedEquipType.NONE)) {
                equip.add(s.getString());
            }
        }
        return equip;
    }
}
