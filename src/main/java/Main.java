package main.java;

import com.sun.scenario.effect.GaussianBlur;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.java.models.FxmlFile;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static Stage mStage,homeStage;
    public static BorderPane borderPane;
    public static String title;

    @Override
    public void start(Stage primaryStage) throws Exception{
        borderPane = FXMLLoader.load(getClass().getResource(FxmlFile.BASE+FxmlFile.MAIN_VIEW+FxmlFile.EXTENSION));
        homeStage = primaryStage;
        homeStage.setMinWidth(425);
        homeStage.setMinHeight(408);
        startRootStage(borderPane);

        //center view//
        changeCenterView(null,FxmlFile.PAYMENT_VIEW);

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
             Parent centerView = FXMLLoader.load(Main.class.getResource(FxmlFile.BASE+url+FxmlFile.EXTENSION));
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
        homeStage.setTitle(FxmlFile.MAIN_VIEW_TITLE);

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

    public static  void launchNewStage(String fxml,String title,boolean resize){
        Parent root;
        try {
            root = FXMLLoader.load(Main.class.getResource(FxmlFile.BASE+fxml+FxmlFile.EXTENSION));

        } catch (IOException e) {
            System.out.println("System error cannot locate file..");
            return;
        }

        if (root == null){
            System.out.println("root is null buddy");
            return;
        }
        mStage = new Stage();
        mStage.setTitle(title);
        mStage.setScene(new Scene(root));
        mStage.initModality(Modality.WINDOW_MODAL);
        mStage.initOwner(borderPane.getScene().getWindow());
        mStage.setResizable(resize);
        mStage.show();
        borderPane.setEffect(new javafx.scene.effect.GaussianBlur( 3)) ;

        mStage.setOnCloseRequest(event -> borderPane.setEffect(new javafx.scene.effect.GaussianBlur(0)));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
