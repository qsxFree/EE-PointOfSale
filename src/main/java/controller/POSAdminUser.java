package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class POSAdminUser extends POSAdminPanel implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXTreeTableView<?> ttvCustomer;

    @FXML
    private TreeTableColumn<?, ?> chCustomerID;

    @FXML
    private TreeTableColumn<?, ?> chCustomerName;

    @FXML
    private TreeTableColumn<?, ?> chAction;

    @FXML
    private TextField tfSearch;

    @FXML
    private JFXButton btnNew;

    @FXML
    void btnNewOnACtion(ActionEvent event) {

    }

    @FXML
    void tfSearchOnKeyReleased(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
