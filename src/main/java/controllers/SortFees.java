package main.java.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import main.java.Main;
import main.java.models.Constants;
import main.java.models.ExportToExcel;
import main.java.models.SortedFessModel;
import main.java.util.DbActions;



import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class SortFees extends DbActions implements Initializable {
    @FXML
    private ChoiceBox<String> sorterLevel,year,periods;
    @FXML
    private TableColumn<SortedFessModel,String> cId,cYearPeriod,cLevel,cAmount,cDate;
    @FXML
    private TableView<SortedFessModel> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            sorterLevel.setItems( getLevels());
            periods.setItems(getPeriods());
            year.setItems(Constants.yearList());
            year.setValue(String.valueOf(LocalDate.now().getYear()));
        });

        sorterLevel.setOnAction(this::refreshTable);
        periods.setOnAction(this::refreshTable);
        year.setOnAction(this::refreshTable);
    }


    private void setDataToTable(){

        if (periods.getValue() == null){
            //Main.showInfoDialog(Main.homeStage,"Select period and level to view data.");
            return;
        }

        if (null == year.getValue()){
            return;
        }

        List<Object> params = new ArrayList<>();
        params.add(periods.getValue().concat(" ").concat(year.getValue()));
        params.add(getLevel());
        ObservableList<SortedFessModel> paymentList = getPaidForLevel(params);
        if (paymentList == null || paymentList.isEmpty()){
            tableView.getItems().clear();
            return;
        }


        cId.setCellValueFactory(data -> data.getValue().idProperty());
        cYearPeriod.setCellValueFactory(data -> data.getValue().yearPeriodProperty());
        cLevel.setCellValueFactory(data -> data.getValue().levelProperty());
        cAmount.setCellValueFactory(data -> data.getValue().amountProperty().concat(" / ".concat(getAmountCharged())));
        cDate.setCellValueFactory(data -> data.getValue().dateProperty());

        tableView.setItems(paymentList);
    }

    private String getLevel(){
        try {
            if (null == sorterLevel.getValue())
                return null;
            return sorterLevel.getValue();

        }catch (Exception e){
            System.out.println("level selection error "+e.getLocalizedMessage());
            return null;
        }

    }

    private String getAmountCharged(){
        List<Object> params = new ArrayList<>();
        params.add(periods.getValue().concat(" ").concat(year.getValue()));
        params.add(getLevel());
        String amount = String.valueOf(getSpecificFeesCharged(params));

        return amount.isEmpty() ? "n/a" : amount;
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        if (event == null)
            return;

        Platform.runLater(this::setDataToTable);
    }

    @FXML
    public void exportExcel(ActionEvent actionEvent) {
        if (actionEvent == null)
            return;

        if (tableView == null || tableView.getItems().size() < 1)
            return;

        Platform.runLater(() -> ExportToExcel.exportTableToExcel(tableView,sorterLevel.getValue().concat("_".concat(periods.getValue().concat("_".concat(year.getValue()))))));
    }
}
