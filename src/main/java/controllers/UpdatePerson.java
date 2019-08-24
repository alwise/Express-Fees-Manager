package main.java.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import main.java.Main;
import main.java.models.Constants;
import main.java.models.Person;
import main.java.util.DbActions;
import main.java.util.DbProps;

import java.net.URL;
import java.util.*;

import static main.java.Main.mStage;


public class UpdatePerson extends DbActions implements Initializable {
    @FXML private TextField name,mobile;
    @FXML private ComboBox<String> level,period;
    @FXML private RadioButton male , female;
    @FXML private ImageView personPhoto;
    @FXML private Text  status;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
          Platform.runLater(() -> {
              level.setItems(getLevels());
              period.setItems(getPeriods());
              if (Constants.IS_UPDATE){
                  setFieldItem();
              }

          });
    }

    private void setFieldItem(){
        name.setText(Constants.NAME);
        mobile.setText(Constants.MOBILE);

        if (Constants.LEVEL != null)
            level.setValue(Constants.LEVEL);

        if (Constants.PERIOD != null)
            period.setValue(Constants.PERIOD);

        if (Constants.GENDER.equalsIgnoreCase(DbProps.male.getProp()))
            male.setSelected(true);
        else female.setSelected(true);

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
        if (actionEvent == null)
            return;

        if (name.getText().isEmpty())
            return;

        if (getGender().isEmpty() || null == getGender())
            return;

        if(null == level.getValue())
            return;

        if (period.getValue() == null)
            return;

        Person person = getPerson().get(0);
        List<Object> personValues = new ArrayList<>();

        personValues.add(person.getName());
        personValues.add(person.getMobile());
        personValues.add(person.getGender());
        personValues.add(person.getPeriod());
        personValues.add(person.getLevel());
        personValues.add(person.getPhoto());
        personValues.add(person.getDate());

        String info = Constants.IS_UPDATE ? "update" : "add ";

        if (Constants.IS_UPDATE)
            personValues.add(person.getId());

        if (!Main.showOptionDialog(mStage,"Confirm to "+info+" person ?")){
            System.out.println("dialog false");
            return;
        }
        Platform.runLater(() -> {
            if (Constants.IS_UPDATE) updatePerson(personValues);
            else addPerson(personValues);
        });
        status.setText(DbProps.success.getProp().concat(name.getText()));
        clearField();
    }


    private void clearField(){
        name.clear();
        mobile.clear();

        if (Constants.EXIT){
            Main.closeNewStage();
            Constants.EXIT = false;
        }
    }


    //TODO get values from fields and add them to list of person
    private List<Person> getPerson(){
        List<Person> person = new ArrayList<>();
        person.add(new Person(
                Constants.ID,
                name.getText(),
                mobile.getText(),
                getGender(),
                period.getValue(),
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
        return  female.isSelected() ? DbProps.female.getProp() : DbProps.male.getProp();
    }

    @FXML
    public void validateMobile(KeyEvent keyEvent) {
        if (keyEvent == null)
            return;

        if (mobile.getText().length() == 10){
            keyEvent.consume();
            return;
        }

        if (!keyEvent.getCharacter().matches("[0-9]")){
            keyEvent.consume();
        }
    }
}
