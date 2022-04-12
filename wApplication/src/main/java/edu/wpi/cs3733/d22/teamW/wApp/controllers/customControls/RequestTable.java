package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.*;
import edu.wpi.cs3733.d22.teamW.wDB.*;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RequestTable extends TableView<SR> {

  public RequestTable() {
    super();
    getStylesheets().add("edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/Standard.css");

    getColumns()
        .addAll(
            createColumn("Request ID", "RequestID"),
            createColumn("Request Type", "RequestType"),
            createColumn("Employee ID", "EmployeeID"),
            createColumn("Status", "Status"));

    // distributeColumnWidths();
    List<Request> requests = null;
    try {
      requests = RequestFactory.getRequestFactory().getAllRequests();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    setItems(requests);
  }

  public void setColumnWidth(String columnText, double prefWidth) {
    for (TableColumn c : getColumns()) {
      if (c.getText().equals(columnText)) {
        c.setPrefWidth(prefWidth);
        return;
      }
    }
  }

  private TableColumn createColumn(String columnText, String propertyName) {
    TableColumn column = new TableColumn(columnText);
    column.setCellValueFactory(new PropertyValueFactory(propertyName));
    return column;
  }

  public void distributeColumnWidths() {
    for (TableColumn c : getColumns()) {
      c.prefWidthProperty().bind(widthProperty().divide(4));
    }
  }

  private void setItems(List<Request> requests) {
    getItems().clear();
    for (Request r : requests) {
      SR sr = null;
      switch (r.getRequestType()) {
        case MedicalEquipmentRequest:
          sr = new MedicalEquipmentSR(r);
          break;
        case LabServiceRequest:
          sr = new LabServiceSR(r);
          break;
        case LanguageInterpreter:
          sr = new LanguageInterpreterSR(r);
          break;
        case MealDelivery:
          sr = new MealDeliverySR(r);
          break;
        case SecurityService:
          sr = new SecuritySR(r);
          break;
        case MedicineDelivery:
          sr = new MedicineDeliverySR(r);
          break;
      }

      getItems().add(sr);
    }
    getSelectionModel().clearSelection();
  }

  public SR getSelection() {
    return getSelectionModel().getSelectedItem();
  }
}
