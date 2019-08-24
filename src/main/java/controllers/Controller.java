package main.java.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import main.java.Main;
import main.java.models.Constants;
import main.java.models.Views;
import main.java.util.DbActions;
import main.java.util.DbProps;

import java.net.URL;
import java.util.ResourceBundle;

import static main.java.Main.*;


public class Controller extends DbActions implements Initializable {
    @FXML
    MenuButton periods;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(this::createDbTables);

    }

    @FXML
    public void launchAddView(ActionEvent actionEvent) {
        if (actionEvent == null)
            return;
        Constants.IS_UPDATE = false;
        launchNewStage(Views.addPerson.getView(),Views.addPerson.getTitle(),false,true);
    }

    @FXML
    public void lunchRegisterView(ActionEvent actionEvent) {
        if (actionEvent == null)
            return;
        Main.changeCenterView(Views.registerView.getTitle(),Views.registerView.getView());
    }

    @FXML
    public void sortedFeesView(ActionEvent event) {
        if (event == null)
            return;
        Main.changeCenterView(Views.sortFees.getTitle(),Views.sortFees.getView());
    }

    @FXML
    public void startSettingsPage(ActionEvent event) {
        if (event == null)
            return;
        Main.changeCenterView(Views.config.getTitle(),Views.config.getView());
    }

    @FXML
    public void setPeriod(MouseEvent mouseEvent) {
        if (mouseEvent == null)
            return;
        ObservableList<String> periodList = getPeriods();
        if (periodList != null && !periodList.isEmpty() ){
            periods.getItems().clear();
            periodList.stream().map(MenuItem::new).forEach(item -> {
                item.setOnAction(ev -> {
                    Constants.PERIOD = item.getText();
                    periods.setText(item.getText());
                });
                periods.getItems().addAll(item);
            });
        }




    }


}
