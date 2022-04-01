package edu.wpi.cs3733.d22.teamW.wMid;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
  public enum Scenes {
    Lab,
    LanguageInterpreter,
    MealDelivery,
    MedicalEquipment,
    MedicineDelivery,
    Security,
    Default,
    MapEditor
  }

  private static class Instance {
    public static final SceneManager instance = new SceneManager();
  }

  private final Dictionary<Scenes, String> fileNames;
  private Stage primaryStage;

  private SceneManager() {
    fileNames = new Hashtable<>();
    fileNames.put(Scenes.Lab, "ServiceRequestPages/LabServiceRequestPage.fxml");
    fileNames.put(
        Scenes.LanguageInterpreter,
        "ServiceRequestPages/LanguageInterpreterServiceRequestPage.fxml");
    fileNames.put(Scenes.MealDelivery, "ServiceRequestPages/MealDeliveryServiceRequestPage.fxml");
    fileNames.put(
        Scenes.MedicalEquipment, "ServiceRequestPages/MedicalEquipmentServiceRequestPage.fxml");
    fileNames.put(
        Scenes.MedicineDelivery, "ServiceRequestPages/MedicineDeliveryServiceRequestPage.fxml");
    fileNames.put(Scenes.Security, "ServiceRequestPages/SecurityServiceRequestPage.fxml");
    fileNames.put(Scenes.Default, "DefaultPage.fxml");
    fileNames.put(Scenes.MapEditor, "MapEditorPage.fxml");
  }

  public static SceneManager getInstance() {
    return Instance.instance;
  }

  public void setPrimaryStage(Stage primaryStage) {
    getInstance().primaryStage = primaryStage;
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public void setScene(Scenes sceneType) throws IOException {
    Parent root =
        FXMLLoader.load(
            getClass()
                .getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/" + fileNames.get(sceneType)));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public Scene getScene() {
    return primaryStage.getScene();
  }
}
