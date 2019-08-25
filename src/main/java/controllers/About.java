package main.java.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class About implements Initializable {

    @FXML private Hyperlink websiteText,emailText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


//    @FXML
//    private void launchAboutUs(ActionEvent event) throws URISyntaxException, IOException {
//        String dir = System.getProperty("user.dir");
//        File file = new File(dir.concat("/files/manual.pdf"));
//        if (!file.exists()){
//            return;
//        }
//        Desktop.getDesktop().open(file);
//
//    }
    @FXML
    private void launchManual(ActionEvent event) {
        //String dir = System.getProperty("user.dir");
        File file = new File(System.getProperty("user.dir").concat("/files/manual.pdf"));
        if (!file.exists())
            return;

      Platform.runLater(() -> {
          try {
              Desktop.getDesktop().open(file);
          } catch (IOException e) {
              e.printStackTrace();
          }
      });

    }

    @FXML
    private void sendEmail(ActionEvent event)  {
        Platform.runLater(() -> {
            try {
                Desktop.getDesktop().mail(new URI("mailto:alwisestudioinc@gmail.com"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void visitWebsite(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                Desktop.getDesktop().browse(new URI("www.alwisestudio.com"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });

    }
}
