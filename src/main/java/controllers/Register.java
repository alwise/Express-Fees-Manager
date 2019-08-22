package main.java.controllers;


import javafx.application.Platform;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import main.java.Main;
import main.java.models.ActionButtonTableCell;
import main.java.models.Person;
import main.java.models.Views;
import main.java.util.DbActions;
import main.java.util.DbProps;

import java.net.URL;
import java.util.ResourceBundle;

public class Register extends DbActions implements Initializable {

    @FXML TableColumn<Person, String >
                                    cId,cName,
                                    cMobile,cGender,
                                    cLevel,cDate;
    @FXML TableColumn<Person, Button> cFees,cPay,cEdit,cDelete;
    @FXML
    TableView<Person> personTable;
    @FXML
    ChoiceBox<String> levelSelector;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this::setDataToTable);
        levelSelector.getItems().addAll("Level 1","Level 2");
    }

    @FXML
    private void setDataToTable(){

        ObservableList<Person> personList = getAllPersons();
        cId.setCellValueFactory(param -> param.getValue().idProperty());
        cName.setCellValueFactory(param -> param.getValue().nameProperty());
        cMobile.setCellValueFactory(param -> param.getValue().mobileProperty());
        cGender.setCellValueFactory(param -> param.getValue().genderProperty());
        cLevel.setCellValueFactory(param -> param.getValue().levelProperty());
        cDate.setCellValueFactory(param -> param.getValue().dateProperty());
        setTableButtons(personList);


    }

    private void setTableButtons(ObservableList<Person> personList){
        cFees.setCellFactory(ActionButtonTableCell.<Person>forTableColumn("View", (Person p) -> {

            return p;
        }));

        cPay.setCellFactory(ActionButtonTableCell.<Person>forTableColumn("Pay", (Person p) -> {

            Main.launchNewStage(Views.makePayment.getView(),p.getId().concat(p.getName()),false,true);
            return p;
        }));

        cEdit.setCellFactory(ActionButtonTableCell.<Person>forTableColumn("Edit", (Person p) -> {

            return p;
        }));

        cDelete.setCellFactory(ActionButtonTableCell.<Person>forTableColumn("Delete", (Person p) -> {

            return p;
        }));

        personTable.setItems(personList);
    }
}
