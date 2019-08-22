package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.java.models.Person;
import main.java.util.DbActions;

import java.net.URL;
import java.util.*;


public class UpdatePerson extends DbActions implements Initializable {
    @FXML private TextField name,mobile;
    @FXML private ComboBox<String> level;
    @FXML private RadioButton male , female;
    @FXML private ImageView personPhoto;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        level.getItems().addAll("level 1","Level 2");
    }

    @FXML
    public void disableMale(MouseEvent event) {
        if (event != null){
            if (getGender(female)){
                male.setSelected(false);
            }
        }
    }

    @FXML
    public void disableFemale(MouseEvent evt) {
        if (evt != null){
            if (getGender(male)){
                female.setSelected(false);
            }
        }
    }

    @FXML
    public void uploadPhoto(ActionEvent actionEvent) {
    }

    @FXML
    public void submitForm(ActionEvent actionEvent) {
        if (actionEvent != null){
            Person person = getPerson().get(0);
            List<Object> personValues = new ArrayList<>();
            //personValues.add(person.getId());
            personValues.add(person.getName());
            personValues.add(person.getMobile());
            personValues.add(person.getGender());
            personValues.add(person.getLevel());
            personValues.add(person.getPhoto());
            personValues.add(person.getDate());
            addPerson(personValues);
        }

    }


    //TODO get values from fields and add them to list of person
    private List<Person> getPerson(){
        List<Person> person = new ArrayList<>();
        person.add(new Person(
                null,
                name.getText(),
                mobile.getText(),
                getGender(),
                level.getValue(),
                null,
                String.valueOf(new Date().getTime())
        ));
        return person;
    }


    //TODO: validate selected gender fields;
    private Boolean getGender(RadioButton radioButton){
        return radioButton.isSelected();
    }

    private String getGender(){
        return  female.isSelected() ? "Female" : "Male";
    }

}
