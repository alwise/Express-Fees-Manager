package main.java.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.java.Main;
import main.java.models.Constants;
import main.java.models.Person;
import main.java.models.PersonFeesModel;
import main.java.models.Views;
import main.java.util.DbActions;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PersonFees extends DbActions implements Initializable {
    @FXML
    Text id,name,mobile,gender;
    @FXML
    ImageView photo;
    @FXML
    FlowPane flowLayout;
    private static PersonFeesModel personFee;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id.setText(new DecimalFormat("0000000000").format(Integer.parseInt(Constants.ID)));
        name.setText(Constants.NAME);
        mobile.setText(Constants.MOBILE);
        gender.setText(Constants.GENDER);

        Platform.runLater(this::viewFeesData);

    }


    public static PersonFeesModel getPerson(){
       if (personFee != null){
           return personFee;
       }
       return null;
    }


    private void viewFeesData(){
        List<String> personId = new ArrayList<>();
        personId.add(Constants.ID);
        ObservableList<PersonFeesModel> personFeesList = personFeesData(personId);
        if (personFeesList == null || personFeesList.isEmpty()){
            System.out.println("Fees list is Empty");
            return;
        }

        for (PersonFeesModel personFeesModel : personFeesList) {
            personFee = personFeesModel;
            AnchorPane item = (AnchorPane) Views.feesItem.getParent();
            flowLayout.getChildren().addAll(item);
        }

    }



}
