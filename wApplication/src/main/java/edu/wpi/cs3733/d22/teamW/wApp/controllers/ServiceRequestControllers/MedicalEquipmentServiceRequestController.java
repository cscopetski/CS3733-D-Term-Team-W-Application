package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.DefaultPageController;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmergencyButton;
import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MedicalEquipmentServiceRequestController {
  Alert invalidFields =
      new Alert(
          Alert.AlertType.ERROR,
          "Invalid characters in Employee ID input" + " !",
          ButtonType.OK,
          ButtonType.CANCEL);

  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();
  @FXML ComboBox<String> equipmentSelection;
  @FXML TextField id;
  // location here
  // boolean emergencyLevel = false;
  int emergency;

  @FXML EmergencyButton emergencyB;

  RequestFactory requestFactory = RequestFactory.getRequestFactory();
  ArrayList<String> lastRequest;

  public void submitButton(ActionEvent actionEvent) throws Exception {
    System.out.println("Button Clicked");
    if ((equipmentSelection.getValue() != null)) {
      confirm.showAndWait();
      if (confirm.getResult() == ButtonType.OK) {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add(getEquipTypeID(equipmentSelection.getValue()));
        fields.add("wSTOR001L1"); // location
        fields.add(
            ((DefaultPageController)
                    SceneManager.getInstance().getController(SceneManager.Scenes.Default))
                .getEmployee()
                .getEmployeeID()
                .toString());
        if (emergencyB.getValue()) {
          emergency = 1;
        } else {
          emergency = 0;
        }
        fields.add("" + emergency);
        requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields, false);
        lastRequest = fields;
      } else {
        invalidFields.show();
      }
    } else {
      emptyFields.show();
    }
  }

  public void switchToRequestList(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.RequestList);
  }

  public void onEnter(ActionEvent actionEvent) throws Exception {
    submitButton(actionEvent);
  }

  private String getEquipTypeID(String equip) {
    String returnVal;
    switch (equip) {
      case "Patient Bed":
        returnVal = "BED";
        break;
      case "Infusion Pump":
        returnVal = "INP";
        break;
      case "Recliner":
        returnVal = "REC";
        break;
      case "XRay Machine":
        returnVal = "XRY";
        break;
      default:
        returnVal = null;
        break;
    }
    return returnVal;
  }
}
