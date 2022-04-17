package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
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

public class LanguageInterpreterServiceRequestController {
  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();

  @FXML ComboBox<String> equipmentSelection;
  // location here
  @FXML TextField employeeName;
  int emergency;

  @FXML EmergencyButton emergencyB;

  RequestFactory requestFactory = RequestFactory.getRequestFactory();
  ArrayList<String> lastRequest;

  public void submitButton(ActionEvent actionEvent) throws Exception {
    System.out.println("Button Clicked");
    if ((equipmentSelection.getValue() != null) && !employeeName.getText().isEmpty()) {
      confirm.showAndWait();
      if (confirm.getResult() == ButtonType.OK) {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add(equipmentSelection.getValue());
        System.out.println(equipmentSelection.getValue());
        fields.add("wSTOR001L1"); // location
        fields.add(employeeName.getText());
        if (emergencyB.getValue()) {
          emergency = 1;
        } else {
          emergency = 0;
        }
        fields.add("" + emergency);
        requestFactory.getRequest(RequestType.LanguageInterpreter, fields, false);
        lastRequest = fields;
      }
    } else {
      emptyFields.show();
    }
  }

  public void cancelButton(ActionEvent actionEvent) {
    // MedicalEquipmentController.cancel(requestFactory.getRequest("MEDEQUIPREQUEST", fields));
  }

  public void switchToRequestList(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.RequestList);
  }
}
