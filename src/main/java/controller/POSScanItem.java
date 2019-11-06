package main.java.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import main.java.data.entity.ProductOrder;
import main.java.misc.InputRestrictor;

import java.net.URL;
import java.util.ResourceBundle;

public class POSScanItem implements Initializable {


    @FXML
    private StackPane rootPane;

    @FXML
    private TextField tfBarcode;

    @FXML
    private Label lblID;

    @FXML
    private Label lblProduct;

    @FXML
    private Label lblBarcode;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblQuantity;

    @FXML
    private TextField tbQuantity;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnAdd;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputRestrictor.numbersInput(tbQuantity);
        InputRestrictor.limitInput(tbQuantity,3);
        InputRestrictor.limitInput(tfBarcode,12);
        InputRestrictor.numbersInput(tfBarcode);

        if (rootPane.isFocused()) tfBarcode.requestFocus();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        //String productNumber = lblBarcode.getText().split(": ")[1] +"-"+lblID.getText().split(": ")[1];
        POSCashier.addItemToList(new ProductOrder("213456789124-26","Kokipo Blanca",60.0,2,120.0));
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        POSCashier.sceneManipulator.closeDialog();//get the scenemanipulator from
                                                    // the parent scene and call the closeDialog
    }

    @FXML
    void tfBarcodeOnKeyReleased(KeyEvent event) {
        if (tfBarcode.getText().length()==12){
            //code here
        }
    }


}
