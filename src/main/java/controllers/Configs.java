package main.java.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import main.java.Main;
import main.java.models.ActionButtonTableCell;
import main.java.models.Constants;
import main.java.models.SortedFessModel;
import main.java.util.DbActions;
import main.java.util.DbProps;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static main.java.Main.homeStage;
import static main.java.Main.showOptionDialog;

public class Configs extends DbActions implements Initializable {
    @FXML
    private TextField name,amount;
    @FXML
    ListView<String> periodList,levelList;
    @FXML
    ChoiceBox<String> year,period,level;
    @FXML
    TableColumn<SortedFessModel,String> cPY,cLevel,cAmount,cDate;
    @FXML
    TableColumn<SortedFessModel,Button>cDelete;
    @FXML
    TableView<SortedFessModel> tableView;
    @FXML private TabPane tab;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Platform.runLater(this::refreshPeriodList);

        Platform.runLater(this::refreshLevelList);


    }

    @FXML
    public void submitPeriod(ActionEvent event) {

            if (event == null)
                return;

            if (name.getText().isEmpty())
                return;

            List<Object> params = new ArrayList<>();
            params.add(name.getText());
            params.add(LocalDateTime.now());

            Platform.runLater(() -> addPeriodLevel(true,params));
            Platform.runLater(this::refreshPeriodList);
    }

    @FXML
    public void submitLevel(ActionEvent event) {
            if (event == null)
                return;

            if (name.getText().isEmpty())
                return;

            List<Object> params = new ArrayList<>();
            params.add(name.getText());
            params.add(LocalDateTime.now());

            Platform.runLater(() -> addPeriodLevel(false,params));
            Platform.runLater(this::refreshLevelList);
    }

    private void refreshPeriodList(){
        periodList.setItems(getPeriods());
    }

    private void refreshLevelList(){
        levelList.setItems(getLevels());
    }

    @FXML
    public void deletePeriod(ActionEvent event) {
        if (event == null)
            return;
        if (null == periodList.getSelectionModel().getSelectedItem())
            return;

        List<String> params = new ArrayList<>();
        params.add(periodList.getSelectionModel().getSelectedItem());

        Platform.runLater(() -> {
            deletePeriodLevel(true,params);
            refreshPeriodList();
        });
    }

    @FXML
    public void deleteLevel(ActionEvent event) {
        if (event == null)
            return;
        if (null == levelList.getSelectionModel().getSelectedItem())
            return;

        List<String> params = new ArrayList<>();
        params.add(levelList.getSelectionModel().getSelectedItem());

        Platform.runLater(() -> {
            deletePeriodLevel(false,params);
            refreshLevelList();
        });
    }

    @FXML
    private void submitFees(ActionEvent actionEvent){
        if (actionEvent == null)
            return;

        if (null == year.getValue())
            return;

        if (null == period.getValue())
            return;

        if (null == level.getValue())
            return;

        if (!showOptionDialog(homeStage, "Confirm fees charged?"))
            return;

        List<Object> params = new ArrayList<>();
        params.add(period.getValue().concat(" ").concat(year.getValue()));
        params.add(level.getValue());
        params.add(amount.getText());
        params.add(LocalDateTime.now());
        Platform.runLater(() -> addFeesCharged(params));

    }

    @FXML private void viewTableData(ActionEvent actionEvent){
        if (actionEvent == null)
            return;
        Platform.runLater(this::setTableData);
    }

    //TODO  set fees charged data to table
    private void setTableData(){
        ObservableList<SortedFessModel> chargeList = getAllFeesCharged();
        if (chargeList == null || chargeList.isEmpty())
            return;

        tableView.getItems().clear();

        cPY.setCellValueFactory(data -> data.getValue().yearPeriodProperty());
        cLevel.setCellValueFactory(data -> data.getValue().levelProperty());
        cAmount.setCellValueFactory(data -> data.getValue().amountProperty());
        cDate.setCellValueFactory(data -> data.getValue().dateProperty());

        cDelete.setCellFactory(ActionButtonTableCell.forTableColumn("Delete", (SortedFessModel p) -> {
            if (p.getYearPeriod() != null){
                if (showOptionDialog(homeStage,"This action is irreversible!\n Delete ".concat(p.getYearPeriod()).concat(" fees?"))){
                    List<Object> params = new ArrayList<>();
                    params.add(p.getYearPeriod());
                    params.add(p.getLevel());
                    Platform.runLater(() -> deleteFeesCharged(params));
                    tableView.getItems().removeAll(p);
                }
            }

            return p;
        }));

        tableView.setItems(chargeList);

    }


    @FXML
    public void validateAmount(KeyEvent keyEvent) {
        if (amount.getText().trim().contains(".")
                && keyEvent.getCharacter().equalsIgnoreCase(".")) {
            keyEvent.consume();
            return;
        }

        if (!keyEvent.getCharacter().matches("[0-9.]")){
            keyEvent.consume();
        }
    }

    @FXML
    public void setComboData(Event event) {
        if (event == null)
            return;

        if (tab.getSelectionModel().isSelected(1)){
                year.setItems(Constants.yearList());
                year.setValue(String.valueOf(LocalDateTime.now().getYear()));
                period.setItems(getPeriods());
                level.setItems(getLevels());

        }
    }
}
