package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.Collection;
import java.util.Collections;

public class HistoryControl extends HBox {
    private final ComboBox<String> history;
    private final Button back;
    private final Button forward;

    boolean ignoreSelection = false;

    public HistoryControl() {
        setAlignment(Pos.CENTER);
        setFillHeight(true);
        setSpacing(10);

        history = new ComboBox<>();
        history.setVisibleRowCount(5);
        history.getSelectionModel().selectedIndexProperty().addListener((e, o, n) ->
        {
            if (ignoreSelection) {
                return;
            }
            //gets to here if it's a true selection(meaning someone clicked and selected rather than updating the list and auto selecting)
            if ((Integer) n >= 0) {
                PageManager.getInstance().setActiveIndex(history.getItems().size() - 1 - n.intValue());
                resetButtons();
            }
        });
        history.setEditable(false);

        back = new Button("<-");
        forward = new Button("->");
        back.setOnAction((e) -> {
            PageManager.getInstance().goBack();
            resetSelection();
            resetButtons();
        });
        forward.setOnAction((e) -> {
            PageManager.getInstance().goForward();
            resetSelection();
            resetButtons();
        });

        PageManager.getInstance().attachHistoryChangeListener(this::resetHistory);
        //PageManager.getInstance().attachPageChangeListener((o, n) -> resetHistory());

        getChildren().add(back);
        getChildren().add(history);
        getChildren().add(forward);
    }

    private void resetHistory() {
        ObservableList<String> temp = FXCollections.observableList(PageManager.getInstance().getHistory());
        FXCollections.reverse(temp);

        setItems(temp);
        resetSelection();
        resetButtons();
    }

    private void setItems(ObservableList<String> items) {
        ignoreSelection = true;
        history.setItems(items);
        ignoreSelection = false;
        history.autosize();
    }

    private void resetSelection() {
        ignoreSelection = true;
        if (PageManager.getInstance().getActiveIndex() != -1) {
            history.getSelectionModel().clearAndSelect(history.getItems().size() - 1 - PageManager.getInstance().getActiveIndex());
        }
        ignoreSelection = false;
        history.autosize();
    }

    private void resetButtons() {
        back.setDisable(!PageManager.getInstance().canGoBack());
        forward.setDisable(!PageManager.getInstance().canGoForward());
        history.autosize();
    }
}
