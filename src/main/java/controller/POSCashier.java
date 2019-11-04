package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.java.data.entity.ProductOrder;
import main.java.misc.InputRestrictor;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class POSCashier implements Initializable {

    /*************************************************/
    /*********** UI COMPONENT VARIABLES **************/
    /*************************************************/
    @FXML
    private StackPane rootPane;

    @FXML
    private JFXTreeTableView<ProductOrder> ttvOrderList;

    @FXML
    private TreeTableColumn<ProductOrder, String> chProduct;

    @FXML
    private TreeTableColumn<ProductOrder, String> chProductID;


    @FXML
    private TreeTableColumn<ProductOrder, Double> chUnitPrice;

    @FXML
    private TreeTableColumn<ProductOrder, Integer> chQuantity;

    @FXML
    private TreeTableColumn<ProductOrder, Double> chTotal;

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
    /****************** VARIABLES ********************/
    /*************************************************/

    private ObservableList<ProductOrder> productList = FXCollections.observableArrayList(
            new ProductOrder("324578126572-21","KOPIKO BROWN",60.0,2,120.0),
            new ProductOrder("123456789124-22","GREATASTE WHITE",56.0,2,112.0),
            new ProductOrder("123456789125-23","NESCAFE ORIGINAL",62.0,1,62.0),
            new ProductOrder("123456791245-24","KOPIKO BLACK",58.0,4,232.0)
    );

    private double total = 0;
    private int items = 0;
    private ProductOrder selectedProduct = null;
    /*************************************************/
    /*************** EVENT HANDLERS ******************/
    /*************************************************/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputRestrictor.numbersInput(this.tfQuantity);
        realTimeClock();
        chProductID.setCellValueFactory(new TreeItemPropertyValueFactory<ProductOrder,String>("productID"));
        chProduct.setCellValueFactory(new TreeItemPropertyValueFactory<ProductOrder,String>("product"));
        chUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<ProductOrder,Double>("unitPrice"));
        chQuantity.setCellValueFactory(new TreeItemPropertyValueFactory<ProductOrder,Integer>("quantity"));
        chTotal.setCellValueFactory(new TreeItemPropertyValueFactory<ProductOrder,Double>("total"));

        TreeItem <ProductOrder>dataItem = new RecursiveTreeItem<ProductOrder>(productList, RecursiveTreeObject::getChildren);
        ttvOrderList.setRoot(dataItem);
        ttvOrderList.setShowRoot(false);

        countTotal();
        countItems();
    }

    @FXML
    void btnCheckoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnFunctionalitiesOnAction(ActionEvent event) {

    }

    @FXML
    void btnQuantityChangerOnAction(ActionEvent event) {
        if (tfQuantity.getText().isEmpty())
            return;
        else if (tfQuantity.getText().equals("0") && event.getSource().equals(btnSubtract))
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
        String id[] =lblBarcodeNumber.getText().split(": ");
        if (id.length>1){
            productList.forEach(e->{
                if (e.getProductID().equals(id[1]))
                    selectedProduct = e;
            });
            productList.remove(selectedProduct);
        }
    }

    @FXML
    void ttvOrderOnKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN)
            populateProductInformation();
    }

    @FXML
    void ttvOrderOnMouseClicked(MouseEvent event) {
        populateProductInformation();

    }


    /*************************************************/
    /*********** FUNCTIONS AND PROCEDURES ************/
    /*************************************************/

    private void realTimeClock(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime currentTime = LocalDateTime.now();
            lblDate.setText(currentTime.format(DateTimeFormatter.ofPattern("hh:mm a EEE, MMM dd, yyyy")));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void populateProductInformation(){
        var treeItem = ttvOrderList.getSelectionModel().getSelectedItem();
        if (treeItem!=null){
            var prod = treeItem.getValue();
            lblProductName.setText("Product: "+prod.getProduct());
            lblBarcodeNumber.setText("Product ID: "+prod.getProductID());
            lblUnitPrice.setText("Unit Price: "+prod.getUnitPrice());
            tfQuantity.setText(prod.getQuantity()+"");
        }
    }


    private void countTotal(){
        productList.forEach((e)->{
            total+=e.getTotal();
        });

        lblTotal.setText(total+"");
    }

    private void countItems(){
        productList.forEach((e)->{
            items+=e.getQuantity();
        });
        lblNumberItem.setText(items+"");
    }

}
