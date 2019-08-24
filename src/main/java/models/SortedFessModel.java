package main.java.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SortedFessModel {
    private StringProperty id,name,yearPeriod,level,amount,date;



    public SortedFessModel(String id,String name, String yearPeriod, String level, String amount, String date) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.yearPeriod = new SimpleStringProperty(yearPeriod);
        this.level = new SimpleStringProperty(level);
        this.amount = new SimpleStringProperty(amount);
        this.date = new SimpleStringProperty(date);
    }


    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }



    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }




    public String getYearPeriod() {
        return yearPeriod.get();
    }

    public StringProperty yearPeriodProperty() {
        return yearPeriod;
    }

    public void setYearPeriod(String yearPeriod) {
        this.yearPeriod.set(yearPeriod);
    }

    public String getLevel() {
        return level.get();
    }

    public StringProperty levelProperty() {
        return level;
    }

    public void setLevel(String level) {
        this.level.set(level);
    }

    public String getAmount() {
        return amount.get();
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
