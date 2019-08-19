package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import main.java.models.FxmlFile;

import java.net.URL;
import java.util.ResourceBundle;

import static main.java.Main.launchNewStage;
import static main.java.models.FxmlFile.ADD_VIEW_TITLE;

public class Controller implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    @FXML
    public void launchAddView(ActionEvent actionEvent) {
        if (actionEvent != null){

            launchNewStage(FxmlFile.ADD_VIEW, ADD_VIEW_TITLE,false);

        }
    }

    @FXML
    public void lunchRegisterView(ActionEvent actionEvent) {
        if (actionEvent != null){

            launchNewStage(FxmlFile.PROGRESS_VIEW, null,false);

        }
    }
}
