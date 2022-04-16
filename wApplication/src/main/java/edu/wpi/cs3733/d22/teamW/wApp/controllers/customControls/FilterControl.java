package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class FilterControl<E extends Enum<E>> extends ScrollPane {
  private final VBox container;
  private final AutoCompleteInput filterInput;
  private final ObservableList<E> filters;
  private E[] values;

  public FilterControl() {
    setFitToWidth(true);
    setHbarPolicy(ScrollBarPolicy.NEVER);

    container = new VBox();
    container.setSpacing(5);
    container.setAlignment(Pos.CENTER);
    setContent(container);

    filters =
        new ModifiableObservableListBase<E>() {
          ArrayList<E> list = new ArrayList<>();

          @Override
          public E get(int index) {
            return list.get(index);
          }

          @Override
          public int size() {
            return list.size();
          }

          @Override
          protected void doAdd(int index, E element) {
            list.add(index, element);
          }

          @Override
          protected E doSet(int index, E element) {
            return list.set(index, element);
          }

          @Override
          protected E doRemove(int index) {
            return list.remove(index);
          }
        };

    filterInput = new AutoCompleteInput();
    filterInput.autosize();
    filterInput
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (e, o, n) -> {
              if (n != null) {
                for (E value : values) {
                  if (value.toString().equals(n)) {
                    addFilter(value);
                    break;
                  }
                }
              }
              filterInput.getSelectionModel().clearSelection();
            });

    container.getChildren().add(filterInput);
  }

  public void addFilter(E value) {
    if (!filters.contains(value)) {
      filters.add(value);
      AnchorPane ap = new AnchorPane();
      ap.setStyle("-fx-border-width: 2px; -fx-border-color: #013895; -fx-border-radius: 15px");
      ap.setPadding(new Insets(5));
      Label filter = new Label(value.toString());
      Button remove = new Button("Remove?");
      remove.setOnAction(
          e -> {
            container.getChildren().remove(ap);
            filters.remove(value);
          });

      ap.getChildren().add(filter);
      ap.getChildren().add(remove);

      AnchorPane.setLeftAnchor(filter, 0.0);
      AnchorPane.setRightAnchor(remove, 0.0);
      AnchorPane.setTopAnchor(filter, 0.0);
      AnchorPane.setBottomAnchor(filter, 0.0);
      AnchorPane.setTopAnchor(remove, 0.0);
      AnchorPane.setBottomAnchor(remove, 0.0);

      container.getChildren().add(ap);
    }
  }

  public void loadValues(E[] values) {
    this.values = values;
    filterInput.loadValues(
        (ArrayList<String>) Arrays.stream(values).map(Enum::toString).collect(Collectors.toList()));
  }

  public ArrayList<E> getEnabledValues() {
    return (ArrayList<E>) filters.stream().collect(Collectors.toList());
  }

  public void addValuesListener(ListChangeListener lcl) {
    filters.addListener(lcl);
  }

  public void removeValuesListener(ListChangeListener lcl) {
    filters.removeListener(lcl);
  }
}
