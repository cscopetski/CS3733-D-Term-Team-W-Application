package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.AutoCompleteInput;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Location;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;

public class SecurityServiceRequestController extends LoadableController {

  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();

  // boolean emergencyLevel = false;
  int emergency = 0;

  @FXML EmergencyButton emergencyB;
  @FXML ChoiceBox threatChoice;
  @FXML AutoCompleteInput locationBox;

  ArrayList<String> threatLevels = new ArrayList<>(Arrays.asList("Wong", "Red", "Yellow", "Blue"));

  //  public void emergencyClicked(MouseEvent mouseEvent) {
  //    if (emergencyLevel) {
  //      emergencyLevel = false;
  //      emergencyB.getStylesheets().clear();
  //
  //      emergencyB
  //          .getStylesheets()
  //          .add(
  //
  // "edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/EmergencyButton/emergencyButtonFalse.css");
  //    } else {
  //      emergencyLevel = true;
  //      emergencyB.getStylesheets().clear();
  //      emergencyB
  //          .getStylesheets()
  //          .add(
  //
  // "edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/EmergencyButton/emergencyButtonTrue.css");
  //    }
  //  }

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Security;
  }

  @Override
  public void onLoad() throws SQLException {
    threatChoice.setItems(FXCollections.observableArrayList(threatLevels));
    locationBox.loadValues(getLocations());
  }

  @Override
  public void onUnload() {
    clearFields();
  }

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    if (checkEmptyFields()) emptyFields.show();
    else {
      confirm.showAndWait();
      if (confirm.getResult() == ButtonType.OK) {
        pushSecurityRequestToDB();
      }
    }
  }

  private void pushSecurityRequestToDB() throws SQLException {
    ArrayList<String> mdFields = new ArrayList<String>();
    mdFields.add(threatChoice.getValue().toString());
    System.out.println(threatChoice.getValue().toString());
  }

  public void cancelButton(ActionEvent event) {
    clearFields();
  }

  private void clearFields() {
    threatLevels.clear();
    // locationBox.clear(); // TODO: locationBox needs a clear function
  }

  private boolean checkEmptyFields() {
    return threatChoice.getSelectionModel().isEmpty() || locationBox.getSelectionModel().isEmpty();
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
