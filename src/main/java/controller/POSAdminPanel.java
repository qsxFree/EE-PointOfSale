package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import main.java.MiscInstances;
import main.java.misc.SceneManipulator;

import java.net.URL;
import java.util.ResourceBundle;

public class POSAdminPanel implements Initializable {

    private static MiscInstances misc = new MiscInstances();
    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane apContainer;

    @FXML
    private JFXButton btnUserSettings;

    @FXML
    private JFXButton btnSystemSettings;

    @FXML
    void navOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
