package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.java.misc.DirectoryHandler;
import main.java.misc.InputRestrictor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class POSLogin implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private VBox vbControlCenteredBox;

    @FXML
    private TextField tfEmpID;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private JFXButton btnSignIn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputRestrictor.numbersInput(tfEmpID);
        InputRestrictor.limitInput(tfEmpID,5);
    }

    @FXML
    void btnSignInOnAction(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/"+ DirectoryHandler.FXML+"POSMessage.fxml"));
            POSDialog dialog = new POSDialog(rootPane, (Pane) parent,false);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
