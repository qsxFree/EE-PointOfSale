package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class POSNewItem extends POSInventory{

    @FXML
    private StackPane rootPane;

    @FXML
    private TextField tfItemCode;

    @FXML
    private TextField tfItemName;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfInititalStock;

    @FXML
    private Label lblTotalValue;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnAdd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();
    }

}
