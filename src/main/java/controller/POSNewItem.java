package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import main.java.MiscInstances;
import main.java.controller.message.POSMessage;
import main.java.data.entity.Item;
import main.java.misc.InputRestrictor;

import java.net.URL;
import java.util.ResourceBundle;

public class POSNewItem extends POSInventory{

    @FXML
    private StackPane rootPane;

    @FXML
    private TextField tfItemCode;

    @FXML
    private TextField tfItemName;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfInititalStock;

    @FXML
    private Label lblTotalValue;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnAdd;

    private MiscInstances misc ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputRestrictor.limitInput(tfItemCode,13);
        InputRestrictor.numbersInput(tfItemCode);
        InputRestrictor.numbersInput(tfPrice);
        InputRestrictor.numbersInput(tfInititalStock);

        tfItemCode.requestFocus();
        misc = new MiscInstances();

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (codeExist(this.tfItemCode.getText())) {
            POSMessage.showMessage(rootPane, "Item Code Already Exist", "Item Code Error", POSMessage.MessageType.ERROR);
        }else if (hasEmptyField()){
            POSMessage.showMessage(rootPane,"Please fill all the Fields","Invalid Value", POSMessage.MessageType.ERROR);
        }else {
            String sql = "Insert into item(itemCode,itemName,itemPrice,stock)" +
                    " values ('"+tfItemCode.getText()+"'" +
                    ",'"+tfItemName.getText()+"'" +
                    ","+Double.parseDouble(tfPrice.getText())+"" +
                    ","+Integer.parseInt(tfInititalStock.getText())+")";

            misc.dbHandler.startConnection();
            misc.dbHandler.execUpdate(sql);

            POSMessage.showMessage(rootPane,"New Item has been Added"
                    ,"Item Added"
                    , POSMessage.MessageType.INFORM);



            queryAllItems();

            tfItemCode.setText("");
            tfItemName.setText("");
            tfInititalStock.setText("");
            tfPrice.setText("");
            tfItemCode.requestFocus();
        }
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        sceneManipulator.closeDialog();
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

    private boolean hasEmptyField(){
        return tfItemName.getText().equals("") ||
                tfItemCode.getText().equals("") ||
                tfPrice.getText().equals("") ||
                tfInititalStock.getText().equals("");
    }

}
