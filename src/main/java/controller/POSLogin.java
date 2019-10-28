package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.java.misc.DirectoryHandler;

import java.io.IOException;

public class POSLogin {

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
