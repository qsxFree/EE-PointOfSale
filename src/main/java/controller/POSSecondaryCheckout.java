package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.java.data.entity.ProductOrder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class POSSecondaryCheckout extends POSSecondaryMain{

    @FXML
    private Label lblTypeCount;

    @FXML
    private Label lblNumberItem;

    @FXML
    private Label lblSubtotal;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblTotal;

    @FXML
    private ImageView ivPrompt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainThread.stop();
        checkoutStatusCalculate();
    }
    private void checkoutStatusCalculate(){
        type = 0;
        subTotal = 0.0;
        items = 0;
        total = 0;
        POSSecondaryMain.productList.forEach(e->{
            subTotal = subTotal+(e.getQuantity()*e.getUnitPrice());
            items+=e.getQuantity();
            type++;
        });
        lblTypeCount.setText(type+"");
        lblSubtotal.setText(subTotal+"");
        lblNumberItem.setText(items+"");
        total =discount!=0
                ? subTotal-((subTotal*discount)/100)
                : subTotal;
        lblTotal.setText(total+"");
    }
    private Scanner scan;

}