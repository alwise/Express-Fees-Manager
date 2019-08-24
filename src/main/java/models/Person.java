package main.java.models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
    private StringProperty id, name,mobile,gender,period,level, date;

    private SimpleObjectProperty<Byte> photo;


    public Person(String id, String name, String mobile, String gender,String period, String level, Byte photo, String date) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.mobile = new SimpleStringProperty(mobile);
        this.gender = new SimpleStringProperty(gender);
        this.period = new SimpleStringProperty(level);
        this.level = new SimpleStringProperty(level);
        this.photo = new SimpleObjectProperty<>(photo);
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

    public String getMobile() {
        return mobile.get();
    }

    public StringProperty mobileProperty() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile.set(mobile);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getPeriod() {
        return period.get();
    }

    public StringProperty periodProperty() {
        return period;
    }

    public void setPeriod(String period) {
        this.period.set(period);
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

    public Byte getPhoto() {
        return photo.get();
    }

    public SimpleObjectProperty<Byte> photoProperty() {
        return photo;
    }

    public void setPhoto(Byte photo) {
        this.photo.set(photo);
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
