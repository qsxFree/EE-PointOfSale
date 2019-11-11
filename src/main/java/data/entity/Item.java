package main.java.data.entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item extends RecursiveTreeObject<Item> {
    private SimpleIntegerProperty itemID;
    private SimpleStringProperty itemCode;
    private SimpleStringProperty itemName;
    private SimpleDoubleProperty itemPrice;
    private SimpleIntegerProperty stock;
    private SimpleDoubleProperty subtotal;

    public Item(int itemID, String itemCode, String itemName, double itemPrice,int stock) {
        this.itemID = new SimpleIntegerProperty(itemID);
        this.itemCode = new SimpleStringProperty(itemCode);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemPrice = new SimpleDoubleProperty(itemPrice);
        this.stock = new SimpleIntegerProperty(stock);
        this.subtotal = new SimpleDoubleProperty(calculateTotal(this.itemPrice,this.stock));
    }

    private double calculateTotal(SimpleDoubleProperty itemPrice,SimpleIntegerProperty stock){
        return itemPrice.get()*stock.get();
    }

    public int getItemID() {
        return itemID.get();
    }

    public SimpleIntegerProperty itemIDProperty() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID.set(itemID);
    }

    public String getItemCode() {
        return itemCode.get();
    }

    public SimpleStringProperty itemCodeProperty() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode.set(itemCode);
    }

    public String getItemName() {
        return itemName.get();
    }

    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public double getItemPrice() {
        return itemPrice.get();
    }

    public SimpleDoubleProperty itemPriceProperty() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice.set(itemPrice);
    }

    public int getStock() {
        return stock.get();
    }

    public SimpleIntegerProperty stockProperty() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public void setSubtotal(double subtotal) {
        this.subtotal.set(subtotal);
    }

    public double getSubtotal() {
        return subtotal.get();
    }


}
