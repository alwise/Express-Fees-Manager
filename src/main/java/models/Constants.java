package main.java.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Constants {
    final public static String YEAR = String.valueOf(LocalDate.now().getYear());
    public static String PERIOD;
    public static String LEVEL;
    public static String ID;
    public static String NAME;
    public static String MOBILE;
    public static String GENDER;
    public static boolean EXIT = false;
    public static boolean IS_UPDATE = false;


    public static ObservableList<String> yearList(){
        ObservableList<String> yearsList = FXCollections.observableArrayList();

        for (int i = LocalDate.now().getYear(); i > 2017  ; i--){
            yearsList.add(String.valueOf(i));
        }
        return yearsList;
    }

}
