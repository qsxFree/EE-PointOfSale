package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import main.java.MiscInstances;
import main.java.misc.SceneManipulator;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class POSCustomerAccount implements Initializable {

    @FXML
    protected StackPane rootPane;

    @FXML
    private JFXButton btnHome;

    @FXML
    private TextField tfSearch;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnCardInfo;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXTreeTableView<?> ttvCustomer;

    @FXML
    private TreeTableColumn<?, ?> chCustomerID;

    @FXML
    private TreeTableColumn<?, ?> chCustomerName;

    @FXML
    private TreeTableColumn<?, ?> chAddress;

    @FXML
    private TreeTableColumn<?, ?> chSex;

    @FXML
    private TreeTableColumn<?, ?> chMobileNumber;

    @FXML
    private TreeTableColumn<?, ?> chEmail;

    protected static MiscInstances misc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        misc = new MiscInstances();

        String sql = "Select * from customer";
        misc.dbHandler.startConnection();
        ResultSet result = misc.dbHandler.execQuery(sql);

        try{
            while(result.next()){

            }
        }catch (Exception e){
            e.printStackTrace();
            misc.dbHandler.closeConnection();
        }

    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }


    protected static SceneManipulator sceneManipulator = new SceneManipulator();

    @FXML
    void functionButtonOnAction(ActionEvent event) {

        JFXButton selectedButton = (JFXButton) event.getSource();
        if (selectedButton.equals(btnNew)){
            sceneManipulator.openDialog(rootPane,"POSCustomerAccountForm");


        }else if (selectedButton.equals(this.btnUpdate)){
            sceneManipulator.openDialog(rootPane,"POSCustomerEdit");
        }else if (selectedButton.equals(this.btnCardInfo)){
            sceneManipulator.openDialog(rootPane,"POSSelectedCardInfo");
        }

    }


    protected static void changeScene(){
        sceneManipulator.closeDialog();
        //sceneManipulator.openDialog(rootPane,"POSCardInformation");
    }

}
