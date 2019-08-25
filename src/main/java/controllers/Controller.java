package main.java.controllers;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import main.java.Main;
import main.java.models.Constants;
import main.java.models.Views;
import main.java.util.DataFile;
import main.java.util.DbActions;
import main.java.util.DbProps;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static main.java.Main.*;


public class Controller extends DbActions implements Initializable {

    private FileChooser fc;
    private  DataFile data;
    @FXML
    private HBox bottomParent,loginParent;
    @FXML
    PasswordField password;

    static boolean isLoginAlready = false;

    @FXML private Button loginBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(this::createDbTables);

        Platform.runLater(this::animateUp);

        Platform.runLater(() -> loginBtn.setText(getAllUsers() ?  "LogIn" : "SingUp"));

    }

    @FXML
    public void launchAddView(ActionEvent actionEvent) {
        if (actionEvent == null)
            return;

        if (!isLoginAlready)
            return;

        Constants.IS_UPDATE = false;
       Platform.runLater(() -> launchNewStage(Views.addPerson.getView(),Views.addPerson.getTitle(),false,true));
    }

    @FXML
    public void lunchRegisterView(ActionEvent actionEvent) {
        if (actionEvent == null)
            return;

        if (!isLoginAlready)
            return;

        Platform.runLater(() -> Main.changeCenterView(Views.registerView.getTitle(),Views.registerView.getView()));
    }

    @FXML
    public void sortedFeesView(ActionEvent event) {
        if (event == null)
            return;

        if (!isLoginAlready)
            return;

       Platform.runLater(() -> Main.changeCenterView(Views.sortFees.getTitle(),Views.sortFees.getView()));
    }

    @FXML
    public void startSettingsPage(ActionEvent event) {
        if (event == null)
            return;

        if (!isLoginAlready)
            return;

       Platform.runLater(() -> Main.changeCenterView(Views.config.getTitle(),Views.config.getView()));
    }

    @FXML
    public void backupData(ActionEvent actionEvent) {
        if (actionEvent == null)
            return;

        if (!isLoginAlready)
            return;

      Platform.runLater(() -> {
          if (backUp()){
              Main.showInfoDialog(homeStage,"Backup was successful.");
          }
      });

    }

    private boolean backUp(){
        fc = new FileChooser();
        fc.setTitle("Save as..");
        FileChooser.ExtensionFilter ex = new FileChooser.ExtensionFilter("Export backup(*.efm)", "*.efm");
        fc.getExtensionFilters().add(ex);
        fc.setInitialFileName("Express_fees_backup_".concat(String.valueOf(LocalDateTime.now()).replaceAll(":.","_")));

        File source = new File(System.getProperty("user.dir").concat("/dox.dll"));

        File destination = fc.showSaveDialog(Main.homeStage.getScene().getWindow());
        data = new DataFile();
        return data.backUpDataBase(source,destination);
    }

    @FXML
    public void restoreData(ActionEvent actionEvent) {
        if (actionEvent == null)
            return;

        if (!isLoginAlready)
            return;

        File source;
        File destination;
        fc = new FileChooser();
        fc.setTitle("Choose efm file..");
        FileChooser.ExtensionFilter ex = new FileChooser.ExtensionFilter("Restore backup(*.efm)", "*.efm");
        fc.getExtensionFilters().add(ex);
        source = fc.showOpenDialog(homeStage.getScene().getWindow());
        destination = new File(System.getProperty("user.dir").concat("/dox.dll"));
        data = new DataFile();
        data.restoreData(source,destination);
    }

    @FXML
    public void resetSystemToDefault(ActionEvent actionEvent) {
        if (actionEvent == null)
            return;
        if (!isLoginAlready)
            return;

        if (!Main.showOptionDialog(homeStage,"You must perform backup before clearing all your data. Do you wish to continue?"))
            return;


        if (backUp()){
            Main.showInfoDialog(homeStage,"Backup was successful. This requires app to close.");
            File file = new File(System.getProperty("user.dir").concat("/dox.dll"));
            if (file.exists())
                file.deleteOnExit();
            Platform.exit();
        }




    }


    private void animateUp(){
        loginParent.setVisible(true);
        //Duration = 0.8 seconds
        Duration duration = Duration.millis(800);
        //Create new translate transition
        TranslateTransition transition = new TranslateTransition(duration, loginParent);
        //Move in X axis by 80
        transition.setByX(80);
        //Move in Y axis by -57
        transition.setByY(-57);
        //Go back to previous position after 2.5 seconds
        //transition.setAutoReverse(true);
        //Repeat animation twice
        //transition.setCycleCount(2);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                password.setFocusTraversable(true);
                isLoginAlready = false;
            }
        });
        transition.play();
    }

    private void animateDown(){
        loginParent.setVisible(true);
        //Duration = 1 seconds
        Duration duration = Duration.millis(1000);
        //Create new translate transition
        TranslateTransition transition = new TranslateTransition(duration, loginParent);
        //Move in X axis by -80
        transition.setByX(-80);
        //Move in Y axis by -57
        transition.setByY(57);
        //Go back to previous position after 2.5 seconds
        //transition.setAutoReverse(true);
        //Repeat animation twice
        //transition.setCycleCount(2);
        transition.setOnFinished(event -> {
            loginParent.setVisible(false);
            isLoginAlready = true;
        });

        transition.play();

    }


    @FXML
    public void login(ActionEvent actionEvent) {
        if (actionEvent == null)
            return;

        if (password.getText().length() < 4)
            return;

        List<Object> params = new ArrayList<>();
        params.add(password.getText().trim());

        boolean allowUsage = loginBtn.getText().equalsIgnoreCase("login") ? logIn(params) : createPassword(params);

        if (!allowUsage)
            return;

        animateDown();

    }


    @FXML
    public void aboutUs(ActionEvent actionEvent) {
        if(actionEvent == null)
            return;
        Platform.runLater(() -> Main.launchNewStage(Views.about.getView(),Views.about.getTitle(),false,true));
    }
}
