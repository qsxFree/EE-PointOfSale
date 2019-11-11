package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;

public class POSInventory {

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXButton btnHome;

    @FXML
    private TextField tfSearch;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnRestock;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXTreeTableView<?> ttvCustomer;

    @FXML
    private TreeTableColumn<?, ?> chItemID;

    @FXML
    private TreeTableColumn<?, ?> chItemCode;

    @FXML
    private TreeTableColumn<?, ?> chItemName;

    @FXML
    private TreeTableColumn<?, ?> chUnit;

    @FXML
    private TreeTableColumn<?, ?> chUnitPrice;

    @FXML
    private TreeTableColumn<?, ?> chStock;


    @FXML
    private TreeTableColumn<?, ?> chTotal;

    @FXML
    void btnHomeOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void functionButtonOnAction(ActionEvent event) {

    }

}
