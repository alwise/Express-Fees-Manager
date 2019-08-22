package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import main.java.models.Constants;
import main.java.util.DbActions;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MakePayment extends DbActions implements Initializable {
    @FXML
    ChoiceBox<String> payeeLevel;
    @FXML
    TextField payeeAmount;
    @FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void submitPay(){
        List<String> fees = new ArrayList<>();
        fees.add("1");
        fees.add(Constants.LEVEL);
                fees.add("$200");
         payFees(fees);
    }
}
