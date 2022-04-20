package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import java.util.Collection;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeTable extends TableView<Employee> {

  public EmployeeTable() {
    super();
    getStylesheets().add("edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/Standard.css");

    getColumns()
        .addAll(
            createColumn("ID", "EmployeeID"),
            createColumn("First Name", "FirstName"),
            createColumn("Last Name", "LastName"),
            createColumn("Type", "Type"),
            createColumn("Email", "Email"),
            createColumn("Phone Num", "Phone Number"),
            createColumn("Address", "Address"),
            createColumn("Username", "Username"));
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

  public void setItems(Collection<? extends Employee> employees) {
    getItems().clear();
    for (Employee e : employees) {

      getItems().add(e);
    }
    getSelectionModel().clearSelection();
  }
}
