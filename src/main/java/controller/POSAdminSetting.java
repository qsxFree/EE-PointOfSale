package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.java.misc.InputRestrictor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class POSAdminSetting extends POSAdminPanel implements Initializable {


    @FXML
    private StackPane rootPane;

    @FXML
    private VBox contBusiness;

    @FXML
    private TextField tfBName;

    @FXML
    private TextField tfBAddress;

    @FXML
    private TextField tfBEmail;

    @FXML
    private TextField tfBPhone;

    @FXML
    private JFXButton btnBCancel;

    @FXML
    private JFXButton btnBEdit;

    @FXML
    private VBox contAdmin;

    @FXML
    private TextField tfAUserId;

    @FXML
    private TextField tfAFirstName;

    @FXML
    private TextField tfAMiddleInitial;

    @FXML
    private TextField tfLastName;

    @FXML
    private JFXButton btnACancel;

    @FXML
    private JFXButton btnAEdit;

    @FXML
    private VBox contOthers;

    @FXML
    private TextField tfBalance;

    @FXML
    private JFXButton btnOCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Scanner scan = new Scanner(new FileInputStream("etc\\initial.file"));
            tfBName.setText(scan.nextLine());
            tfBAddress.setText(scan.nextLine());
            tfBPhone.setText(scan.nextLine());
            tfBEmail.setText(scan.nextLine());
            scan = new Scanner(new FileInputStream("etc\\cache-user.file"));
            tfAUserId.setText(scan.nextLine());
            tfAFirstName.setText(scan.nextLine());
            tfAMiddleInitial.setText(scan.nextLine());
            tfLastName.setText(scan.nextLine());
            scan = new Scanner(new FileInputStream("etc\\cache-others.file"));
            tfBalance.setText(scan.nextLine());
            InputRestrictor.limitInput(tfAMiddleInitial, 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnMainOnAction(ActionEvent event) {

    }


}
