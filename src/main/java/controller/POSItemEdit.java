package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class POSItemEdit {

    @FXML
    private StackPane rootPane;

    @FXML
    private Label lblItemID;

    @FXML
    private TextField tfItemCode;

    @FXML
    private TextField tfItemName;

    @FXML
    private TextField tfUnitType;

    @FXML
    private TextField tfPrice;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnSave;

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

}
