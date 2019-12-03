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
import main.java.controller.message.POSMessage;
import main.java.misc.BackgroundProcesses;
import main.java.misc.InputRestrictor;

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
        InputRestrictor.limitInput(tfMobileNumber,11);
        InputRestrictor.numbersInput(tfMobileNumber);
        InputRestrictor.limitInput(tfMiddleInitial,3);
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
        if (hasEmptyField()){
            POSMessage.showMessage(rootPane,"Please fill all the required fields","Invalid Value", POSMessage.MessageType.ERROR);
        }else if (!emailIsValid()){
            POSMessage.showMessage(rootPane,"The email you've entered is invalid","Invalid Value", POSMessage.MessageType.ERROR);
        }else if (!mobileIsValid()){
            POSMessage.showMessage(rootPane,"The mobile number you've entered is invalid","Invalid Value", POSMessage.MessageType.ERROR);
        }else{
            //TODO Updating data falls here
        }

    }

    @FXML
    void sexOptionOnAction(ActionEvent event) {

    }


    private boolean hasEmptyField(){
        return tfFirstName.getText().equals("") ||
                tfLastName.getText().equals("") ||
                tfMobileNumber.getText().equals("") ||
                tfAddress.getText().equals("") ||
                !(rbFemale.isSelected() || rbMale.isSelected());
    }

    private boolean mobileIsValid(){
        return (tfMobileNumber.getText().startsWith("0") && tfMobileNumber.getText().length() == 11) ||
                (tfMobileNumber.getText().startsWith("9") && tfMobileNumber.getText().length() ==10);

    }

    private boolean emailIsValid(){
        return tfEmailAddress.getText().contains("@")
                && tfEmailAddress.getText().contains(".");
    }

}
