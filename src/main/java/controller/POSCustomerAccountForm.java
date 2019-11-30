package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.java.MiscInstances;
import main.java.controller.message.POSMessage;
import main.java.misc.SceneManipulator;

import java.net.URL;
import java.util.ResourceBundle;

public class POSCustomerAccountForm extends POSCustomerAccount{

    @FXML
    private StackPane rootPane;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfMiddleInitial;

    @FXML
    private TextField tfLastName;

    @FXML
    private JFXRadioButton rbMale;

    @FXML
    private JFXRadioButton rbFemale;

    @FXML
    private ToggleGroup sex;

    @FXML
    private TextField tfMobileNumber;

    @FXML
    private TextField tfEmailAddress;

    @FXML
    private TextField tfAddress;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnCreate;

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();
    }

    @FXML
    void btnCreateOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();
        sceneManipulator.openDialog((StackPane) sceneManipulator.getDialogController(),"POSCardInformation");

        /*
        String firstName = tfFirstName.getText();
        String middleInitial = tfMiddleInitial.getText().equals("") ? null : tfMiddleInitial.getText();
        String lastName = tfLastName.getText();
        String mobile = tfMobileNumber.getText();
        String address = tfAddress.getText();
        String email = tfEmailAddress.getText();
        String sex = null;

        if (this.sex.getSelectedToggle().equals(rbMale))
            sex = "Male";
        else if (this.sex.getSelectedToggle().equals(rbFemale))
            sex = "Female";

        String sql = "Insert into customer(firstName,middleInitial,lastName,sex,address,phonenumber,emailAddress)" +
                " values('"+firstName+"','"+middleInitial+"','"+lastName+"','"+sex+"','"+address+"','"+mobile+"','"+email+"')";

        MiscInstances misc = new MiscInstances();
        misc.dbHandler.startConnection();
        misc.dbHandler.execUpdate(sql);
        */

        if (hasEmptyField())
            POSMessage.showMessage(rootPane,"Please fill out all the required fields","Invalid Value", POSMessage.MessageType.ERROR);
        else if(!emailIsValid()){
            POSMessage.showMessage(rootPane,"Please enter a valid email","Invalid Email", POSMessage.MessageType.ERROR);
        }else if(!mobileNumberIsValid()){
            POSMessage.showMessage(rootPane,"Please enter a valid mobile number","Invalid Mobile Number", POSMessage.MessageType.ERROR);
        }

    }

    @FXML
    void sexOptionOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private boolean hasEmptyField(){
        return tfAddress.getText().equals("") ||
                tfEmailAddress.getText().equals("") ||
                tfFirstName.getText().equals("") ||
                tfLastName.getText().equals("") ||
                tfMobileNumber.getText().equals("") ||
                (rbFemale.isSelected()==false && rbMale.isSelected()==false);
    }

    private boolean emailIsValid(){
        return tfEmailAddress.getText().contains("@") &&
                tfEmailAddress.getText().contains(".");
    }

    private boolean mobileNumberIsValid(){
        return (tfMobileNumber.getText().startsWith("09") ||
                tfMobileNumber.getText().startsWith("9")) &&
                        !tfMobileNumber.getText().contains(".");
    }
}
