package main.java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.models.Views;
import main.java.util.DbActions;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static Stage mStageProgress,mStage,homeStage;
    public static BorderPane borderPane;
    public static String title;

    @Override
    public void start(Stage primaryStage) throws Exception{
        borderPane = FXMLLoader.load(getClass().getResource(Views.mainView.getView()));
        homeStage = primaryStage;
//        homeStage.setMinWidth(800);
//        homeStage.setMinHeight(500);
        homeStage.getIcons().add(Views.getIcon());
        startRootStage(borderPane);




    }

    /**
     *  change the center view of the borderPane
     * @param newTitle: the title to display on window frame
     * @param url : fxml directory
     */

    public static void changeCenterView(String newTitle, String url){
        title = homeStage.getTitle() ;
        if (Objects.equals(title, newTitle)){
            System.out.println("Already on this page");
            return;
        }
        if (borderPane == null){
            System.out.println("Null BorderPane parent");
            return;
        }
        try {
             Parent centerView = FXMLLoader.load(Main.class.getResource(url));
             borderPane.setCenter(null);
             borderPane.setCenter(centerView);
             homeStage.setTitle(newTitle);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *  @param root : th parent view
     *
     *
     *
     */
    private static void  startRootStage(Parent root){
        homeStage.setTitle(Views.mainView.getTitle());
        homeStage.setScene(new Scene(root));
        homeStage.show();
    }


    /**
     *
     * @param fxml : file to be loaded
     * @param title : title for window
     *
     * @param resize : set whether to allow window resizing
     */

    public static  void launchNewStage(String fxml,String title,boolean resize,boolean decorated){
        Parent root;
        try {
            root = FXMLLoader.load(Main.class.getResource(fxml));

        } catch (IOException e) {
            System.out.println("System error cannot locate file.."+e.getLocalizedMessage());
            return;
        }

        if (root == null){
            System.out.println("root is null buddy");
            return;
        }
        mStage = new Stage();
        mStage.setTitle(title);
        mStage.getIcons().add(Views.getIcon());
        mStage.setScene(new Scene(root));
        mStage.initModality(Modality.WINDOW_MODAL);
        mStage.initOwner(borderPane.getScene().getWindow());
        mStage.initStyle(decorated ? StageStyle.DECORATED : StageStyle.UNDECORATED);
        mStage.setResizable(resize);
        new Main().blurPage(mStage);
        setLocationRelativeToParent(mStage);

    }

    public static void closeNewStage(){
        if (mStage != null){
            mStage.close();
        }
    }

    public static void showProgress(Stage parent){

        Parent root;
        try {
            root = FXMLLoader.load(Main.class.getResource(Views.progressIndicator.getView()));

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (root == null){
            return;
        }

        mStageProgress = new Stage();
        mStageProgress.setTitle(title);
        mStageProgress.getIcons().add(Views.getIcon());
        mStageProgress.setScene(new Scene(root));
        mStageProgress.initModality(Modality.WINDOW_MODAL);
        mStageProgress.initOwner(parent.getScene().getWindow());
        mStageProgress.initStyle(StageStyle.UNDECORATED);
        mStageProgress.setResizable(false);
        setLocationRelativeToParent(mStageProgress);

    }

    //TODO dismiss or close the progress indicator
    public static void progressDismiss(){
        mStageProgress.close();
    }


    public static Boolean showOptionDialog(Stage parent,String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,content, ButtonType.YES,ButtonType.NO);
        alert.initOwner(parent.getScene().getWindow());
        alert.showAndWait();
        return alert.getResult() == (ButtonType.YES);
    }

    public static void showInfoDialog(Stage parent,String content){
       Alert alert =  new Alert(Alert.AlertType.INFORMATION,content);
       alert.initOwner(parent.getScene().getWindow());
       alert.showAndWait();
    }


    private static void setLocationRelativeToParent(Stage stage){
        ChangeListener<Number> widthListener = (observable, oldValue, newValue) -> {
            stage.setX(homeStage.getX()+homeStage.getWidth()/2-newValue.doubleValue()/2);
        };
        ChangeListener<Number> heightListener = (observable, oldValue, newValue) -> {
            stage.setY(homeStage.getY()+homeStage.getHeight()/2-newValue.doubleValue()/2);
        };
        stage.widthProperty().addListener(widthListener);
        stage.heightProperty().addListener(heightListener);

        stage.setOnShown(event -> {
            stage.widthProperty().removeListener(widthListener);
            stage.heightProperty().removeListener(heightListener);
        });

        stage.show();


    }


    private void blurPage(Stage stage){

        stage.showingProperty().addListener((observable, oldValue, newValue) -> {
            if(!stage.getScene().getWindow().isShowing()){
                borderPane.setEffect(new javafx.scene.effect.GaussianBlur(0));
            }else{
                borderPane.setEffect(new javafx.scene.effect.GaussianBlur( 3)) ;
            }
        });
    }





    public static void main(String[] args) {
        launch(args);
    }



}
