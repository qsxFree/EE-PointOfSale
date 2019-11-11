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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.java.Main;
import main.java.rfid.RFIDReaderInterface;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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
    Timeline clock,clock1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
         clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            if (tfCardID.isFocused()){
                tfCardID.setText(Main.rfid.scan());
                clock.stop();
            }

        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();


        clock1 = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            if (pfPIN.isFocused()){
                pfPIN.setText(Main.rfid.newPasscode());
                clock1.stop();
            }

        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock1.setCycleCount(Animation.INDEFINITE);
        clock1.play();
        */
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnCreateOnAction(ActionEvent event) {

    }

    @FXML
    void pfPINMenuRequest(ContextMenuEvent event) {
       pfPIN.setText(Main.rfid.newPasscode());
    }

}
