package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.java.MiscInstances;
import main.java.controller.message.POSMessage;
import main.java.data.entity.Item;
import main.java.misc.BackgroundProcesses;
import main.java.misc.CacheWriter;
import main.java.misc.SceneManipulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class POSInventory implements Initializable, CacheWriter {

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXButton btnHome;

    @FXML
    private TextField tfSearch;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnRestock;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXTreeTableView<Item> ttvCustomer;

    @FXML
    private TreeTableColumn<Item, Integer> chItemID;

    @FXML
    private TreeTableColumn<Item, String> chItemCode;

    @FXML
    private TreeTableColumn<Item, String> chItemName;


    @FXML
    private TreeTableColumn<Item, Double> chUnitPrice;

    @FXML
    private TreeTableColumn<Item,Integer> chStock;


    @FXML
    private TreeTableColumn<Item, Double> chTotal;


    protected static SceneManipulator sceneManipulator= new SceneManipulator();
    protected static MiscInstances misc = new MiscInstances();
    protected static ObservableList<Item> itemList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline clock = new Timeline(new KeyFrame(Duration.millis(300), e -> {
            queryAllItems();
            loadTable();
            BackgroundProcesses.createCacheDir("etc\\cache-user.file");
        }),
                new KeyFrame(Duration.millis(300))
        );
        clock.setCycleCount(1);
        clock.play();

    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
        sceneManipulator.changeScene(rootPane,"POSDashboard","Dashboard");
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }


    @FXML
    void functionButtonOnAction(ActionEvent event) {
            writeToCache("etc\\cache-user.file");
            JFXButton selectedButton = (JFXButton) event.getSource();
            if (selectedButton.equals(this.btnRestock)) {
                if (hasSelectedItem())
                    sceneManipulator.openDialog(rootPane, "POSRestock");
                else
                    POSMessage.showMessage(rootPane,"Please Select from the Table First"
                            ,"No Selected Item", POSMessage.MessageType.ERROR);

            } else if (selectedButton.equals(this.btnNew)) {
                sceneManipulator.openDialog(rootPane, "POSNewItem");

            } else if (selectedButton.equals(this.btnUpdate)) {
                if (hasSelectedItem())
                    sceneManipulator.openDialog(rootPane, "POSItemEdit");
                else
                    POSMessage.showMessage(rootPane,"Please Select from the Table First"
                            ,"No Selected Item", POSMessage.MessageType.ERROR);

            }else if (selectedButton.equals(this.btnDelete)){
                if (hasSelectedItem()){
                    JFXButton btnNo = new JFXButton("No");
                    btnNo.setOnAction(e->POSMessage.closeMessage());

                    JFXButton btnYes = new JFXButton("Yes");
                    btnYes.setOnAction(e->POSMessage.closeMessage());

                    POSMessage.showConfirmationMessage(rootPane,"Do you really want to delete selected\n Item?"
                            ,"No Selected Item", POSMessage.MessageType.ERROR,btnNo,btnYes);
                }

                else
                    POSMessage.showMessage(rootPane,"Please Select from the Table First"
                            ,"No Selected Item", POSMessage.MessageType.ERROR);
            }

    }


    protected static void queryAllItems(){
        itemList.clear();
        String sql = "Select * from Item";
        misc.dbHandler.startConnection();
        ResultSet result = misc.dbHandler.execQuery(sql);
        try{
            Item item;
            while(result.next()){
                item = new Item(result.getInt("itemID")
                        ,result.getString("itemCode")
                        ,result.getString("itemName")
                        ,result.getDouble("itemPrice")
                        ,result.getInt("stock"));
                itemList.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
            misc.dbHandler.closeConnection();
        }
        misc.dbHandler.closeConnection();
    }

    private void loadTable(){
        chItemID.setCellValueFactory(new TreeItemPropertyValueFactory<Item,Integer>("itemID"));
        chItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<Item,String>("itemCode"));
        chItemName.setCellValueFactory(new TreeItemPropertyValueFactory<Item,String>("itemName"));
        chUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<Item,Double>("itemPrice"));
        chStock.setCellValueFactory(new TreeItemPropertyValueFactory<Item,Integer>("stock"));
        chTotal.setCellValueFactory(new TreeItemPropertyValueFactory<Item,Double>("subtotal"));

        TreeItem<Item> dataItem = new RecursiveTreeItem<Item>(itemList, RecursiveTreeObject::getChildren);
        ttvCustomer.setRoot(dataItem);
        ttvCustomer.setShowRoot(false);
    }

     public void writeToCache(String file){
        if (hasSelectedItem()){
            Item selectedItem = ttvCustomer.getSelectionModel().getSelectedItem().getValue();
            String cacheData = "";
            cacheData+=selectedItem.getItemID();
            cacheData+="\n"+selectedItem.getItemCode();
            cacheData+="\n"+selectedItem.getItemName();
            cacheData+="\n"+selectedItem.getItemPrice();
            cacheData+="\n"+selectedItem.getStock();
            cacheData+="\n"+selectedItem.getSubtotal();
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

    private boolean hasSelectedItem(){
        return ttvCustomer.getSelectionModel().getSelectedItem() != null;
    }


    //TODO handling common errors
    //TODO If there is no selected from the table
    //TODO The search feature

}
