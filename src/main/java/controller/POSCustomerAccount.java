package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import main.java.misc.SceneManipulator;

import java.net.URL;
import java.util.ResourceBundle;

public class POSCustomerAccount implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXButton btnHome;

    @FXML
    private TextField tfSearch;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnCardInfo;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXTreeTableView<?> ttvCustomer;

    @FXML
    private TreeTableColumn<?, ?> chCustomerID;

    @FXML
    private TreeTableColumn<?, ?> chCustomerName;

    @FXML
    private TreeTableColumn<?, ?> chAddress;

    @FXML
    private TreeTableColumn<?, ?> chSex;

    @FXML
    private TreeTableColumn<?, ?> chMobileNumber;

    @FXML
    private TreeTableColumn<?, ?> chEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }


    protected SceneManipulator sceneManipulator = new SceneManipulator();
    @FXML
    void functionButtonOnAction(ActionEvent event) {
        if (event.getSource().equals(btnNew))
            sceneManipulator.openDialog(rootPane,"POSCustomerAccountForm");
    }


}
