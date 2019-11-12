package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.java.misc.BackgroundProcesses;
import main.java.misc.InputRestrictor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

public class POSRestock extends POSInventory{

    @FXML
    private StackPane rootPane;

    @FXML
    private TextField tfItemCode;

    @FXML
    private TextField tfItemName;

    @FXML
    private TextField tfCurrentStock;

    @FXML
    private TextField tfAddStock;

    @FXML
    private Label lblNewStockValue;

    @FXML
    private Label lblEstimatedValue;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnDone;

    protected double price = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Scanner scan = new Scanner(new FileInputStream(BackgroundProcesses.getFile("etc\\cache-user.file")));
            scan.nextLine();
            tfItemCode.setText(scan.nextLine());
            tfItemName.setText(scan.nextLine());
            price = Double.parseDouble(scan.nextLine());
            tfCurrentStock.setText(scan.nextLine());
            lblEstimatedValue.setText(scan.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputRestrictor.numbersInput(tfAddStock);
        InputRestrictor.limitInput(tfAddStock,3);
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            int oldVal = Integer.parseInt(tfCurrentStock.getText());
            int newVal = tfAddStock.getText().equals("") ? 0 : Integer.parseInt(tfAddStock.getText());
            lblNewStockValue.setText(""+(oldVal+newVal));
            lblEstimatedValue.setText(((oldVal+newVal)*price)+"");
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    //TODO recalculating the Text everytime the user are entering new stock value
    //TODO update database when adding stocks

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();
    }

    @FXML
    void btnCreateOnAction(ActionEvent event) {

    }

}
