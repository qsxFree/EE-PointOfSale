package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import main.java.misc.BackgroundProcesses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Scanner scan = new Scanner(new FileInputStream(BackgroundProcesses.getFile("etc\\cache-user.file")));
            scan.nextLine();
            tfItemCode.setText(scan.nextLine());
            tfItemName.setText(scan.nextLine());
            scan.nextLine();
            tfCurrentStock.setText(scan.nextLine());
            lblEstimatedValue.setText(scan.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();
    }

    @FXML
    void btnCreateOnAction(ActionEvent event) {

    }

}
