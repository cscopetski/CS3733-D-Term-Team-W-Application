package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javax.swing.*;

public class LabServiceRequestController extends LoadableController {

  // Combo Boxes:
  @FXML ComboBox employeeIDCBox;
  @FXML ComboBox locationCBox;
  @FXML CheckBox bloodBox;
  @FXML CheckBox urineBox;
  @FXML CheckBox mriBox;
  @FXML CheckBox xRayBox;
  @FXML CheckBox catBox;

  int emergency = 0;
  @FXML EmergencyButton emergencyB;

  // Alerts:
  Alert emptyFields = new EmptyAlert();

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    createRequest();
    clearFields();
  }

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Lab;
  }

  @Override
  public void onLoad() {
    locationCBox.setItems(FXCollections.observableArrayList(getLocations()));
    employeeIDCBox.setItems(FXCollections.observableArrayList(getEmployeeIDs()));
  }

  @Override
  public void onUnload() {
    clearFields();
  }

  // ----------------------- HELPERS -------------------

  // TRUE if ALL filled --> OR at least one Check Box
  private boolean fieldsFull() {
    boolean result =
        (!(employeeIDCBox.getSelectionModel().isEmpty())
            && !(locationCBox.getSelectionModel().isEmpty())
            && (catBox.isSelected()
                || mriBox.isSelected()
                || xRayBox.isSelected()
                || bloodBox.isSelected()
                || urineBox.isSelected()));

    return result;
  }

  private void createRequest() {
    if (fieldsFull()) {
      try {
        pushDataToDB();
      } catch (SQLException e) {
        System.out.println("Unable to push Medicine Delivery request to DB");
        e.printStackTrace();
      }
    } else {
      emptyFields.show();
    }
  }

  private void pushDataToDB() throws SQLException {

    // Check Boxes:
    if (bloodBox.isSelected()) {
      ArrayList<String> fields = new ArrayList<>();

      fields.add("Blood Sample");
      fields.add(locationToNodeID(locationCBox.getSelectionModel().getSelectedItem().toString()));
      fields.add(employeeIDCBox.getSelectionModel().getSelectedItem().toString());
      if (emergencyB.getValue()) {
        emergency = 1;
      } else {
        emergency = 0;
      }
      fields.add(String.valueOf(emergency));
      for (String e : fields) {
        System.out.println(e);
      }
      RequestFactory.getRequestFactory().getRequest(RequestType.LabServiceRequest, fields);
    }
    if (urineBox.isSelected()) {
      ArrayList<String> fields = new ArrayList<>();

      fields.add("Urine Sample");
      fields.add(locationToNodeID(locationCBox.getSelectionModel().getSelectedItem().toString()));
      fields.add(employeeIDCBox.getSelectionModel().getSelectedItem().toString());
      if (emergencyB.getValue()) {
        emergency = 1;
      } else {
        emergency = 0;
      }
      fields.add(String.valueOf(emergency));
      for (String e : fields) {
        System.out.println(e);
      }
      RequestFactory.getRequestFactory().getRequest(RequestType.LabServiceRequest, fields);
    }
    if (mriBox.isSelected()) {
      ArrayList<String> fields = new ArrayList<>();

      fields.add("MRI");
      fields.add(locationToNodeID(locationCBox.getSelectionModel().getSelectedItem().toString()));
      fields.add(employeeIDCBox.getSelectionModel().getSelectedItem().toString());
      if (emergencyB.getValue()) {
        emergency = 1;
      } else {
        emergency = 0;
      }
      fields.add(String.valueOf(emergency));
      for (String e : fields) {
        System.out.println(e);
      }
      RequestFactory.getRequestFactory().getRequest(RequestType.LabServiceRequest, fields);
    }
    if (xRayBox.isSelected()) {
      ArrayList<String> fields = new ArrayList<>();

      fields.add("X-Ray");
      fields.add(locationToNodeID(locationCBox.getSelectionModel().getSelectedItem().toString()));
      fields.add(employeeIDCBox.getSelectionModel().getSelectedItem().toString());
      if (emergencyB.getValue()) {
        emergency = 1;
      } else {
        emergency = 0;
      }
      fields.add(String.valueOf(emergency));
      for (String e : fields) {
        System.out.println(e);
      }
      RequestFactory.getRequestFactory().getRequest(RequestType.LabServiceRequest, fields);
    }
    if (catBox.isSelected()) {
      ArrayList<String> fields = new ArrayList<>();

      fields.add("CAT Scan");
      fields.add(locationToNodeID(locationCBox.getSelectionModel().getSelectedItem().toString()));
      fields.add(employeeIDCBox.getSelectionModel().getSelectedItem().toString());
      if (emergencyB.getValue()) {
        emergency = 1;
      } else {
        emergency = 0;
      }
      fields.add(String.valueOf(emergency));
      for (String e : fields) {
        System.out.println(e);
      }
      RequestFactory.getRequestFactory().getRequest(RequestType.LabServiceRequest, fields);
    }
  }

  private void clearFields() {
    employeeIDCBox.getSelectionModel().clearSelection();
    locationCBox.getSelectionModel().clearSelection();
    bloodBox.setSelected(false);
    urineBox.setSelected(false);
    mriBox.setSelected(false);
    xRayBox.setSelected(false);
    catBox.setSelected(false);
  }

  public String locationToNodeID(String target) {
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

  private ArrayList<Integer> getEmployeeIDs() {
    ArrayList<Integer> ids = new ArrayList<>();
    ArrayList<Employee> employees = null;
    try {
      employees = EmployeeManager.getEmployeeManager().getAllEmployees();
    } catch (SQLException e) {
      System.out.println("Failed to unearth employees from database");
      e.printStackTrace();
    }
    for (Employee e : employees) {
      ids.add(e.getEmployeeID());
    }
    return ids;
  }

  private ArrayList<String> getLocations() {
    ArrayList<String> locations = new ArrayList<>();
    ArrayList<Location> locationsRaw = null;
    try {
      locationsRaw = LocationManager.getLocationManager().getAllLocations();
    } catch (SQLException e) {
      System.out.println("Failed to unearth locations from database");
      e.printStackTrace();
    }
    for (Location l : locationsRaw) {
      locations.add(l.getLongName());
    }
    return locations;
  }
}
