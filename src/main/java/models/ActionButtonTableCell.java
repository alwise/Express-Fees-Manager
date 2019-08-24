/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alwise Studio
 */
package main.java.models;

import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ActionButtonTableCell<S> extends TableCell<S, Button> {

    private final Button actionButton;

    public ActionButtonTableCell(String label, Function< S, S> function) {
        this.actionButton = new Button(label);
        if (label.equalsIgnoreCase("Edit")) {
            this.actionButton.setStyle("-fx-background-color:green;-fx-text-fill:white;-fx-background-radius:2;");
        }
        if (label.equalsIgnoreCase("Delete")) {
            this.actionButton.setStyle("-fx-background-color:red;-fx-text-fill:white;-fx-background-radius:2;");
        }
        if (label.equals("Pay")) {
            this.actionButton.setStyle("-fx-background-color:#309BE5;-fx-text-fill:white;-fx-background-radius:2;");
        }
        if (label.equalsIgnoreCase("View")) {
            this.actionButton.setStyle("-fx-background-color:#309BE5;-fx-text-fill:white;-fx-background-radius:2;");
        }
        this.actionButton.setCursor(Cursor.HAND);

        this.actionButton.setOnAction((ActionEvent e) -> {
            function.apply(getCurrentItem());
        });
        this.actionButton.setMaxWidth(actionButton.getPrefWidth());
        this.actionButton.setMaxHeight(actionButton.getPrefHeight());
    }

    public S getCurrentItem() {
        return (S) getTableView().getItems().get(getIndex());
    }

    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String label, Function< S, S> function) {
        return param -> new ActionButtonTableCell<>(label, function);
    }

    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(actionButton);
        }
    }
}
