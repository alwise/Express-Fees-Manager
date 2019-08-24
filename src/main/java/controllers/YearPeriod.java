package main.java.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import main.java.Main;
import main.java.models.Constants;
import main.java.models.Views;
import main.java.util.DbActions;
import main.java.util.DbProps;

import java.net.URL;
import java.util.ResourceBundle;

public class YearPeriod extends DbActions implements Initializable {
    @FXML ChoiceBox<String> year,period;
    @FXML
    Button continueBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> years = FXCollections.observableArrayList();
        ObservableList<String> periods = FXCollections.observableArrayList();

        for (int i = 2019; i <2021 ; i++) {
            years.add(String.valueOf(i));
        }
        year.setItems(years);

        periods.addAll("Period 1","Period 2","Period 3");
        period.setItems(periods);

    }


    @FXML
    private void continueToStart(){
        try{
            if (null == year.getValue())
                return;

            if (null == period.getValue())
                return;

            //Constants.YEAR = year.getValue();

            Constants.PERIOD = period.getValue().replace(" ","");

        }catch (Exception e){
            System.out.println("Empty values selected for year and period");
            return;
        }

        System.out.println("new table : "+ DbProps.feesTable1.getProp());
        Platform.runLater(() -> {
            createDbTables();
            Main.changeCenterView(Views.registerView.getTitle(),Views.registerView.getView());
            Main.closeNewStage();
        });

    }
    @FXML
    private void exitApp(){
        Platform.exit();
    }
}
