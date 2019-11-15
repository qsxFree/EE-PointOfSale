package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import main.java.controller.message.POSMessage;
import main.java.data.entity.Item;
import main.java.misc.BackgroundProcesses;
import main.java.misc.InputRestrictor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class POSItemEdit extends POSInventory{

    @FXML
    private StackPane rootPane;

    @FXML
    private Label lblItemID;

    @FXML
    private TextField tfItemCode;

    @FXML
    private TextField tfItemName;


    @FXML
    private TextField tfPrice;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnSave;


    private String oldCode = "";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Scanner scan = new Scanner(new FileInputStream(BackgroundProcesses.getFile("etc\\cache-selected-item.file")));
            lblItemID.setText("Item ID : "+scan.nextLine());
            oldCode = scan.nextLine();
            tfItemCode.setText(oldCode);
            tfItemName.setText(scan.nextLine());
            double price = Double.parseDouble(scan.nextLine());
            tfPrice.setText(price+"");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputRestrictor.numbersInput(tfItemCode);
        InputRestrictor.limitInput(tfItemCode,13);

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean codeExist = !this.tfItemCode.getText().equals(oldCode) ? codeExist(this.tfItemCode.getText()) : false;
        if (hasEmptyField()){

            //An Error Message if the some of the textboxes are Empty
            POSMessage.showMessage(rootPane
                    ,"Please fill all the Fields"
                    ,"Invalid Value"
                    , POSMessage.MessageType.ERROR);

        }else if (codeExist){

            //An Error Message of the Item code that is entered is already exist
            POSMessage.showMessage(rootPane
                    , "Item Code Already Exist"
                    , "Item Code Error"
                    , POSMessage.MessageType.ERROR);

        }else if (tfItemCode.getText().length()<12){

            //An Error Message if the Item code entered is less than the digit of standard bar code
            POSMessage.showMessage(rootPane
                    , "You've entered an Invalid Code"
                    , "Invalid Code"
                    , POSMessage.MessageType.ERROR);

        }else{
            JFXButton btnNo = new JFXButton("No");
            btnNo.setOnAction(e->POSMessage.closeMessage());

            JFXButton btnYes = new JFXButton("Yes");
            btnYes.setOnAction(e->{
                POSMessage.closeMessage();
                //This is where the update will process
                String sql = "Update Item set " +
                        "itemName = '"+tfItemName.getText()+"', " +
                        "itemCode = '"+tfItemCode.getText()+"'," +
                        "itemPrice = "+tfPrice.getText()+"" +
                        " where itemID = "+lblItemID.getText().split(" : ")[1];//to Get the ID from the ItemID label

                misc.dbHandler.startConnection();
                misc.dbHandler.execUpdate(sql);
                misc.dbHandler.closeConnection();

                JFXButton btnOk = new JFXButton("Ok");
                btnOk.setOnAction(ev->{
                    POSMessage.closeMessage();
                    queryAllItems();
                    sceneManipulator.closeDialog();

                });

                POSMessage.showConfirmationMessage(rootPane,
                        "Item "+lblItemID.getText().split(" : ")[1]+" is now updated",
                        "Update Success",
                        POSMessage.MessageType.INFORM,btnOk);

            });

            POSMessage.showConfirmationMessage(rootPane,"Do you really want to update the item?"
                    ,"Please Confirm Update", POSMessage.MessageType.CONFIRM,btnNo,btnYes);




        }


    }

    private boolean hasEmptyField(){
        return tfItemCode.getText().equals("") ||
                tfItemName.getText().equals("") ||
                tfPrice.getText().equals("");
    }

    private boolean codeExist(String itemCode){
        boolean itemExist = false;
        for (Item item:itemList) {
            if (item.getItemCode().equals(itemCode)){
                itemExist = true;
                break;
            }
        }
        return itemExist;
    }

}
