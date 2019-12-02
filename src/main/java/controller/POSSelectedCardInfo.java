package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.java.misc.BackgroundProcesses;
import main.java.misc.InputRestrictor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class POSSelectedCardInfo extends POSCustomerAccount {

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
    private VBox vbPINContainer;

    @FXML
    private PasswordField pfOld;

    @FXML
    private PasswordField pfNew;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnClose;

    private String pin,customerID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputRestrictor.numbersInput(pfOld);
        InputRestrictor.numbersInput(pfNew);
        InputRestrictor.limitInput(pfNew,6);
        InputRestrictor.limitInput(pfOld,6);
        try {
            Scanner scan = new Scanner(new FileInputStream(BackgroundProcesses.getFile("etc\\cache-card-info.file")));
            tfCardID.setText(scan.nextLine());
            tfBalance.setText(scan.nextLine());
            tfStatus.setText(scan.nextLine().equals("1")?"Active":"Inactive");
            tfActivationDate.setText(scan.nextLine());
            tfExpirationDate.setText(scan.nextLine());
            pin = scan.nextLine();
            customerID = scan.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnChangePinOnAction(ActionEvent event) {

    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();
    }

    @FXML
    void pinFunctionsOnAction(ActionEvent actionEvent) {
    }

}
