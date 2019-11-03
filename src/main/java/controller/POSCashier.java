package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import main.java.misc.InputRestrictor;

import java.net.URL;
import java.util.ResourceBundle;

public class POSCashier implements Initializable {

    /*************************************************/
    /*********** UI COMPONENT VARIABLES **************/
    /*************************************************/
    @FXML
    private StackPane rootPane;

    @FXML
    private JFXTreeTableView<?> ttvOrderList;

    @FXML
    private TreeTableColumn<?, ?> chProduct;

    @FXML
    private TreeTableColumn<?, ?> chUnitPrice;

    @FXML
    private TreeTableColumn<?, ?> chQuantity;

    @FXML
    private TreeTableColumn<?, ?> chTotal;

    @FXML
    private JFXButton btnHome;

    @FXML
    private Label lblDate;

    @FXML
    private ImageView ivAdmin;

    @FXML
    private ImageView ivGsmSignal;

    @FXML
    private ImageView ivRfidSignal;

    @FXML
    private Label lblProductName;

    @FXML
    private Label lblBarcodeNumber;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private JFXButton btnSubtract;

    @FXML
    private TextField tfQuantity;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private JFXButton btnMoreAction;

    @FXML
    private JFXButton btnCalculator;

    @FXML
    private JFXButton btnAddCredits;

    @FXML
    private JFXButton btnRemoveAll;

    @FXML
    private JFXButton btnPriceInquiry;

    @FXML
    private JFXButton btnScanItem;

    @FXML
    private Label lblNumberItem;

    @FXML
    private Label lblSubtotal;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblTax;

    @FXML
    private Label lblTotal;

    @FXML
    private JFXButton btnCheckout;


    /*************************************************/
    /*************** EVENT HANDLERS ******************/
    /*************************************************/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputRestrictor.numbersInput(this.tfQuantity);
    }

    @FXML
    void btnCheckoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnFunctionalitiesOnAction(ActionEvent event) {

    }

    @FXML
    void btnQuantityChangerOnAction(ActionEvent event) {
        if (tfQuantity.getText().isEmpty() && tfQuantity.getText().equals("0"))
            return;

        var x = Integer.parseInt(tfQuantity.getText());
        if (event.getSource().equals(btnAdd))
            x=x+1;
        else if (event.getSource().equals(btnSubtract))
            x=x-1;
        tfQuantity.setText(String.valueOf(x));
    }

    @FXML
    void btnRemove(ActionEvent event) {

    }


}
