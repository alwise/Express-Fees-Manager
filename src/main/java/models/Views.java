package main.java.models;

import javafx.scene.image.Image;

public enum  Views{


    //TODO add fxml file name and title

    mainView("main.fxml", "Express Fees Manager"),

    addPerson("add_person.fxml", "Add New Person"),

    registerView("Register.fxml", "Make Payment"),

    yearPeriod("year_period.fxml", "Year and period"),

    makePayment("make_payment.fxml", "Make payment"),

    progressIndicator("progress_indicator.fxml", "Please wait..");


    private String view;
    private String title;

    Views(String view, String title) {
        this.view = view;
        this.title = title;
    }

   public String getView(){
       String BASE_DIR = "/main/resources/views/";
       return BASE_DIR.concat(view);
    }


   public String getTitle(){
       return title;
    }

    public static Image getIcon(){
        String IMAGES_DIR = "/main/resources/images/icon.png";
        return new Image(IMAGES_DIR);
    }

}
