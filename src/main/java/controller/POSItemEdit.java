package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class POSItemEdit extends POSInventory{

    @FXML
    private StackPane rootPane;

    @FXML
    private Label lblItemID;

    @FXML
    private TextField tfItemCode;

    @FXML
    private TextField tfItemName;


    @FXML
    private TextField tfPrice;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

}