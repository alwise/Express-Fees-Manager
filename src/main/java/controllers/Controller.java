package main.java.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import main.java.models.FxmlFile;
import main.java.models.Views;

import javax.swing.text.View;
import java.net.URL;
import java.util.ResourceBundle;

import static main.java.Main.*;


public class Controller implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    @FXML
    public void launchAddView(ActionEvent actionEvent) {
        if (actionEvent != null){

            launchNewStage(Views.addPerson.getView(),Views.addPerson.getTitle(),false);

        }
    }

    @FXML
    public void lunchRegisterView(ActionEvent actionEvent) {
        if (actionEvent != null){
            showProgress();
            Task<Integer> progress = new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    for (int i = 0; i < 5 ; i++) {
                        System.out.println(i);
                        Thread.sleep(500);
                    }
                    return 0;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    mStageProgress.close();
                }
            };

            new Thread(progress).start();


        }
    }
}
