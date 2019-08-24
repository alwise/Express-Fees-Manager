package main.java.controllers;


import javafx.application.Platform;
import javafx.collections.ObservableList;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.input.KeyEvent;
import main.java.Main;
import main.java.models.ActionButtonTableCell;
import main.java.models.Constants;
import main.java.models.Person;
import main.java.models.Views;
import main.java.util.DbActions;
import main.java.util.DbProps;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static main.java.Main.*;

public class Register extends DbActions implements Initializable {


    @FXML TableColumn<Person, String >
                                    cId,cName,
                                    cMobile,cGender,
                                    cLevel,cDate;
    @FXML TableColumn<Person, Button> cFees,cPay,cEdit,cDelete;
    @FXML
    TableView<Person> personTable;

    @FXML
    TextField searchView;

    private FilteredList<Person> filteredList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this::setDataToTable);
    }

    @FXML
    private void setDataToTable(){

        ObservableList<Person> personList = getAllPersons();

        if (personList == null || personList.isEmpty())
            return;
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
            updateSelectedPersonConstants(p);
            if (p.getId() != null) {
                Main.launchNewStage(Views.personFees.getView(), p.getId().concat("-".concat(p.getName())), true, true);
            }
            return p;
        }));

        cPay.setCellFactory(ActionButtonTableCell.<Person>forTableColumn("Pay", (Person p) -> {
            updateSelectedPersonConstants(p);
            if(p.getId() != null){
                Main.launchNewStage(Views.makePayment.getView(),p.getId().concat(p.getName()),false,true);
            }
            return p;
        }));

        cEdit.setCellFactory(ActionButtonTableCell.<Person>forTableColumn("Edit", (Person p) -> {
            updateSelectedPersonConstants(p);
            if (p.getId() != null){
                launchNewStage(Views.addPerson.getView(),Views.addPerson.getTitle(),false,true);

            }
            return p;
        }));

        cDelete.setCellFactory(ActionButtonTableCell.<Person>forTableColumn("Delete", (Person p) -> {
            if (p.getId() != null){
                if (showOptionDialog(homeStage,"This action is irreversible!\n Delete ".concat(p.getName().toUpperCase()).concat("?"))){

                    List<Object> params = new ArrayList<>();

                    params.add(p.getId());

                    Platform.runLater(() -> deletePerson(params));

                    personTable.getItems().removeAll(p);

                }
            }

            return p;
        }));



        personTable.setItems(personList);
        filteredList = new FilteredList<>(personList, e -> true);
    }


    private void updateSelectedPersonConstants(Person p){
        Constants.ID = p.getId();
        Constants.NAME = p.getName();
        Constants.MOBILE = p.getMobile();
        Constants.PERIOD = p.getPeriod();
        Constants.LEVEL = p.getLevel();
        Constants.GENDER = p.getGender();
        Constants.IS_UPDATE = true;
        Constants.EXIT = true;
    }




    @FXML
    private void searchPerson(KeyEvent event){
        if (event == null)
            return;

        if (filteredList == null || filteredList.isEmpty())
            return;

        searchView.textProperty().addListener((ObservableList, oldValue, newValue) -> {
            filteredList.setPredicate((Predicate<? super Person>) person -> {
                if ((newValue == null) || newValue.isEmpty()) {
                    return true;
                }
                if (person.getId().contains(newValue)) {
                    return true;
                } else return person.getName().toLowerCase().contains(newValue.toLowerCase());

            });

        });

        SortedList<Person> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(personTable.comparatorProperty());
        personTable.setItems(sortedList);

    }




}
