package edu.wpi.cs3733.d22.teamW.wApp.controllers.ServiceRequestControllers;

import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class ServiceRequestHelper {

  ArrayList<Control> fields = new ArrayList<>();

  public ServiceRequestHelper(ArrayList<Control> fields) {
    this.fields = fields;
  }

  // false when ALL empty, true when ALL full
  public boolean fieldsFull() {
    boolean result = false;

    for (Control field : fields) {
      if (field.getClass().equals(TextField.class)) {
        result = !((TextField) field).getText().isEmpty();
      } else if (field.getClass().equals(ComboBox.class)) {
        result = !((ComboBox) field).getValue().toString().equals(null);
      }
    }

    return result;
  }

  public void clearFields() {
    for (Control field : fields) {
      if (field.getClass().equals(TextField.class)) {
        ((TextField) field).clear();
      } else if (field.getClass().equals(ComboBox.class)) {
        ((ComboBox) field).setValue(null);
      }
    }
  }
}
