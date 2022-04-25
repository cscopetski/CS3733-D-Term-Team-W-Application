package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import edu.wpi.cs3733.d22.teamW.wDB.entity.HighScore;
import java.util.Collection;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HighScoreTable extends TableView<HighScore> {

  public HighScoreTable() {
    super();
    getStylesheets().add("edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/Standard.css");

    getColumns()
        .addAll(
            createColumn("ID", "EmployeeID"),
            createColumn("Name", "Name"),
            createColumn("Wiggling Wong", "scoreWiggling"),
            createColumn("Threat Level Wong", "scoreThreat"));
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

  public void setItems(Collection<? extends HighScore> highscore) {
    getItems().clear();
    for (HighScore hs : highscore) {

      getItems().add(hs);
    }
    getSelectionModel().clearSelection();
  }
}
