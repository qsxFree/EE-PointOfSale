package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.java.MiscInstances;
import main.java.controller.message.POSMessage;
import main.java.data.CacheWriter;
import main.java.misc.BackgroundProcesses;
import main.java.misc.DirectoryHandler;
import main.java.misc.InputRestrictor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class POSLogin implements Initializable, CacheWriter {

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

    private static MiscInstances misc;

    private String userID = "";
    private String fname = "";
    private String mi = "";
    private String lname = "";
    private int accountType=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputRestrictor.numbersInput(tfEmpID);
        InputRestrictor.limitInput(tfEmpID,5);
        BackgroundProcesses.createCacheDir("etc\\cache-user.file");
        misc  = new MiscInstances();
    }

    @FXML
    void btnSignInOnAction(ActionEvent event) {
        String sql = "Select * from user where userID = '"+tfEmpID.getText()+"' and password = md5('"+pfPassword.getText()+"')";

        misc.dbHandler.startConnection();
        ResultSet result =  misc.dbHandler.execQuery(sql);
        try {
            if(result.next()){
                userID = result.getString("userID");
                fname = result.getString("firstName");
                mi = result.getString("middleInitial");
                lname = result.getString("lastName");
                accountType = result.getInt("accountType");
                writeToCache("etc\\cache-user.file");

                misc.dbHandler.closeConnection();
                POSMessage.showConfirmationMessage(rootPane
                        ,"Please wait . . ."
                        ,"Access Granted"
                        , POSMessage.MessageType.INFORM
                        );

                Timeline changeSceneThread = new Timeline(new KeyFrame(
                        Duration.seconds(1),
                        e -> {
                            misc.sceneManipulator.changeScene(rootPane,"POSDashboard","Dashboard");
                        })
                );
                changeSceneThread.setDelay(Duration.seconds(2));
                changeSceneThread.setCycleCount(1);
                changeSceneThread.play();

            }
            else{
                JFXButton btnOk = new JFXButton("Ok");
                btnOk.setOnAction(ev->{
                    POSMessage.closeMessage();
                });
                POSMessage.showConfirmationMessage(rootPane
                        ,"You've entered an unauthorized" +
                        "\naccount. Please double-check and\ntry again"
                        ,"Access Denied"
                        , POSMessage.MessageType.ERROR
                        ,btnOk);

                tfEmpID.setText("");
                pfPassword.setText("");
                btnSignIn.setDisable(true);
                misc.dbHandler.closeConnection();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            misc.dbHandler.closeConnection();
        }

    }

    @FXML
    private void fieldsOnKeyReleased(KeyEvent keyEvent) {
        if (tfEmpID.getText().equals("")
                || pfPassword.getText().equals("")
                || tfEmpID.getText().length()<5
                || pfPassword.getText().length()<5)
            btnSignIn.setDisable(true);
        else
            btnSignIn.setDisable(false);
    }

    @Override
    public void writeToCache(String file) {
        String cacheData = "";
        cacheData += userID;
        cacheData += "\n"+fname;
        cacheData += "\n"+mi;
        cacheData += "\n"+lname;
        cacheData += "\n"+accountType;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(BackgroundProcesses.getFile(file)));
            writer.write(cacheData);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
