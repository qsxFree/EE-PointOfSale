package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.java.Main;
import main.java.MiscInstances;
import main.java.controller.message.POSMessage;
import main.java.misc.InputRestrictor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class POSCardInformation extends POSCustomerAccount implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private TextField tfCardID;

    @FXML
    private TextField tfInitialBalance;

    @FXML
    private PasswordField pfPIN;

    @FXML
    private PasswordField pfConfirm;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnDone;

    private MiscInstances misc = new MiscInstances();
    private Timeline cardIdScannerThread;
    private Timeline pinThread = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputRestrictor.numbersInput(tfInitialBalance);
       try {
           Main.rfid.newScan();
           cardIdScannerThread = new Timeline(new KeyFrame(Duration.ZERO, e -> {
               try {
                   Scanner scan = new Scanner(new FileInputStream("etc\\rfid-cache.file"));
                   if (scan.hasNextLine()){
                       tfCardID.setText(scan.nextLine());
                       Main.rfid.clearCache();
                       scanForPIN();
                       cardIdScannerThread.stop();
                   }
               } catch (FileNotFoundException ex) {
                   ex.printStackTrace();
               }

           }),
                   new KeyFrame(Duration.seconds(1))
           );
           cardIdScannerThread.setCycleCount(Animation.INDEFINITE);
           cardIdScannerThread.play();
       }catch (NullPointerException e){
           JFXButton button = new JFXButton("Ok");
           button.setOnAction(s->{
               POSMessage.closeMessage();
           });
           POSMessage.showConfirmationMessage(rootPane,"Please connect the RFID Scanner to complete Task",
                   "RFID Scanner not detected",
                   POSMessage.MessageType.ERROR,button);
       }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();
    }

    @FXML
    void btnCreateOnAction(ActionEvent event) throws FileNotFoundException, SQLException {
        if (hasEmptyField()){
            POSMessage.showMessage(rootPane,"Please fill all the required fields","Invalid Value", POSMessage.MessageType.ERROR);
        }else{
            JFXButton btnNo = new JFXButton("No");// Confirmation button - "No"
            btnNo.setOnAction(e -> POSMessage.closeMessage());// After pressing the No button, it simply close the message

            JFXButton btnYes = new JFXButton("Yes");// Confirmation button - "Yes"
            btnYes.setOnAction(e -> {
                try {
                    doInsertion();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            // Confirmation Message
            POSMessage.showConfirmationMessage(rootPane, "Do you really want to delete selected\nitem?"
                    , "No Selected Item", POSMessage.MessageType.ERROR, btnNo, btnYes);

        }
    }



    private void scanForPIN(){
        Main.rfid.newPasscode();
         pinThread = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                Scanner scan = new Scanner(new FileInputStream("etc\\rfid-cache.file"));
                if (scan.hasNextLine()){
                    pfPIN.setText(scan.nextLine());
                    Main.rfid.clearCache();
                    pinThread.stop();

                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        }),
                new KeyFrame(Duration.seconds(1))
        );
        pinThread.setCycleCount(Animation.INDEFINITE);
        pinThread.play();
    }

    private boolean hasEmptyField(){
        return tfCardID.getText().equals("") || pfPIN.getText().equals("") ||
                tfInitialBalance.getText().equals("");
    }

    private final void doInsertion() throws FileNotFoundException, SQLException {
        Scanner scan = new Scanner(new FileInputStream("etc\\cache-new-account.file"));
        String firstName,middleInitial,lastName,address,email,mobile,sex;
        firstName = scan.nextLine();
        middleInitial = scan.nextLine();
        lastName = scan.nextLine();
        address = scan.nextLine();
        email = scan.nextLine();
        mobile = scan.nextLine();
        sex = scan.nextLine();
        String sql = "Insert into customer(firstname,middleInitial,lastName,address,emailAddress,phoneNumber,sex) values ("
                + "'"+firstName+"',"
                + "'"+middleInitial+"',"
                + "'"+lastName+"',"
                + "'"+address+"',"
                + "'"+email+"',"
                + "'"+mobile+"',"
                + "'"+sex+"')";

        misc.dbHandler.startConnection();
        misc.dbHandler.execUpdate(sql);
        misc.dbHandler.closeConnection();


        sql = "Select max(customerID) as cusID from customer";

        misc.dbHandler.startConnection();
        ResultSet result = misc.dbHandler.execQuery(sql);

        int id = 0;
        if (result.next()){
            id = result.getInt("cusID");
        }
        misc.dbHandler.closeConnection();

        Date d = new Date();
        SimpleDateFormat date = new SimpleDateFormat("MM-dd-YYYY");
        String cardID,PIN,activation,expiry;
        cardID = tfCardID.getText();
        PIN = pfPIN.getText();
        activation  = date.format(d);
        date = new SimpleDateFormat("MM-dd-");
        expiry = date.format(d)+((d.getYear()+1900)+1);

        sql = "Insert into card(cardID,PIN,activationDate,expiryDate,credits,customerID) values("
                + "'" +cardID+ "',"
                + "'" +PIN + "',"
                + "'" +activation+ "',"
                + "'" +expiry+ "',"
                + Double.parseDouble(tfInitialBalance.getText())+","
                + id+ ")";

        misc.dbHandler.startConnection();
        misc.dbHandler.execUpdate(sql);
        misc.dbHandler.closeConnection();

        POSMessage.showMessage(rootPane,"New account has been added","Registration Successful", POSMessage.MessageType.INFORM);
    }

}
