package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.LabServiceSR;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.MedicalEquipmentSR;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.SR;
import edu.wpi.cs3733.d22.teamW.wDB.Request;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import java.util.List;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RequestTable extends TableView<SR> {

  public RequestTable() { // Class<SR> requestType
    super();
    TableColumn requestIDColumn = new TableColumn("Request ID");
    TableColumn requestTypeColumn = new TableColumn("Request Type");
    TableColumn employeeNameColumn = new TableColumn("Employee Name");
    TableColumn statusColumn = new TableColumn("Status");
    requestIDColumn.setCellValueFactory(new PropertyValueFactory("RequestID"));
    requestTypeColumn.setCellValueFactory(new PropertyValueFactory("RequestType"));
    employeeNameColumn.setCellValueFactory(new PropertyValueFactory("EmployeeName"));
    statusColumn.setCellValueFactory(new PropertyValueFactory("Status"));
    // figure uot how to add the property value factory to the column
    getColumns().addAll(requestIDColumn, requestTypeColumn, employeeNameColumn, statusColumn);

    /*List<SR> requests =
            RequestFactory.getRequestFactory().getAllRequests().stream()
                .map(MedicalEquipmentSR::new)
                .collect(Collectors.toList());
    */
    List<Request> requests = RequestFactory.getRequestFactory().getAllRequests();
    for (Request r : requests) {
      if (r.getRequestType().equals("MEDICALEQUIPREQUEST")) {
        this.getItems().add(new MedicalEquipmentSR(r));
      } else if (r.getRequestType().equals("LABSERVICEREQUEST")) {
        this.getItems().add(new LabServiceSR(r));
      }
    }
    // this.getItems().addAll(requests);
  }
}
