package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import main.java.misc.BackgroundProcesses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class POSCustomerEdit extends POSCustomerAccount{

    /**
     * TODO not yet linked to its parent
     */

    @FXML
    private StackPane rootPane;

    @FXML
    private Label lblCustomerID;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfMiddleInitial;

    @FXML
    private TextField tfLastName;

    @FXML
    private JFXRadioButton rbMale;

    @FXML
    private ToggleGroup sex;

    @FXML
    private JFXRadioButton rbFemale;

    @FXML
    private DatePicker dpBirthdate;

    @FXML
    private TextField tfMobileNumber;

    @FXML
    private TextField tfEmailAddress;

    @FXML
    private TextField tfAddress;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Scanner scan = new Scanner(new FileInputStream(BackgroundProcesses.getFile("etc\\cache-selected-customer.file")));
            lblCustomerID.setText("ID : "+scan.nextLine());
            tfFirstName.setText(scan.nextLine());
            tfMiddleInitial.setText(scan.nextLine());
            tfLastName.setText(scan.nextLine());
            if(scan.nextLine().equals("Male"))
                rbMale.setSelected(true);
            else
                rbFemale.setSelected(false);

            tfAddress.setText(scan.nextLine());
            tfMobileNumber.setText(scan.nextLine());
            tfEmailAddress.setText(scan.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void sexOptionOnAction(ActionEvent event) {

    }

}
