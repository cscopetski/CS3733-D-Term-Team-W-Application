package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class DefaultPage {

  @FXML private Pane content;

  public void switchToMedicineDelivery(ActionEvent event) throws IOException {
    content.getChildren().clear();
    content
        .getChildren()
        .add(
            FXMLLoader.load(
                getClass()
                    .getResource(
                        "/edu/wpi/cs3733/d22/teamW/wApp/views/ServiceRequestPages/MedicineDeliveryServiceRequestPage.fxml")));
  }

  public void switchToLab(ActionEvent event) throws IOException {
    content.getChildren().clear();
    content
        .getChildren()
        .add(
            FXMLLoader.load(
                getClass()
                    .getResource(
                        "/edu/wpi/cs3733/d22/teamW/wApp/views/ServiceRequestPages/LabServiceRequestPage.fxml")));
  }

  public void switchToMedicalEquipmentDelivery(ActionEvent event) throws IOException {
    content.getChildren().clear();
    content
        .getChildren()
        .add(
            FXMLLoader.load(
                getClass()
                    .getResource(
                        "edu/wpi/cs3733/d22/teamW/wApp/views/ServiceRequestPages/MedicalEquipmentServiceRequestPage.fxml")));
  }

  public void switchToMealDelivery(ActionEvent event) throws IOException {
    content.getChildren().clear();
    content
        .getChildren()
        .add(
            FXMLLoader.load(
                getClass()
                    .getResource(
                        "/edu/wpi/cs3733/d22/teamW/wApp/views/ServiceRequestPages/MealDeliveryServiceRequestPage.fxml")));
  }

  public void switchToLanguageInterpreter(ActionEvent event) throws IOException {
    content.getChildren().clear();
    content
        .getChildren()
        .add(
            FXMLLoader.load(
                getClass()
                    .getResource(
                        "/edu/wpi/cs3733/d22/teamW/wApp/views/ServiceRequestPages/LanguageInterpreterServiceRequestPage.fxml")));
  }

  public void switchToSecurity(ActionEvent event) throws IOException {
    content.getChildren().clear();
    content
        .getChildren()
        .add(
            FXMLLoader.load(
                getClass()
                    .getResource(
                        "/edu/wpi/cs3733/d22/teamW/wApp/views/ServiceRequestPages/SecurityServiceRequestPage.fxml")));
  }

  public void switchToMapEditor(ActionEvent event) throws IOException {
    content.getChildren().clear();
    content
        .getChildren()
        .add(
            FXMLLoader.load(
                getClass().getResource("/edu/wpi/cs3733/d22/teamW/wApp/views/MapEditorPage.fxml")));
  }
}
