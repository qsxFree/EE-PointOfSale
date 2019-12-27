package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.java.Main;
import main.java.MiscInstances;
import main.java.data.CacheWriter;
import main.java.misc.BackgroundProcesses;
import main.java.misc.DirectoryHandler;
import main.java.misc.SceneManipulator;

import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class POSDashboard implements Initializable , CacheWriter {

    @FXML
    private StackPane rootPane;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblDate;

    @FXML
    private ImageView ivAdmin;

    @FXML
    private ImageView ivGsmSignal;

    @FXML
    private ImageView ivRfidSignal;

    @FXML
    private Label lblActive;

    @FXML
    private Label lblAvailable;

    @FXML
    private Label lblTotalValue;

    @FXML
    private JFXButton btnCashier;

    @FXML
    private JFXButton btnInventory;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnTransaction;

    @FXML
    private JFXButton btnLogs;

    @FXML
    private JFXButton btnAdmin;

    protected SceneManipulator manipulator = new SceneManipulator();
    protected static MiscInstances misc = new MiscInstances();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkGsmSignal();
        //checkRFIDStatus();
        BackgroundProcesses.realTimeClock(lblDate);
        BackgroundProcesses.changeSecondaryFormStageStatus((short)2);
        writeToCache("etc\\loader\\load-sl-users.file");
        try {
            initReport();
            Scanner scan = new Scanner(new FileInputStream("etc\\cache-user.file"));
            scan.nextLine();
            String fullName = scan.nextLine();
            fullName += (" "+scan.nextLine());
            fullName += (" "+scan.nextLine());
            lblUser.setText("Logged in as "+fullName);
            ivAdmin.setImage(scan.nextLine().equals("1")
                    ? new Image(DirectoryHandler.IMG+"pos-admin.png")
                    : new Image(DirectoryHandler.IMG+"pos-admin-disable.png") );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adminToolTip();


    }

    @FXML
    void menuButtonsOnAction(ActionEvent event) {
        JFXButton selectedButton = (JFXButton) event.getSource();
        gsmSignalThread.stop();
        //rfidStatus.stop();
        if (selectedButton.equals(this.btnCashier)){
            manipulator.changeScene(rootPane,"POSCashier"," | Cashier");
        }else if (selectedButton.equals(this.btnCustomer)){
            manipulator.changeScene(rootPane,"POSCustomerAccount"," | Customer Account");
        }else if (selectedButton.equals(this.btnInventory)) {
            manipulator.changeScene(rootPane, "POSInventory", " | Inventory and Stock Management");
        } else if (selectedButton.equals(this.btnLogs)) {
            manipulator.changeScene(rootPane, "POSSystemLogs", " | System Logs");
        }else if (selectedButton.equals(this.btnTransaction)){
            manipulator.changeScene(rootPane, "POSTransactionLogs", " | Transaction Logs");
        }

    }

    @FXML
    void btnSignOutOnAction(ActionEvent event) {
        manipulator.changeScene(rootPane,"POSLogin","POS | Login");
    }

    @Override
    public void writeToCache(String file) {
        String sql = "Select userID,firstName,lastName from user";
        misc.dbHandler.startConnection();
        ResultSet result = misc.dbHandler.execQuery(sql);
        try {
            String data = "---\n";

            while (result.next()) {
                data += result.getString("userID")
                        + ", " + result.getString("firstName")
                        + " " + result.getString("lastName").charAt(0) + "\n";
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initReport() throws SQLException {
        String sql = "Select count(cardId) as active from card where isActive = 1";
        misc.dbHandler.startConnection();
        ResultSet resultSet = misc.dbHandler.execQuery(sql);
        resultSet.next();
        lblActive.setText(lblActive.getText()+" "+resultSet.getInt("active"));
        misc.dbHandler.closeConnection();

        sql = "Select count(itemId) as available from item where stock > 0";
        misc.dbHandler.startConnection();
        resultSet = misc.dbHandler.execQuery(sql);
        resultSet.next();
        lblAvailable.setText(lblAvailable.getText()+" "+resultSet.getInt("available"));
        misc.dbHandler.closeConnection();

        sql = "Select sum(itemPrice*stock) as total from item where deleted = 0";
        misc.dbHandler.startConnection();
        resultSet = misc.dbHandler.execQuery(sql);
        resultSet.next();
        lblTotalValue.setText(lblTotalValue.getText()+" "+resultSet.getInt("total"));
        misc.dbHandler.closeConnection();
    }

    Timeline gsmSignalThread,rfidStatus;
    private void checkGsmSignal(){


        gsmSignalThread = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                Main.rfid.gsmSignal();
                Scanner scan = new Scanner(new FileInputStream("etc/status/rfid-gsm-signal.file"));
                if (scan.hasNextLine()){
                    String value[] = scan.nextLine().split("=");
                    if (value[0].equals("gsmSignal")){
                        int val = Integer.parseInt(value[1]);
                        String url = "";
                        if (val>=1 && val<=10)
                            url = DirectoryHandler.IMG+"pos-connection-low.png";
                        else if (val>=11 && val<=20)
                            url = DirectoryHandler.IMG+"pos-connection-medium.png";
                        else if (val>=21 && val<=30)
                            url = DirectoryHandler.IMG+"pos-connection-high.png";
                        ivGsmSignal.setImage(new Image(url));
                        gsmSignalToolTip();
                        Main.rfid.clearStatusCache();
                    }else{
                        String url = DirectoryHandler.IMG+"pos-connection-dc.png";

                        ivGsmSignal.setImage(new Image(url));
                        gsmSignalToolTip();
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                String url = DirectoryHandler.IMG+"pos-connection-dc.png";

                ivGsmSignal.setImage(new Image(url));
                gsmSignalToolTip();
            }
        }),
                new KeyFrame(Duration.seconds(3))
        );
        gsmSignalThread.setCycleCount(Animation.INDEFINITE);
        gsmSignalThread.play();
    }

    private void checkRFIDStatus(){
        rfidStatus = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                Main.rfid.testConnection();
                Scanner scan = new Scanner(new FileInputStream("etc/status/rfid-device-signal.file"));
                if (scan.hasNextLine()){
                    String value[] = scan.nextLine().split("=");
                    if (value[0].equals("connectionStatus")){
                        int val = Integer.parseInt(value[1]);
                        String url = "";
                        if (val==0)
                            url = DirectoryHandler.IMG+"pos-rfid-signal-dc.png";
                        else if (val==1)
                            url = DirectoryHandler.IMG+"pos-rfid-signal.png";

                        ivRfidSignal.setImage(new Image(url));
                        rfidToolTip();
                        Main.rfid.clearStatusCache();
                    }
                }else{
                    String url = DirectoryHandler.IMG+"pos-rfid-signal-dc.png";

                    ivRfidSignal.setImage(new Image(url));
                    rfidToolTip();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                String url = DirectoryHandler.IMG+"pos-rfid-signal-dc.png";

                ivRfidSignal.setImage(new Image(url));
                rfidToolTip();
            }
        }),
                new KeyFrame(Duration.seconds(5))
        );
        rfidStatus.setCycleCount(Animation.INDEFINITE);
        rfidStatus.play();

    }
    private void adminToolTip(){
        File file = new File(ivAdmin.getImage().getUrl());
        String value = file.getName().equals("pos-admin-disable.png") ? "Non-Administrator" : "Administrator";
        Tooltip.install(ivAdmin,new Tooltip(value));

    }

    private void gsmSignalToolTip(){
        File file = new File(ivGsmSignal.getImage().getUrl());
        String value = file.getName();
        if (value.equals("pos-connection-low.png")){
            value = "GSM Signal : Weak";
        }else if (value.equals("pos-connection-medium.png"))
            value = "GSM Signal : Moderate";
        else if (value.equals("pos-connection-high.png"))
            value = "GSM Signal : Strong";
        else if (value.equals("pos-connection-dc.png"))
            value = "GSM Disconnected / No Signal";
        Tooltip.install(ivGsmSignal,new Tooltip(value));
    }

    private void rfidToolTip(){
        File file = new File(ivRfidSignal.getImage().getUrl());
        String value = file.getName();
        if (value.equals("pos-rfid-signal.png")){
            value="RFID Device | Connected";
        }else if (value.equals("pos-rfid-signal-dc.png")){
            value="RFID Device | Disconnected";
        }
        Tooltip.install(ivRfidSignal,new Tooltip(value));
    }

}
