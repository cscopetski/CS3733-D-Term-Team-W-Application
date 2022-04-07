package edu.wpi.cs3733.d22.teamW.wApp.controllers.CustomControls;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class CustomControl extends VBox {
  public CustomControl() throws IOException {
    FXMLLoader fxmlLoader =
        new FXMLLoader(
            getClass()
                .getResource(
                    "edu/wpi/cs3733/d22/teamW/wApp/views/CustomControls/CustomControl.fxm"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    fxmlLoader.load();
  }
}
