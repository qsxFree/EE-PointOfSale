package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import main.java.MiscInstances;
import main.java.data.CacheWriter;
import main.java.misc.BackgroundProcesses;
import main.java.misc.DirectoryHandler;
import main.java.misc.SceneManipulator;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.sql.ResultSet;
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
        BackgroundProcesses.realTimeClock(lblDate);
        BackgroundProcesses.changeSecondaryFormStageStatus((short)2);
        writeToCache("etc\\loader\\load-sl-users.file");
        try {
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
        }


    }

    @FXML
    void menuButtonsOnAction(ActionEvent event) {
        JFXButton selectedButton = (JFXButton) event.getSource();
        if (selectedButton.equals(this.btnCashier)){
            manipulator.changeScene(rootPane,"POSCashier","POS | Cashier");
        }else if (selectedButton.equals(this.btnCustomer)){
            manipulator.changeScene(rootPane,"POSCustomerAccount","POS | Customer Account");
        }else if (selectedButton.equals(this.btnInventory)) {
            manipulator.changeScene(rootPane, "POSInventory", "POS | Inventory and Stock Management");
        } else if (selectedButton.equals(this.btnLogs)) {
            manipulator.changeScene(rootPane, "POSSystemLogs", "POS | System Logs");
        }else if (selectedButton.equals(this.btnTransaction)){
            manipulator.changeScene(rootPane, "POSTransactionLogs", "POS | Transaction Logs");
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
}
