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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.java.MiscInstances;
import main.java.controller.message.POSMessage;
import main.java.data.CacheWriter;
import main.java.data.entity.Item;
import main.java.misc.BackgroundProcesses;
import main.java.misc.SceneManipulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class POSInventory implements Initializable, CacheWriter {
    //TODO Restock function is not yet working
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
    private TreeTableColumn<Item, JFXButton> chRestock;

    @FXML
    private TreeTableColumn<Item,HBox> chAction;


    @FXML
    private TreeTableColumn<Item, Double> chTotal;


    protected static SceneManipulator sceneManipulator= new SceneManipulator();
    protected static MiscInstances misc = new MiscInstances();
    protected static ObservableList<Item> itemList = FXCollections.observableArrayList();
    private static ArrayList allItem = new ArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline clock = new Timeline(new KeyFrame(Duration.millis(300), e -> {
            queryAllItems();
            loadTable();
            BackgroundProcesses.createCacheDir("etc\\cache-selected-item.file");
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
        String basis = tfSearch.getText().toLowerCase();
        ArrayList result = new ArrayList() ;
        itemList.stream()
                .filter(e->
                    e.getItemCode().toLowerCase().contains(basis)
                            || e.getItemName().toLowerCase().contains(basis)
                ).forEach(e->
                    result.add(e)
                );
        itemList.clear();
        itemList.addAll(result);

    }


    @FXML
    void functionButtonOnAction(ActionEvent event) {
            try {
                writeToCache("etc\\cache-selected-item.file");
                JFXButton selectedButton = (JFXButton) event.getSource();

                /*
                * FUNCTION BUTTON 1 - Button Restock
                * */
                if (selectedButton.equals(this.btnRestock)) {
                    if (hasSelectedItem())
                        sceneManipulator.openDialog(rootPane, "POSRestock");
                    else
                        POSMessage.showMessage(rootPane, "Please Select from the Table First"
                                , "No Selected Item", POSMessage.MessageType.ERROR);


                /*
                * FUNCTION BUTTON 2 - Button New Item
                * */
                } else if (selectedButton.equals(this.btnNew)) {
                    sceneManipulator.openDialog(rootPane, "POSNewItem");


                /*
                * FUNCTION BUTTON 3 - Button Edit
                * */
                } else if (selectedButton.equals(this.btnUpdate)) {
                    if (hasSelectedItem())
                        sceneManipulator.openDialog(rootPane, "POSItemEdit");
                    else
                        POSMessage.showMessage(rootPane, "Please Select from the Table First"
                                , "No Selected Item", POSMessage.MessageType.ERROR);

                /*
                * FUNCTION BUTTON 4 - Button Delete
                * */
                } else if (selectedButton.equals(this.btnDelete)) {
                    if (hasSelectedItem()) {
                        //When the function is pressed, a confirmation message will appear

                        JFXButton btnNo = new JFXButton("No");// Confirmation button - "No"
                        btnNo.setOnAction(e -> POSMessage.closeMessage());// After pressing the No button, it simply close the messgae

                        JFXButton btnYes = new JFXButton("Yes");// Confirmation button - "Yes"
                        btnYes.setOnAction(e -> {
                            deleteItemFromButtonAction(e);
                        });

                        // Confirmation Message
                        POSMessage.showConfirmationMessage(rootPane, "Do you really want to delete selected\nitem?"
                                , "No Selected Item", POSMessage.MessageType.ERROR, btnNo, btnYes);
                    } else
                        POSMessage.showMessage(rootPane, "Please Select from the Table First"
                                , "No Selected Item", POSMessage.MessageType.ERROR);
                }
            }catch (Exception e){
                e.printStackTrace();
                POSMessage.showMessage(rootPane,e.getMessage(),"System Error", POSMessage.MessageType.ERROR);
            }
    }

    private void deleteItemFromButtonAction(ActionEvent e){
        //Creating query for update
        Item selectedItem = ttvCustomer.getSelectionModel().getSelectedItem().getValue();
        String sql = "Delete from item where itemId = "+selectedItem.getItemID();

        //To update the database
        misc.dbHandler.startConnection();
        misc.dbHandler.execUpdate(sql);
        misc.dbHandler.closeConnection();

        //Closing the Message Dialog
        POSMessage.closeMessage();

        //Requery the table
        queryAllItems();
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
                        ,result.getInt("stock")
                        ,new JFXButton("Modify")
                        ,new HBox());
                item.setManipulator(sceneManipulator);
                item.setMisc(misc);
                itemList.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
            misc.dbHandler.closeConnection();
        }
        misc.dbHandler.closeConnection();
        allItem.clear();
        allItem.addAll(itemList);
    }

    private void loadTable(){
        chItemID.setCellValueFactory(new TreeItemPropertyValueFactory<Item,Integer>("itemID"));
        chItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<Item,String>("itemCode"));
        chItemName.setCellValueFactory(new TreeItemPropertyValueFactory<Item,String>("itemName"));
        chUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<Item,Double>("itemPrice"));
        chStock.setCellValueFactory(new TreeItemPropertyValueFactory<Item,Integer>("stock"));
        chTotal.setCellValueFactory(new TreeItemPropertyValueFactory<Item,Double>("subtotal"));
        chRestock.setCellValueFactory(new TreeItemPropertyValueFactory<Item,JFXButton>("btnRestock"));
        chAction.setCellValueFactory(new TreeItemPropertyValueFactory<Item,HBox>("hbActionContainer"));

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

    @FXML
    private void tfSearchOnKeyReleased(KeyEvent keyEvent) {
        String basis = tfSearch.getText().toLowerCase();
        ArrayList result = new ArrayList() ;
        itemList.clear();
        itemList.addAll(allItem);
        if (!basis.equals("")){
            itemList.parallelStream()
                    .filter(e->
                            e.getItemCode().toLowerCase().contains(basis)
                                    || e.getItemName().toLowerCase().contains(basis)
                    ).forEach(e->
                    result.add(e)
            );
            itemList.clear();
            itemList.addAll(result);
        }
        System.gc();
    }


}
