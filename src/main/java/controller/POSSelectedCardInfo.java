package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class POSSelectedCardInfo {

    /**
     * TODO not yet linked to its parent
     */

    @FXML
    private StackPane rootPane;

    @FXML
    private TextField tfCardID;

    @FXML
    private TextField tfBalance;

    @FXML
    private TextField tfStatus;

    @FXML
    private TextField tfActivationDate;

    @FXML
    private TextField tfExpirationDate;

    @FXML
    private JFXButton btnChangePin;

    @FXML
    private JFXButton btnClose;

    @FXML
    void btnChangePinOnClose(ActionEvent event) {

    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {

    }

}
