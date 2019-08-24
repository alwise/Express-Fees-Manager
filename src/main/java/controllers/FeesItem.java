package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.text.Text;
import main.java.models.Constants;
import main.java.models.Person;
import main.java.models.PersonFeesModel;
import main.java.util.DbActions;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class FeesItem extends DbActions implements Initializable {
    @FXML public Text year,period,level,amount,date;
    @FXML Button deleteBtn;
    @FXML
    AnchorPane rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       PersonFeesModel fee =  PersonFees.getPerson();

       if(fee != null){
          rootPane.setDisable(fee.isDelete());
          year.setText(fee.getYr());
          period.setText(fee.getPeriod());
          level.setText(fee.getLevel());
          amount.setText(fee.getAmount());
          date.setText(fee.getDate());
          deleteBtn.setDisable(fee.isDelete());
       }

    }




    @FXML
    private void deleteItem(ActionEvent event){
        List<Object> params = new ArrayList<>();
        params.add(true);
        params.add(Constants.ID);
        params.add(date.getText().trim());
        deletePayment(params);

        //disable deleted..
        rootPane.setDisable(true);
    }



}
