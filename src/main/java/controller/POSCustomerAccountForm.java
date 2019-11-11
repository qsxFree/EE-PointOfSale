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

    }

    @FXML
    void sexOptionOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
