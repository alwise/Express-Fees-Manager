package main.java.models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import java.io.IOException;

public enum  Views{



    //TODO add fxml file name and title

    about("about.fxml", "About us"),

    mainView("main.fxml", "Express Fees Manager"),

    addPerson("add_person.fxml","Update Person"),

    registerView("Register.fxml", "Register"),

    yearPeriod("year_period.fxml", "Year and period"),

    makePayment("make_payment.fxml", "Payment"),

    personFees("view_person_fees.fxml", "Person Fees"),

    feesItem("fees_item.fxml", "fees item"),

    sortFees("sort_fees.fxml", "Sort Fees"),

    config("config_view.fxml", "Preferences"),

    progressIndicator("progress_indicator.fxml", "Please wait..");


    private String view;
    private String title;
    private String BASE_DIR = "/main/resources/views/";

    Views(String view, String title) {
        this.view = view;
        this.title = title;
    }

   public String getView(){

       return BASE_DIR.concat(view);
    }

    public Parent getParent(){
        try {
            return FXMLLoader.load(this.getClass().getResource(BASE_DIR.concat(view)));
        } catch (IOException e) {
            System.out.println("could not load file");
            e.printStackTrace();
            return null;
        }
    }


   public String getTitle(){
       return title;
    }

    public static Image getIcon(){
        String IMAGES_DIR = "/main/resources/images/icon.png";
        return new Image(IMAGES_DIR);
    }

}
