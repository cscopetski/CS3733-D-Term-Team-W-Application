package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.ConfirmAlert;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.DefaultPageController;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.EmptyAlert;
import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MedicalEquipmentServiceRequestController {
  Alert confirm = new ConfirmAlert();
  Alert emptyFields = new EmptyAlert();

  @FXML ComboBox<String> equipmentSelection;
  // location here
  boolean emergencyLevel = false;
  int emergency;

  @FXML Button emergencyB;

  RequestFactory requestFactory = RequestFactory.getRequestFactory();
  ArrayList<String> lastRequest;

  public void submitButton(ActionEvent actionEvent) throws SQLException {
    System.out.println("Button Clicked");
    if ((equipmentSelection.getValue() != null)) {
      confirm.showAndWait();
      if (confirm.getResult() == ButtonType.OK) {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add(equipmentSelection.getValue());
        fields.add("wSTOR001L1"); // location
        fields.add(
            ((DefaultPageController)
                    SceneManager.getInstance().getController(SceneManager.Scenes.Default))
                .getEmployee()
                .getEmployeeID()
                .toString());
        if (emergencyLevel) {
          emergency = 1;
        } else {
          emergency = 0;
        }
        fields.add("" + emergency);
        requestFactory.getRequest(RequestType.MedicalEquipmentRequest, fields);
        lastRequest = fields;
      }
    } else {
      emptyFields.show();
    }
  }

  public void switchToRequestList(ActionEvent event) throws IOException {
    SceneManager.getInstance().transitionTo(SceneManager.Scenes.RequestList);
  }

  public void onEnter(ActionEvent actionEvent) throws SQLException {
    submitButton(actionEvent);
  }
}
