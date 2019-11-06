package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class POSPriceInquiry extends POSCashier implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXTreeTableView<?> ttvProductResult;

    @FXML
    private TreeTableColumn<?, ?> chProductCode;

    @FXML
    private TreeTableColumn<?, ?> chProductName;

    @FXML
    private JFXButton btnClose;

    @FXML
    private TextField tfSearch;

    @FXML
    private JFXButton btnSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

}
