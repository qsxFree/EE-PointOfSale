package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.message.POSMessage;
import main.java.misc.BackgroundProcesses;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class POSConfirmation extends POSMessage implements Initializable {

    @FXML
    private Label lblTitle;

    @FXML
    private ImageView ivIcon;

    @FXML
    private Label lblMessage;

    @FXML
    void noBtnOnAction(ActionEvent event) {
        POSMessage.sceneManipulator.closeDialog();
        cacheWrite("0");
    }

    @FXML
    void yesBtnOnAction(ActionEvent event) {
        POSMessage.sceneManipulator.closeDialog();
        cacheWrite("1");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Scanner scan = new Scanner(new FileInputStream(BackgroundProcesses.getFile("etc\\cache-message.file")));
            lblTitle.setText(scan.nextLine());
            lblMessage.setText(scan.nextLine());
            ivIcon.setImage(new Image(scan.nextLine()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void cacheWrite(String str){
        BackgroundProcesses.createCacheDir("etc\\cache-confirm-return.file");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(BackgroundProcesses.getFile("etc\\cache-confirm-return.file")));
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
