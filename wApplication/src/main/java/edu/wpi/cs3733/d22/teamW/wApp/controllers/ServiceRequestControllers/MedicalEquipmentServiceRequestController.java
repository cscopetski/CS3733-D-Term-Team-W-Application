package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import edu.wpi.cs3733.d22.teamW.wDB.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MedicalEquipmentServiceRequestController {
  Alert confirm =
      new Alert(
          Alert.AlertType.CONFIRMATION,
          "Would you like to confirm this request " + " ?",
          ButtonType.OK,
          ButtonType.CANCEL);
  Alert emptyFields =
      new Alert(
          Alert.AlertType.ERROR,
          "There are required fields empty " + " !",
          ButtonType.OK,
          ButtonType.CANCEL);
  @FXML ComboBox<String> equipmentSelection;
  // location here
  @FXML TextField employeeName;
  @FXML CheckBox emergencyLevel;
  int emergency;

  RequestFactory requestFactory = RequestFactory.getRequestFactory();

  public void submitButton(ActionEvent actionEvent) throws SQLException {

    if (!equipmentSelection.getValue().isEmpty() && !employeeName.getText().isEmpty()) {
      confirm.show();
      if (confirm.getResult() == ButtonType.OK) {
        System.out.println("confirm");
        ArrayList<String> fields = new ArrayList<String>();
        fields.add(equipmentSelection.getValue());
        fields.add("wSTOR001L1");
        fields.add(employeeName.getText());
        if (emergencyLevel.isSelected()) {
          emergency = 1;
        } else {
          emergency = 0;
        }
        fields.add("" + emergency);
        for (String e : fields) {
          System.out.println(e);
        }
        requestFactory.getRequest("MEDEQUIPREQUEST", fields);
      }
    } else {
      emptyFields.show();
    }
  }
}
