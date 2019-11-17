package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.java.Main;
import main.java.controller.message.POSMessage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class POSCardInformation implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private TextField tfCardID;

    @FXML
    private TextField tfInitialBalance;

    @FXML
    private PasswordField pfPIN;

    @FXML
    private PasswordField pfConfirm;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnDone;
    private Timeline cardIdScannerThread;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       try {
           Main.rfid.scan();
           cardIdScannerThread = new Timeline(new KeyFrame(Duration.ZERO, e -> {
               try {
                   Scanner scan = new Scanner(new FileInputStream("etc\\rfid-cache.file"));
                   if (scan.hasNextLine()){
                       tfCardID.setText(scan.nextLine());
                       cardIdScannerThread.stop();
                   }
               } catch (FileNotFoundException ex) {
                   ex.printStackTrace();
               }

           }),
                   new KeyFrame(Duration.seconds(1))
           );
           cardIdScannerThread.setCycleCount(Animation.INDEFINITE);
           cardIdScannerThread.play();
       }catch (NullPointerException e){
           POSMessage.showMessage(rootPane,"Please connect the RFID Scanner to complete Task",
                   "RFID Scanner not detected",
                   POSMessage.MessageType.ERROR);
           e.printStackTrace();
       }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnCreateOnAction(ActionEvent event) {

    }


}
