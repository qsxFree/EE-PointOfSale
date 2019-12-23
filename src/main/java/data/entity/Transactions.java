package main.java.data.entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import main.java.misc.SceneManipulator;

public class Transactions extends RecursiveTreeObject<Transactions> {
    private int transactionID;
    private String type;
    private String user,customer;
    private int typeID;
    private String date,time;
    private JFXButton btnView;
    private SceneManipulator manipulator;

    public Transactions(int transactionID, String type, String user, String customer, int typeID, String date, String time, JFXButton btnView) {
        this.transactionID = transactionID;
        this.type = type;
        this.user = user;
        this.customer = customer;
        this.typeID = typeID;
        this.date = date;
        this.time = time;
        this.btnView = btnView;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public JFXButton getBtnView() {
        return btnView;
    }

    public void setBtnView(JFXButton btnView) {
        this.btnView = btnView;
    }

    public SceneManipulator getManipulator() {
        return manipulator;
    }

    public void setManipulator(SceneManipulator manipulator) {
        this.manipulator = manipulator;
    }
}
