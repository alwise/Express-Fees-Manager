package main.java.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import main.java.Main;
import main.java.models.Constants;
import main.java.util.DbActions;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static main.java.Main.closeNewStage;


public class MakePayment extends DbActions implements Initializable {
    @FXML
    ChoiceBox<String> payeeLevel,payeePeriod;
    @FXML
    TextField payeeAmount;
    @FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            payeeLevel.setItems(getLevels());
            payeePeriod.setItems(getPeriods());
        });

    }

    @FXML
    private void submitPay(){
        if (null == payeePeriod.getValue())
            return;
        if (null == getLevel())
            return;
        if (null == getAmount())
            return;

        List<Object> fees = new ArrayList<>();
        fees.add(Constants.ID);
        fees.add(Constants.YEAR);
        fees.add(Constants.PERIOD);
        fees.add(getLevel());
        fees.add(getAmount());
        fees.add(LocalDateTime.now().toString());
        fees.add(false);

        if (!Main.showOptionDialog(Main.mStage,"Confirm payment?")){
            System.out.println("dialog false");
            return;
        }

        Platform.runLater(() -> {
            payFees(fees);//submit fees to original fees table;
        });


        fees.clear();//clear old params and update new list data
        fees.add(Constants.ID);
        fees.add(Constants.NAME);
        fees.add(payeePeriod.getValue().concat(" ").concat(Constants.YEAR));
        fees.add(getLevel());
        fees.add(getAmount());
        fees.add(LocalDateTime.now());

        Platform.runLater(() -> {
            updateTableTwoFees(fees);
            closeNewStage();
        });//update table wo fees//
    }

   private String getLevel(){
        try{
            if (null == payeeLevel.getValue() ){
                return null;
            }
            return  payeeLevel.getValue();

        }catch (Exception e){
            System.out.println("cannot get level");
            return  null;
        }
    }

   private String  getAmount(){
      try{
          double dbAmount = Double.parseDouble(payeeAmount.getText());
          return String.valueOf(dbAmount);
      }catch (Exception e){
          System.out.println("cannot get amount");
          return null;
      }
    }


    @FXML
    public void validateAmount(KeyEvent keyEvent) {

        if (payeeAmount.getText().trim().contains(".")
                && keyEvent.getCharacter().equalsIgnoreCase(".")) {
                    keyEvent.consume();
                    return;
        }

        if (!keyEvent.getCharacter().matches("[0-9.]")){
            keyEvent.consume();
        }



    }


}
