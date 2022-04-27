package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.enums.EmployeeType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedEquipType;
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
import javafx.util.Duration;

public class MedicalEquipmentServiceRequestController implements Initializable {
  private FadeTransition fadeOut = new FadeTransition(Duration.millis(5000));

  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();
  @FXML
  PieChart requestChart;
  @FXML AutoCompleteInput equipmentSelection;
  @FXML AutoCompleteInput employeeNameComboBox;
  @FXML AutoCompleteInput locationComboBox;

  @FXML Label successLabel;

  @FXML EmergencyButton emergencyButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      onLoad();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
                    new PieChart.Data("Grapefruit", 13),
                    new PieChart.Data("Oranges", 25),
                    new PieChart.Data("Plums", 10),
                    new PieChart.Data("Pears", 22),
                    new PieChart.Data("Apples", 30));
    requestChart.setData(pieChartData);
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
  }

  private String getEmployeeID(String name) throws SQLException {
    name = name.trim();
    Integer employeeID = null;
    String employeeLastName;
    String employeeFirstName;
    Integer commaIndex = name.indexOf(',');
    employeeLastName = name.substring(0, commaIndex);
    employeeFirstName = name.substring(commaIndex + 2);
    employeeID = EmployeeManager.getEmployeeManager().getEmployeeFromName(employeeLastName,employeeFirstName).getEmployeeID();

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
