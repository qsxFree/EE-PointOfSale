package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.java.MiscInstances;
import main.java.misc.BackgroundProcesses;
import main.java.misc.SceneManipulator;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class POSDashboard implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblDate;

    @FXML
    private ImageView ivAdmin;

    @FXML
    private ImageView ivGsmSignal;

    @FXML
    private ImageView ivRfidSignal;

    @FXML
    private JFXButton btnCashier;

    @FXML
    private JFXButton btnInventory;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnTransaction;

    @FXML
    private JFXButton btnLogs;

    @FXML
    private JFXButton btnAdmin;

    protected SceneManipulator manipulator = new SceneManipulator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundProcesses.realTimeClock(lblDate);
    }

    @FXML
    void menuButtonsOnAction(ActionEvent event) {
        JFXButton selectedButton = (JFXButton) event.getSource();
        if (selectedButton.equals(this.btnCashier)){
            manipulator.changeStage(rootPane,"POSCashier","Cashier");
        }else if (selectedButton.equals(this.btnCustomer)){
            manipulator.changeStage(rootPane,"POSCustomerAccount","Customer Account");
        }

    }



    private void realTimeClock(){

    }


}
